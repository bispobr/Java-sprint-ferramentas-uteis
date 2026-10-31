package ferramentas.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ferramentas.dto.response.PageResponseDTO;

import java.util.List;

public class ResponseBuilder {

    private ResponseBuilder() {}



    public static <T> ResponseEntity<ResponseApi<T>> ok(T data) {
        return build(data, "Operação realizada com sucesso", HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseApi<T>> ok(T data, String message) {
        return build(data, message, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseApi<T>> created(T data) {
        return build(data, "Recurso criado com sucesso", HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<ResponseApi<T>> created(T data, String message) {
        return build(data, message, HttpStatus.CREATED);
    }

    public static ResponseEntity<ResponseApi<Void>> noContent() {
        ResponseApi<Void> response = new ResponseApi.Builder<Void>()
                .success(true)
                .message("Operação realizada com sucesso")
                .status(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }


    public static <T> ResponseEntity<ResponseApi<PagedResponse<T>>> pagedFromDTO(PageResponseDTO<T> dto) {
        return pagedFromDTO(dto, "Consulta realizada com sucesso");
    }

    public static <T> ResponseEntity<ResponseApi<PagedResponse<T>>> pagedFromDTO(PageResponseDTO<T> dto, String message) {
        PagedResponse<T> pagedResponse = new PagedResponse<>(
                dto.content(),
                dto.pageNumber(),
                dto.pageSize(),
                dto.totalElements(),
                dto.totalPages(),
                dto.last(),
                dto.pageNumber() == 0
        );

        ResponseApi<PagedResponse<T>> response = new ResponseApi.Builder<PagedResponse<T>>()
                .success(true)
                .message(message)
                .data(pagedResponse)
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(response);
    }



    public static <T> ResponseEntity<ResponseApi<T>> error(String message, HttpStatus status) {
        ResponseApi<T> response = new ResponseApi.Builder<T>()
                .success(false)
                .message(message)
                .status(status.value())
                .build();
        return ResponseEntity.status(status).body(response);
    }

    public static <T> ResponseEntity<ResponseApi<T>> error(String message, List<FieldErrorDetail> errors, HttpStatus status) {
        ResponseApi<T> response = new ResponseApi.Builder<T>()
                .success(false)
                .message(message)
                .errors(errors)
                .status(status.value())
                .build();
        return ResponseEntity.status(status).body(response);
    }



    private static <T> ResponseEntity<ResponseApi<T>> build(T data, String message, HttpStatus status) {
        ResponseApi<T> response = new ResponseApi.Builder<T>()
                .success(true)
                .message(message)
                .data(data)
                .status(status.value())
                .build();
        return ResponseEntity.status(status).body(response);
    }
}

