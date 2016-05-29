package by.hrychanok.training.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom, JpaSpecificationExecutor<Customer> {

	Customer findByEmail(String email);

}
