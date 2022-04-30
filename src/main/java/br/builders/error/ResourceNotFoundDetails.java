package br.builders.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Danillo.
 */
@Builder
@AllArgsConstructor
@Data
@ResponseStatus
public class ResourceNotFoundDetails {

	private String titulo;
	private int status;
	private String detalhes;
	private long timestamp;
	private String mensagem;
}
