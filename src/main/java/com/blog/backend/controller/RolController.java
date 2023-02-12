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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.backend.dto.RolDTO;
import com.blog.backend.service.RolService;

@RestController
@RequestMapping("/api/rol")
public class RolController {

	@Autowired
	private RolService rolService;
		
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping
	public ResponseEntity<List<RolDTO>> listarRoles(){
		List<RolDTO> roles = rolService.listarRoles();
		return ResponseEntity.ok(roles);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/crearRol")
	public ResponseEntity<RolDTO> crearRol(@RequestBody RolDTO rolDTO){
		RolDTO rol = rolService.crearRol(rolDTO);
		return new ResponseEntity<>(rol,HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{rolId}")
	public ResponseEntity<String> eliminarRol(@PathVariable(name = "rolId")long rolId){
		rolService.eliminarRol(rolId);
		return new ResponseEntity<>("Rol eliminado con exito",HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/agregarRol/{rolId}/usuario/{usuarioId}")
	public ResponseEntity<String> agregarRolaUsuario(@PathVariable(name = "usuarioId")long usuarioId,@PathVariable(name = "rolId")long rolId){
		rolService.agregarRolaUsuario(usuarioId, rolId);
		return new ResponseEntity<>("Rol agregado correctamente",HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/eliminarRol/{rolId}/usuario/{usuarioId}")
	public ResponseEntity<String> quitarRolaUsuario(@PathVariable(name = "usuarioId")long usuarioId,@PathVariable(name = "rolId")long rolId){
		rolService.eliminarRolaUsuario(usuarioId, rolId);
		return new ResponseEntity<>("Rol eliminado correctamente",HttpStatus.OK);
	}
}
