package guru.springframework.springwebfluxrest.controller;

import guru.springframework.springwebfluxrest.domain.Category;
import guru.springframework.springwebfluxrest.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

public class CategoryControllerTest {

    WebTestClient webTestClient;

    CategoryRepository categoryRepository;

    CategoryController categoryController;

    @Before
    public void setUp() throws Exception {

        // Assign a mock of the CategoryRepository to the categoryController
        categoryRepository = Mockito.mock(CategoryRepository.class);

        // Inject mock object into the controller
        categoryController = new CategoryController(categoryRepository);

        // set up webtestclient
        webTestClient = WebTestClient.bindToController(categoryController).build();

    }

    @Test
    public void listAll() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("Cat1").build(),
                Category.builder().description("Cat2").build()));

        webTestClient.get().uri("/api/v1/categories/")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given(categoryRepository.findById("someId"))
                .willReturn(Mono.just(Category.builder().description("Cat1").build()));

        webTestClient.get().uri("/api/v1/categories/someId")
                .exchange()
                .expectBodyList(Category.class);
    }

    @Test
    public void testCreateCategory() {

        // create the mock object to be injected
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().description("description").build()));

        // inject the mock object
        Mono<Category> catToSaveMono = Mono.just(Category.builder().description("Some Cat").build());

        // execute the test
        // using the POST method
        webTestClient.post()
                // go to this url
                .uri("/api/v1/categories/")
                // expect the mock object to be of type Category
                .body(catToSaveMono, Category.class)
                // exchange the info
                .exchange()
                // expect a return status of CREATED
                .expectStatus().isCreated();
    }

    @Test
    public void TestUpdateCategory() {
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToUpdateMono = Mono.just(Category.builder().description("Some Cat").build());

        webTestClient.put()
                .uri("/api/v1/categories/someidvalue")
                .body(catToUpdateMono, Category.class)
                .exchange()
                .expectStatus().isOk();
    }
}