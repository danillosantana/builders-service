package br.builders.support.builder.impl;

import br.builders.entities.Cliente;
import br.builders.entities.Telefone.Telefone;
import br.builders.service.dto.ClienteDTO;
import br.builders.service.dto.TelefoneDTO;
import br.builders.support.builder.AbstractBuilder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe responsável pela conversão da Entidade Cliente na Entidade ClienteDTO ou vice versa.
 */
@RequiredArgsConstructor
@Component
public class ClienteBuilder implements AbstractBuilder<Cliente, ClienteDTO> {

    @Override
    public ClienteDTO converterEntidadeEmDTO(Cliente primario) {
        if (Objects.isNull(primario)) {
            return null;
        }


        return ClienteDTO.builder()
                            .nome(primario.getNome())
                            .email(primario.getEmail())
                            .numeroRegistro(primario.getNumeroRegistro())
                            .telefones(
                                    Optional.ofNullable(primario.getTelefones())
                                            .orElse(Collections.emptySet())
                                            .stream().map(Telefone::getNumero)
                                            .collect(Collectors.toList())
                            )
                            .idade(calculateIdade(primario.getDataNascimento()))
                            .dataNascimento(primario.getDataNascimento())
                            .build();
    }


    private static int calculateIdade(LocalDate dataNascimento) {
        LocalDate dataAtual = LocalDate.now();
        if (Objects.nonNull(dataNascimento)) {
            return Period.between(dataNascimento, dataAtual).getYears();
        } else {
            return BigDecimal.ZERO.intValue();
        }
    }

    @Override
    public Cliente converterDTOEmEntidade(ClienteDTO secundario) {
        if (Objects.isNull(secundario)) {
            return null;
        }

        return Cliente.builder()
                        .id(secundario.getId())
                        .email(secundario.getEmail())
                        .nome(secundario.getNome())
                        .numeroRegistro(secundario.getNumeroRegistro())
                        .telefones( Optional.ofNullable(secundario.getTelefones())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(numero -> Telefone.builder().numero(numero).build())
                                .collect(Collectors.toSet()))
                        .dataNascimento(secundario.getDataNascimento())
                        .build();
    }
}
