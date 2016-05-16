package by.hrychanok.training.shop.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import by.hrychanok.training.shop.model.CartContent;

public interface CartContentRepository extends JpaRepository<CartContent, Long>, CartContentRepositoryCustom {
	
	@Modifying
	@Query ("delete from CartContent  where customer.id=:param")
	void clearCustomerCartContent(@Param("param")Long id);


}
