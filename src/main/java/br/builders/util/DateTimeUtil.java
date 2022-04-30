package br.builders.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Classe utilitária para manipulação de datas.
 * 
 * @author Danillo Santana
 */
public class DateTimeUtil {

	private final static String FORMATO_DATA_PADRAO = "dd-MM-yyyy";

	/**
	 * Converte {@link Date} para {@link LocalDateTime}. 
	 * 
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public static LocalDateTime LocalDateTimeFromDate(Date data) throws Exception {
		if (data == null) {
			throw new Exception("A data deve ser informada.");
		}
		
		return data.toInstant().atZone(ZoneId.systemDefault() ).toLocalDateTime();
	}


	/**
	 * Retorna a data formatada no padrão dd-MM-yyyy
	 *
	 * @param data
	 * @return
	 */
	public static String getLocalDateTimeFormatada(final LocalDateTime data) {
		if (data == null) {
			throw new IllegalArgumentException("A data deve ser informada");
		}

		return getLocalDateTimeFormatada(data, DateTimeUtil.FORMATO_DATA_PADRAO);
	}

	/**
	 * Retorna a data formatada no padrão dd-MM-yyyy
	 *
	 * @param data
	 * @param formato
	 * @return
	 */
	public static String getLocalDateTimeFormatada(final LocalDateTime data, final String formato) {
		if (data == null) {
			throw new IllegalArgumentException("A data/formato deve ser informada");
		}

		DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern(formato);
		return data.format(dataFormatada);
	}

}
