package br.builders.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class FiltroClienteDTO {

    private static final long serialVersionUID = -4113145618212549062L;

    private PageRequest pageable;
    private String nome;
    private String email;
    private String telefone;
    private String numeroRegistro;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

}
