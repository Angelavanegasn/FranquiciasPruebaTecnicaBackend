package com.vanegas.angela.webflux.app;

import com.vanegas.angela.webflux.app.models.documents.Franquicia;
import com.vanegas.angela.webflux.app.models.services.FranquiciaService;
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
public class FranquiciaControllerTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private FranquiciaService service;


    @Test
    public void guardarfranquicia() {

        Franquicia franquicia = service.findByNombre("uno").block();

        client.get()
                .uri("/api/franquicia/{id}", Collections.singletonMap("id", franquicia.getId()))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.nombre").isEqualTo("uno");
    }
    @Test
    public void listarFranquicia() {

        client.get()
                .uri("/api/franquicia")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .expectBodyList(Franquicia.class)
                .consumeWith(response -> {
                    List<Franquicia> franquicia = response.getResponseBody();

                    franquicia.forEach(p ->{
                        System.out.println(p.getNombre());

                    });
                    Assertions.assertThat(franquicia.size()>0).isTrue();
                });


    }

    @Test
    public void guardarFranquicia() {
        Franquicia franquicia = new Franquicia("dos");

        client.post()
                .uri("/api/franquicia")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(franquicia), Franquicia.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.nombre").isEqualTo("dos");

    }


}