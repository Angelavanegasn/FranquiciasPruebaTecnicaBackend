package com.vanegas.angela.webflux.app.models.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.vanegas.angela.webflux.app.models.documents.Franquicia;
import reactor.core.publisher.Mono;

public interface FranquiciaDao extends ReactiveMongoRepository<Franquicia, String>{

    public Mono<Franquicia> findByNombre(String nombre);

    @Query ("{'nombre': ?0 }")
    public Mono<Franquicia> buscarPorNombre(String nombre);
}