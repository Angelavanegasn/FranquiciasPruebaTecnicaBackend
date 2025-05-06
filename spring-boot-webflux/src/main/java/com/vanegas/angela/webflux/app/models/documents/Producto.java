package com.vanegas.angela.webflux.app.models.documents;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="productos")
public class Producto {
	
	@Id
	private String id;
	
	@NotEmpty
	private String nombre;
	
	@Valid
	private Sucursal sucursal;
	
	private int cantidadSlock;
	
	private Date createAt;
	

	public Producto() {}

	public Producto(String nombre, Sucursal sucursal, int cantidadSlock) {
		this.nombre = nombre;
		this.sucursal = sucursal;
		this.cantidadSlock = cantidadSlock;
		this.createAt = new Date();
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
	
	

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public int getCantidadSlock() {
		return cantidadSlock;
	}

	public void setCantidadSlock(int cantidadSlock) {
		this.cantidadSlock = cantidadSlock;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	
	

}
