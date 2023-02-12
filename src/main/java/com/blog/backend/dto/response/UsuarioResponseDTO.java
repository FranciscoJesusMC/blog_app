package com.blog.backend.dto.response;

import com.blog.backend.entity.Usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioResponseDTO {


	private String nombre;
	private String apellido;
	private String username;
	private String email;
	private String foto;
	
	
	public UsuarioResponseDTO(Usuario usuario) {
		this.nombre = usuario.getNombre();
		this.apellido = usuario.getApellido();
		this.username = usuario.getUsername();
		this.email = usuario.getEmail();
		this.foto = usuario.getFoto();
	}
	
	
}
