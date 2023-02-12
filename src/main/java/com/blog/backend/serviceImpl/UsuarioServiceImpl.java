package com.blog.backend.serviceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.backend.dto.request.UsuarioRequestDTO;
import com.blog.backend.dto.response.SeguidoresResponseDTO;
import com.blog.backend.dto.response.SeguidosResponseDTO;
import com.blog.backend.dto.response.UsuarioResponseDTO;
import com.blog.backend.entity.Imagenes;
import com.blog.backend.entity.Publicacion;
import com.blog.backend.entity.Rol;
import com.blog.backend.entity.Usuario;
import com.blog.backend.exceptions.BlogAppException;
import com.blog.backend.exceptions.ResourceNotFoundException;
import com.blog.backend.repository.ImagenesRepositorio;
import com.blog.backend.repository.PublicacionRepositorio;
import com.blog.backend.repository.RolRepositorio;
import com.blog.backend.repository.UsuarioRepositorio;
import com.blog.backend.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private RolRepositorio rolRepositorio;
	
	@Autowired
	private PublicacionRepositorio publicacionRepositorio;
	
	@Autowired
	private ImagenesRepositorio imagenesRepositorio;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
		
	@Autowired
	private  ModelMapper modelMapper;
	
	@Autowired
	private AcccionesAdmiServiceImpl acciones;
	
	private Usuario mapearEntidad(UsuarioRequestDTO usuarioDTO) {
		Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
		return usuario;
	}
	
	private UsuarioResponseDTO mapearDTO(Usuario usuario) {
		UsuarioResponseDTO usuarioDTO = modelMapper.map(usuario, UsuarioResponseDTO.class);
		return usuarioDTO;
	}

	@Override
	public List<UsuarioResponseDTO> listarUsuarios() {
		List<Usuario> usuarios = usuarioRepositorio.findAll();
		if(usuarios.isEmpty()) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "No hay usuarios registrados");
		}
		return usuarios.stream().map(usuario -> mapearDTO(usuario)).collect(Collectors.toList());
	}

	@Override
	public UsuarioResponseDTO buscarUsuarioPortId(long usuarioId) {
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		
		return mapearDTO(usuario);
	}

	@Override
	public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO usuarioRequestDTO) {
		Usuario usuario = mapearEntidad(usuarioRequestDTO);
		
		if(usuarioRepositorio.existsByUsername(usuarioRequestDTO.getUsername())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "El username :" + usuarioRequestDTO.getUsername() + " ya se encuentra registrado");
		}
		
		if(usuarioRepositorio.existsByEmail(usuarioRequestDTO.getEmail())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "El email : " + usuarioRequestDTO.getEmail() + " ya se encuentra registrado");
		}
		
		
		usuario.setPassword(passwordEncoder.encode(usuarioRequestDTO.getPassword()));
		Rol rol = rolRepositorio.findByNombre("ROLE_USER").get();
		usuario.setRol(Collections.singleton(rol));
		
		Usuario nuevoUsuario = usuarioRepositorio.save(usuario);
		
		UsuarioResponseDTO guardarUsuario = mapearDTO(nuevoUsuario);
		
		return guardarUsuario;
		
	}

	@Override
	public UsuarioResponseDTO actualizarUsuario(long usuarioId, UsuarioRequestDTO usuarioRequestDTO) {
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		
		Usuario update = new Usuario();
		update.setId(usuario.getId());
		update.setNombre(usuarioRequestDTO.getNombre());
		update.setApellido(usuarioRequestDTO.getApellido());
		update.setPassword(passwordEncoder.encode(usuarioRequestDTO.getPassword()));
		update.setUsername(usuario.getUsername());
		update.setEmail(usuario.getEmail());
	
		if(!update.getEmail().equals(usuarioRequestDTO.getEmail())){
			if(usuarioRepositorio.existsByEmail(usuarioRequestDTO.getEmail())) {
				throw new BlogAppException(HttpStatus.BAD_REQUEST, "El email ingresado :" + usuarioRequestDTO.getEmail() + " ya esta en uso");
			}else {
				update.setEmail(usuarioRequestDTO.getEmail());
			}
		}
	
		Usuario actualizarUsuario = usuarioRepositorio.save(update);
		
		UsuarioResponseDTO guardarUsuario = mapearDTO(actualizarUsuario);
		acciones.realizarAccion("Actualizar usuario", usuarioId, "Usuario actualizado con exito");
		return guardarUsuario;
	}

	@Override
	public void eliminarUsuario(long usuarioId) {
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		usuarioRepositorio.delete(usuario);
		acciones.realizarAccion("Usuario", usuarioId, "Usuario eliminado con exito");
	}

	@Override
	public void actualizarFotoDePerfil(long usuarioId, long publicacionId, long imagenId) {
	
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));
		
		Set<Imagenes> listaImagenes =imagenesRepositorio.findByPublicacionId(publicacionId);
		
		Imagenes imagen = imagenesRepositorio.findById(imagenId).orElseThrow(()-> new ResourceNotFoundException("Imagen", "id", imagenId));

		if(!publicacion.getUsuario().getId().equals(usuario.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "La publicacion el id : " + publicacionId + " no pertenece al usuario con el id :" +usuarioId);
		}
		
		if(listaImagenes.contains(imagen)) {
			
			usuario.setFoto(imagen.getUri());
			usuarioRepositorio.save(usuario);
		
		}else {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "La imagen con el id: " + imagenId + " no pertenece a la publicacion con el id :" + publicacionId);
		}
		
		
	}

	//Buscadores
	
	@Override
	public List<UsuarioResponseDTO> buscarUsuarioPorNombre(String nombre) {
		List<Usuario> usuarios = usuarioRepositorio.findByNombreIgnoreCaseContaining(nombre);
		if(usuarios.isEmpty()) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "No se encontraron coindicencias para el nombre : " + nombre);
		}
		return usuarios.stream().map(usuario -> mapearDTO(usuario)).collect(Collectors.toList());
	}

	
	//Seguidores
	
	@Override
	public void seguir(long usuario1, long usuario2) {
		Usuario usuarioSeguidor = usuarioRepositorio.findById(usuario1).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuario1));
		
		Usuario usuarioSeguido = usuarioRepositorio.findById(usuario2).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuario2));
		
		
		if(usuarioSeguidor.getSeguidos().contains(usuarioSeguido)) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "Usted ya sigue a este usuario");
		}
		
		usuarioSeguidor.getSeguidos().add(usuarioSeguido);
		usuarioRepositorio.save(usuarioSeguidor);			
		
		usuarioSeguido.getSeguidores().add(usuarioSeguidor);
		usuarioRepositorio.save(usuarioSeguido);
		
	}

	@Override
	public void dejarDeSeguir(long usuario1, long usuario2) {
		Usuario usuarioSeguidor = usuarioRepositorio.findById(usuario1).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuario1));
		
		Usuario usuarioSeguido = usuarioRepositorio.findById(usuario2).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuario1));
		
		if(!usuarioSeguidor.getSeguidos().contains(usuarioSeguido)) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "Usted no sigue a este usuario");
		}
		
		usuarioSeguidor.getSeguidos().remove(usuarioSeguido);
		usuarioRepositorio.save(usuarioSeguidor);
		
		usuarioSeguido.getSeguidores().remove(usuarioSeguidor);
		usuarioRepositorio.save(usuarioSeguido);
		
	}

	
	//Lista seguidores y seguidos
	
	@Override
	public SeguidoresResponseDTO listarSeguidores(long usuarioId) {
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		Set<Usuario> seguidores = usuario.getSeguidores();
		
		return new SeguidoresResponseDTO(seguidores);
	}

	@Override
	public SeguidosResponseDTO listarSeguidos(long usuarioId) {
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		Set<Usuario> seguidos = usuario.getSeguidos();
		
		return new SeguidosResponseDTO(seguidos);
	}

}
