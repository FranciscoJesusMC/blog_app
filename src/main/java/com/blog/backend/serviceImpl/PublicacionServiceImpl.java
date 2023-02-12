package com.blog.backend.serviceImpl;


import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blog.backend.dto.PaginarPublicaciones;
import com.blog.backend.dto.PublicacionesSeguidosDTO;
import com.blog.backend.dto.request.PublicacionRequestDTO;
import com.blog.backend.dto.response.PublicacionResponseDTO;
import com.blog.backend.entity.Imagenes;
import com.blog.backend.entity.Publicacion;
import com.blog.backend.entity.Tag;
import com.blog.backend.entity.Usuario;
import com.blog.backend.exceptions.BlogAppException;
import com.blog.backend.exceptions.ResourceNotFoundException;
import com.blog.backend.repository.ImagenesRepositorio;
import com.blog.backend.repository.PublicacionRepositorio;
import com.blog.backend.repository.TagRepositorio;
import com.blog.backend.repository.UsuarioRepositorio;
import com.blog.backend.service.PublicacionService;

@Service
public class PublicacionServiceImpl implements PublicacionService {
	
	@Autowired
	private PublicacionRepositorio publicacionRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private ImagenesRepositorio imagenesRepositorio;
	
	@Autowired
	private TagRepositorio tagRepositorio;
			
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AcccionesAdmiServiceImpl acciones;
	
	private Publicacion mapearEntidad(PublicacionRequestDTO publicacionDTO) {
		Publicacion publicacion = modelMapper.map(publicacionDTO, Publicacion.class);
		return publicacion;
	}
		
	private PublicacionResponseDTO mapearDTO(Publicacion publicacion) {
		PublicacionResponseDTO publicacionDTO = modelMapper.map(publicacion, PublicacionResponseDTO.class);
		return publicacionDTO;
	}
	
	
	@Override
	public PaginarPublicaciones ordenarPublicaciones(int numeroDePagina, int numeroDeElementos, String ordenarPor,String direccion) {
		Sort sort = direccion.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();
		Pageable pageable = PageRequest.of(numeroDePagina, numeroDeElementos,sort);
		Page<Publicacion> publicaciones = publicacionRepositorio.findAll(pageable);
		
		List<Publicacion> listaPublicaciones = publicaciones.getContent();
		
		List<PublicacionResponseDTO> contenido = listaPublicaciones.stream().map(publicacion ->mapearDTO(publicacion)).collect(Collectors.toList());
		
		PaginarPublicaciones respuesta =  new PaginarPublicaciones();
		respuesta.setPublicaciones(contenido);
		respuesta.setNumeroDePaginas(publicaciones.getNumber());
		respuesta.setNumeroDeElementos(publicaciones.getSize());
		respuesta.setTotalDePaginas(publicaciones.getTotalPages());
		respuesta.setTotalDeElementos(publicaciones.getTotalElements());
		respuesta.setUltima(publicaciones.isLast());
		
		return respuesta;
	}
	
