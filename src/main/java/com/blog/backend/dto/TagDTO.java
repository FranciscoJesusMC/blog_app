package com.blog.backend.dto;

import com.blog.backend.entity.Tag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TagDTO {

	private String nombre;

	public TagDTO(Tag tag) {
		this.nombre = tag.getNombre();
	}

	
	

	
}
