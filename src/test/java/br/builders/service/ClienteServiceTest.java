package br.builders.service;

import br.builders.entities.Cliente;
import br.builders.exception.ClienteException;
import br.builders.repository.ClienteRepository;
import br.builders.service.ClienteService;
import br.builders.service.cliente.FiltroClienteFactory;
import br.builders.service.dto.ClienteDTO;
import br.builders.support.builder.impl.ClienteBuilder;
import br.builders.util.ClienteConstantes;
import br.builders.util.MockCliente;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ClienteServiceTest {

    private ClienteService clienteService;
    @Mock
    private ClienteRepository clienteRepository;

    @Before
    public void setUp() throws Exception {
        clienteService = new ClienteService(clienteRepository, new ClienteBuilder(), new FiltroClienteFactory());
    }

    @Test
    public void salvar() {
        final ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);
        final LocalDate dataNascimento = LocalDate.of(1990, 1, 1);

        final ClienteDTO clienteDTO = ClienteDTO.builder().nome("Teste").email("teste@gmail.com")
                .numeroRegistro("980.129.080-34").dataNascimento(dataNascimento).build();

        when(clienteRepository.findByEmail("teste@gmail.com")).thenReturn(Optional.empty());
        clienteService.salvarOuAtualizar(clienteDTO);


        Mockito.verify(clienteRepository).save(clienteCaptor.capture());
        Cliente cliente = clienteCaptor.getValue();
        assertThat(cliente).isNotNull();
        assertThat(cliente.getNome()).isEqualTo("Teste");
        assertThat(cliente.getEmail()).isEqualTo("teste@gmail.com");
        assertThat(cliente.getNumeroRegistro()).isEqualTo("98012908034");
        assertThat(cliente.getDataNascimento()).isNotNull();
    }

    @Test
    public void atualizarNomeENumeroRegistro() {
        final ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);
        final ClienteDTO clienteParaAtualizacao = MockCliente.getClienteParaAtualizacao();
        final Cliente clienteBanco = MockCliente.getClienteBanco();


        when(clienteRepository.findByEmail("teste@gmail.com")).thenReturn(Optional.of(clienteBanco));
        clienteService.salvarOuAtualizar(clienteParaAtualizacao);

        Mockito.verify(clienteRepository).save(clienteCaptor.capture());
        Cliente clienteAtualizado = clienteCaptor.getValue();
        assertThat(clienteAtualizado).isNotNull();
        assertThat(clienteAtualizado.getId()).isEqualTo(1);
        assertThat(clienteAtualizado.getNome()).isEqualTo(clienteParaAtualizacao.getNome());
        assertThat(clienteAtualizado.getNumeroRegistro()).isEqualTo("98012908034");
    }

    @Test
    public void buscarClientePorNome() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put(ClienteConstantes.FILTRO_NOME, "Teste");

        final Cliente clienteBanco = MockCliente.getClienteBanco();

        when(clienteRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(
               new PageImpl(Arrays.asList(clienteBanco))
        );

        Page<ClienteDTO> clientes = clienteService.getClientesPorFiltro(parametros);
        assertThat(clientes).isNotNull();
        assertThat(clientes.stream().findFirst().get().getNome()).isEqualTo("Teste");
    }

    @Test
    public void buscarClientePorNomeEEmail() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put(ClienteConstantes.FILTRO_NOME, "Teste");
        parametros.put(ClienteConstantes.FILTRO_EMAIL, "teste@gmail.com");

        final Cliente clienteBanco = MockCliente.getClienteBanco();

        when(clienteRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(
                new PageImpl(Arrays.asList(clienteBanco))
        );

        Page<ClienteDTO> clientes = clienteService.getClientesPorFiltro(parametros);
        assertThat(clientes).isNotNull();
        assertThat(clientes.stream().findFirst().get().getNome()).isEqualTo("Teste");
        assertThat(clientes.stream().findFirst().get().getEmail()).isEqualTo("teste@gmail.com");
    }

    @Test
    public void buscarClientePorNumeroRegistro() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put(ClienteConstantes.FILTRO_NUMERO_REGISTRO, "98012908038");

        final Cliente clienteBanco = MockCliente.getClienteBanco();

        when(clienteRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(
                new PageImpl(Arrays.asList(clienteBanco))
        );

        Page<ClienteDTO> clientes = clienteService.getClientesPorFiltro(parametros);
        assertThat(clientes).isNotNull();
        assertThat(clientes.stream().findFirst().get().getNumeroRegistro()).isEqualTo("98012908038");
    }


    @Test
    public void retornarErroAoRealizarBuscaComNenhumFiltroInformado() {
        final Cliente clienteBanco = MockCliente.getClienteBanco();

        when(clienteRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(
                new PageImpl(Arrays.asList(clienteBanco))
        );

        ClienteException clienteException = Assert.assertThrows(ClienteException.class, () -> clienteService.getClientesPorFiltro(null));
        assertThat(clienteException).isNotNull();
        assertTrue(clienteException.getMenssagemErro().getCodigo().contains("MSG-001"));
        assertTrue(clienteException.getMenssagemErro().getMensagem().contains("Pelo menos um filtro deve ser informado."));
    }

    @Test
    public void retornarErroAoRealizarBuscaComParametroInvalidoNoFiltro() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("bolinha", "Teste");
        final Cliente clienteBanco = MockCliente.getClienteBanco();

        when(clienteRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(
                new PageImpl(Arrays.asList(clienteBanco))
        );

        ClienteException clienteException = Assert.assertThrows(ClienteException.class, () -> clienteService.getClientesPorFiltro(parametros));
        assertThat(clienteException).isNotNull();
        assertTrue(clienteException.getMenssagemErro().getCodigo().contains("MSG-001"));
        assertTrue(clienteException.getMenssagemErro().getMensagem().contains("Pelo menos um filtro deve ser informado."));
    }


    //Teste de Integração
//    @Test
//    public void retornarErroAoSalvarUsuarioComTelefoneInvalido() {
//        final LocalDate dataNascimento = LocalDate.of(1990, 1, 1);
//        Set<String> telefones = new HashSet<>();
//        telefones.add("123");
//
//        final ClienteDTO clienteDTO = ClienteDTO.builder().nome("Teste Atualizado").email("teste@gmail.com")
//                .telefones(telefones)
//                .numeroRegistro("980.129.080-34").dataNascimento(dataNascimento).build();
//
//        ClienteException clienteException = Assert.assertThrows(ClienteException.class, () -> clienteService.salvarOuAtualizar(clienteDTO));
//        assertTrue(clienteException.getMenssagemErro().getCodigo().contains("MSG-014"));
//        assertTrue(clienteException.getMenssagemErro().getMensagem().contains("O número de telefone deve conter 10 dígitos."));
//    }

//    @Test
//    public void retornarErroAoSalvarUsuarioComEmailInvalido() {
//        final LocalDate dataNascimento = LocalDate.of(1990, 1, 1);
//        Set<String> telefones = new HashSet<>();
//        telefones.add("12345678901");
//
//        final ClienteDTO clienteDTO = ClienteDTO.builder().nome("Teste Atualizado").email("testegmail.com")
//                .telefones(telefones)
//                .numeroRegistro("980.129.080-34").dataNascimento(dataNascimento).build();
//
//        ClienteException clienteException = Assert.assertThrows(ClienteException.class, () -> clienteService.salvarOuAtualizar(clienteDTO));
////        assertTrue(clienteException.getMenssagemErro().getCodigo().contains("MSG-014"));
//        assertTrue(clienteException.getMenssagemErro().getMensagem().contains("Email inválido."));
//    }



}