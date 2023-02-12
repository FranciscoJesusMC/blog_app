package com.blog.backend.service;

import java.util.List;

import com.blog.backend.dto.request.ComentarioRequestDTO;
import com.blog.backend.dto.response.ComentarioResponseDTO;

public interface ComentarioService {

	public List<ComentarioResponseDTO> listarComentariosPorPublicacion(long publicacionId); 
	
	public ComentarioResponseDTO crearComentario(long publicacionId,long usuarioId,ComentarioRequestDTO comentarioRequestDTO);
	
	public ComentarioResponseDTO actualizarComentario(long publicacionId,long usuarioId,long comentarioId,ComentarioRequestDTO comentarioRequestDTO);
	
	public void eliminarComentario(long publicacionId,long usuarioId,long comentarioId);

	//CreadordelaPublicacion
	public void eliminarComentarioPorPublicador(long publicacionId,long usuarioId,long comentarioId);
	
	//Admin
	public void eliminarComentarioPorAdmi(long publicacionId,long comentarioId);
}
