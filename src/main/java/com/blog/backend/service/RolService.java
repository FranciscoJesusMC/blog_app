package com.blog.backend.service;

import com.blog.backend.dto.RolDTO;

import java.util.List;

public interface RolService {

	public List<RolDTO> listarRoles();
	
	public RolDTO crearRol(RolDTO rolDTO);
	
	public void eliminarRol(long rolId);
	
	public void agregarRolaUsuario(long usuarioId,long rolId);
	
	public void eliminarRolaUsuario(long usuarioId,long rolId);
	
}
