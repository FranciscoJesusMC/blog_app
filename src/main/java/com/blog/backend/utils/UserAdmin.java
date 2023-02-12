package com.blog.backend.utils;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.blog.backend.entity.Rol;
import com.blog.backend.entity.Usuario;
import com.blog.backend.repository.RolRepositorio;
import com.blog.backend.repository.UsuarioRepositorio;

@Component
public class UserAdmin {
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private RolRepositorio rolRepositorio;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@PostConstruct
	private void crearRoles() {
		Rol roleAdmin = rolRepositorio.findByNombre("ROLE_ADMIN").orElse(null);
		if(roleAdmin == null) {
			roleAdmin = new Rol();
			roleAdmin.setNombre("ROLE_ADMIN");
			rolRepositorio.save(roleAdmin);
		}
		
		Rol roleUser = rolRepositorio.findByNombre("ROLE_USER").orElse(null);
		if(roleUser == null) {
			roleUser = new Rol();
			roleUser.setNombre("ROLE_USER");
			rolRepositorio.save(roleUser);
		}
		
		Rol roleModerador = rolRepositorio.findByNombre("ROLE_MODERATOR").orElse(null);
		if(roleModerador == null) {
			roleModerador = new Rol();
			roleModerador.setNombre("ROLE_MODERATOR");
			rolRepositorio.save(roleModerador);
		}
		
		if(usuarioRepositorio.findByUsername("admin") == null) {
			Usuario userAdmin = new Usuario();
			userAdmin.setNombre("admin");
			userAdmin.setApellido("admin");
			userAdmin.setEmail("admin@gmail.com");
			userAdmin.setUsername("admin");
			userAdmin.setPassword(passwordEncoder.encode("12345"));
			userAdmin.setFoto(null);
			
			Rol role = rolRepositorio.findByNombre("ROLE_ADMIN").get();
			userAdmin.setRol(Collections.singleton(role));
		
			usuarioRepositorio.save(userAdmin);
		}
	
	}
}
