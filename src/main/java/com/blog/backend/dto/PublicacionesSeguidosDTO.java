package com.blog.backend.dto;

import java.sql.Date;
import java.util.Set;
import java.util.stream.Collectors;

import com.blog.backend.dto.response.UsuarioResponseDTO;
import com.blog.backend.entity.Publicacion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PublicacionesSeguidosDTO {
	
	private UsuarioResponseDTO usuario;
	public String titulo;
	public String contenido;
	private Date fechaCreacion;
	private Set<ImagenesDTO> imagenes;
	private Set<TagDTO> tag;
	
	public PublicacionesSeguidosDTO(Publicacion publicacion) {
		this.titulo = publicacion.getTitulo();
		this.contenido = publicacion.getContenido();
		this.fechaCreacion = (Date) publicacion.getFechaCreacion();
		this.imagenes = publicacion.getImagenes().stream().map(ImagenesDTO::new).collect(Collectors.toSet());
		this.tag = publicacion.getTag().stream().map(tag -> new TagDTO(tag)).collect(Collectors.toSet());
	}
	

}
