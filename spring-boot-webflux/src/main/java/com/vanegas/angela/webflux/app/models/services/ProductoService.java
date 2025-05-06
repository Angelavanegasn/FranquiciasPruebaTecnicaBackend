package com.vanegas.angela.webflux.app.models.services;

import com.vanegas.angela.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {

	public Flux<Producto> findAll();

	public Flux<Producto> findAllConNombreUpperCase();

	public Flux<Producto> findAllConNombreUpperCaseRepeat();

	public Mono<Producto> findById(String id);

	public Mono<Producto> save(Producto producto);

	public Mono<Void> delete(Producto producto);

	public Mono<Producto> update(Producto producto, String id);

	public Mono<Void> delete(String id);

	public Mono<Producto> findByNombre(String nombre);



}
