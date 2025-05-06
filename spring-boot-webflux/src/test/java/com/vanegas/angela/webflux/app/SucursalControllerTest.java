package com.vanegas.angela.webflux.app;

import com.vanegas.angela.webflux.app.models.documents.Sucursal;
import com.vanegas.angela.webflux.app.models.services.SucursalService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;


    @RunWith(SpringRunner.class)
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    public class SucursalControllerTest {

        @Autowired
        private WebTestClient client;

        @Autowired
        private SucursalService service;




        @Test
        public void listarSucursa() {

            client.get()
                    .uri("/api/sucursal")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .expectBodyList(Sucursal.class)
                    .consumeWith(response -> {
                        List<Sucursal> sucursal = response.getResponseBody();

                        sucursal.forEach(p ->{
                            System.out.println(p.getNombre());

                        });
                        Assertions.assertThat(sucursal.size()>0).isTrue();
                    });


        }


    }

