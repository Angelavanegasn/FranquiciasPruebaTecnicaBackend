package com.vanegas.angela.webflux.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.springframework.http.MediaType.*;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vanegas.angela.webflux.app.models.documents.Franquicia;
import com.vanegas.angela.webflux.app.models.services.FranquiciaService;

import reactor.core.publisher.Mono;


@Component
public class FranquiciaController {
	
	@Autowired
	private FranquiciaService franquiciaservice;
	
	
	private Mono<ServerResponse> errorHandler(Mono<ServerResponse> response){
		return response.onErrorResume(error -> {
			WebClientResponseException errorResponse = (WebClientResponseException) error;
			if(errorResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
				Map<String, Object> body = new HashMap<>();
				body.put("error", "No existe el producto: ".concat(errorResponse.getMessage()));
				body.put("timestamp", new Date());
				body.put("status", errorResponse.getStatusCode().value());
				return ServerResponse.status(HttpStatus.NOT_FOUND).syncBody(body);
			}
			return Mono.error(errorResponse);
		});
	}
	
	public Mono<ServerResponse> guardarfranquicia(ServerRequest request){
		Mono<Franquicia> franquicia = request.bodyToMono(Franquicia.class);
				
		return errorHandler(
				franquicia
				.flatMap(p -> franquiciaservice.saveFranquicia(p))
				.flatMap(p-> ServerResponse.created(URI.create("/api/franquicia/".concat(p.getId())))
				.contentType(APPLICATION_JSON_UTF8)
				.syncBody(p))
				);
	}

	public Mono<ServerResponse> listarfranquicia(ServerRequest request){
		return ServerResponse.ok().contentType(APPLICATION_JSON_UTF8)
				.body(franquiciaservice.findAll(), Franquicia.class);
	}
	
	public Mono<ServerResponse> verFranquicia(ServerRequest request){
		String id = request.pathVariable("id");
		return errorHandler(
				franquiciaservice.findById(id).flatMap(p -> ServerResponse.ok()
				.contentType(APPLICATION_JSON_UTF8)
				.syncBody(p))
				.switchIfEmpty(ServerResponse.notFound().build())
				);
	}
	
	
}
