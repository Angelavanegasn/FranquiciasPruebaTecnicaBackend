package com.vanegas.angela.webflux.app.models.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.vanegas.angela.webflux.app.models.documents.Sucursal;
import reactor.core.publisher.Mono;

public interface SucursalDao extends ReactiveMongoRepository<Sucursal, String>{

    public Mono<Sucursal> findByNombre(String nombre);

    @Query("{'nombre': ?0 }")
    public Mono<Sucursal> buscarPorNombre(String nombre);
}