package com.blog.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.backend.dto.TagDTO;
import com.blog.backend.service.TagService;

@RestController
@RequestMapping("/api/tag")
public class TagController {
	
	@Autowired
	private TagService tagService;
	

	@GetMapping
	public ResponseEntity<List<TagDTO>> listarTags(){
		List<TagDTO> tags = tagService.listarTags();
		return ResponseEntity.ok(tags);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@PostMapping
	public ResponseEntity<TagDTO> crearTag(@RequestBody TagDTO tagDTO){
		TagDTO tag = tagService.crearHashtag(tagDTO);
		return new ResponseEntity<>(tag,HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{tagId}")
	public ResponseEntity<String> eliminarTag(@PathVariable(name = "tagId")long hashTagId){
		tagService.eliminarTag(hashTagId);
		return new ResponseEntity<>("Tag eliminado con exito",HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@DeleteMapping("/{tagId}/usuario/{usuarioId}/publicacion/{publicacionId}")
	public ResponseEntity<String> eliminarTagDePublicacion(@PathVariable(name = "tagId")long tagId,@PathVariable(name = "usuarioId")long usuarioId,@PathVariable(name = "publicacionId")long publicacionId){
		tagService.eliminarTagDeLaPublicacion(usuarioId,publicacionId,tagId);
		return new ResponseEntity<>("Tag eliminado de la publicacion con exito",HttpStatus.OK);
	}
}
