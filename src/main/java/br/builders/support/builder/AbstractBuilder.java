package br.builders.support.builder;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Estudar conceitos mapper ou builders.
 */

/**
 * Responsável pela criação de objetos em diferentes tipos
 * de representações.
 *
 * @param <PRIMARY>
 * @param <SECONDARY>
 */
public interface AbstractBuilder<PRIMARY, SECONDARY> {

    default Set<SECONDARY> converterEntidadesEmDTOs(Collection<PRIMARY> primarios) {
        return primarios == null ? Collections.emptySet():
            primarios.stream()
                .filter(Objects::nonNull)
                .map(this::converterEntidadeEmDTO)
                .collect(Collectors.toSet());
    }

    default Set<PRIMARY> converteDTOsEmEntidades(Collection<SECONDARY> secondarios) {
        return secondarios == null ? Collections.emptySet() :
            secondarios.stream()
                .filter(Objects::nonNull)
                .map(this::converterDTOEmEntidade)
                .collect(Collectors.toSet());
    }

    SECONDARY converterEntidadeEmDTO(PRIMARY primario);

    PRIMARY converterDTOEmEntidade(SECONDARY secundario);

}
