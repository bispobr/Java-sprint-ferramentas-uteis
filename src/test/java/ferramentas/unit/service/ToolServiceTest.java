package ferramentas.unit.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ferramentas.dto.response.PageResponseDTO;
import ferramentas.dto.request.ToolReqDTO;
import ferramentas.dto.response.ToolRespDTO;
import ferramentas.dto.request.ToolUpdateDTO;
import ferramentas.loggin.logger.BusinessEventLogger;
import ferramentas.loggin.logger.ErrorLogger;
import ferramentas.loggin.logger.PerformanceLogger;
import ferramentas.mapper.ToolMapper;
import ferramentas.model.tool.Tool;
import ferramentas.repository.ToolRepository;
import ferramentas.service.ToolService;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ToolServiceTest {

    @Mock
    private ToolRepository toolRepository;

    @Mock
    private BusinessEventLogger businessEventLogger;

    @Mock
    private PerformanceLogger performanceLogger;

    @Mock
    private ErrorLogger errorLogger;


    @Mock
    private ToolMapper toolMapper;


    @InjectMocks
    private ToolService toolService;

    private Tool buildTool(Long id) {
        Tool tool = new Tool();
        tool.setId(id);
        tool.setTitle("Tool");
        tool.setLink("http://link");
        tool.setDescription("desc");
        return tool;
    }

    private ToolReqDTO buildReq() {
        return new ToolReqDTO("Tool", "http://link", "desc", List.of("java"));
    }

    private ToolRespDTO buildResp(Long id) {
        return new ToolRespDTO( "Tool", "http://link", "desc", List.of("java"),id);
    }


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_dadosValidos_toolCriadaComSucesso() {
        ToolReqDTO req = buildReq();
        Tool tool = buildTool(null);
        Tool saved = buildTool(1L);
        ToolRespDTO resp = buildResp(1L);

        when(toolMapper.toEntity(req)).thenReturn(tool);
        when(toolRepository.save(tool)).thenReturn(saved);
        when(toolMapper.toResponse(saved)).thenReturn(resp);

        ToolRespDTO result = toolService.create(req);

        assertNotNull(result);
        assertEquals(1L, result.id());

        verify(toolRepository).save(tool);
        verify(businessEventLogger).logEvent(any(), any(), any(), any());
        verify(performanceLogger).logExecution(eq("Ferrameta_Cadastrado"), anyLong());
    }

    @Test
    void findById_idExistente_retornaTool() {
        Tool tool = buildTool(1L);
        ToolRespDTO resp = buildResp(1L);

        when(toolRepository.findById(1L)).thenReturn(Optional.of(tool));
        when(toolMapper.toResponse(tool)).thenReturn(resp);

        ToolRespDTO result = toolService.findById(1L);

        assertEquals(1L, result.id());

        verify(businessEventLogger).logEvent(any(), any(), any(), any());
        verify(performanceLogger).logExecution(eq("Busca_ferramenta_PorID"), anyLong());
    }

    @Test
    void findById_idInexistente_lancaEntityNotFoundException() {
        when(toolRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> toolService.findById(99L));
    }

    @Test
    void list_comTags_retornaListaFiltrada() {
        List<String> tags = List.of("JAVA");
        List<Tool> tools = List.of(buildTool(1L));
        List<ToolRespDTO> resp = List.of(buildResp(1L));

        when(toolRepository.findByTagsIgnoreCase(List.of("java"))).thenReturn(tools);
        when(toolMapper.toListResponse(tools)).thenReturn(resp);

        List<ToolRespDTO> result = toolService.list(tags);

        assertEquals(1, result.size());

        verify(toolRepository).findByTagsIgnoreCase(List.of("java"));
        verify(businessEventLogger).logEvent(any(), any(), any(), any());
        verify(performanceLogger).logExecution(eq("Busca_ferramenta_PorTag"), anyLong());
    }

    @Test
    void list_semTags_retornaTodos() {
        List<Tool> tools = List.of(buildTool(1L));
        List<ToolRespDTO> resp = List.of(buildResp(1L));

        when(toolRepository.findAll()).thenReturn(tools);
        when(toolMapper.toListResponse(tools)).thenReturn(resp);

        List<ToolRespDTO> result = toolService.list(null);

        assertEquals(1, result.size());

        verify(toolRepository).findAll();


    }

    @Test
    void listpaginated_parametrosValidos_retornaPagina() {
        Tool tool = buildTool(1L);
        Page<Tool> page = new PageImpl<>(List.of(tool));
        ToolRespDTO resp = buildResp(1L);

        when(toolRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(toolMapper.toResponse(tool)).thenReturn(resp);

        PageResponseDTO<ToolRespDTO> result = toolService.listpaginated(0, 10);

        assertEquals(1, result.totalElements());

        verify(businessEventLogger).logEvent(any(), any(), any(), any());
        verify(performanceLogger).logExecution(eq("Lista_ferramenta_Paginado"), anyLong());
    }

    @Test
    void update_idExistente_atualizaCamposInformados() {
        Tool tool = buildTool(1L);
        ToolUpdateDTO dto;
        dto = new ToolUpdateDTO("Novo", null, "desc");
        ToolRespDTO resp = buildResp(1L);

        when(toolRepository.findById(1L)).thenReturn(Optional.of(tool));
        when(toolRepository.save(tool)).thenReturn(tool);
        when(toolMapper.toResponse(tool)).thenReturn(resp);

        ToolRespDTO result = toolService.update(1L, dto);

        assertEquals(1L, result.id());
        assertEquals("Novo", tool.getTitle());

        verify(businessEventLogger).logEvent(any(), any(), any(), any());
        verify(performanceLogger).logExecution(eq("ferramenta_Atualização"), anyLong());
    }

    @Test
    void update_idInexistente_lancaEntityNotFoundException() {
        when(toolRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> toolService.update(99L, new ToolUpdateDTO("Novo", "https://linknovo.com", "desc")));
    }

    @Test
    void delete_idExistente_removeComSucesso() {
        Tool tool = buildTool(1L);

        when(toolRepository.findById(1L)).thenReturn(Optional.of(tool));

        toolService.delete(1L);

        verify(toolRepository).deleteById(1L);

        verify(businessEventLogger).logEvent(any(), any(), any(), any());
        verify(performanceLogger).logExecution(eq("ferramenta_remoção"), anyLong());

    }

    @Test
    void delete_idInexistente_lancaEntityNotFoundException() {
        when(toolRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> toolService.delete(99L));
    }

}