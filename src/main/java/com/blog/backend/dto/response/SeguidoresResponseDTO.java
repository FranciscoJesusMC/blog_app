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
public class SeguidoresResponseDTO {

	private Set<UsuarioResponseDTO> seguidores;

	public SeguidoresResponseDTO(Set<Usuario> seguidores) {
		this.seguidores = seguidores.stream().map(UsuarioResponseDTO::new).collect(Collectors.toSet());
	}
	
	
}
