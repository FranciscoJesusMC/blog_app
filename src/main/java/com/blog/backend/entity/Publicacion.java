package com.blog.backend.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "publicacion")
public class Publicacion extends AuditModel {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String contenido;
	
	private int reacciones;
	
	@JsonBackReference(value = "usuario-publicacion")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@JsonManagedReference(value = "publicacion-tag")
	@ManyToMany
	@JoinTable(name = "publicacion_tag",joinColumns = @JoinColumn(name ="publicacion_id",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name="tag_id",referencedColumnName = "id"))
	private Set<Tag> tag = new HashSet<>();
	
	@JsonManagedReference(value = "publicacion-comentario")
	@OneToMany(mappedBy = "publicacion",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<Comentario> comentario = new HashSet<>();
	
	
	@JsonManagedReference(value = "publicacion-imagenes")
	@OneToMany(mappedBy = "publicacion",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<Imagenes> imagenes = new HashSet<>();
	
	@JsonManagedReference(value = "publicacion-reaccion")
	@OneToMany(mappedBy = "publicacion",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<Reaccion> reaccion = new HashSet<>();

	
	public void addImagenes(Set<Imagenes> imagen) {
		this.imagenes.addAll(imagen);
	}
	
	public void addTags(Set<Tag> tag) {
		this.tag.addAll(tag);
	}

	public void removeTag(Tag tag) {
		this.tag.remove(tag);
	}


}
