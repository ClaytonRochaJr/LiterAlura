package com.claytonDEV.Literalura.main;

import com.claytonDEV.Literalura.models.Autor;
import com.claytonDEV.Literalura.models.DadosLivro;
import com.claytonDEV.Literalura.models.Livro;
import com.claytonDEV.Literalura.models.ResultadoAPI;
import com.claytonDEV.Literalura.repository.AutorRepository;
import com.claytonDEV.Literalura.repository.LivroRepository;
import com.claytonDEV.Literalura.service.ApiLivro;
import com.claytonDEV.Literalura.service.ConverteDados;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.*;

public class Principal {
    private ApiLivro apiLivro = new ApiLivro();

    private final String ENDERECO = "https://gutendex.com/books/?search="; //dom%20casmurro

    private ConverteDados converteDados = new ConverteDados();

    private Scanner scanner = new Scanner(System.in);

    private List<Livro> dadosLivros = new ArrayList<>();

    private LivroRepository livroRepository;
    private AutorRepository autorRepository;

    public Principal(LivroRepository livroRepository) {
    }

    public void menu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    ***Opções para escolha***
                    1 - Buscar livros pelo título.
                    2 - Lista de Livros registrados.
                    3 - lista de autores registrados.
                    4 - Lista de autores registrados vivos em determinado ano.
                    5 - Lista de livros por idioma.

                    0 - Sair.
                    """;

            try{
                System.out.println(menu);
                opcao = scanner.nextInt();
                scanner.nextLine();


                switch (opcao) {
                    case 1:
                        buscarLivrosPorTitulo();
                        break;
                    case 2:
                        listaDeLivrosRegistrados();
                        break;
                    case 3:
                        listaDeAutoresRegistrados();
                        break;
                    case 4:
                        listaDeAutoresVivosEmDeterminadoAno();
                        break;
                    case 5:
                        listaLivrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Dado invalido tente novamente");
                scanner.next();
            }
        }
    }

    private Livro pegarDadosLivro() {
        System.out.println("Digite o nome do livro para a busca");
        var nomedoLivro = scanner.nextLine().toLowerCase();
        var json = apiLivro.obterDados(ENDERECO +nomedoLivro.replace(" ", "%20"));
        ResultadoAPI dados = converteDados.obterDados(json, ResultadoAPI.class);
        System.out.println(dados);
        if (dados != null && dados.getDadosLivrosR() != null && !dados.getDadosLivrosR().isEmpty()){
            DadosLivro livro = dados.getDadosLivrosR().get(0);
            System.out.println(livro.toString());

            return new Livro(livro);

        }
        else {
            System.out.println("Livro não encontrado");
            return  null;
        }
    }
    @Transactional
    protected void buscarLivrosPorTitulo(){
        Livro livro = pegarDadosLivro();
        if (livro == null) {
            System.out.println("Não foi possível encontrar o livro");
            return;
        }

        try{ boolean livroExists = Boolean.parseBoolean(livroRepository.existsByTitulo(livro.getTitulo()));
            if (livroExists){
                System.out.println(livro);
            } else{
               livroRepository.save(livro);
                System.out.println(livro);
            }
        }catch (InvalidDataAccessApiUsageException | DataIntegrityViolationException e) {









            System.out.println("Livro invalido");
        }


    }

    @Transactional
    public void listaDeLivrosRegistrados() {
        dadosLivros = livroRepository.findAll();
        dadosLivros.stream()
                .sorted(Comparator.comparing(Livro::getTitulo))
                .forEach(System.out::println);
    }


    private void listaDeAutoresRegistrados() {
        List<Autor> autors = autorRepository.findAll();
        autors.stream()
                .sorted(Comparator.comparing(Autor::getNome))
                .forEach(System.out::println);
    }

    private void listaDeAutoresVivosEmDeterminadoAno() {
        System.out.println("Escolha o ano para ver a lista de autores vivos nesse ano: ");
        try{ var anoVivo = scanner.nextInt();
            scanner.nextLine();
            List<Autor> autoresVivos = autorRepository.findAutoresVivos(anoVivo);
            autoresVivos.forEach(System.out::println);
        }catch (InputMismatchException e) {
            System.out.println("Dado invalido tente novamente");
            scanner.next();}
    }

    private void listaLivrosPorIdioma() {
        var opcaoIdioma = -1;
        while (opcaoIdioma != 0) {
            System.out.println("***Opções de idiomas para escolha***");
            System.out.println("1 - Espanhol (es).");
            System.out.println("2 - Português (pt).");
            System.out.println("3 - Inglês (en).");
            System.out.println("4 - Francês (fr).");
            System.out.println("0 - sair");
            System.out.println("escolha uma opção");
            opcaoIdioma = scanner.nextInt();
            scanner.nextLine();
            if (opcaoIdioma == 1) {
                listarLivrosPorIdioma("es");
            } else if (opcaoIdioma == 2) {
                listarLivrosPorIdioma("pt");
            } else if (opcaoIdioma == 3) {
                listarLivrosPorIdioma("en");
            } else if (opcaoIdioma == 4) {
                listarLivrosPorIdioma("fr");
            } else if (opcaoIdioma == 0) {
                System.out.println("Você saiu do Menu, programa encerrado");
            } else {
                System.out.println("opção invalida tente novamente");
            }


        }

    }
    private void listarLivrosPorIdioma(String idioma){


        List<Livro> livrosPorIdioma = livroRepository.livrosPorIdioma(idioma);
        System.out.println("*** Livros em " + idioma + " ***");
        livrosPorIdioma.forEach(l -> System.out.println(l.getTitulo()));


    }


}

