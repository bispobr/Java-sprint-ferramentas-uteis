package ferramentas.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ferramentas.controller.ToolController;
import ferramentas.dto.response.PageResponseDTO;
import ferramentas.dto.request.ToolReqDTO;
import ferramentas.dto.response.ToolRespDTO;
import ferramentas.dto.request.ToolUpdateDTO;
import ferramentas.service.ToolService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ToolControllerTest {

    @Mock
    private ToolService toolService;


    @InjectMocks
    private ToolController toolController;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;


    private ToolReqDTO buildReq() {
        return new ToolReqDTO(
                "Tool",
                "http://link",
                "desc",
                List.of("java", "node")
        );
    }

    private ToolRespDTO buildResp(Long id) {
        return new ToolRespDTO(
                "Tool",
                "http://link",
                "desc",
                List.of("java", "node"),
                id
        );
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(toolController).build();
    }

    @Test
    void create_dadosValidos_retorna201() throws Exception {
        ToolReqDTO req = buildReq();
        ToolRespDTO resp = buildResp(1L);

        when(toolService.create(any())).thenReturn(resp);

        mockMvc.perform(post("/tools")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.message").value("Ferramenta cadastrada com sucesso"));
    }

    @Test
    void create_dadosInvalidos_retorna400() throws Exception {
        ToolReqDTO req = new ToolReqDTO(null, null, null, null);

        mockMvc.perform(post("/tools")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void list_semTags_retorna200() throws Exception {
        when(toolService.list(null)).thenReturn(List.of(buildResp(1L)));

        mockMvc.perform(get("/tools"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }


    @Test
    void list_comTags_retorna200() throws Exception {
        when(toolService.list(List.of("java", "node")))
                .thenReturn(List.of(buildResp(1L)));

        mockMvc.perform(get("/tools")
                        .param("tag", "java")
                        .param("tag", "node"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void paginatedlisting_parametrosValidos_retorna200() throws Exception {
        PageResponseDTO<ToolRespDTO> page =
                PageResponseDTO.of(new PageImpl<>(List.of(buildResp(1L))));

        when(toolService.listpaginated(0, 10)).thenReturn(page);

        mockMvc.perform(get("/tools/paginated"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content[0].id").value(1L));
    }

    @Test
    void update_idExistente_retorna200() throws Exception {
        ToolRespDTO resp = buildResp(1L);

        when(toolService.update(eq(1L), any())).thenReturn(resp);

        mockMvc.perform(put("/tools/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new ToolUpdateDTO("Novo", null, null)
                        )))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void update_idInexistente_retorna404() throws Exception {
        when(toolService.update(eq(99L), any()))
                .thenThrow(EntityNotFoundException.class);


        assertThrows(ServletException.class, () -> {
            mockMvc.perform(put("/tools/99")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(
                                    new ToolUpdateDTO("Novo", null, null)
                            )))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.success").value(false));;

        });

    }

    @Test
    void delete_idExistente_retorna204() throws Exception {
        doNothing().when(toolService).delete(1L);

        mockMvc.perform(delete("/tools/1"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void delete_idInexistente_retorna404() throws Exception {
        doThrow(EntityNotFoundException.class)
                .when(toolService).delete(99L);

        assertThrows(ServletException.class, () -> {
            mockMvc.perform(delete("/tools/99"))
                    .andExpect(status().isNotFound());

        });


    }


}