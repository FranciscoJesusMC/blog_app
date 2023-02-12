package com.blog.backend.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.backend.entity.Publicacion;
import com.blog.backend.entity.Usuario;

public interface PublicacionRepositorio extends JpaRepository<Publicacion, Long> {

	public List<Publicacion> findByUsuarioId(long usuarioId);
	
	public List<Publicacion> findByTituloIgnoreCaseContaining(String titulo);
	
	public  List<Publicacion> findByTagId(long tagId);
	
	public List<Publicacion> findByUsuarioIn(Set<Usuario> seguidos);
	
	public List<Publicacion> findByReaccionId(long reaccionId);
	
}
