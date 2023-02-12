package com.blog.backend.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blog.backend.dto.ReaccionDTO;
import com.blog.backend.entity.Publicacion;
import com.blog.backend.entity.Reaccion;
import com.blog.backend.entity.Usuario;
import com.blog.backend.exceptions.BlogAppException;
import com.blog.backend.exceptions.ResourceNotFoundException;
import com.blog.backend.repository.PublicacionRepositorio;
import com.blog.backend.repository.ReaccionRepositorio;
import com.blog.backend.repository.UsuarioRepositorio;
import com.blog.backend.service.ReaccionService;

@Service
public class ReaccionServiceImpl implements ReaccionService {
	
	@Autowired
	private ReaccionRepositorio reaccionRepositorio;
	
	@Autowired
	private PublicacionRepositorio publicacionRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
			
	@Autowired
	private ModelMapper modelMapper;
		
	private ReaccionDTO mapearDTO(Reaccion reaccion) {
		ReaccionDTO reaccionDTO = modelMapper.map(reaccion, ReaccionDTO.class);
		return reaccionDTO;
	}

	@Override
	public void agregarReaccion(long publicacionId, long usuarioId,int reaccionId) {
		
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));
		
		List<String> reacciones =new ArrayList<>();
		reacciones.add("Me gusta");
		reacciones.add("Me divierte");
		reacciones.add("Me encanta");
		reacciones.add("Me entristece");
		reacciones.add("Me enoja");
		
		String seleccionado = reacciones.get(reaccionId);
		
		if(!reacciones.contains(seleccionado)) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "La reaccion con el id:" + reaccionId +" no esta permitida");
		}
		
		Reaccion reaccion = reaccionRepositorio.findByUsuarioIdAndPublicacionId(usuarioId, publicacionId).orElseGet(() -> new Reaccion(seleccionado,usuario,publicacion));
		
		publicacion.setReacciones(publicacion.getReacciones() + 1);
		reaccion.setNombre(seleccionado);
		reaccionRepositorio.save(reaccion);
		
			
	}

	@Override
	public void quitarReaccion(long publicacionId, long usuarioId) {
		
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		
		Publicacion publicacion =publicacionRepositorio.findById(publicacionId).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));
		
		Reaccion reaccion = reaccionRepositorio.findByUsuarioIdAndPublicacionId(usuarioId, publicacionId).orElseThrow(()->  new ResourceNotFoundException("Reaccion", "nombre",null));
		
		usuario.getReaccion().remove(reaccion);
		usuarioRepositorio.save(usuario);
		
		publicacion.getReaccion().remove(reaccion);
		publicacion.setReacciones(publicacion.getReacciones() - 1);
		publicacionRepositorio.save(publicacion);
		
		reaccionRepositorio.delete(reaccion);
	
	}

	@Override
	public List<ReaccionDTO> listarReaccionesDePublicaciones(long publicacionId) {
		List<Reaccion> reacciones = reaccionRepositorio.findByPublicacionId(publicacionId);
		if(reacciones.isEmpty()) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "La publicacion no tiene reacciones");
		}
		
		return reacciones.stream().map(reaccion -> mapearDTO(reaccion)).collect(Collectors.toList());
	}
	


}
