package com.blog.backend.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String apellido;
	private String email;
	private String username;
	private String password;
	private String foto;
			
	@JsonManagedReference(value = "usuario-publicacion")
	@OneToMany(mappedBy = "usuario",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Set<Publicacion> publicacion = new HashSet<>();
	
	@JsonManagedReference(value = "usuario-comentario")
	@OneToMany(mappedBy = "usuario",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Set<Comentario> comentario = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_roles",joinColumns = @JoinColumn(name = "usuario_id",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "rol_id",referencedColumnName = "id"))
	private Set<Rol> rol = new HashSet<>();
	
	@ManyToMany(mappedBy = "seguidos")
	private Set<Usuario> seguidores = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name = "usuario_segudios",joinColumns = @JoinColumn(name="seguidores_id"),inverseJoinColumns = @JoinColumn(name="seguidos_id"))
	private Set<Usuario> seguidos = new HashSet<>();
	
	@JsonManagedReference(value = "usuario-reaccion")
	@OneToMany(mappedBy = "usuario",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<Reaccion> reaccion = new HashSet<>();

}
