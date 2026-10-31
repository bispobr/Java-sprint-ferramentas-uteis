package ferramentas.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.slf4j.MDC;

import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ResponseApi<T> {


    private final boolean success;
    private final String message;
    private final T data;
    private final List<FieldErrorDetail> errors;
    private final int status;
    private final Instant timestamp;
    private final String traceId;

    private ResponseApi(Builder<T> builder) {
        this.success   = builder.success;
        this.message   = builder.message;
        this.data      = builder.data;
        this.errors    = builder.errors;
        this.status    = builder.status;
        this.timestamp = Instant.now();
        this.traceId   = MDC.get("correlationId");
    }


    public static class Builder<T> {
        private boolean success;
        private String message;
        private T data;
        private List<FieldErrorDetail> errors;
        private int status;

        public Builder<T> success(boolean success)             { this.success = success; return this; }
        public Builder<T> message(String message)              { this.message = message; return this; }
        public Builder<T> data(T data)                         { this.data = data; return this; }
        public Builder<T> errors(List<FieldErrorDetail> errors){ this.errors = errors; return this; }
        public Builder<T> status(int status)                   { this.status = status; return this; }
        public ResponseApi<T> build()                          { return new ResponseApi<>(this); }
    }


}
