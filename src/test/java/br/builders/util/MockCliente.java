package br.builders.util;

import br.builders.entities.Cliente;
import br.builders.service.dto.ClienteDTO;

import java.time.LocalDate;

public class MockCliente {

    public static ClienteDTO getClienteParaAtualizacao() {
        final LocalDate dataNascimento = LocalDate.of(1990, 1, 1);
        return ClienteDTO.builder().nome("Teste Atualizado").email("teste@gmail.com")
                .numeroRegistro("980.129.080-34").dataNascimento(dataNascimento).build();
    }


    public static Cliente getClienteBanco() {
        final LocalDate dataNascimento = LocalDate.of(1990, 1, 1);

        return  Cliente.builder().id(1L).nome("Teste").email("teste@gmail.com")
                .numeroRegistro("98012908038").dataNascimento(dataNascimento).build();
    }

}
