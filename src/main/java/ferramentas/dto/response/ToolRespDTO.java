package ferramentas.dto.response;

import java.util.List;

public record ToolRespDTO(String title, String link, String description, List<String> tags,long id) {
}
