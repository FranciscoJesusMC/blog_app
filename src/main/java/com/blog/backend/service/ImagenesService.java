package com.blog.backend.service;

import com.blog.backend.entity.Imagenes;

public interface ImagenesService {
	
	public Imagenes obtenerImagenPorNombre(String nombre);
	
	public void eliminarImagen(long usuarioId,long publicacionId,String nombre);
	
	public void eliminarImagenByAdmin(String nombre);
	

	
	
}
