package com.blog.backend.serviceImpl;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blog.backend.dto.RolDTO;
import com.blog.backend.entity.Rol;
import com.blog.backend.entity.Usuario;
import com.blog.backend.exceptions.BlogAppException;
import com.blog.backend.exceptions.ResourceNotFoundException;
import com.blog.backend.repository.RolRepositorio;
import com.blog.backend.repository.UsuarioRepositorio;
import com.blog.backend.service.RolService;

@Service
public class RolServiceImpl implements RolService {

	@Autowired
	private AcccionesAdmiServiceImpl acciones;
	
	@Autowired
	private RolRepositorio rolRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private RolDTO mapearDTO(Rol rol) {
		RolDTO rolDTO = modelMapper.map(rol, RolDTO.class);
		return rolDTO;
	}
	
	private Rol mapearEntidad(RolDTO rolDTO) {
		Rol rol = modelMapper.map(rolDTO, Rol.class);
		return rol;
	}

	@Override
	public List<RolDTO> listarRoles() {
		List<Rol> roles = rolRepositorio.findAll();
		return roles.stream().map(rol -> mapearDTO(rol)).collect(Collectors.toList());
	}

	@Override
	public RolDTO crearRol(RolDTO rolDTO) {
		Rol rol = mapearEntidad(rolDTO);
		
		if(rolRepositorio.existsByNombre(rolDTO.getNombre())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "El nombre ingresado ya se encuentra registrado :" + rolDTO.getNombre());
		}
		
		String nombre = "ROLE_" + rolDTO.getNombre().toUpperCase();
		
		rol.setNombre(nombre);
		Rol nuevoRol = rolRepositorio.save(rol);
		
		RolDTO guardarRol = mapearDTO(nuevoRol);
		acciones.realizarAccion("Crear rol",rol.getId(), "Rol creado exitosamente");
		
		return guardarRol;
	}

	@Override
	public void eliminarRol(long rolId) {
		Rol rol = rolRepositorio.findById(rolId).orElseThrow(()-> new ResourceNotFoundException("Rol", "id", rolId));
		rolRepositorio.delete(rol);
		acciones.realizarAccion("Eliminar rol",rol.getId(), "Rol eliminado exitosamente");
		
	}

	@Override
	public void agregarRolaUsuario(long usuarioId, long rolId) {
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		
		Rol rol = rolRepositorio.findById(rolId).orElseThrow(()-> new ResourceNotFoundException("Rol", "id", rolId));
		
		if(usuario.getRol().contains(rol)) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "EL usuario ya tiene este rol asignado");
		}
		
		usuario.getRol().add(rol);
		usuarioRepositorio.save(usuario);
		acciones.realizarAccion("Agregar rol a usuario", rol.getId(),"Rol agregado al usuario exitosamente");
	}

	@Override
	public void eliminarRolaUsuario(long usuarioId, long rolId) {
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		
		Rol rol = rolRepositorio.findById(rolId).orElseThrow(()-> new ResourceNotFoundException("Rol", "id", rolId));
		
		if(!usuario.getRol().contains(rol)) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "El usuario no tiene este rol asignado");
		}
		
		usuario.getRol().remove(rol);
		usuarioRepositorio.save(usuario);
		acciones.realizarAccion("Eliminar rol a usuario",rol.getId() ,"Rol eliminado al usuario exitosamente");
	}
	
	
}
