package br.builders.exception;

import br.builders.handler.MenssagemErro;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Exceção a ser lançada na ocorrência de falhas e validações de negócio.
 * 
 * @author Danillo Santana
 */
@Getter
public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = -1499606212515490657L;

	private MenssagemErro menssagemErro;
	
	private static ResourceBundle bundle = ResourceBundle.getBundle("builders-service/messages");
	
	/**
	 * Construtor Padrão da classe
	 * 
	 * @param msg
	 */
	public NegocioException(String msg) {
		super(msg);
		this.menssagemErro = new MenssagemErro("", msg);
	}

	public NegocioException(MessageCode code, String... parametro) {
		super(getMensagemComFormatada(code, parametro));
		String mensagemComFormatada = getMensagemComFormatada(code, parametro);
		this.menssagemErro = new MenssagemErro(code.toString(), mensagemComFormatada);
	}

	public NegocioException(MessageCode code) {
		super(getMensagem(code));
		this.menssagemErro = new MenssagemErro(code.toString(), getMensagem(code));
	}

	public static String getMensagemComFormatada(MessageCode code, String... parametro) {
		final String msg = getMensagem(code);
		final String mensagemFormatada = MessageFormat.format(msg, parametro);
		
		return mensagemFormatada;
	}

	public NegocioException(String msg, Throwable e) {
		super(msg, e);
	}

	private static String getMensagem(MessageCode chave) {
        return bundle.getString(chave.toString());
    }
}
