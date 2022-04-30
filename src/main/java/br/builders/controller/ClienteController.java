package br.builders.controller;

import br.builders.controller.api.ClienteRestController;
import br.builders.service.ClienteService;
import br.builders.service.dto.ClienteDTO;
import br.builders.service.dto.FiltroClienteDTO;
import br.builders.service.dto.PaginacaoDTO;
import io.undertow.server.protocol.http.HttpServerConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;



@RequiredArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController implements ClienteRestController {

    private final ClienteService clienteService;

//    @Override
//    public ResponseEntity<ClienteDTO> getClientePorId(final Long id) {
////        ClienteDTO cliente = clienteService.getClientePorId(id);
//        return ResponseEntity.ok(null);
//    }

    @Override
    public ResponseEntity<Page<ClienteDTO>> getClientesPorFiltro(final Map<String, Object> parametros) {
        Page<ClienteDTO> lista = clienteService.getClientesPorFiltro(parametros);
        return ResponseEntity.ok(lista);
    }

    @Override
    public ResponseEntity<Void> salvar(@RequestBody @Valid  final ClienteDTO clienteDTO) {
        clienteService.salvarOuAtualizar(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> alterar(@RequestBody @Valid  final ClienteDTO clienteDTO) {
        clienteService.salvarOuAtualizar(clienteDTO);
        return ResponseEntity.ok().build();
    }
}
