package com.blog.backend.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "acciones_admin")
public class AccionesAdmin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String tipo;
	private Long entidadId;
	private String descripcion;
	private Date fecha_creacion;
	
	@ManyToOne
	private Usuario usuario;


	public AccionesAdmin(String tipo,Long entidadId, String descripcion, Usuario usuario,Date fecha_creacion) {
		super();
		this.tipo = tipo;
		this.entidadId = entidadId;
		this.descripcion = descripcion;
		this.usuario = usuario;
		this.fecha_creacion=fecha_creacion;
	}


	
	

}
