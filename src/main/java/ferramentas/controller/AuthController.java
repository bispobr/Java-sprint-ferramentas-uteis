package ferramentas.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ferramentas.dto.request.LoginRequest;
import ferramentas.dto.request.RegisterUserRequest;
import ferramentas.dto.response.LoginResponse;
import ferramentas.dto.response.RegisterUserResponse;
import ferramentas.response.ResponseApi;
import ferramentas.response.ResponseBuilder;
import ferramentas.service.AuthService;


@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints de autenticação e registro de usuários")
public class AuthController {



    @Autowired
    AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário", description = "Endpoint responsável por autenticar o usuário e retornar o token JWT")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Token obtido com sucesso",
            content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos campos",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Usuario inexistente ou senha invalida",
                    content = @Content(schema = @Schema(hidden = true))),

    })

    public  ResponseEntity<ResponseApi<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {

        return ResponseBuilder.ok(authService.Login(request),"Login realizado com sucesso");
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar usuário", description = "Endpoint responsável por realizar o registro de novos usuários")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = RegisterUserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos campos",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "409", description = "E-mail já cadastrado",
                    content = @Content(schema = @Schema(hidden = true)))
    })

    public ResponseEntity<ResponseApi<RegisterUserResponse>> register(@Valid @RequestBody RegisterUserRequest request) {

        return ResponseBuilder.created(authService.Register(request),"Usuário registrado com sucesso");

    }

}



