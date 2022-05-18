package com.generation.blogpessoal.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.blogpessoal.model.Usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;


//indicando que a classe UsuarioRepositoryTest é uma classe de test, que vai rodarem uma porta aleatoria a cada teste realizado

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

//cria uma instancia de testes, que define que o ciclo de vida do teste vai respeitar o ciclo de vida da classe(será executado e resetado após o uso

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository repository;
	
	@BeforeAll
	void start() {
		repository.save(new Usuario(0L, "Maiar Ferreira", "isadora@gmail.com","51 e pinga","https://i.imgur.com/FETvs2O.jpg"));
repository.save(new Usuario(0L, "Michael Ferreira", "michaeltrimundial@gmail.com","nunca fui rebaixado","https://i.imgur.com/FETvs2O.jpg"));
		
		repository.save(new Usuario(0L, "Brocco Mendes", "broco@gmail.com","broccolis","https://i.imgur.com/FETvs2O.jpg"));
		
		repository.save(new Usuario(0L, "Mayara Ferreira", "will31smith@gmail.com","cenoura","https://i.imgur.com/FETvs2O.jpg"));


	}
	@Test
	@DisplayName("Teste que retorna 1 usuario")
public void retornaUmUsuario() {
		Optional<Usuario> usuario = repository.findByUsuario("isadora@gmail.com");
		assertTrue(usuario.get().getUsuario().equals("isadora@gmail.com"));
	}
	
	@Test // Diz ao Spring que essa é uma função para testes
	@DisplayName("Teste que retorna 3 usuários") // Nome do teste
	public void retornaTresUsuario() { // Toda função que tem um 'public' na frente significa que estamos criando ela
		List <Usuario> listaDeUsuarios = repository.findAllByNomeContainingIgnoreCase("Ferreira");
		assertEquals(3, listaDeUsuarios.size()); 
		assertTrue(listaDeUsuarios.get(0).getNome().equals("Maiar  Ferreira")); 
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Michael Ferreira")); 
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Mayara Ferreira")); 
	}
	
	
	
	
	
	@AfterAll
	public void end() {
		repository.deleteAll();
	}



}
