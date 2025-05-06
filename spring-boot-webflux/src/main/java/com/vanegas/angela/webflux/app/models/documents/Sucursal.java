package com.vanegas.angela.webflux.app.models.documents;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sucursales")
public class Sucursal {
	
	@Id
	@NotEmpty
	private String id;
	
	private String nombre;
	
	@Valid
	private Franquicia franquicia;
	
	private Date createAt;
	
	public Sucursal() {
	}
	
	public Sucursal(String nombre, Franquicia franquicia) {
			this.nombre = nombre;
			this.franquicia = franquicia;
			}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Franquicia getFranquicia() {
		return franquicia;
	}

	public void setFranquicia(Franquicia franquicia) {
		this.franquicia = franquicia;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	
	

}
