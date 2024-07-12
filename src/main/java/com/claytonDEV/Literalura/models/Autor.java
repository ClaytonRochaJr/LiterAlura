package com.claytonDEV.Literalura.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "autores")
public class Autor {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private Long id_autor;

        private String nome;
        private int anoMorte;
        private int anoNascimento;

        @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private List<Livro> livros = new ArrayList<>();


        public Autor(DadosAutores dadosAutores) {

        }

        public Autor() {

        }

        @Override
        public String toString() {
                return "Autor{" +
                        "nome='" + nome + '\'' +
                        ", anoMorte=" + anoMorte +
                        ", anoNascimento=" + anoNascimento +
                        '}';
        }

        public String getNome() {
                return nome;
        }

        public void setNome(String nome) {
                this.nome = nome;
        }

        public int getAnoMorte() {
                return anoMorte;
        }

        public void setAnoMorte(int anoMorte) {
                this.anoMorte = anoMorte;
        }

        public int getAnoNascimento() {
                return anoNascimento;
        }

        public void setAnoNascimento(int anoNascimento) {
                this.anoNascimento = anoNascimento;
        }
}
