package br.builders.entities.Telefone;

import br.builders.entities.Cliente;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "builders.Telefone")
@Table(name = "TELEFONE")
public class Telefone implements Serializable {

    private static final long serialVersionUID = -399812748670662116L;

    @Id
    @Column(name = "PK_TELEFONE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DS_NUMERO")
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FK_CLIENTE", referencedColumnName="PK_CLIENTE", nullable=false, updatable=false)
    private  Cliente cliente;
}
