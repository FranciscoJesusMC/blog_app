package com.blog.backend.exceptions;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDetalles {

	private Date marcaDeTiempo;
	private String mensaje;
	private String detalles;
	
	
	public ErrorDetalles(Date marcaDeTiempo, String mensaje, String detalles) {
		super();
		this.marcaDeTiempo = marcaDeTiempo;
		this.mensaje = mensaje;
		this.detalles = detalles;
	}
	
	

	
}
