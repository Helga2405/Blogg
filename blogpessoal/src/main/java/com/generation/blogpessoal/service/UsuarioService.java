package com.generation.blogpessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	public Optional<Usuario>cadastraUsuario(Usuario usuario){
		
		if(repository.findByUsuario(usuario.getUsuario()).isPresent())
		
              return Optional.empty();
		
              usuario.setSenha(criptografarSenha(usuario.getSenha()));
              return Optional.of(repository.save(usuario));

		
	}
public Optional <Usuario> atualizarUsuario(Usuario usuario) {
		
		// Checa se o id já existe no banco de dados antes de fazer a atualização 
		if (repository.findById(usuario.getId()).isPresent()) {
			/* Senão criptografar de novo, vai mandar ela sem a criptografia aí
			 * vai dar erro por que o sistema não entende, não estará no padrão 
			 * necessário. */
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			
			// Aí salva a senha criptografada
			return Optional.of(
repository.save
(usuario));
		}
		return Optional.empty();
} 
	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(senha);
	}
    public Optional<UsuarioLogin> autenticaUsuario(Optional<UsuarioLogin> usuarioLogin){
    	Optional<Usuario> usuario = repository.findByUsuario(usuarioLogin.get().getUsuario());

    	if (usuario.isPresent()) {
			if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha()));
				usuarioLogin.get().setSenha(usuario.get().getSenha());

				return usuarioLogin;

			}
    	}
    	
    	
    	return Optional.empty();
		
    
		
		

	}
private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.matches(senhaDigitada, senhaBanco);

	}
private String gerarBasicToken(String usuario, String senha) {

	String token = usuario + ":" + senha;
	byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
	return "Basic " + new String(tokenBase64);

}


    
    	


	
}
