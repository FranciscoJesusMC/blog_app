package com.blog.backend.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.blog.backend.entity.Tag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TagDTO {

	@NotNull
	@NotEmpty(message = "EL campo tag no puede estar vacio")
	@Size(min = 2 , max = 30,message = "El tag debe tener un minimo de 2 caracteres y un maximo de 30")
	@Pattern(regexp = "^[a-zA-Z0-9 ]+$",message = "Solo esta permitido ingresar letras")
	private String nombre;

	public TagDTO(Tag tag) {
		this.nombre = tag.getNombre();
	}

	
	

	
}
