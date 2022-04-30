package br.builders.util;

import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class ClienteUtil {

    public static String normalizeNumeroRegistro(final String numeroRegistro) {
        return Objects.nonNull(numeroRegistro) ? numeroRegistro.replaceAll("[\\.\\/-]", "") : null;
    }

}
