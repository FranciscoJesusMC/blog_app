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

import com.blog.backend.entity.Comentario;
import com.blog.backend.entity.Imagenes;
import com.blog.backend.entity.Publicacion;
import com.blog.backend.entity.Tag;
import com.blog.backend.entity.Usuario;
import com.blog.backend.repository.ComentarioRepositorio;
import com.blog.backend.repository.PublicacionRepositorio;
import com.blog.backend.repository.TagRepositorio;
import com.blog.backend.repository.UsuarioRepositorio;

@DataJpaTest
public class ComentarioRepositorioTest {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private PublicacionRepositorio publicacionRepositorio;
	
	@Autowired
	private TagRepositorio tagRepositorio;
	
	@Autowired
	private ComentarioRepositorio comentarioRepositorio;
	
	
	
	@DisplayName("Test para crear comentario")
	@Test
	void crearComentario (){
		
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
		
		
		Comentario comentario = new Comentario();
		comentario.setDescripcion("mi primer comentario");
		comentario.setUsuario(usuario);
		comentario.setPublicacion(publicacion);
		
		Comentario guardarComentario = comentarioRepositorio.save(comentario);
		
		assertNotNull(guardarComentario);
		assertThat(guardarComentario.getDescripcion()).isEqualTo("mi primer comentario");
		assertThat(comentario.getUsuario().getId()).isEqualTo(usuario.getId());
		assertThat(publicacion.getComentario().contains(guardarComentario));
		
	}
	
	@DisplayName("Test para listar comentarios")
	@Test
	void listarComentarios(){
		

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
		
		
		Comentario comentario = new Comentario();
		comentario.setDescripcion("mi primer comentario");
		comentario.setUsuario(usuario);
		comentario.setPublicacion(publicacion);
		comentarioRepositorio.save(comentario);
		
		Comentario comentario2 = new Comentario();
		comentario2.setDescripcion("mi segundo comentario");
		comentario2.setUsuario(usuario);
		comentario2.setPublicacion(publicacion);
		comentarioRepositorio.save(comentario2);
		
		
		List<Comentario> listarComentarios = comentarioRepositorio.findByPublicacionId(publicacion.getId());
		
		assertNotNull(listarComentarios);
		assertEquals(2, listarComentarios.size());
	}
	
	@DisplayName("Test para buscar comentario por id")
	@Test
	void bucarComentarioPorId(){
		
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
		
		
		Comentario comentario = new Comentario();
		comentario.setDescripcion("mi primer comentario");
		comentario.setUsuario(usuario);
		comentario.setPublicacion(publicacion);
		comentarioRepositorio.save(comentario);
		
		Comentario comentario2 = new Comentario();
		comentario2.setDescripcion("mi segundo comentario");
		comentario2.setUsuario(usuario);
		comentario2.setPublicacion(publicacion);
		comentarioRepositorio.save(comentario2);
		
		Comentario buscarComentario =  comentarioRepositorio.findById(comentario.getId()).get();
		
		assertNotNull(buscarComentario);
		assertEquals("mi primer comentario", buscarComentario.getDescripcion());
		assertThat(publicacion.getComentario().contains(buscarComentario));
		
		
	}
	@DisplayName("Test para actualizar comentario")
	@Test
	void actualizarComentario(){
		
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
		
		
		Comentario comentario = new Comentario();
		comentario.setDescripcion("mi primer comentario");
		comentario.setUsuario(usuario);
		comentario.setPublicacion(publicacion);
		comentarioRepositorio.save(comentario);
		
		Comentario comentario2 = new Comentario();
		comentario2.setDescripcion("mi segundo comentario");
		comentario2.setUsuario(usuario);
		comentario2.setPublicacion(publicacion);
		comentarioRepositorio.save(comentario2);
		
		Comentario buscarComentario = comentarioRepositorio.findById(comentario.getId()).get();
		buscarComentario.setDescripcion("comentario actualizado");
		
		Comentario actualiazarComentario = comentarioRepositorio.save(buscarComentario);
		
		assertNotNull(actualiazarComentario);
		assertEquals("comentario actualizado", actualiazarComentario.getDescripcion());
		
		
		
		
	}
	@DisplayName("Test para eliminar comentario")
	@Test
	void eliminarComentario(){
		
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
		
		
		Comentario comentario = new Comentario();
		comentario.setDescripcion("mi primer comentario");
		comentario.setUsuario(usuario);
		comentario.setPublicacion(publicacion);
		comentarioRepositorio.save(comentario);
		
		Comentario comentario2 = new Comentario();
		comentario2.setDescripcion("mi segundo comentario");
		comentario2.setUsuario(usuario);
		comentario2.setPublicacion(publicacion);
		comentarioRepositorio.save(comentario2);
		
		
		comentarioRepositorio.delete(comentario);
		Optional<Comentario> existComentario = comentarioRepositorio.findById(comentario.getId());
		List<Comentario> listarComentarios = comentarioRepositorio.findAll();
		
		assertThat(existComentario).isEmpty();
		assertEquals(1, listarComentarios.size());
		
	}
}
