package com.blog.backend.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioRequestDTO {

	@NotNull
	@Size(min = 2, message = "EL campo nombre debe contener al menos 2 caracteres")
	private String nombre;
	
	@NotNull
	@Size(min = 2, message = "EL campo apellido debe contener al menos 2 caracteres")
	private String apellido;
	
	@NotNull
	@Size(min = 5, message = "EL campo username debe contener al menos 5 caracteres")
	private String username;
	
	@NotNull(message = "El campo email no puede estar vacio")
	@Email
	private String email;
	
	@NotNull
	@Size(min = 4, message = "EL campo password debe contener al menos 5 caracteres")
	private String password;

}

