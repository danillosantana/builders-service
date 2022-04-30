package br.builders.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelefoneDTO  implements Serializable {

    private static final long serialVersionUID = 1540436856460787925L;
    private Long id;
    private String numero;
}
