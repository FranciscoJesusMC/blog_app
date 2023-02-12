package com.blog.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.backend.entity.Comentario;

public interface ComentarioRepositorio extends JpaRepository<Comentario, Long> {
	
	public List<Comentario> findByPublicacionId(long publicacionId);
	
	public List<Comentario> findByUsuarioId(long usuarioId);
	


}
