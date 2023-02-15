package com.blog.backend.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioRequestDTO {

	@NotNull
	@NotEmpty(message = "EL campo nombre no puede estar vacio")
	@Size(min = 2 , max = 30,message = "El nombre debe tener un minimo de 2 caracteres y un maximo de 30")
	@Pattern(regexp = "^[a-zA-Z]+$",message = "Solo esta permitido ingresar letras")
	private String nombre;
	
	@NotNull
	@NotEmpty(message = "EL campo nombre no puede estar vacio")
	@Size(min = 2 , max = 30,message = "El nombre debe tener un minimo de 2 caracteres y un maximo de 30")
	@Pattern(regexp = "^[a-zA-Z]+$",message = "Solo esta permitido ingresar letras")
	private String apellido;
	
	@NotNull
	@NotEmpty(message = "EL campo nombre no puede estar vacio")
	@Size(min = 2 , max = 30,message = "El nombre debe tener un minimo de 2 caracteres y un maximo de 30")
	@Pattern(regexp = "^[a-zA-Z]+$",message = "Solo esta permitido ingresar letras")
	private String username;
	
	@NotNull(message = "El campo email no puede estar vacio")
	@Email
	private String email;
	
	@NotNull
	@NotEmpty(message = "EL campo nombre no puede estar vacio")
	@Size(min = 5 , max = 30,message = "El nombre debe tener un minimo de 2 caracteres y un maximo de 30")
	@Pattern(regexp = "^[a-zA-Z]+$",message = "Solo esta permitido ingresar letras")
	private String password;

}

