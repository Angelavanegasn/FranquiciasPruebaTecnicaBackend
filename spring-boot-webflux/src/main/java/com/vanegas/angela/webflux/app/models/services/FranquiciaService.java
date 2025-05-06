package com.vanegas.angela.webflux.app.models.services;

import com.vanegas.angela.webflux.app.models.documents.Franquicia;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranquiciaService {

	public Flux<Franquicia> findAll();

	public Flux<Franquicia> findAllConNombreUpperCase();

	public Flux<Franquicia> findAllConNombreUpperCaseRepeat();

	public Mono<Franquicia> findById(String id);

	public Mono<Franquicia> saveFranquicia(Franquicia franquicia);

	public Mono<Void> delete(Franquicia producto);

	public Mono<Void> delete(String id);

	public Mono<Franquicia> update(Franquicia franquicia, String id);

	public Mono<Franquicia> findByNombre(String nombre);
}