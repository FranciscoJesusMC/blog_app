package com.blog.backend.serviceImpl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blog.backend.entity.Imagenes;
import com.blog.backend.entity.Publicacion;
import com.blog.backend.entity.Usuario;
import com.blog.backend.exceptions.BlogAppException;
import com.blog.backend.exceptions.ResourceNotFoundException;
import com.blog.backend.repository.ImagenesRepositorio;
import com.blog.backend.repository.PublicacionRepositorio;
import com.blog.backend.repository.UsuarioRepositorio;
import com.blog.backend.service.ImagenesService;

@Service
public class ImagenesServiceImpl implements ImagenesService {
	
	@Autowired
	private UsuarioRepositorio usuarioRepositiorio;

	@Autowired
	private PublicacionRepositorio publicacionRepositorio;
	
	@Autowired
	private ImagenesRepositorio imagenesRepositorio;
	
		
	@Override
	public Imagenes obtenerImagenPorNombre(String nombre) {
		Imagenes imagen = imagenesRepositorio.findByNombre(nombre).orElseThrow(()-> new ResourceNotFoundException("Imagen", "nombre", nombre));
		return imagen;
	}

	@Override
	public void eliminarImagen(long usuarioId,long publicacionId,String nombre) {
		
		Usuario usuario = usuarioRepositiorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));
		
		Set<Imagenes> listaImagenes = imagenesRepositorio.findByPublicacionId(publicacionId);
		
		Imagenes imagen = imagenesRepositorio.findByNombre(nombre).orElseThrow(()-> new ResourceNotFoundException("Imagen", "nombre", nombre));
		
		if(!publicacion.getUsuario().getId().equals(usuario.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "La publicacion :" + publicacionId + " no pertenece al usuario : " + usuarioId); 
		}
		
		if(listaImagenes.contains(imagen)) {
			imagenesRepositorio.delete(imagen);
			
		}else {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "No se encontro la imagen :" + nombre + " en la publicacion : " + publicacionId);
		}
		
		
		
	}

	@Override
	public void eliminarImagenByAdmin(String nombre) {
		Imagenes imagen = imagenesRepositorio.findByNombre(nombre).orElseThrow(()-> new ResourceNotFoundException("Imagen", "nombre", nombre));
		imagenesRepositorio.delete(imagen);
		
	}



}
