package com.claytonDEV.Literalura.models;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long id_livro;

    private String titulo;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idiomas;
    private Long donwloads;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "autor")
    private Autor autor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_livro() {
        return id_livro;
    }

    public void setId_livro(Long id_livro) {
        this.id_livro = id_livro;
    }

    public void setDonwloads(Long donwloads) {
        this.donwloads = donwloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    private String idiomaN(List<String> idiomas) {
        if (idiomas == null || idiomas.isEmpty()) {
            return "Idioma não encontrado";
        } else {
            return idiomas.get(0);
        }
    }

    public Livro() {

    }


    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", idiomas=" + idiomas +
                ", donwloads=" + donwloads +
                '}';
    }

    public Livro(DadosLivro livro) {

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Long getDonwloads() {
        return donwloads;
    }
}
