package br.builders.service.cliente;


import br.builders.exception.ClienteException;
import br.builders.exception.MessageCode;
import br.builders.service.dto.FiltroClienteDTO;
import br.builders.util.ClienteConstantes;
import br.builders.util.ClienteUtil;
import br.builders.util.DateUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FiltroClienteFactory {

    public FiltroClienteDTO getFiltro(final Map<String, Object> parametros) {
        validarParametros(parametros);

        FiltroClienteDTO filtro = getFiltroClienteDTO(parametros);
        validarFiltro(filtro);

        return filtro;
    }

    private void validarFiltro(FiltroClienteDTO filtro) {
        boolean filtroInvalido = Stream.of(
                Objects.isNull(filtro.getNome()),
                Objects.isNull(filtro.getEmail()),
                Objects.isNull(filtro.getTelefone()),
                Objects.isNull(filtro.getDataNascimento()),
                Objects.isNull(filtro.getNumeroRegistro())

        ).allMatch(Boolean::booleanValue);

        if (filtroInvalido) {
            throw new ClienteException(MessageCode.FILTRO_INVALIDO);
        }
    }

    private void validarParametros(Map<String, Object> parametros) {
        if (CollectionUtils.isEmpty(parametros)) {
            throw new ClienteException(MessageCode.FILTRO_INVALIDO);
        }
    }

    private FiltroClienteDTO getFiltroClienteDTO(Map<String, Object> parametros) {
        FiltroClienteDTO filtro = FiltroClienteDTO.builder()
                .pageable(extrairPagina(parametros))
                .nome(extrairNome(parametros))
                .email(extrairEmail(parametros))
                .telefone(extrairTelefone(parametros))
                .dataNascimento(extrairDataNascimento(parametros))
                .numeroRegistro(extrairNumeroRegistro(parametros))
                .build();
        return filtro;
    }

    private String extrairNumeroRegistro(Map<String, Object> parametros) {
        return Optional.ofNullable(parametros.get(ClienteConstantes.FILTRO_NUMERO_REGISTRO))
                .map(parametro -> ClienteUtil.normalizeNumeroRegistro(parametro.toString()))
                .orElse(null);
    }

    private LocalDate extrairDataNascimento(Map<String, Object> parametros) {
        return Optional.ofNullable(parametros.get(ClienteConstantes.FILTRO_DATA_NASCIMENTO))
                .filter(date -> Strings.isNotBlank(date.toString()))
                .map(object -> DateUtils.parserLocalDate(object.toString(), DateUtils.FORMATO_DATA_YYYY_MM_DD))
                .orElse(null);
    }

    private String extrairTelefone(Map<String, Object> parametros) {
        return Optional.ofNullable(parametros.get(ClienteConstantes.FILTRO_TELEFONE))
                .map(parametro -> parametro.toString())
                .orElse(null);
    }

    private String extrairEmail(Map<String, Object> parametros) {
        return Optional.ofNullable(parametros.get(ClienteConstantes.FILTRO_EMAIL))
                .map(parametro -> parametro.toString())
                .orElse(null);
    }

    private String extrairNome(Map<String, Object> parametros) {
        return Optional.ofNullable(parametros.get(ClienteConstantes.FILTRO_NOME))
                .map(parametro -> parametro.toString())
                .orElse(null);
    }

    private PageRequest extrairPagina(Map<String, Object> parametros) {
        Integer numeroPagina = Optional.ofNullable(parametros.get(ClienteConstantes.NUMERO_PAGINA))
                .map(parametro -> Integer.valueOf(parametro.toString()))
                .orElse(BigInteger.ZERO.intValue());

        Integer tamanhoPagina =  Optional.ofNullable(parametros.get(ClienteConstantes.TAMANHO_PAGINA))
                .map(parametro -> Integer.valueOf(parametro.toString()))
                .orElse(ClienteConstantes.TAMANHO_PAGINA_PADRAO);

        return PageRequest.of(numeroPagina, tamanhoPagina);
    }

}
