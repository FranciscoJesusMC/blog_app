package com.blog.backend.repositorio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.blog.backend.entity.Imagenes;
import com.blog.backend.entity.Publicacion;
import com.blog.backend.entity.Tag;
import com.blog.backend.entity.Usuario;
import com.blog.backend.repository.PublicacionRepositorio;
import com.blog.backend.repository.TagRepositorio;
import com.blog.backend.repository.UsuarioRepositorio;

@DataJpaTest
public class PublicacionRepositorioTest {

	@Autowired
	private PublicacionRepositorio publicacionRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private TagRepositorio tagRepositorio;
	
	@DisplayName("Test para crear una publicacion")
	@Test
	void crearPublicacion() {
	
		Imagenes imagen1 = new Imagenes("https://example.com/images/myimage1.jpg");
		Imagenes imagen2 = new Imagenes("https://example.com/images/myimage2.jpg");
		Imagenes imagen3 = new Imagenes("https://example.com/images/myimage3.jpg");
		
	    Set<Imagenes> imagenes = new HashSet<>(Arrays.asList(imagen1,imagen2,imagen3));
	   
	    			
		Usuario usuario = new Usuario();
		usuario.setNombre("Francisco");
		usuario.setApellido("Macullunco");
		usuario.setEmail("franck@gmail.com");
		usuario.setPassword("12345");
		usuario.setUsername("rukero");
		usuario.setFoto("foto.png");
		usuarioRepositorio.save(usuario);
		
		Tag tag = new Tag();
		tag.setNombre("Ocio");
		tagRepositorio.save(tag);
		
		Publicacion publicacion = new Publicacion();
		publicacion.setTitulo("Mi primera publicacion");
		publicacion.setContenido("primer contenido");
		publicacion.setTag(Collections.singleton(tag));
		publicacion.setImagenes(imagenes);
		publicacion.setUsuario(usuario);
		publicacionRepositorio.save(publicacion);
		
		assertNotNull(publicacion);
		assertEquals("Mi primera publicacion", publicacion.getTitulo());
		assertEquals("primer contenido", publicacion.getContenido());
		assertThat(publicacion.getTag()).contains(tag);
		assertThat(publicacion.getImagenes().size()).isEqualTo(3);
		assertThat(publicacion.getUsuario().getId()).isEqualTo(usuario.getId());
		
	}
	
	@DisplayName("Test para listar las publicaciones")
	@Test
	void listarPublicaciones() {
		
		
		Imagenes imagen1 = new Imagenes("https://example.com/images/myimage1.jpg");
		Imagenes imagen2 = new Imagenes("https://example.com/images/myimage2.jpg");
		Imagenes imagen3 = new Imagenes("https://example.com/images/myimage3.jpg");
		
	    Set<Imagenes> imagenes = new HashSet<>(Arrays.asList(imagen1,imagen2,imagen3));
	    	
	    			
		Usuario usuario = new Usuario();
		usuario.setNombre("Francisco");
		usuario.setApellido("Macullunco");
		usuario.setEmail("franck@gmail.com");
		usuario.setPassword("12345");
		usuario.setUsername("rukero");
		usuario.setFoto("foto.png");
		usuarioRepositorio.save(usuario);
		
		Tag tag = new Tag();
		tag.setNombre("Ocio");
		tagRepositorio.save(tag);
		
		Publicacion publicacion = new Publicacion();
		publicacion.setTitulo("Mi primera publicacion");
		publicacion.setContenido("primer contenido");
		publicacion.setTag(Collections.singleton(tag));
		publicacion.setImagenes(imagenes);
		publicacion.setUsuario(usuario);
		publicacionRepositorio.save(publicacion);
		
		Publicacion publicacion2 = new Publicacion();
		publicacion2.setTitulo("Mi segundo publicacion");
		publicacion2.setContenido("segundo contenido");
		publicacion2.setTag(Collections.singleton(tag));
		publicacion2.setImagenes(imagenes);
		publicacion2.setUsuario(usuario);
		publicacionRepositorio.save(publicacion2);
		
		List<Publicacion> listaPublicaciones = publicacionRepositorio.findAll();
		
		assertNotNull(listaPublicaciones);
		assertEquals(2, listaPublicaciones.size());
		
	}
	
