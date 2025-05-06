package com.vanegas.angela.webflux.app.models.services;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vanegas.angela.webflux.app.models.dao.FranquiciaDao;
import com.vanegas.angela.webflux.app.models.documents.Franquicia;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FranquiciaServiceImpl implements FranquiciaService{

	@Autowired
	private WebClient client;
	
	
	@Autowired
	private FranquiciaDao dao;
	
	@Override
	public Flux<Franquicia> findAll() {
		return dao.findAll();
	}

	@Override
	public Mono<Franquicia> findById(String id) {
		return dao.findById(id);
	}

	@Override
	public Mono<Franquicia> saveFranquicia(Franquicia franquicia) {
		
		if(franquicia.getCreateAt() == null) {
			franquicia.setCreateAt(new Date());
		}
		return dao.save(franquicia);
	}

	@Override
	public Mono<Void> delete(Franquicia franquicia) {
		return dao.delete(franquicia);
	}

	@Override
	public Flux<Franquicia> findAllConNombreUpperCase() {
		return dao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		});
	}

	@Override
	public Flux<Franquicia> findAllConNombreUpperCaseRepeat() {

		return findAllConNombreUpperCase().repeat(5000);
	}

	
	

	@Override
	public Mono<Franquicia> update(Franquicia franquicia, String id) {
		
		return client.put()
				.uri("/{id}", Collections.singletonMap("id", id))
				.accept(APPLICATION_JSON_UTF8)
				.contentType(APPLICATION_JSON_UTF8)
				.syncBody(franquicia)
				.retrieve()
				.bodyToMono(Franquicia.class);
	}

	@Override
	public Mono<Void> delete(String id) {
		return client.delete().uri("/{id}", Collections.singletonMap("id", id))
				.retrieve()
				.bodyToMono(Void.class);
	}
	@Override
	public Mono<Franquicia> findByNombre(String nombre) {
		return dao.findByNombre(nombre);

	}
}
