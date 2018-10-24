package guru.springframework.springwebfluxrest.bootstrap;

import guru.springframework.springwebfluxrest.domain.Category;
import guru.springframework.springwebfluxrest.domain.Vendor;
import guru.springframework.springwebfluxrest.repositories.CategoryRepository;
import guru.springframework.springwebfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;

    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if(categoryRepository.count().block() == 0) {
            // load data
            System.out.println("##### LOADING DATA ON BOOTSTRAP");

            categoryRepository.save(Category.builder()
                            .description("Fruits").build()).block();

            categoryRepository.save(Category.builder()
                            .description("Nuts").build()).block();

            categoryRepository.save(Category.builder()
                            .description("Breads").build()).block();

            categoryRepository.save(Category.builder()
                            .description("Meats").build()).block();

            categoryRepository.save(Category.builder()
                            .description("Eggs").build()).block();

            System.out.println("Loaded Categories: " + categoryRepository.count().block());

            vendorRepository.save(Vendor.builder()
                    .firstName("Joe")
                    .lastName("Buck").build()).block();


            vendorRepository.save(Vendor.builder()
                    .firstName("Harry")
                    .lastName("Houdini").build()).block();


            vendorRepository.save(Vendor.builder()
                    .firstName("Foxy")
                    .lastName("Cleopatra").build()).block();


            vendorRepository.save(Vendor.builder()
                    .firstName("Ron")
                    .lastName("Swanson").build()).block();


            vendorRepository.save(Vendor.builder()
                    .firstName("Trey")
                    .lastName("Anastasio").build()).block();


            System.out.println("Loaded Vendors: " + vendorRepository.count().block());
        }

    }



}
