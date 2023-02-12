package com.blog.backend.repository;


import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.backend.entity.Imagenes;

public interface ImagenesRepositorio extends JpaRepository<Imagenes, Long> {

	public Optional<Imagenes> findByNombre(String nombre);
	
	public Set<Imagenes> findByPublicacionId(long publicacionId);
	
	public Imagenes findByUri(String uri);
	
	
}
