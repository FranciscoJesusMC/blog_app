package com.blog.backend.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blog.backend.dto.request.ComentarioRequestDTO;
import com.blog.backend.dto.response.ComentarioResponseDTO;
import com.blog.backend.entity.Comentario;
import com.blog.backend.entity.Publicacion;
import com.blog.backend.entity.Usuario;
import com.blog.backend.exceptions.BlogAppException;
import com.blog.backend.exceptions.ResourceNotFoundException;
import com.blog.backend.repository.ComentarioRepositorio;
import com.blog.backend.repository.PublicacionRepositorio;
import com.blog.backend.repository.UsuarioRepositorio;
import com.blog.backend.service.ComentarioService;

@Service
public class ComentarioServiceImpl implements ComentarioService {
	
	@Autowired
	private ComentarioRepositorio comentarioRepositorio;
	
	@Autowired
	private PublicacionRepositorio publicacionRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private Comentario mapearEntidad(ComentarioRequestDTO comentarioDTO) {
		Comentario comentario = modelMapper.map(comentarioDTO, Comentario.class);
		return comentario;
	}
	
	private ComentarioResponseDTO mapearDTO(Comentario comentario) {
		ComentarioResponseDTO comentarioDTO = modelMapper.map(comentario, ComentarioResponseDTO.class);
		return comentarioDTO;
	}

	@Override
	public List<ComentarioResponseDTO> listarComentariosPorPublicacion(long publicacionId) {
		List<Comentario> comentarios = comentarioRepositorio.findByPublicacionId(publicacionId);
		if(comentarios.isEmpty()) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "La publicacion con el id :" + publicacionId + "no tiene comentarios");
		}
		return comentarios.stream().map(comentario -> mapearDTO(comentario)).collect(Collectors.toList());
	}

	@Override
	public ComentarioResponseDTO crearComentario(long publicacionId,long usuarioId, ComentarioRequestDTO comentarioRequestDTO) {
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));
		
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		
		Comentario comentario = mapearEntidad(comentarioRequestDTO);
		comentario.setPublicacion(publicacion);
		comentario.setUsuario(usuario);
		
		Comentario nuevoComentario = comentarioRepositorio.save(comentario);
		
		ComentarioResponseDTO guardarComentario = mapearDTO(nuevoComentario);
		
		return guardarComentario;
	}

	@Override
	public ComentarioResponseDTO actualizarComentario(long publicacionId,long usuarioId,long comentarioId, ComentarioRequestDTO comentarioRequestDTO) {
		
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));
		
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		
		Comentario comentario = comentarioRepositorio.findById(comentarioId).orElseThrow(()-> new ResourceNotFoundException("Comentario", "id", comentarioId));
	
		if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicacion");
		}
		
		if(!comentario.getUsuario().getId().equals(usuario.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertece al usuario");
		}
		
		comentario.setDescripcion(comentarioRequestDTO.getDescripcion());
		
		Comentario actualizarComentario = comentarioRepositorio.save(comentario);
		
		ComentarioResponseDTO guardarComentario = mapearDTO(actualizarComentario);
		
		return guardarComentario;
	}

	@Override
	public void eliminarComentario(long publicacionId,long usuarioId, long comentarioId) {
		
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));
		
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		
		Comentario comentario = comentarioRepositorio.findById(comentarioId).orElseThrow(()-> new ResourceNotFoundException("Comentario", "id", comentarioId));
		
		if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicacion");
		}
		
		if(!comentario.getUsuario().getId().equals(usuario.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la al usuario");		
		}
		
		
		comentarioRepositorio.delete(comentario);
		
	
	}

	@Override
	public void eliminarComentarioPorPublicador(long publicacionId, long usuarioId, long comentarioId) {
		
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));
		
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		
		Comentario comentario = comentarioRepositorio.findById(comentarioId).orElseThrow(()-> new ResourceNotFoundException("Comentario", "id", comentarioId));
		
		if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
			throw new BlogAppException(HttpStatus.NOT_FOUND,"El comentario no pertenece a la publicacion");
		}
		
		if(!publicacion.getId().equals(usuario.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "La publicacion no pertenece al usuario");
			
		}
		
		comentarioRepositorio.delete(comentario);
		
	}

	@Override
	public void eliminarComentarioPorAdmi(long publicacionId, long comentarioId) {
		
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));
		
		Comentario comentario = comentarioRepositorio.findById(comentarioId).orElseThrow(()-> new ResourceNotFoundException("Comentario", "id", comentarioId));
			
		if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertence a la publicacion :" + comentarioId);
		}
		
		comentarioRepositorio.deleteById(comentario.getId());
		
		
	}

}
