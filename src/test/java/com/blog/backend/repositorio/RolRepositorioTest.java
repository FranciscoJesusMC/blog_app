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

import com.blog.backend.entity.Rol;
import com.blog.backend.repository.RolRepositorio;

@DataJpaTest
public class RolRepositorioTest {

	@Autowired
	private RolRepositorio rolRepositorio;
	
	private Rol rol;
	
	private Rol rol2;
	
	@BeforeEach
	void init() {
		
		rol = new Rol();
		rol.setNombre("ROLE_USER");
		
		
		rol2 = new Rol();
		rol2.setNombre("ROLE_ADMIN");
	
	}

	@DisplayName("Test para crear un rol")
	@Test
	void crearRol() {
				
		Rol nuevoRol =rolRepositorio.save(rol);
		
		assertNotNull(nuevoRol);
		assertThat(rol.getNombre()).isEqualTo("ROLE_USER");
	}
	
	
	@DisplayName("Test para listar roles")
	@Test
	void listarRoles() {
		
		rolRepositorio.save(rol);
		
		rolRepositorio.save(rol2);
		
		List<Rol> listaRoles = rolRepositorio.findAll();
		
		assertNotNull(listaRoles);
		assertEquals(2, listaRoles.size());
		
	}
	
	@DisplayName("Test para buscar un rol")
	@Test
	void buscarRolPorId() {
		
		rolRepositorio.save(rol);
		
		Rol buscarRol = rolRepositorio.findById(rol.getId()).get();
		
		assertNotNull(buscarRol);
		assertThat(buscarRol.getId()).isNotEqualTo(0);
	}
	
	@DisplayName("Test para actualizar un rol")
	@Test
	void actualizarRol() {
		
		rolRepositorio.save(rol);
		
		Rol buscarRol = rolRepositorio.findById(rol.getId()).get();
		buscarRol.setNombre("ROLE_SUPERUSER");
		
		Rol nuevoRol = rolRepositorio.save(buscarRol);
		
		assertNotNull(nuevoRol);
		assertThat(nuevoRol.getNombre()).isEqualTo("ROLE_SUPERUSER");
	}
	
	@DisplayName("Test para eliminar un rol")
	@Test
	void eliminarRol() {
		
		rolRepositorio.save(rol);
	
		rolRepositorio.save(rol2);
		
		rolRepositorio.delete(rol);
		
		Optional<Rol> buscarRol = rolRepositorio.findById(rol.getId());
		List<Rol> listaRoles = rolRepositorio.findAll();
		
		assertThat(buscarRol).isEmpty();
		assertEquals(1, listaRoles.size());
		
	}
}
