package ferramentas.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Detalhe de um erro de campo")
@Getter
public class FieldErrorDetail {

    @Schema(description = "Campo que gerou o erro")
    private final String field;

    @Schema(description = "Mensagem do erro")
    private final String message;

    @Schema(description = "Valor rejeitado")
    private final Object rejectedValue;

    public FieldErrorDetail(String field, String message, Object rejectedValue) {
        this.field         = field;
        this.message       = message;
        this.rejectedValue = rejectedValue;
    }
}
