package ferramentas.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Envelope de resposta paginada")
@Getter
public class PagedResponse<T> {

    @Schema(description = "Lista de itens da página atual")
    private final List<T> content;

    @Schema(description = "Número da página atual (base 0)")
    private final int page;

    @Schema(description = "Tamanho da página")
    private final int size;

    @Schema(description = "Total de elementos")
    private final long totalElements;

    @Schema(description = "Total de páginas")
    private final int totalPages;

    @Schema(description = "É a última página?")
    private final boolean last;

    @Schema(description = "É a primeira página?")
    private final boolean first;


    public static <T> PagedResponse<T> of(Page<T> page) {
        return new PagedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst()
        );
    }

    public PagedResponse(List<T> content, int page, int size, long totalElements, int totalPages, boolean last, boolean first) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
        this.first = first;
    }


}

