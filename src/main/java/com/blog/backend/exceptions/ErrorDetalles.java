package com.blog.backend.exceptions;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetalles {

	private Date marcaDeTiempo;
	private String mensaje;
	private String detalles;
}
