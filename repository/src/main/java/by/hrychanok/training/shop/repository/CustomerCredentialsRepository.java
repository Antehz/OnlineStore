package by.hrychanok.training.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.CustomerCredentials;

public interface CustomerCredentialsRepository extends JpaRepository<CustomerCredentials, Long> {
	CustomerCredentials findByLoginAndPassword(String login, String password);
	CustomerCredentials findByLogin(String login);
}
