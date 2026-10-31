package ferramentas.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.util.List;

public record ToolReqDTO(@NotBlank String title, @NotBlank @URL String link, @NotNull String description, @Size(min = 1)List<String> tags) {
}
