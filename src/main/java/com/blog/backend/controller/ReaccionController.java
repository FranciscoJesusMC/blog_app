package com.blog.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.backend.dto.ReaccionDTO;
import com.blog.backend.service.ReaccionService;

@RestController
@RequestMapping("/api")
public class ReaccionController {
	
	@Autowired
	private ReaccionService reaccionService;
		
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@PutMapping("/agregarReaccion/publicacion/{publicacionId}/usuario/{usuarioId}/reaccion/{reaccionId}")
	public ResponseEntity<String> agregarReaccionAlaPublicacion(@PathVariable(name = "publicacionId")long publicacionId,@PathVariable(name = "usuarioId")long usuarioId,@PathVariable(name = "reaccionId") int reaccionid){
		reaccionService.agregarReaccion(publicacionId, usuarioId, reaccionid);
		return new ResponseEntity<>("Reaccion agregada con exito",HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@DeleteMapping("/quitarReaccion/publicacion/{publicacionId}/usuario/{usuarioId}")
	public ResponseEntity<String> quitarReaccionAlaPublicacion(@PathVariable(name = "publicacionId")long publicacionId,@PathVariable(name = "usuarioId")long usuarioId){
		reaccionService.quitarReaccion(publicacionId, usuarioId);
		return new ResponseEntity<>("Reaccion quitada con exito",HttpStatus.OK);
	}
	
	@GetMapping("/reacciones/publicacion/{publicacionId}")
	public ResponseEntity<List<ReaccionDTO>> listarReaccionesDePublicacion(@PathVariable(name = "publicacionId")long publicacionId){
		List<ReaccionDTO> reacciones = reaccionService.listarReaccionesDePublicaciones(publicacionId);
		return ResponseEntity.ok(reacciones);
	}

}
