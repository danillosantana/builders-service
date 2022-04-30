package br.builders.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginacaoDTO {

    private Integer pagina;
    private Integer tamanhoPagina;
}
