package br.builders.repository.specification;

import br.builders.entities.Cliente;
import br.builders.entities.Cliente_;
import br.builders.entities.Telefone.Telefone_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteSpecification {

    public static Specification<Cliente> porNome(final String nome) {
        return Strings.isEmpty(nome) ? null :
                (root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.upper(root.get(Cliente_.NOME)), "%" +  nome.toUpperCase() + "%");
    }

    public static Specification<Cliente> porEmail(final String email) {
        return Strings.isEmpty(email) ? null :
                (root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(Cliente_.EMAIL), email);
    }

    public static Specification<Cliente> porNumeroRegistro(final String numeroRegistro) {
        return Strings.isEmpty(numeroRegistro) ? null :
                (root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(Cliente_.NUMERO_REGISTRO), numeroRegistro);
    }

    public static Specification<Cliente> porDataNascimento(final LocalDate dataNascimento) {
        return Objects.isNull(dataNascimento) ? null :
                (root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(Cliente_.DATA_NASCIMENTO), dataNascimento);
    }

    public static Specification<Cliente> porTelefone(String telefone) {
        return Objects.isNull(telefone) ? null :
                (root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.in(root.join(Cliente_.TELEFONES).get(Telefone_.NUMERO)).value(telefone);
    }
}
