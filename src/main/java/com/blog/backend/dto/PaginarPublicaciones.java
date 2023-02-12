package com.blog.backend.dto;

import java.util.List;

import com.blog.backend.dto.response.PublicacionResponseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaginarPublicaciones {

	private List<PublicacionResponseDTO> publicaciones;
	private int numeroDePaginas;
	private int numeroDeElementos;
	private long totalDeElementos;
	private int totalDePaginas;
	private boolean ultima;
}
