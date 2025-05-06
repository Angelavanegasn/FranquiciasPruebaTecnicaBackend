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

import com.vanegas.angela.webflux.app.models.documents.Sucursal;
import com.vanegas.angela.webflux.app.models.services.SucursalService;

import reactor.core.publisher.Mono;


@Component
public class SucursaController {
	
	@Autowired
	private SucursalService sucursalservice;
	
	
	
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
	
	
	public Mono<ServerResponse> guardarSucursal(ServerRequest request){
		Mono<Sucursal> sucursal = request.bodyToMono(Sucursal.class);
		
		
		return errorHandler(
				sucursal
				.flatMap(p -> sucursalservice.saveSucursal(p))
				.flatMap(p-> ServerResponse.created(URI.create("/api/sucursal/".concat(p.getId())))
				.contentType(APPLICATION_JSON_UTF8)
				.syncBody(p))
				);
	}
	
	public Mono<ServerResponse> verSucursal(ServerRequest request){
		String id = request.pathVariable("id");
		return errorHandler(
				sucursalservice.findSucursalById(id).flatMap(p -> ServerResponse.ok()
				.contentType(APPLICATION_JSON_UTF8)
				.syncBody(p))
				.switchIfEmpty(ServerResponse.notFound().build())
				);
	}
	
	public Mono<ServerResponse> listarSucursal(ServerRequest request){
		return ServerResponse.ok().contentType(APPLICATION_JSON_UTF8)
				.body(sucursalservice.findAllSucursales(), Sucursal.class);
	}
}
