package com.blog.backend.dto.request;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComentarioRequestDTO {

	@NotNull(message = "EL campo descripcion no puede estar vacio")
	@Size(min = 2 , max = 30,message = "El nombre debe tener un minimo de 2 caracteres y un maximo de 30")
	@Pattern(regexp = "^[a-zA-Z0-9 ,]+$",message = "No esta permitido el uso de caracteres especiales")
	private String descripcion;

}
