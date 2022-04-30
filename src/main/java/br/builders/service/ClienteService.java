package br.builders.service;

import br.builders.entities.Cliente;
import br.builders.exception.ClienteException;
import br.builders.exception.MessageCode;
import br.builders.repository.ClienteRepository;
import br.builders.repository.specification.ClienteSpecification;
import br.builders.service.cliente.FiltroClienteFactory;
import br.builders.service.dto.ClienteDTO;
import br.builders.service.dto.FiltroClienteDTO;
import br.builders.service.dto.PaginacaoDTO;
import br.builders.support.builder.impl.ClienteBuilder;
import br.builders.util.Constantes;
import br.builders.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.util.*;

import static org.springframework.data.jpa.domain.Specification.where;


/**
 * Responsável pela execução das operações do cadastro de cliente, onde será possível:
 * - Criação de novos clientes;
 * - Atualização de clientes existentes;
 * - Busca de informaçoes de clientes;
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = ClienteException.class)
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteBuilder builder;
    private final FiltroClienteFactory filtroClienteFactory;

    public void salvarOuAtualizar(final ClienteDTO clienteDTO) {
        clienteRepository.findByEmail(clienteDTO.getEmail())
                .ifPresentOrElse(cliente ->
                        editar(clienteDTO, cliente), () -> salvar(clienteDTO)
                );
    }
    private void salvar(final ClienteDTO clienteDTO) {
        Cliente cliente = builder.converterDTOEmEntidade(clienteDTO);
        cliente.normalizarNumeroRegistro();
        clienteRepository.save(cliente);
    }

    private void editar(final ClienteDTO clienteDTO, Cliente cliente) {
        cliente.clearTelefones();
        Cliente clienteEditado  = builder.converterDTOEmEntidade(clienteDTO);
        BeanUtils.copyProperties(clienteEditado , cliente, "id");
        cliente.normalizarNumeroRegistro();
        clienteRepository.save(cliente);
    }


    public Page<ClienteDTO> getClientesPorFiltro(final Map<String, Object> queryParams) {
        FiltroClienteDTO filtro = filtroClienteFactory.getFiltro(queryParams);
        Specification<Cliente> specification = getFiltroSpecification(filtro);
        Page<Cliente> clientes = clienteRepository.findAll(specification, filtro.getPageable());
        return clientes.map(cliente -> builder.converterEntidadeEmDTO(cliente));
    }


//    private void validarNumerosTelefone(Set<String> telefones) {
//        if (!CollectionUtils.isEmpty(telefones)) {
//            telefones.forEach(telefone -> {
//                if (!Util.isTelefoneValido(telefone)) {
//                    throw new ClienteException(MessageCode.NUMERO_TELEFONE_INVALIDO, telefone);
//                }
//            });
//        }
//    }

    private void validarFiltros(FiltroClienteDTO filtro) throws ClienteException {
        if (Objects.isNull(filtro)) {
            throw new ClienteException(MessageCode.FILTRO_INVALIDO);
        }
    }

    private Specification<Cliente> getFiltroSpecification(FiltroClienteDTO filtro) {
        return where(ClienteSpecification.porNome(filtro.getNome()))
                                                    .and(ClienteSpecification.porEmail(filtro.getEmail()))
                                                    .and(ClienteSpecification.porTelefone(filtro.getTelefone()))
                                                    .and(ClienteSpecification.porNumeroRegistro(filtro.getNumeroRegistro()))
                                                    .and(ClienteSpecification.porDataNascimento(filtro.getDataNascimento()));
    }
}