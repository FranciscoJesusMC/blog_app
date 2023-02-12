package com.blog.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.backend.entity.Imagenes;
import com.blog.backend.service.ImagenesService;

@RestController
@RequestMapping("/api/imagenes")
public class ImagenesController {

	@Autowired
	private ImagenesService imagenesService;
	
	@GetMapping("/obtenerImagen/{nombre}")
	public ResponseEntity<Resource> obtenerImagen(@PathVariable(name = "nombre")String nombre){
		Imagenes imagenes = imagenesService.obtenerImagenPorNombre(nombre);
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(imagenes.getTipo()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imagenes.getNombre() + "\"")
				.body(new ByteArrayResource(imagenes.getData()));
		
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping("/usuario/{usuarioId}/publicacion/{publicacionId}/eliminarImagen/{nombre}")
	public ResponseEntity<String> eliminarImagen(@PathVariable(name = "usuarioId")long usuarioId,@PathVariable(name = "publicacionId")long publicacionId,@PathVariable(name = "nombre")String nombre){
		imagenesService.eliminarImagen(usuarioId,publicacionId,nombre);
		return new ResponseEntity<>("Imagen eliminada con exito",HttpStatus.OK);
	}
	
	@PreAuthorize("hasROLE('ROLE_ADMIN')")
	@DeleteMapping("/eliminarImagen/{nombre}")
	public ResponseEntity<String> eliminarImagenByAdmin(@PathVariable(name = "nombre")String nombre){
		imagenesService.eliminarImagenByAdmin(nombre);
		return new ResponseEntity<>("Imagen eliminada by admin" , HttpStatus.OK);
	}
	
}