	@DisplayName("Test para buscar una publicacion")
	@Test
	void buscarPublicacion() {
		

		Imagenes imagen1 = new Imagenes("https://example.com/images/myimage1.jpg");
		Imagenes imagen2 = new Imagenes("https://example.com/images/myimage2.jpg");
		Imagenes imagen3 = new Imagenes("https://example.com/images/myimage3.jpg");
		
	    Set<Imagenes> imagenes = new HashSet<>(Arrays.asList(imagen1,imagen2,imagen3));
	    	
	    			
		Usuario usuario = new Usuario();
		usuario.setNombre("Francisco");
		usuario.setApellido("Macullunco");
		usuario.setEmail("franck@gmail.com");
		usuario.setPassword("12345");
		usuario.setUsername("rukero");
		usuario.setFoto("foto.png");
		usuarioRepositorio.save(usuario);
		
		Tag tag = new Tag();
		tag.setNombre("Ocio");
		tagRepositorio.save(tag);
		
		Publicacion publicacion = new Publicacion();
		publicacion.setTitulo("Mi primera publicacion");
		publicacion.setContenido("primer contenido");
		publicacion.setTag(Collections.singleton(tag));
		publicacion.setImagenes(imagenes);
		publicacion.setUsuario(usuario);
		publicacionRepositorio.save(publicacion);
		
		Publicacion buscarPublicacion = publicacionRepositorio.findById(publicacion.getId()).get();
		
		assertNotNull(buscarPublicacion);
		assertThat(buscarPublicacion.getId()).isNotEqualTo(0);
		
	}
	
	@DisplayName("Test para actualizar una publicacion")
	@Test
	void actualizarPublicacion() {
			    	
		Usuario usuario = new Usuario();
		usuario.setNombre("Francisco");
		usuario.setApellido("Macullunco");
		usuario.setEmail("franck@gmail.com");
		usuario.setPassword("12345");
		usuario.setUsername("rukero");
		usuario.setFoto("foto.png");
		usuarioRepositorio.save(usuario);
		
		Tag tag = new Tag();
		tag.setNombre("Ocio");
		tagRepositorio.save(tag);
		
		Publicacion publicacion = new Publicacion();
		publicacion.setTitulo("Mi primera publicacion");
		publicacion.setContenido("primer contenido");
		publicacion.setTag(Collections.singleton(tag));
		publicacion.setUsuario(usuario);
		publicacion.setImagenes(null);
		publicacionRepositorio.save(publicacion);
		
		
		Publicacion buscarActualizada = publicacionRepositorio.findById(publicacion.getId()).get();
		buscarActualizada.setTitulo("Titulo actualizado");
		
		Publicacion publicacionActualizada = publicacionRepositorio.findById(buscarActualizada.getId()).get();
		
		assertEquals("Titulo actualizado", publicacionActualizada.getTitulo());
		
		
	}
	
	@DisplayName("Test para eliminar publicacion")
	@Test
	void eliminarPublicacion() {
		
		Imagenes imagen1 = new Imagenes("https://example.com/images/myimage1.jpg");
		Imagenes imagen2 = new Imagenes("https://example.com/images/myimage2.jpg");
		Imagenes imagen3 = new Imagenes("https://example.com/images/myimage3.jpg");
		
	    Set<Imagenes> imagenes = new HashSet<>(Arrays.asList(imagen1,imagen2,imagen3));
	    	
	    			
		Usuario usuario = new Usuario();
		usuario.setNombre("Francisco");
		usuario.setApellido("Macullunco");
		usuario.setEmail("franck@gmail.com");
		usuario.setPassword("12345");
		usuario.setUsername("rukero");
		usuario.setFoto("foto.png");
		usuarioRepositorio.save(usuario);
		
		Tag tag = new Tag();
		tag.setNombre("Ocio");
		tagRepositorio.save(tag);
		
		Publicacion publicacion = new Publicacion();
		publicacion.setTitulo("Mi primera publicacion");
		publicacion.setContenido("primer contenido");
		publicacion.setTag(Collections.singleton(tag));
		publicacion.setImagenes(imagenes);
		publicacion.setUsuario(usuario);
		publicacionRepositorio.save(publicacion);
		
		Publicacion publicacion2 = new Publicacion();
		publicacion2.setTitulo("Mi segundo publicacion");
		publicacion2.setContenido("segundo contenido");
		publicacion2.setTag(Collections.singleton(tag));
		publicacion2.setImagenes(imagenes);
		publicacion2.setUsuario(usuario);
		publicacionRepositorio.save(publicacion2);
		
		publicacionRepositorio.delete(publicacion);
		
		Optional<Publicacion> existPublicacion = publicacionRepositorio.findById(publicacion.getId());
		List<Publicacion> listarPublicaciones = publicacionRepositorio.findAll();
		
		assertThat(existPublicacion).isEmpty();
		assertEquals(1, listarPublicaciones.size());
	}
}
