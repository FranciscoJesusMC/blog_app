package com.blog.backend.dto;

import lombok.Setter;

import com.blog.backend.entity.Imagenes;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class ImagenesDTO {

	private String nombre;
	private String uri;
	private String tipo;
	
	
	public ImagenesDTO(Imagenes imagenes) {
		this.nombre = imagenes.getNombre();
		this.uri = imagenes.getUri();
		this.tipo = imagenes.getTipo();
	}
	
	
}
