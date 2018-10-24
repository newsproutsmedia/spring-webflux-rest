package guru.springframework.springwebfluxrest.controller;

import guru.springframework.springwebfluxrest.domain.Vendor;
import guru.springframework.springwebfluxrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

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

        webTestClient.get().uri("/api/v1/vendors")
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

    @Test
    public void testCreateVendor(){
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().firstName("Barney").lastName("Rubble").build()));

        Mono<Vendor> venToSaveMono = Mono.just(Vendor.builder().firstName("Fred").lastName("Flintstone").build());

        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(venToSaveMono, Vendor.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void testUpdateVendor() {
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> venToUpdateMono = Mono.just(Vendor.builder().firstName("Fred").lastName("Flintstone").build());

        webTestClient.put()
                .uri("/api/v1/vendors/someid")
                .body(venToUpdateMono, Vendor.class)
                .exchange()
                .expectStatus().isOk();
    }
}