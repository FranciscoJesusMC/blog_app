package com.blog.backend.service;

import java.util.List;

import com.blog.backend.dto.ReaccionDTO;

public interface ReaccionService {
	
	public void agregarReaccion(long publicacionId,long usuarioId,int reaccionId);
	
	public void quitarReaccion(long publicacionId,long usuarioId);
	
	public List<ReaccionDTO> listarReaccionesDePublicaciones(long publicacionId);
	

}
