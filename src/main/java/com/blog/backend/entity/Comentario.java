package com.blog.backend.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "comentario")
public class Comentario  extends AuditModel{

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descripcion;
	
	@JsonBackReference(value = "usuario-comentario")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id",nullable = false)
	private Usuario usuario;

	@JsonBackReference(value = "publicacion-comentario")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "publicacion_id",nullable = false)
	private Publicacion publicacion;
	

}
