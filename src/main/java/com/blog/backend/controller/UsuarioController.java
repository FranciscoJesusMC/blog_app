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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.backend.dto.request.UsuarioRequestDTO;
import com.blog.backend.dto.response.SeguidoresResponseDTO;
import com.blog.backend.dto.response.SeguidosResponseDTO;
import com.blog.backend.dto.response.UsuarioResponseDTO;
import com.blog.backend.service.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(){
		List<UsuarioResponseDTO> usuarios = usuarioService.listarUsuarios();
		return ResponseEntity.ok(usuarios);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/{usuarioId}")
	public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable(name = "usuarioId")long usuarioId){
		UsuarioResponseDTO usuario = usuarioService.buscarUsuarioPortId(usuarioId);
		return ResponseEntity.ok(usuario);
		
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@PutMapping("/{usuarioId}")
	public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable(name = "usuarioId")long usuarioId,@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO){
		UsuarioResponseDTO usuario = usuarioService.actualizarUsuario(usuarioId, usuarioRequestDTO);
		return ResponseEntity.ok(usuario);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{usuarioId}")
	public ResponseEntity<String> eliminarUsuario(@PathVariable(name = "usuarioId")long usuarioId){
		usuarioService.eliminarUsuario(usuarioId);
		return new ResponseEntity<>("Usuario eliminado con exito",HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/{usuarioId}/publicacion/{publicacionId}/imagen/{imagenId}")
	public ResponseEntity<String> actualizarFotoPerfil(@PathVariable(name = "usuarioId")long usuarioId,@PathVariable(name = "publicacionId")long publicacionId,@PathVariable(name = "imagenId")long imagenId){
		usuarioService.actualizarFotoDePerfil(usuarioId, publicacionId, imagenId);
		return new ResponseEntity<>("Foto actualizada con exito",HttpStatus.NO_CONTENT);
	}
	
	//Buscadores
	@GetMapping("/buscarUsuario/{nombre}")
	public ResponseEntity<List<UsuarioResponseDTO>> buscarUsuarioPorNombre(@PathVariable(name = "nombre")String nombre){
		List<UsuarioResponseDTO> usuarios = usuarioService.buscarUsuarioPorNombre(nombre);
		return ResponseEntity.ok(usuarios);
	}
	
	
	//Seguimiento
	@PutMapping("/{usuario1}/seguir/usuario/{usuario2}")
	public ResponseEntity<String> seguir(@PathVariable(name = "usuario1")long usuario1,@PathVariable(name = "usuario2")long usuario2){
		usuarioService.seguir(usuario1, usuario2);
		return new ResponseEntity<>("Comenzaste a seguir a este usuario",HttpStatus.OK);
	}
	
	@PutMapping("/{usuario1}/dejarDeSeguir/usuario/{usuario2}")
	public ResponseEntity<String> dejarDeSeguir(@PathVariable(name = "usuario1")long usuario1,@PathVariable(name = "usuario2")long usuario2){
		usuarioService.dejarDeSeguir(usuario1, usuario2);
		return new ResponseEntity<>("Dejaste de seguir a este usuario",HttpStatus.OK);
	}
	
	@GetMapping("/{usuarioId}/listarSeguidores")
	public ResponseEntity<SeguidoresResponseDTO> listarSeguidoresDelUsuario(@PathVariable(name = "usuarioId")long usuarioId){
		SeguidoresResponseDTO seguidores = usuarioService.listarSeguidores(usuarioId);
		return ResponseEntity.ok(seguidores);
	}
	
	@GetMapping("/{usuarioId}/listarSeguidos")
	public ResponseEntity<SeguidosResponseDTO> listarSeguidosDelUsuario(@PathVariable(name = "usuarioId")long usuarioId){
		SeguidosResponseDTO seguidores = usuarioService.listarSeguidos(usuarioId);
		return ResponseEntity.ok(seguidores);
	}

}
