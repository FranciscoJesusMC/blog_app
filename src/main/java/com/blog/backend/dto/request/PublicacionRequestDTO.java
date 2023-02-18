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

	@NotNull
	@NotEmpty(message = "EL campo titulo no puede estar vacio")
	@Size(min = 2 , max = 50,message = "El nombre debe tener un minimo de 2 caracteres y un maximo de 50")
	@Pattern(regexp = "^[a-zA-Z0-9 ,]+$",message = "No esta permitido el uso de caracteres especiales")
	private String titulo;
	
	@NotNull
	@NotEmpty(message = "EL campo contenido no puede estar vacio")
	@Size(min = 2 , max = 500,message = "El contenido debe tener un minimo de 2 caracteres y un maximo de 500")
	@Pattern(regexp = "^[a-zA-Z0-9 ,]+$",message = "No esta permitido el uso de caracteres especiales")
	private String contenido;
	
	private List<String> tags;

}
