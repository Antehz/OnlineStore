package by.hrychanok.training.shop.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import by.hrychanok.training.shop.model.CartContent;
import by.hrychanok.training.shop.model.Product;

public interface CartContentRepository extends JpaRepository<CartContent, Long>,JpaSpecificationExecutor<CartContent>{
	
	@Modifying
	@Query ("delete from CartContent  where customer.id=:param")
	void clearCustomerCartContent(@Param("param")Long id);
	
	@Modifying
	@Query ("FROM CartContent  WHERE customer.id=:param")   
	List<CartContent> getCartContentByCustomerId(@Param("param")Long id);


}
