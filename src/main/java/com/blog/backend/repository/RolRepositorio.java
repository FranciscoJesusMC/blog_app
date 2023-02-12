package com.blog.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.backend.entity.Rol;

public interface RolRepositorio extends JpaRepository<Rol, Long> {

	public Optional<Rol> findByNombre(String nombre);

	public boolean existsByNombre(String nombre);
}
