package com.blog.backend.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.blog.backend.entity.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
	
	public Optional<Usuario> findByUsernameOrEmail(String username, String email);
	
	public boolean existsByEmail(String email);
	
	public boolean existsByUsername(String username);
	
	public List<Usuario> findByNombreIgnoreCaseContaining(String nombre);
	
	public Usuario findByUsername(String username);
	
	public Usuario findByEmail(String email);
	
	
}
