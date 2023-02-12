package com.blog.backend.dto;

import com.blog.backend.dto.response.UsuarioResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReaccionDTO {

	private String nombre;
	private UsuarioResponseDTO usuario;
}
