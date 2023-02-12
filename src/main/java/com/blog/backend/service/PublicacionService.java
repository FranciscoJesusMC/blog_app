package com.blog.backend.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.blog.backend.dto.PaginarPublicaciones;
import com.blog.backend.dto.PublicacionesSeguidosDTO;
import com.blog.backend.dto.request.PublicacionRequestDTO;
import com.blog.backend.dto.response.PublicacionResponseDTO;


public interface PublicacionService {
	
	public PaginarPublicaciones ordenarPublicaciones(int numeroDePagina,int numeroDeElementos,String ordenarPor,String direccion);
	
	public List<PublicacionResponseDTO> listarTodas();
	
	public List<PublicacionResponseDTO> listarPublicacionesPorUsuario(long usuarioId);
	
	public PublicacionResponseDTO crearPublicacion(long usuarioId,PublicacionRequestDTO publicacionRequestDTO,List<MultipartFile> file);
	
	public PublicacionResponseDTO buscarPublicacionPorId(long usuarioId,long publicacionId);
	
	public PublicacionResponseDTO actualizarPublicacion(long usuarioId,long publicacionId,PublicacionRequestDTO publicacionDTO,List<MultipartFile> file);
	
	public void eliminarPublicacion(long usuarioId,long publicacionId);

	public PublicacionResponseDTO crearFotoPerfil(long usuarioId,PublicacionRequestDTO publicacionDTO, MultipartFile file);
	
	//Admin
	public void eliminarPublicacionPorAdmin(long publicacionId);
	
	//Busquedas
	
	public List<PublicacionResponseDTO> listarPublicacionesPorTitutlo(String titulo);
	
	public List<PublicacionResponseDTO> listarPublicacionesPorTagId(long tagId);

	//ListarPublicacionesDeUsuariosSeguidos
	
	public List<PublicacionesSeguidosDTO> listarPublicacionesDeUsuariosSeguidos(long usuarioId);
	

	
}
