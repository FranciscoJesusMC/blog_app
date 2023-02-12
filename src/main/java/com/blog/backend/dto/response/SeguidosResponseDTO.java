package com.blog.backend.dto.response;

import java.util.Set;
import java.util.stream.Collectors;

import com.blog.backend.entity.Usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SeguidosResponseDTO {


	private Set<UsuarioResponseDTO> seguidos;
	
	public SeguidosResponseDTO(Set<Usuario> seguidos) {
		this.seguidos = seguidos.stream().map(usuario -> new UsuarioResponseDTO(usuario)).collect(Collectors.toSet());
	}
}
