package com.vanegas.angela.webflux.app.models.services;

import com.vanegas.angela.webflux.app.models.documents.Franquicia;
import com.vanegas.angela.webflux.app.models.documents.Sucursal;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SucursalService {

	public Flux<Sucursal> findAllSucursales();

	public Mono<Sucursal> findSucursalById(String id);

	public Mono<Franquicia> saveFranquicia(Franquicia sucursal);

	public Flux<Franquicia> findAllFranquicias();

	public Mono<Sucursal> saveSucursal(Sucursal sucursal);

	public Mono<Sucursal> findByNombre(String nombre);




}
