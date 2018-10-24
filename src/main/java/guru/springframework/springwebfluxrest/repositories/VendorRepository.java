package guru.springframework.springwebfluxrest.repositories;

import guru.springframework.springwebfluxrest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {

}
