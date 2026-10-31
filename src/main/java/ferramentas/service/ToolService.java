package ferramentas.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

import java.util.List;

@Slf4j
@Service
public class ToolService {

    @Autowired
    ToolRepository ToolRepository;

    @Autowired
    ToolMapper toolMapper;

    @Autowired
    BusinessEventLogger businessEventLogger;

    @Autowired
    PerformanceLogger performanceLogger;

    @Autowired
    ErrorLogger errorLogger;

    @Autowired
    PasswordEncoder passwordEncoder;

    @CachePut(value = "tool", key = "#result.id")
    public ToolRespDTO create(ToolReqDTO dto) {

        long start = System.currentTimeMillis();

        Tool tool = toolMapper.toEntity(dto);
        Tool saved = ToolRepository.save(tool);
        log.info("tool id {} saved",saved.getId());

        businessEventLogger.logEvent("Ferrameta_Cadastrado","Tool",saved.getId(),"SUCESSO");
        long duration = System.currentTimeMillis() - start;
        performanceLogger.logExecution("Ferrameta_Cadastrado",duration);

        return toolMapper.toResponse(saved);
    }

    @Cacheable(value = "tool", key = "#id")
    public ToolRespDTO findById(long id){

        long start = System.currentTimeMillis();

        Tool tool = ToolRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        businessEventLogger.logEvent("Busca_ferramenta_PorID","Tool", tool.getId(), "SUCESSO");
        long duration = System.currentTimeMillis() - start;
        performanceLogger.logExecution("Busca_ferramenta_PorID",duration);

        return toolMapper.toResponse(tool);
    }



    public List<ToolRespDTO> list(List<String> tags) {

        long start = System.currentTimeMillis();

        if (tags != null && !tags.isEmpty()) {
            List<String> tagsLower = tags.stream()
                    .map(String::toLowerCase)
                    .toList();

            businessEventLogger.logEvent("Busca_ferramenta_PorTag","Tool", null, "SUCESSO");
            long duration = System.currentTimeMillis() - start;
            performanceLogger.logExecution("Busca_ferramenta_PorTag",duration);

            return toolMapper.toListResponse(ToolRepository.findByTagsIgnoreCase(tagsLower));
        }
        return toolMapper.toListResponse(ToolRepository.findAll());
    }

    public PageResponseDTO<ToolRespDTO> listpaginated(int page, int size) {

        long start = System.currentTimeMillis();

        Page<Tool> pageResult = ToolRepository.findAll(PageRequest.of(page, size, Sort.by("id")));
        Page<ToolRespDTO> dtoPage = pageResult.map(toolMapper::toResponse);
        log.info("Paginated listing Completed");

        businessEventLogger.logEvent("Lista_ferramenta_Paginado","tool",null,"SUCESSO");
        long duration = System.currentTimeMillis() - start;
        performanceLogger.logExecution("Lista_ferramenta_Paginado",duration);

        return PageResponseDTO.of(dtoPage);
    }


    @CachePut(value = "tool", key = "#result.id")
    public ToolRespDTO update (long id, ToolUpdateDTO dto) {

        long start = System.currentTimeMillis();

        Tool tool = ToolRepository.findById(id).orElseThrow(EntityNotFoundException::new);


        tool.setTitle(dto.title() != null ? dto.title() : tool.getTitle());
        tool.setLink(dto.link() != null ? dto.link() : tool.getLink());
        tool.setDescription(dto.description() != null ? dto.description() : tool.getDescription());


        businessEventLogger.logEvent("ferramenta_Atualização","Usuario", tool.getId(), "SUCESSO");
        long duration = System.currentTimeMillis() - start;
        performanceLogger.logExecution("ferramenta_Atualização",duration);

        return toolMapper.toResponse(ToolRepository.save(tool));
    }



    @CacheEvict(value = "tool", key = "#id")
    public void delete(Long id) {

        long start = System.currentTimeMillis();

        ToolRepository.deleteById(ToolRepository.findById(id).orElseThrow(EntityNotFoundException::new).getId());

        businessEventLogger.logEvent("ferramenta_remoção","tool",id,"SUCESSO");
        long duration = System.currentTimeMillis() - start;
        performanceLogger.logExecution("ferramenta_remoção",duration);
    }
}
