package ferramentas.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import ferramentas.model.authentication.Role;

public record RegisterUserRequest(@NotEmpty(message = "Nome é obrigatório") String name, @Email @NotEmpty(message = "E-mail é obrigatório") String email, @NotEmpty(message = "Senha é obrigatória") String password, Role role) {
}