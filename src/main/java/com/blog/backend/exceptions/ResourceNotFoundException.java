package com.blog.backend.exceptions;


import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ResponseStatus
public class ResourceNotFoundException extends RuntimeException {
	

	private static final long serialVersionUID = 1L;
	private String nombreDelRecurso;
	private String nombreDelCampo;
	private long valorDelCampo;
	private String nombre;
	
	
	public ResourceNotFoundException(String nombreDelRecurso, String nombreDelCampo, long valorDelCampo) {
		super(String.format("%s no encontrado para %s: '%s'", nombreDelRecurso,nombreDelCampo,valorDelCampo));
		this.nombreDelRecurso = nombreDelRecurso;
		this.nombreDelCampo = nombreDelCampo;
		this.valorDelCampo = valorDelCampo;
	}


	public ResourceNotFoundException(String nombreDelRecurso, String nombreDelCampo, String nombre) {
		super(String.format("%s no encontrado para %s: '%s'", nombreDelRecurso,nombreDelCampo,nombre));
		this.nombreDelRecurso = nombreDelRecurso;
		this.nombreDelCampo = nombreDelCampo;
		this.nombre = nombre;
	}

	

	
	

}
