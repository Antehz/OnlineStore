package by.hrychanok.training.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	@Modifying
	@Query ("FROM Product  WHERE category.id=:param")   
	List<Product> getProductByCategoryId(@Param("param")Long id);
	
	@Modifying
	@Query ("FROM Product product left join  fetch product.category category WHERE category.name=:param")   
	List<Product> getProductByCategoryName(@Param("param")String name);
}
