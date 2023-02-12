package com.blog.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "publicacion_reaccion")
public class Reaccion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nombre;
	
	@JsonBackReference(value = "usuario-reaccion")
	@ManyToOne
	@JoinColumn(name = "usuarioId")
	private Usuario usuario;
	
	@JsonBackReference(value = "publicacion-reaccion")
	@ManyToOne
	@JoinColumn(name = "publicacion_id")
	private Publicacion publicacion;

	public Reaccion(String nombre, Usuario usuario, Publicacion publicacion) {
		super();
		this.nombre = nombre;
		this.usuario = usuario;
		this.publicacion = publicacion;

	}
	

	
}
