package com.blog.backend.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.backend.dto.PaginarPublicaciones;
import com.blog.backend.dto.PublicacionesSeguidosDTO;
import com.blog.backend.dto.request.PublicacionRequestDTO;
import com.blog.backend.dto.response.PublicacionResponseDTO;
import com.blog.backend.service.PublicacionService;
import com.blog.backend.utils.Paginacion;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class PublicacionController {
	
	@Autowired
	private PublicacionService publicacionService;
	
	@GetMapping("/paginarPublicaciones")
	public PaginarPublicaciones paginacionDePublicaciones(
			@RequestParam(value = "pageNo",defaultValue = Paginacion.NUMERO_DE_PAGINAS,required = false )int numeroDePagina,
			@RequestParam(value = "pageSize",defaultValue = Paginacion.NUMERO_DE_ELEMENTOS,required = false)int numeroDeElementos,
			@RequestParam(value = "sortBy",defaultValue = Paginacion.ORDENAR_POR_DEFECTO,required = false)String ordenarPor,
			@RequestParam(value = "sortDir",defaultValue = Paginacion.ORDENAR_DIRECCION_POR_DEFECTO,required = false)String direccion) {
		return publicacionService.ordenarPublicaciones(numeroDePagina, numeroDeElementos, ordenarPor, direccion);
	}
	
	@GetMapping("/listarPublicaciones")
	public ResponseEntity<List<PublicacionResponseDTO>> listarPublicaciones(){
		List<PublicacionResponseDTO> publicaciones = publicacionService.listarTodas();
		return ResponseEntity.ok(publicaciones);
	}

	@GetMapping("/usuario/{usuarioId}/publicaciones")
	public ResponseEntity<List<PublicacionResponseDTO>> listarPublicaciones(@PathVariable(name = "usuarioId")long usuarioId){
		List<PublicacionResponseDTO> publicaciones = publicacionService.listarPublicacionesPorUsuario(usuarioId);
		return ResponseEntity.ok(publicaciones);
	}
	
	@GetMapping("/usuario/{usuarioId}/publicacion/{publicacionId}")
	public ResponseEntity<PublicacionResponseDTO> buscarPublicacionPor(@PathVariable(name = "usuarioId")long usuarioId,@PathVariable(name = "publicacionId")long publicacionId){
		PublicacionResponseDTO publicacion =publicacionService.buscarPublicacionPorId(usuarioId,publicacionId);
		return ResponseEntity.ok(publicacion);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@PostMapping(value = {"/usuario/{usuarioId}/nuevaPublicacion"},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<PublicacionResponseDTO> crearPublicacion(@PathVariable(name = "usuarioId")long usuarioId,@Valid @ModelAttribute  PublicacionRequestDTO publicacionDTO, @ApiParam(value = "List of files", required = false) @RequestParam("file") List<MultipartFile> file){
		PublicacionResponseDTO publicacion = publicacionService.crearPublicacion(usuarioId,publicacionDTO,file);
		return new ResponseEntity<>(publicacion,HttpStatus.CREATED);
	}

	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@PutMapping(value = {"/usuario/{usuarioId}/publicacion/{publicacionId}"},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PublicacionResponseDTO> actualizarPublicacion(@PathVariable(name = "usuarioId")long usuarioId,@PathVariable(name = "publicacionId")long publicacionId,@Valid @ModelAttribute PublicacionRequestDTO publicacionDTO,@RequestParam(value = "file",required = false) List<MultipartFile> file){ 
		PublicacionResponseDTO publicacion = publicacionService.actualizarPublicacion(usuarioId,publicacionId, publicacionDTO,file);
		return ResponseEntity.ok(publicacion);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping("/usuario/{usuarioId}/publicacion/{publicacionId}")
	public ResponseEntity<String> eliminarPublicacion(@PathVariable(name = "usuarioId")long usuarioId,@PathVariable(name = "publicacionId")long publicacionId){
		publicacionService.eliminarPublicacion(usuarioId,publicacionId);
		return new ResponseEntity<>("Publicacion eliminada con exito",HttpStatus.NO_CONTENT);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping(value = {"/usuario/{usuarioId}/fotoPerfil"},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<String> fotoDePerfil(@PathVariable(name = "usuarioId")long usuarioId,@Valid @ModelAttribute PublicacionRequestDTO publicacionDTO,MultipartFile file){
		publicacionService.crearFotoPerfil(usuarioId, publicacionDTO, file);
		return new ResponseEntity<>("Foto de perfil actualizada con exito",HttpStatus.CREATED);
		
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/publicacion/{publicacionId}")
	public ResponseEntity<String> eliminarPublicacionPorAdmin(@PathVariable(name = "publicacionId")long publicacionId){
		publicacionService.eliminarPublicacionPorAdmin(publicacionId);
		return new ResponseEntity<>("Publicacion eliminada por admin",HttpStatus.OK);
	}
	
	//Buscadores
	@GetMapping("/buscarPublicaciones/{titulo}")
	public ResponseEntity<List<PublicacionResponseDTO>> listarPublicacionesPorTitulo(@PathVariable(name = "titulo")String titulo){
		List<PublicacionResponseDTO> publicaciones = publicacionService.listarPublicacionesPorTitutlo(titulo);
		return ResponseEntity.ok(publicaciones);
	}
	
	@GetMapping("/buscarPublicaciones/tag/{tagId}")
	public ResponseEntity<List<PublicacionResponseDTO>> listarPublicacionesPorTagId(@PathVariable(name = "tagId")long tagId){
		List<PublicacionResponseDTO> publicaciones = publicacionService.listarPublicacionesPorTagId(tagId);
		return ResponseEntity.ok(publicaciones);
	}

	@GetMapping("/usuario/{usuarioId}/publicacionesDeSeguidos")
	public ResponseEntity<List<PublicacionesSeguidosDTO>> listarPublicaiconesDeSeguidos(@PathVariable(name = "usuarioId")long usuarioId){
		List<PublicacionesSeguidosDTO> publicaciones = publicacionService.listarPublicacionesDeUsuariosSeguidos(usuarioId);
		return new ResponseEntity<>(publicaciones,HttpStatus.OK);
	}
}
