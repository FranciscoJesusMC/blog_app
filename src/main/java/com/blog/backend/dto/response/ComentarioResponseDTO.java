package com.blog.backend.dto.response;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComentarioResponseDTO {
	
	private String descripcion;
	private Date fechaCreacion;
	private UsuarioResponseDTO usuario;

}
