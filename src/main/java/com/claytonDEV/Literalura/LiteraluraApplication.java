package com.claytonDEV.Literalura;

import com.claytonDEV.Literalura.main.Principal;
import com.claytonDEV.Literalura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LivroRepository livroRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}
	public void run(String... args) throws Exception {
		Principal principal = new Principal(livroRepository);
		principal.menu();
	}
}