	@Override
	public List<PublicacionResponseDTO> listarTodas() {
		List<Publicacion> publicaciones = publicacionRepositorio.findAll();
		if(publicaciones.isEmpty()) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "No hay publicaciones registradas");
		}
		return publicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());
	}

	@Override
	public List<PublicacionResponseDTO> listarPublicacionesPorUsuario(long usuarioId) {
		
		List<Publicacion> publicaciones = publicacionRepositorio.findByUsuarioId(usuarioId);
		if(publicaciones.isEmpty()) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST,"El usuario con el id :" + usuarioId + " no tiene publicaciones");
		}
		
		return publicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());
	}

	@Override
	public PublicacionResponseDTO crearPublicacion(long usuarioId,PublicacionRequestDTO publicacionDTO,List<MultipartFile> file) {
		
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
				
		Publicacion publicacion = mapearEntidad(publicacionDTO);

		try {
			
			//Imagenes
			Set<Imagenes> imagenes = subirImagenesPorLista(file);
			
			imagenes.forEach(imagen ->{
				imagen.setPublicacion(publicacion);
			});
			
			//Tags
			Set<Tag> tags = new HashSet<>();
			for(String tagName : publicacionDTO.getTags()) {
				Tag tag = tagRepositorio.findByNombre(tagName).orElse(null);
				if(tag == null) {
					tag = new Tag();
					tag.setNombre(tagName);
					tagRepositorio.save(tag);
				}
				tags.add(tag);
			}
			
			publicacion.setUsuario(usuario);
			publicacion.setImagenes(imagenes);
			publicacion.setTag(tags);
			

			Publicacion nuevaPublicacion = publicacionRepositorio.save(publicacion);
			PublicacionResponseDTO guardarPublicacion = mapearDTO(nuevaPublicacion);		
			
			return guardarPublicacion;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "Hubo un error al crear la publicacion");
		}
			
	}
		
	
	@Override
	public PublicacionResponseDTO buscarPublicacionPorId(long usuarioId,long publicacionId) {
		
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));
		
		if(!publicacion.getUsuario().getId().equals(usuario.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "La publicacion el id : " + publicacionId + " no pertenece al usuario con el id :" + usuarioId);
		}
		
		return mapearDTO(publicacion);
	}

	@Override
	public PublicacionResponseDTO actualizarPublicacion(long usuarioId,long publicacionId, PublicacionRequestDTO publicacionDTO,List<MultipartFile> file) {
		
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));
		
		if(!publicacion.getUsuario().getId().equals(usuario.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "La publicacion el id : " + publicacionId + " no pertenece al usuario con el id :" + usuarioId);
		}
		
		try {
			//Imagenes
			Set<Imagenes> imagenes = subirImagenesPorLista(file);
			
			imagenes.forEach(imagen ->{
				imagen.setPublicacion(publicacion);
			});
			
			//Tags
			Set<Tag> tags = new HashSet<>();
			for(String tagName : publicacionDTO.getTags()) {
				Tag tag = tagRepositorio.findByNombre(tagName).orElse(null);
				if(tag == null) {
					tag = new Tag();
					tag.setNombre(tagName);
					tagRepositorio.save(tag);
				}
				tags.add(tag);
			}
			
			publicacion.setTitulo(publicacionDTO.getTitulo());
			publicacion.setContenido(publicacionDTO.getContenido());
			publicacion.addImagenes(imagenes);
			publicacion.addTags(tags);
			
			Publicacion actualizarPublicacion = publicacionRepositorio.save(publicacion);
			
			PublicacionResponseDTO guardarPublicacion = mapearDTO(actualizarPublicacion);
			
			return guardarPublicacion;
						
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "No se pudo actualizar la publicacion");
		}
		
	}

	@Override
	public void eliminarPublicacion(long usuarioId,long publicacionId) {
		
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));
		
		Imagenes imagen = imagenesRepositorio.findByUri(usuario.getFoto());
		
		Set<Imagenes> listaImagenes = imagenesRepositorio.findByPublicacionId(publicacionId);
		
		if(!publicacion.getUsuario().getId().equals(usuario.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "La publicacion el id : " + publicacionId + " no pertenece al usuario con el id :" + usuarioId);
		}
		
		if(listaImagenes.contains(imagen)) {
			usuario.setFoto(null);
			publicacionRepositorio.delete(publicacion);
		}else {
			publicacionRepositorio.delete(publicacion);			
		}
		
		
	}
	
	@Override
	public PublicacionResponseDTO crearFotoPerfil(long usuarioId, PublicacionRequestDTO publicacionDTO, MultipartFile file) {
		
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		
		Publicacion publicacion = mapearEntidad(publicacionDTO);

		try {
		
			Imagenes imagen = subirImagenUnica(file);
			imagen.setPublicacion(publicacion);
			publicacion.setUsuario(usuario);
			publicacion.setImagenes(Collections.singleton(imagen));
			usuario.setFoto(imagen.getUri());
			
			Publicacion nuevaPublicacion = publicacionRepositorio.save(publicacion);
			
			PublicacionResponseDTO guardarPublicacion = mapearDTO(nuevaPublicacion);
			
			return guardarPublicacion;
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "No se pudo subir la foto");
		}
		
	}

	@Override
	public void eliminarPublicacionPorAdmin(long publicacionId) {
		
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));
		publicacionRepositorio.delete(publicacion);
		acciones.realizarAccion("Publicacion", publicacionId, "Publicacion eliminada con exito");
		
	}
	
	
	//Busqueda
	@Override
	public List<PublicacionResponseDTO> listarPublicacionesPorTitutlo(String titulo) {
		List<Publicacion> publicaciones = publicacionRepositorio.findByTituloIgnoreCaseContaining(titulo);
		if(publicaciones.isEmpty()) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "No se encontraron resultados para el titulo : " + titulo);
		}
		return publicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());
	}
	
	
	@Override
	public List<PublicacionResponseDTO> listarPublicacionesPorTagId(long tagId) {
		List<Publicacion> publicaciones = publicacionRepositorio.findByTagId(tagId);
		
		if(publicaciones.isEmpty()) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "No hay publicaciones con ese tag");
		}
		
		return publicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());
	}
	
	@Override
	public List<PublicacionesSeguidosDTO> listarPublicacionesDeUsuariosSeguidos(long usuarioId) {
		
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(()-> new ResourceNotFoundException("Usuario", "id", usuarioId));
		
		Set<Usuario> seguidos = usuario.getSeguidos();
		List<Publicacion> publicaciones = publicacionRepositorio.findByUsuarioIn(seguidos);
		
		List<PublicacionesSeguidosDTO> publicacionesDTO = publicaciones.stream().map(PublicacionesSeguidosDTO::new).collect(Collectors.toList());
		
		return publicacionesDTO;
	}
	
			
	//Meotodo para subir una imagen
	public Imagenes subirImagenUnica(MultipartFile file) throws IOException {
		String nombreDeImagen= UUID.randomUUID() +file.getOriginalFilename();
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/imagenes/obtenerImagen/")
				.path(nombreDeImagen)
				.toUriString();
		
		Imagenes imagen = new Imagenes(nombreDeImagen,fileDownloadUri, file.getContentType(), file.getBytes());
		return imagen;
	}

	//Meotodo para subir una lista de imagenes
	public Set<Imagenes> subirImagenesPorLista(List<MultipartFile> files) throws IOException {
		Set<Imagenes> agregarImagenes = new HashSet<>();
	
		for(MultipartFile file : files) {
			String nombreDeImagen= UUID.randomUUID() +file.getOriginalFilename();
			
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/api/imagenes/obtenerImagen/")
					.path(nombreDeImagen)
					.toUriString();
			
			Imagenes imagen = new Imagenes(nombreDeImagen,fileDownloadUri, file.getContentType(), file.getBytes());
			
			if(imagen.getTipo() == null) {
				agregarImagenes.remove(imagen);
			}else {
				agregarImagenes.add(imagen);
				
			}
			
		}
		
		return agregarImagenes;
		
	}


	
}


