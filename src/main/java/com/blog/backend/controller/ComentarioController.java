package com.blog.backend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.backend.dto.request.ComentarioRequestDTO;
import com.blog.backend.dto.response.ComentarioResponseDTO;
import com.blog.backend.service.ComentarioService;

@RestController
@RequestMapping("/api")
public class ComentarioController {

	@Autowired
	private ComentarioService comentarioService;
	
	@GetMapping("/publicacion/{publicacionId}/comentarios")
	public ResponseEntity<List<ComentarioResponseDTO>> listarComentario(@PathVariable(name = "publicacionId")long publicacionId){
		List<ComentarioResponseDTO> comentarios = comentarioService.listarComentariosPorPublicacion(publicacionId);
		return ResponseEntity.ok(comentarios);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/publicacion/{publicacionId}/usuario/{usuarioId}/crearComentario")
	public ResponseEntity<ComentarioResponseDTO> crearComentario(@PathVariable(name = "publicacionId")long publicacionId,@PathVariable(name = "usuarioId")long usuarioId,@Valid @RequestBody ComentarioRequestDTO comentarioRequestDTO){
		ComentarioResponseDTO comentario = comentarioService.crearComentario(publicacionId, usuarioId,comentarioRequestDTO);
		return new ResponseEntity<>(comentario,HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/publicacion/{publicacionId}/usuario/{usuarioId}/comentario/{comentarioId}")
	public ResponseEntity<ComentarioResponseDTO> actualizarComentario(@PathVariable(name = "publicacionId")long publicacionId,@PathVariable(name = "usuarioId")long usuarioId,@PathVariable(name = "comentarioId")long comentarioId, @Valid @RequestBody ComentarioRequestDTO comentarioRequestDTO){
		ComentarioResponseDTO comentario = comentarioService.actualizarComentario(publicacionId, usuarioId,comentarioId, comentarioRequestDTO);
		return ResponseEntity.ok(comentario);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping("/eliminarComentarioPorComentarista/publicacion/{publicacionId}/usuario/{usuarioId}/comentario/{comentarioId}")
	public ResponseEntity<String> eliminarComentario(@PathVariable(name = "publicacionId")long publicacionId,@PathVariable(name = "usuarioId")long usuarioId,@PathVariable(name = "comentarioId")long comentarioId){
		comentarioService.eliminarComentario(publicacionId, usuarioId,comentarioId);
		return new ResponseEntity<>("Comentario eliminado con exito",HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping("/eliminarComentarioPorPublicador/publicacion/{publicacionId}/usuario/{usuarioId}/comentario/{comentarioId}")
	public ResponseEntity<String> eliminarComentarioPorPublicacdor(@PathVariable(name = "publicacionId")long publicacionId,@PathVariable(name = "usuarioId")long usuarioId,@PathVariable(name = "comentarioId")long comentarioId){
		comentarioService.eliminarComentarioPorPublicador(publicacionId, usuarioId, comentarioId);
		return new ResponseEntity<>("Comentario eliminado con exito por publicador",HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/eliminarComentarioPorAdmin/publicacion/{publicacionId}/comentario/{comentarioId}")
	public ResponseEntity<String> elimiarComentarioPorAdmin(@PathVariable(name = "publicacionId")long publicacionId,@PathVariable(name = "comentarioId")long comentarioId){
		comentarioService.eliminarComentarioPorAdmi(publicacionId, comentarioId);
		return new ResponseEntity<>("Comentario elimiado con exito por admin",HttpStatus.OK);
	}
}
