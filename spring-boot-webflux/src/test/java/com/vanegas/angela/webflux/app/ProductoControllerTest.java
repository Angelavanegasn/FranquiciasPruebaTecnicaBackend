package com.vanegas.angela.webflux.app;

import com.vanegas.angela.webflux.app.models.documents.Franquicia;
import com.vanegas.angela.webflux.app.models.documents.Producto;
import com.vanegas.angela.webflux.app.models.documents.Sucursal;
import com.vanegas.angela.webflux.app.models.services.ProductoService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;


    @RunWith(SpringRunner.class)
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    public class ProductoControllerTest {

        @Autowired
        private WebTestClient client;

        @Autowired
        private ProductoService service;


        @Test
        public void verProducto() {

            Producto prodcuto = service.findByNombre("Apple iPod").block();

            client.get()
                    .uri("/api/producto/{id}", Collections.singletonMap("id", prodcuto.getId()))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                    .expectBody()
                    .jsonPath("$.id").isNotEmpty()
                    .jsonPath("$.nombre").isEqualTo("Apple iPod");
        }

        @Test
        public void listarProducto() {

            client.get()
                    .uri("/api/producto")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .expectBodyList(Producto.class)
                    .consumeWith(response -> {
                        List<Producto> productos = response.getResponseBody();

                        productos.forEach(p ->{
                            System.out.println(p.getNombre());

                        });
                        Assertions.assertThat(productos.size()>0).isTrue();
                    });


        }

        @Test
        public void guardarProducto() {
            Franquicia dos = new Franquicia("dos");
            Sucursal pasoancho = new Sucursal("Pasoancho",dos);
            Producto producto = new Producto("Sony Camara HD Digital", pasoancho,10);

            client.post()
                    .uri("/api/producto")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .body(Mono.just(producto), Producto.class)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                    .expectBody()
                    .jsonPath("$.nombre").isEqualTo("Sony Camara HD Digital")
                    .jsonPath("$.sucursal.nombre").isEqualTo("Pasoancho");

        }

	@Test
	public void eliminarProducto() {
		Producto producto = service.findByNombre("Mica Cómoda 5 Cajones").block();

		client.delete()
		.uri("/api/producto/{id}", Collections.singletonMap("id", producto.getId()))
		.exchange()
		.expectStatus().isNoContent()
		.expectBody()
		.isEmpty();
	}


    }


