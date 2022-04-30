package br.builders.entities;

import br.builders.entities.Telefone.Telefone;
import br.builders.util.Constantes;
import br.builders.util.Util;
import lombok.*;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity(name = "builders.Cliente")
@Table(name = "CLIENTE")
public class Cliente {

	@Id
    @Column(name = "PK_CLIENTE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "DS_NOME")
	private String nome;

	@Column(name = "DS_EMAIL")
	private String email;

	@Column(name = "NUMERO_REGISTRO")
	private String numeroRegistro;

	@Column(name = "DT_NASCIMENTO")
	private LocalDate dataNascimento;

	@Setter(AccessLevel.NONE)
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<Telefone> telefones;

	public void clearTelefones() {
		if (CollectionUtils.isEmpty(this.telefones)) {
			this.telefones = new HashSet<>();
			return;
		}

		this.telefones.clear();
	}

	public void addAll(final Set<Telefone> telefones) {
		if (CollectionUtils.isEmpty(telefones)) {
			this.telefones = new HashSet<>();
		}

		telefones
				.parallelStream()
				.forEach(telefone -> {
					String telefoneNormalizado = telefone.getNumero().replaceAll(Constantes.PATTERN_APENAS_NUMEROS, "");
					telefone.setNumero(telefoneNormalizado);
				});
		this.getTelefones().addAll(telefones);
	}

	public void normalizarNumeroRegistro() {
		this.numeroRegistro = this.numeroRegistro.replaceAll(Constantes.PATTERN_APENAS_NUMEROS, "");
	}
}