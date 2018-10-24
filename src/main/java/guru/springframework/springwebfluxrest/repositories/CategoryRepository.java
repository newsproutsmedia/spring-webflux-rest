package guru.springframework.springwebfluxrest.repositories;

import guru.springframework.springwebfluxrest.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {



}
