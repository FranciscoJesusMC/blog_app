package com.blog.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "imagenes")
public class Imagenes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String uri;
	private String tipo;
	@JsonProperty(access = Access.WRITE_ONLY)
	@Lob
	private byte[] data;
	
	
	@JsonBackReference(value = "publicacion-imagenes")
	@ManyToOne
	@JoinColumn(name = "publicacion_id")
	private Publicacion publicacion;
	
	
	
	
	public Imagenes(String nombre,String uri ,String tipo, byte[] data) {
		super();
		this.nombre = nombre;
		this.uri = uri;
		this.tipo = tipo;
		this.data = data;
	}




	public Imagenes(String uri) {
		this.uri = uri;
	}







//	
	
	
	
}
