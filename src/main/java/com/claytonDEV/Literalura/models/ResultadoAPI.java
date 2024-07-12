package com.claytonDEV.Literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultadoAPI {
    private @JsonAlias("results") List<DadosLivro> dadosLivrosR;


    public List<DadosLivro> getDadosLivrosR() {
        return dadosLivrosR;
    }

    public void setDadosLivrosR(List<DadosLivro> dadosLivrosR) {
        this.dadosLivrosR = dadosLivrosR;
    }
}
