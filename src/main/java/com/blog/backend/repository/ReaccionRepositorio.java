package com.blog.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.backend.entity.Reaccion;

public interface ReaccionRepositorio extends JpaRepository<Reaccion, Integer>{
	
	public List<Reaccion> findByPublicacionId(long publicacionId);
	
	public Optional<Reaccion> findByUsuarioIdAndPublicacionId(long usuarioId,long publicacion);

}
