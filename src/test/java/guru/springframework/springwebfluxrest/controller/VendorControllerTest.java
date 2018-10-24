package guru.springframework.springwebfluxrest.controller;

import guru.springframework.springwebfluxrest.domain.Vendor;
import guru.springframework.springwebfluxrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class VendorControllerTest {

    WebTestClient webTestClient;

    VendorRepository vendorRepository;

    VendorController vendorController;

    @Before
    public void setUp() throws Exception {

        vendorRepository = Mockito.mock(VendorRepository.class);

        vendorController = new VendorController(vendorRepository);

        webTestClient = WebTestClient.bindToController(vendorController).build();

    }

    @Test
    public void listAllVendors() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(
                        Vendor.builder()
                            .firstName("Bob")
                            .lastName("Builder")
                                .build(),
                        Vendor.builder()
                                .firstName("Snarky")
                                .lastName("Suzy")
                                .build()));

        webTestClient.get().uri("/api/v1/vendors/")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getVendorById() {

        BDDMockito.given(vendorRepository.findById("SomeID"))
                .willReturn(Mono.just(Vendor.builder()
                        .firstName("Bob")
                        .lastName("Builder")
                        .build()));

        webTestClient.get().uri("/api/v1/vendors/SomeId")
                .exchange()
                .expectBodyList(Vendor.class);

    }
}