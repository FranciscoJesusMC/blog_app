package com.blog.backend.dto.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicacionRequestDTO {

	@NotNull(message = "El campo titulo no puede estar vacio")
	@NotEmpty(message = "EL campo nombre no puede estar vacio")
	@Size(min = 2 , max = 30,message = "El nombre debe tener un minimo de 2 caracteres y un maximo de 30")
	@Pattern(regexp = "^[a-zA-Z]+$",message = "Solo esta permitido ingresar letras")
	private String titulo;
	
	@NotEmpty(message = "EL campo nombre no puede estar vacio")
	@Size(min = 2 , max = 30,message = "El nombre debe tener un minimo de 2 caracteres y un maximo de 30")
	@Pattern(regexp = "^[a-zA-Z]+$",message = "Solo esta permitido ingresar letras")
	private String contenido;
	
	private List<String> tags;

}
