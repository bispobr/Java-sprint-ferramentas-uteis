package ferramentas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ferramentas.dto.request.ToolReqDTO;
import ferramentas.dto.response.ToolRespDTO;
import ferramentas.model.tool.Tool;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ToolMapper {

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    Tool toEntity (ToolReqDTO dto);

    ToolRespDTO toResponse (Tool tool);

    List<ToolRespDTO> toListResponse (List<Tool> list);
}
