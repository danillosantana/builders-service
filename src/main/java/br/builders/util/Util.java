package br.builders.util;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe utilitária da aplicação.
 * 
 * @author Danillo Santana
 *
 */

public class Util {

	private static final Locale BRASIL = new Locale("pt","BR");
    private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRASIL);
    public static final DecimalFormat VALOR = new DecimalFormat("¤ ###,###,##0.00",REAL);
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String TELEFONE_PATTERN = "\\d{10,11}";

	private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
	private static final Pattern patternTelefone = Pattern.compile(TELEFONE_PATTERN, Pattern.CASE_INSENSITIVE);

	/**
	 * Verifica que String informada é vazia.
	 * 
	 * @param msg
	 * 
	 * @return
	 */
	public static boolean isBlank(final String msg) {
		return msg == null || msg.isEmpty() || msg.equals("");
	}

	/**
	 * Verifica que coleção e vazia.
	 * 
	 * @return
	 */
	public static <E> boolean isEmpty(final Collection<E> colecao) {
		return colecao == null || colecao.isEmpty();
	}
	
	/**
	 * Retorna o valor formatado. Ex: 2,45.
	 * 
	 * @param valor
	 * @return
	 */
	public static String getValorFormatado(final Double valor) {	
		if (valor == null) {
			throw new IllegalArgumentException("O valor deve ser informado.");
		}
		
		return VALOR.format(valor);
	}

	public static boolean isEmailValido(String email){
		Matcher matcher  = pattern.matcher(email);
		return matcher.matches();
	}


	public static boolean isTelefoneValido(String telefone){
		Matcher matcher  = patternTelefone.matcher(removerEspacoesEmBranco(telefone));
		return matcher.matches();
	}

	public static String removerEspacoesEmBranco(String texto) {
		return texto.replaceAll("\\s+","");
	}
}
