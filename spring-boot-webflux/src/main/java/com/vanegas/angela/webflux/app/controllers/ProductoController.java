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

import com.vanegas.angela.webflux.app.models.documents.Producto;
import com.vanegas.angela.webflux.app.models.services.ProductoService;

import reactor.core.publisher.Mono;


@Component
public class ProductoController {
	
	@Autowired
	private ProductoService productoservice;
		
	public Mono<ServerResponse> listar(ServerRequest request){
		return ServerResponse.ok().contentType(APPLICATION_JSON_UTF8)
				.body(productoservice.findAll(), Producto.class);
	}
	
	public Mono<ServerResponse> verProducto(ServerRequest request){
		String id = request.pathVariable("id");
		return errorHandler(
				productoservice.findById(id).flatMap(p -> ServerResponse.ok()
				.contentType(APPLICATION_JSON_UTF8)
				.syncBody(p))
				.switchIfEmpty(ServerResponse.notFound().build())
				);
	}
	
	public Mono<ServerResponse> editarProducto(ServerRequest request){
		Mono<Producto> producto = request.bodyToMono(Producto.class);
		String id = request.pathVariable("id");
		
		Mono<Producto> productoDb = productoservice.findById(id);
			
		return productoDb.zipWith(producto, (db,req)->{
			db.setNombre(req.getNombre());
			db.setSucursal(req.getSucursal());
			db.setCantidadSlock(req.getCantidadSlock());
			return db;
		}).flatMap(p-> ServerResponse.created(URI.create("/api/producto/".concat(p.getId())))
				.contentType(APPLICATION_JSON_UTF8)
				.body(productoservice.save(p), Producto.class));
				
}
	
	
	public Mono<ServerResponse> eliminarProducto(ServerRequest request){
		String id = request.pathVariable("id");
		
		Mono<Producto> productoDb = productoservice.findById(id);
		
		return productoDb.flatMap(p-> productoservice.delete(p).then(ServerResponse.noContent().build()))
				.switchIfEmpty(ServerResponse.notFound().build());
				
	}
	
	
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
	
	public Mono<ServerResponse> guardarProducto(ServerRequest request){
		Mono<Producto> producto = request.bodyToMono(Producto.class);
		
		
		return errorHandler(
				producto
				.flatMap(p -> productoservice.save(p))
				.flatMap(p-> ServerResponse.created(URI.create("/api/producto/".concat(p.getId())))
				.contentType(APPLICATION_JSON_UTF8)
				.syncBody(p))
				);
	}
}
