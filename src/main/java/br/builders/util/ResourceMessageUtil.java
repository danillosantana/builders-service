/*
 * ResourceMessageUtil.java
 * 
 * Copyright (c) CVM.
 *
 * Este software é confidencial e propriedade da CVM.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da CVM.
 * Este arquivo contém informações proprietárias.
 */
package br.builders.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Classe utilitária que provê os métodos para manipulação do arquivo *.properties 
 * com as mensagens e descrições apresentadas no sistema. 
 * 
 * @author Danillo Santana.
 */
public final class ResourceMessageUtil {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("builders-service/messages");
	
	/**
	 * Retorna a mensagem associada a chave informada.
	 * 
	 * @param chave
	 * @return
	 */
	public static String getDescricao(final String chave) {
		if (chave == null || chave.length() == 0) {
			throw new IllegalArgumentException("A chave do atributo informado é inválida.");
		}
		return RESOURCE_BUNDLE.getString(chave);
	}
	
	/**
	 * Retorna a mensagem associada a chave informada.
	 * 
	 * @param code
	 * @return
	 */
	public static String getDescricao(final Object code) {
		return getDescricao(code.toString());
	}
	
	/**
	 * Retorna a mensagem de erro, formatada com a descrição do {@link Throwable} informado.
	 * 
	 * @param e
	 * @return
	 */
	public static String getDescricaoErro(final String chave, final Throwable e){
		String descricao = getDescricao(chave);		
		return MessageFormat.format(descricao, e);
	}
	
	
	/**
	 * Retorna a mensagem de erro, formatada com a descrição do {@link Throwable} informado.
	 * 
	 * @param chave
	 * @param e
	 * @return
	 */
	public static String getDescricaoErro(final Object chave, final Throwable e){	
		return getDescricaoErro(chave.toString(), e);
	}
}
