package com.blog.backend.serviceImpl;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.backend.entity.AccionesAdmin;
import com.blog.backend.entity.Rol;
import com.blog.backend.entity.Usuario;
import com.blog.backend.repository.AccionesAdminRepositorio;
import com.blog.backend.repository.UsuarioRepositorio;
import com.blog.backend.utils.AutenticationFacade;

@Service
public class AcccionesAdmiServiceImpl {

	@Autowired
	private AccionesAdminRepositorio accionesAdminRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private AutenticationFacade autenticationFacade;
	
	public void realizarAccion(String tipo,long entidadId,String descripcion) {
		Usuario usuario = usuarioRepositorio.findByEmail(autenticationFacade.getAuthentication().getName());
		AccionesAdmin accion = new AccionesAdmin(tipo,entidadId, descripcion,usuario,new Date());
		
		Set<Rol> roles = usuario.getRol();
		
		for(Rol role : roles) {
			if(role.getNombre().equals("ROLE_ADMIN")) {
				accionesAdminRepositorio.save(accion);				
			}
		}
		
	}
}
