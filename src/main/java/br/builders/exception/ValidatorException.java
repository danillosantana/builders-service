package br.builders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidatorException extends NegocioException {

    private static final long serialVersionUID = -679227711873483268L;

    public ValidatorException(String msg) {
        super(msg);
    }

    public ValidatorException(MessageCode code, String... parametros) {
        super(code, parametros);
    }
}
