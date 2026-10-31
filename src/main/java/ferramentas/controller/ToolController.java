package ferramentas.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ferramentas.dto.request.ToolReqDTO;
import ferramentas.dto.response.ToolRespDTO;
import ferramentas.dto.request.ToolUpdateDTO;
import ferramentas.response.PagedResponse;
import ferramentas.response.ResponseApi;
import ferramentas.response.ResponseBuilder;
import ferramentas.service.ToolService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tools")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "tools", description = "Endpoints para registro  de ferramentas")

public class ToolController {
    @Autowired
    ToolService service;


    @PostMapping
    @Operation(summary = "Cadatrar ferramenta", description = "Endpoint responsável por cadastra ferramentas")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "ferramenta cadastrada com sucesso",
                    content = @Content(schema = @Schema(implementation = ToolReqDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos campos",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Credenciais de autenticação inválidas",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Requisição não autorizada",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Erro interno",
                    content = @Content(schema = @Schema(hidden = true)))
    })

    public ResponseEntity<ResponseApi<ToolRespDTO>> create(@RequestBody @Valid ToolReqDTO dto) {

        return ResponseBuilder.created(service.create(dto),"Ferramenta cadastrada com sucesso");
    }


    @GetMapping
    @Operation(summary = "Listar ferramenta", description = "Endpoint responsável por listar ferramentas por tags")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listagem  concluída com sucesso",
                    content = @Content(schema = @Schema(implementation = ToolReqDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos campos",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Credenciais de autenticação inválidas",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Requisição não autorizada",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Erro interno",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<ResponseApi<List<ToolRespDTO>>> list( @RequestParam(value = "tag", required = false) List<String> tags) {
        return ResponseBuilder.ok(service.list(tags));
    }



    @GetMapping("/paginated")
    @Operation(summary = "Listar ferramentas", description = "Endpoint responsável por listar ferramentas  com paginação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de ferramentas retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = ToolRespDTO.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais de autenticação inválidas",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Requisição não autorizada",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Erro interno",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<ResponseApi<PagedResponse<ToolRespDTO>>> paginatedlisting(@RequestParam(defaultValue = "0") int page,
                                                                                    @RequestParam(defaultValue = "10") int size) {
        return ResponseBuilder.pagedFromDTO(service.listpaginated(page, size));
    }



    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ferramenta", description = "Endpoint responsável por atualizar ferramenta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ferramenta atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ToolRespDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos campos",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Credenciais de autenticação inválidas",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Requisição não autorizada",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "ferramenta não encontrado",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Erro interno",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<ResponseApi<ToolRespDTO>> update(@PathVariable  Long id , @RequestBody ToolUpdateDTO dto){
        log.info("Request to update data received.");
        return ResponseBuilder.ok(service.update(id,dto));
    }




    @DeleteMapping("/{id}")
    @Operation(summary = "Remover ferramenta", description = "Endpoint responsável por remover ferramenta )")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "ferramenta removido com sucesso",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Credenciais de autenticação inválidas",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Requisição não autorizada",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "ferramenta não encontrado",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Erro interno",
                    content = @Content(schema = @Schema(hidden = true)))
    })

    public  ResponseEntity<ResponseApi<Void>>delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseBuilder.noContent();
    }
}