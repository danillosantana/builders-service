package br.builders.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenssagemErro {

    private String codigo;
    private String mensagem;

    public MenssagemErro(String mensagem) {
        this.mensagem = mensagem;
    }
}
