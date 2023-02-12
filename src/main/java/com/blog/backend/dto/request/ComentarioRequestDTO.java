package com.blog.backend.dto.request;


import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComentarioRequestDTO {

	@NotNull(message = "EL campo descripcion no puede estar vacio")
	private String descripcion;

}
