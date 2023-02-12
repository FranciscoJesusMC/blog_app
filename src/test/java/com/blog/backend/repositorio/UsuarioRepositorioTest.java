package com.blog.backend.repositorio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.blog.backend.entity.Rol;
import com.blog.backend.entity.Usuario;
import com.blog.backend.repository.RolRepositorio;
import com.blog.backend.repository.UsuarioRepositorio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class UsuarioRepositorioTest {
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private RolRepositorio rolRepositorio;
	
	private Usuario usuario;
	
	private Usuario usuario2;
		
	@BeforeEach
	void init() {	
		Rol role = rolRepositorio.findByNombre("ROLE_USER").orElse(null);
		if(role == null) {
			role = new Rol();
			role.setNombre("ROLE_USER");
			rolRepositorio.save(role);
		}
		
		usuario = new Usuario();
		usuario.setNombre("Francisco");
		usuario.setApellido("Macullunco");
		usuario.setEmail("franck@gmail.com");
		usuario.setPassword("12345");
		usuario.setUsername("rukero");
		usuario.setFoto("foto.png");
		usuario.setRol(Collections.singleton(role));
		

		
		usuario2 = new Usuario();
		usuario2.setNombre("Alberto");
		usuario2.setApellido("del rio");
		usuario2.setEmail("alberto@gmail.com");
		usuario2.setPassword("12345");
		usuario2.setUsername("abelote");
		usuario2.setFoto("foto.png");
		usuario.setRol(Collections.singleton(role));

		
	
		
	}
	
	@DisplayName("Test para guardar usuario")
	@Test
	void guardarUsuario() {
				
		Usuario guardarUsuario = usuarioRepositorio.save(usuario);
		
		assertNotNull(guardarUsuario);
		assertThat(guardarUsuario.getId()).isNotEqualTo(0);

		
		
		
	}
	
	@DisplayName("Test para listar usuarios")
	@Test
	void listarUsuarios() {
		
		usuarioRepositorio.save(usuario);
		
		usuarioRepositorio.save(usuario2);
			
		List<Usuario> listaUsuarios = usuarioRepositorio.findAll();
		
		assertNotNull(listaUsuarios);
		assertEquals(2, listaUsuarios.size());
	}
	
	@DisplayName("Test para obtener un usuario por id")
	@Test
	void obtenerUsuarioPorId() {
		
		usuarioRepositorio.save(usuario);
		
		Usuario buscarUsuario = usuarioRepositorio.findById(usuario.getId()).get();
		
		assertNotNull(buscarUsuario);
		assertThat(usuario.getId()).isNotEqualTo(0);
		assertEquals("Francisco", usuario.getNombre());
	}
	
	
	@DisplayName("Test para actualizar un usuario")
	@Test
	void actualizarUsuario() {
		
		
		Usuario usuario = new Usuario();
		usuario.setNombre("Francisco");
		usuario.setApellido("Macullunco");
		usuario.setEmail("franck@gmail.com");
		usuario.setPassword("12345");
		usuario.setUsername("rukero");
		usuario.setFoto("foto.png");
		
		usuarioRepositorio.save(usuario);
		
		Usuario buscarUsuario = usuarioRepositorio.findById(usuario.getId()).get();
		
		buscarUsuario.setNombre("Juan");
		buscarUsuario.setUsername("clinkz");
		Usuario actualizarUsuario = usuarioRepositorio.save(buscarUsuario);
		
		assertEquals("Juan", actualizarUsuario.getNombre());
		assertEquals("clinkz", actualizarUsuario.getUsername());
		
		
	}
	
	@DisplayName("Test para eliminar un usuario")
	@Test
	void eliminarUsuario() {
		
		usuarioRepositorio.save(usuario);
		
		usuarioRepositorio.save(usuario2);
		
		usuarioRepositorio.delete(usuario);
		
		Optional<Usuario> existsUsuario = usuarioRepositorio.findById(usuario.getId());
		List<Usuario> listaUsuarios = usuarioRepositorio.findAll();
		
		assertEquals(1, listaUsuarios.size());
		assertThat(existsUsuario).isEmpty();
	}
}
