package com.blog.backend.dto.response;

import java.sql.Date;
import java.util.Set;

import com.blog.backend.dto.ImagenesDTO;
import com.blog.backend.dto.TagDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicacionResponseDTO {
	
	public String titulo;
	public String contenido;
	private Date fechaCreacion;
	private int reacciones;
	private Set<ImagenesDTO> imagenes;
	private Set<TagDTO> tag;
	
}
