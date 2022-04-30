package br.builders.handler;

import br.builders.exception.NegocioException;
import br.builders.exception.MessageCode;
import br.builders.util.ResourceMessageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;


/**
 * Classe responsável por gerenciar as ocorrencias de erros da aplicação.
 */
@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class RestExceptionHandler {

	@ExceptionHandler({NegocioException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected ResponseEntity<Object> handlerErroNegocio(NegocioException negocioException) {
		return new ResponseEntity<>(negocioException.getMenssagemErro(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class, MissingPathVariableException.class, MissingServletRequestParameterException.class, ServletRequestBindingException.class, ConversionNotSupportedException.class, TypeMismatchException.class, HttpMessageNotReadableException.class, HttpMessageNotWritableException.class, MethodArgumentNotValidException.class, MissingServletRequestPartException.class, BindException.class, NoHandlerFoundException.class, AsyncRequestTimeoutException.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@Nullable
	protected ResponseEntity<Object> handlerErroNegocioBadRequest(Exception e) throws Exception {
		String msg =  ResourceMessageUtil.getDescricaoErro(MessageCode.ERRO_INESPERADO, e.getCause());
		log.error(msg, e);
		return new ResponseEntity<>(new MenssagemErro(MessageCode.ERRO_INESPERADO.toString(), msg), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
