package br.builders.exception;

/**
 * Enum com os código de exceções/mensagens de negócio.
 * 
 * @author Danillo Santana.
 */
public enum MessageCode {

	/* Mensagens Globais. */
	FILTRO_INVALIDO("MSG-001"),
	CPF_DIGITOS_INVALIDOS("MSG-002"),
	CPF_DIGITOS_REPETIDOS("MSG-003"),
	CPF_FORMATO_INVALIDO("MSG-004"),
	CNPJ_DIGITOS_INVALIDOS("MSG-005"),
	CNPJ_DIGITOS_REPETIDOS("MSG-006"),
	CNPJ_FORMATO_INVALIDO("MSG-007"),
	CAMPOS_OBRIGATORIOS("MSG-008"),
	CLIENTE_JA_CADASTRADO("MSG-009"),
	EMAIL_INVALIDO("MSG-010"),
	OPERACAO_NAO_PERMITIDA("MSG-011"),
	MENSAGEM_INVALIDA("MSG-012"),
	RECURSO_NAO_ENCONTRADO("MSG-013"),
	NUMERO_TELEFONE_INVALIDO("MSG-014"),
	CLIENTE_NAO_ENCONTRADO("MSG-015"),
	ERRO_INESPERADO("MSG-100");
    private final String chave;

	/**
	 * Construtor da classe.
	 *
	 * @param chave
	 */
	private MessageCode(final String chave) {
		this.chave = chave;
	}

	/**
	 * @see Enum#toString()
	 */
	@Override
	public String toString() {
		return chave;
	}
}