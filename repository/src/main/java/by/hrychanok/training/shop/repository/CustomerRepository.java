package by.hrychanok.training.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import by.hrychanok.training.shop.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom, JpaSpecificationExecutor<Customer> {

	Customer findByEmail(String email);

}
