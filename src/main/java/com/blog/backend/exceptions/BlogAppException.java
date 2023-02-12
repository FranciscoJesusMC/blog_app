package com.blog.backend.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogAppException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private HttpStatus estado;
	private String mensaje;
	
	
	public BlogAppException(HttpStatus estado, String mensaje) {
		super();
		this.estado = estado;
		this.mensaje = mensaje;
	}
	

	
	
}
