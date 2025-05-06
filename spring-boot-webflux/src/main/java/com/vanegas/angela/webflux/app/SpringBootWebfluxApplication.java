package com.vanegas.angela.webflux.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.vanegas.angela.webflux.app.models.documents.Franquicia;
import com.vanegas.angela.webflux.app.models.documents.Producto;
import com.vanegas.angela.webflux.app.models.documents.Sucursal;
import com.vanegas.angela.webflux.app.models.services.FranquiciaService;
import com.vanegas.angela.webflux.app.models.services.ProductoService;
import com.vanegas.angela.webflux.app.models.services.SucursalService;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner{

	@Autowired
	private ProductoService service;
	
	@Autowired
	private SucursalService servicesucursal;
	
	
	@Autowired
	private FranquiciaService franquiciaService;
	
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		mongoTemplate.dropCollection("productos").subscribe();
		mongoTemplate.dropCollection("sucursales").subscribe();
		mongoTemplate.dropCollection("franquicias").subscribe();
		
		Franquicia uno = new Franquicia("uno");
		Franquicia dos = new Franquicia("dos");
		Franquicia tres = new Franquicia("tres");
		Franquicia cuatro = new Franquicia("cuatro");
		
		
		Sucursal pasoancho = new Sucursal("Pasoancho",dos);
		Sucursal Calima = new Sucursal("Calima",cuatro);
		Sucursal Juanchito = new Sucursal("Juanchito",tres);
		Sucursal Centro = new Sucursal("Centro",uno);
		
		
		Flux.just(pasoancho, Calima, Juanchito, Centro)
		.flatMap(servicesucursal::saveSucursal)
		.doOnNext(c ->{
			log.info("Sucursal creada: " + c.getNombre() + ", Id: " + c.getId());
		}).thenMany(
				
				
				
				Flux.just(new Producto("TV Panasonic Pantalla LCD", pasoancho,4),
						new Producto("Sony Camara HD Digital", pasoancho,10),
						new Producto("Apple iPod", pasoancho,5),
						new Producto("Sony Notebook", Juanchito,8),
						new Producto("Hewlett Packard Multifuncional", Juanchito,10),
						new Producto("Bianchi Bicicleta", Calima,11),
						new Producto("HP Notebook Omen 17", Juanchito,12),
						new Producto("Mica Cómoda 5 Cajones", Centro,203),
						new Producto("TV Sony Bravia OLED 4K Ultra HD", pasoancho,92)
						
						)
				.flatMap(producto -> {
					
					return service.save(producto);
					})
		).subscribe(producto -> log.info("Insert: " + producto.getId() + " " + producto.getNombre()));
		
		Flux.just(uno, dos, tres, cuatro)
		.flatMap(franquiciaService::saveFranquicia)
		.doOnNext(c ->{
			log.info("Sucursal creada: " + c.getNombre() + ", Id: " + c.getId());
		}).thenMany(
				Flux.just(new Sucursal("De Vanegas", uno),
						new Sucursal("De Luz", dos),
						new Sucursal("De Angela", tres),
						new Sucursal("De Nuñez", cuatro)
						
						
						)
				.flatMap(sucursal -> {
					
					return servicesucursal.saveSucursal(sucursal);
					})
		).subscribe(sucursal -> log.info("Insert: " + sucursal.getId() + " " + sucursal.getNombre()));
		
		
	}
}
