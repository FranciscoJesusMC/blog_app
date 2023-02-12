package com.blog.backend.service;

import java.util.List;

import com.blog.backend.dto.request.UsuarioRequestDTO;
import com.blog.backend.dto.response.SeguidoresResponseDTO;
import com.blog.backend.dto.response.SeguidosResponseDTO;
import com.blog.backend.dto.response.UsuarioResponseDTO;

public interface UsuarioService {

	public List<UsuarioResponseDTO> listarUsuarios();
	
	public UsuarioResponseDTO buscarUsuarioPortId(long usuarioId);
	
	public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO usuarioRequestDTO);
	
	public UsuarioResponseDTO actualizarUsuario(long usuarioId,UsuarioRequestDTO usuarioRequestDTO);
	
	public void eliminarUsuario(long usuarioId);
	
	public void actualizarFotoDePerfil(long usuarioId, long publicacionId,long imagenId);
	
	//Buscadores
	
	public List<UsuarioResponseDTO> buscarUsuarioPorNombre(String nombre);
	
	//Seguir y dejar de seguir
	
	public void seguir(long usuario1, long usuario2);
	
	public void dejarDeSeguir(long usuario1, long usuario2);

	//Listar Segudiores y seguidos
	
	public SeguidoresResponseDTO listarSeguidores(long usuarioId);
	
	public SeguidosResponseDTO listarSeguidos(long usuarioId);
	
	
}
