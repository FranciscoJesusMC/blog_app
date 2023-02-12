package com.blog.backend.serviceImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blog.backend.dto.TagDTO;
import com.blog.backend.entity.Publicacion;
import com.blog.backend.entity.Tag;
import com.blog.backend.entity.Usuario;
import com.blog.backend.exceptions.BlogAppException;
import com.blog.backend.exceptions.ResourceNotFoundException;
import com.blog.backend.repository.PublicacionRepositorio;
import com.blog.backend.repository.TagRepositorio;
import com.blog.backend.repository.UsuarioRepositorio;
import com.blog.backend.service.TagService;

@Service
public class TagsServiceImpl implements TagService {
	
	@Autowired
	private TagRepositorio tagRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private PublicacionRepositorio publicacionRepositorio;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AcccionesAdmiServiceImpl acciones;
	
	
	private Tag mapearEntidad(TagDTO tagDTO) {
		Tag tag = modelMapper.map(tagDTO, Tag.class);
		return tag;
	}
		
	private TagDTO mapearDTO(Tag tag) {
		TagDTO tagDTO  = modelMapper.map(tag, TagDTO.class);
		return tagDTO;
	}

	@Override
	public List<TagDTO> listarTags() {
		List<Tag> tags = tagRepositorio.findAll();
		if(tags.isEmpty()) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "No hay tags registrados");
		}
		return tags.stream().map(hashTag -> mapearDTO(hashTag)).collect(Collectors.toList());
	}

	@Override
	public void eliminarTag(long tagId) {
		Tag tag = tagRepositorio.findById(tagId).orElseThrow(()-> new ResourceNotFoundException("Tag", "id", tagId));

		List<Publicacion> publicaciones = publicacionRepositorio.findByTagId(tagId);
		
		for(int i = 0; i<publicaciones.size();i++) {
			if(publicaciones.get(i).getTag().contains(tag)) {
				publicaciones.get(i).getTag().remove(tag);
				publicacionRepositorio.saveAll(publicaciones);
				tagRepositorio.delete(tag);
				acciones.realizarAccion("Eliminar Tag",tag.getId(), "Tag eliminado exitosamente");
				
			}else {
				
				tagRepositorio.delete(tag);
				acciones.realizarAccion("Eliminar Tag",tag.getId(), "Tag eliminado exitosamente");
				
			}
		}
		
	}

	@Override
	public TagDTO crearHashtag(TagDTO tagDTO) {
		Tag tag = mapearEntidad(tagDTO);
		
		Tag nuevoTag = tagRepositorio.save(tag);
		
		TagDTO guardarTag = mapearDTO(nuevoTag);
		acciones.realizarAccion("Crear Tag",tag.getId(), "Tag creado exitosamente");
		
		return guardarTag;
	}

	@Override
	public void eliminarTagDeLaPublicacion(long usuarioId, long publicacionId, long tagId) {
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		
		Publicacion publicacion =publicacionRepositorio.findById(publicacionId).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));
		
		Set<Tag> listaTags =  tagRepositorio.findByPublicacionId(publicacionId);

		Tag tag = tagRepositorio.findById(tagId).orElseThrow(()-> new ResourceNotFoundException("Tag", "id", tagId));
		
		
		if(!publicacion.getUsuario().getId().equals(usuario.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "La publicacion no pertenece al usuario");
		}
		
		if(listaTags.contains(tag)) {
			publicacion.removeTag(tag);
			publicacionRepositorio.save(publicacion);
		}
		
		
	}

}
