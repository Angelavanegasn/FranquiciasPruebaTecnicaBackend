package com.vanegas.angela.webflux.app.models.documents;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "franquicias")
public class Franquicia {

	@Id
	@NotEmpty
	private String id;
	
	private String nombre;
	
	private Date createAt;
	
	public Franquicia() {
	}

	public Franquicia(String nombre) {
		this.nombre = nombre;
		
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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	

}
