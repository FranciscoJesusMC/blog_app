package com.blog.backend.service;

import java.util.List;

import com.blog.backend.dto.TagDTO;

public interface TagService {

	public TagDTO crearHashtag (TagDTO tagDTO);
	
	public List<TagDTO> listarTags();
	
	public void eliminarTag(long tagId);
	
	public void eliminarTagDeLaPublicacion(long usuarioId,long publicacionId,long tagId);
}
