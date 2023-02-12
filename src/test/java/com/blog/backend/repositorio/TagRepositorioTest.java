package com.blog.backend.repositorio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.blog.backend.entity.Tag;
import com.blog.backend.repository.TagRepositorio;

@DataJpaTest
public class TagRepositorioTest {

	@Autowired
	public TagRepositorio tagRepositorio;
	
	private Tag tag;
	
	private Tag tag2;
	
	@BeforeEach
	void init() {
		
		tag = new Tag();
		tag.setNombre("Negocios");
	
		
		tag2 = new Tag();
		tag2.setNombre("Negocios");
	
		
	}
	
	@DisplayName("Test para crear un tag")
	@Test
	void crearTag() {
	
		Tag guardarTag = tagRepositorio.save(tag);
		
		assertNotNull(guardarTag);
		assertThat(guardarTag.getNombre()).isEqualTo("Negocios");
	}
	
	@DisplayName("Test para listar un tag")
	@Test
	void listarTag() {
		
		tagRepositorio.save(tag);
		
		tagRepositorio.save(tag2);
		
		List<Tag> listaTags = tagRepositorio.findAll();
		
		assertNotNull(listaTags);
		assertEquals(2, listaTags.size());
		
	}
	
	@DisplayName("Test para buscar un tag")
	@Test
	void buscarTag() {
		
		tagRepositorio.save(tag);
		
		Tag buscarTag = tagRepositorio.findById(tag.getId()).get();
		
		assertNotNull(buscarTag);
		assertThat(buscarTag.getId()).isNotEqualTo(0);
		
	}
	
	@DisplayName("Test para actualizar un tag")
	@Test
	void actualizarTag() {
		
		tagRepositorio.save(tag);
		
		Tag buscarTag = tagRepositorio.findById(tag.getId()).get();
		buscarTag.setNombre("Ocio");
		
		Tag actualizarTag = tagRepositorio.save(buscarTag);
		
		assertNotNull(actualizarTag);
		assertThat(actualizarTag.getNombre()).isEqualTo("Ocio");
		
	}
	
	@DisplayName("Test para eliminar un tag")
	@Test
	void eliminarTag() {
		
		tagRepositorio.save(tag);
		
		tagRepositorio.save(tag2);
		
		tagRepositorio.delete(tag);
		Optional<Tag> existTag = tagRepositorio.findById(tag.getId());
		List<Tag> listaTags = tagRepositorio.findAll();
		
		assertThat(existTag).isEmpty();
		assertEquals(1, listaTags.size());
		
	}

}
