package br.builders.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClienteException extends NegocioException {

    public ClienteException(String msg) {
        super(msg);
    }

    public ClienteException(MessageCode code, String... parametro) {
        super(code, parametro);
    }

    public ClienteException(MessageCode code) {
        super(code);
    }

    public ClienteException(String msg, Throwable e) {
        super(msg, e);
    }
}
