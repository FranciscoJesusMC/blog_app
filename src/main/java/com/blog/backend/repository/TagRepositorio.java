package com.blog.backend.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.backend.entity.Tag;

public interface TagRepositorio extends JpaRepository<Tag, Long> {
	
	public Optional<Tag> findByNombre(String nombre);
	
	public Set<Tag> findByPublicacionId(long publicacionId);

}
