package com.blog.backend.dto.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicacionRequestDTO {

	@NotNull(message = "El campo titulo no puede estar vacio")
	private String titulo;
	private String contenido;
	private List<String> tags;

}
