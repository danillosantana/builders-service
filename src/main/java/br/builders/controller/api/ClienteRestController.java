package br.builders.controller.api;

import br.builders.service.dto.ClienteDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public interface ClienteRestController {

//    @GetMapping("/{id}")
//    @Operation(summary  = "Permite bsucar o cliente pelo id informado.")
//    @ApiResponse(content = {  @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))})
//    ResponseEntity<ClienteDTO> getClientePorId(@PathVariable("id") final Long id);

    @GetMapping
    @Operation(summary = "Permita que seja possível listar os clientes de forma paginada.")
    ResponseEntity<Page<ClienteDTO>> getClientesPorFiltro(@RequestParam final Map<String, Object> parametros);

    @PostMapping
    @Operation( summary =  "Permita criação de novos clientes. Obs: Formato da data de nascimento dd/MM/yyyy e o telefone apenas números")
    ResponseEntity<Void> salvar(@RequestBody final ClienteDTO clienteDTO);

    @Operation( summary = "Permita a atualização de clientes existentes. Obs: Formado da data de nascimento dd/MM/yyyy e o telefone apenas números")
    @PutMapping
    ResponseEntity<Void> alterar(@RequestBody final ClienteDTO clienteDTO);
}
