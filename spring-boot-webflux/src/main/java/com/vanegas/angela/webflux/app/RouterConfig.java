package com.vanegas.angela.webflux.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import org.springframework.web.reactive.function.server.RouterFunction;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vanegas.angela.webflux.app.controllers.FranquiciaController;
import com.vanegas.angela.webflux.app.controllers.ProductoController;
import com.vanegas.angela.webflux.app.controllers.SucursaController;

@Configuration
public class RouterConfig {
	
	@Bean
	public RouterFunction<ServerResponse> rutasProducto(ProductoController productoController){
		return route(GET("/api/producto"), productoController::listar)
				.andRoute(GET("/api/producto/{id}"), productoController::verProducto)
				.andRoute(PUT("/api/producto/{id}"), productoController::editarProducto)
				.andRoute(DELETE("/api/producto/{id}"), productoController::eliminarProducto)
				.andRoute(POST("/api/producto"), productoController::guardarProducto);
				
	}
	
	@Bean
	public RouterFunction<ServerResponse> rutasSucursales(SucursaController sucursaController){
		return route(POST("/api/sucursal"), sucursaController::guardarSucursal)
				.andRoute(GET("/api/sucursal/{id}"), sucursaController::verSucursal)
				.andRoute(GET("/api/sucursal"), sucursaController::listarSucursal)
				;
				
	}

	@Bean
	public RouterFunction<ServerResponse> rutasFranquicias(FranquiciaController franquiciaController){
		return route(POST("/api/franquicia"), franquiciaController::guardarfranquicia)
				.andRoute(GET("/api/franquicia"), franquiciaController::listarfranquicia)
				.andRoute(GET("/api/franquicia/{id}"), franquiciaController::verFranquicia);
	}

	

}
