package by.hrychanok.training.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import by.hrychanok.training.shop.model.Customer;
import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.model.StatusOrder;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
	@Modifying
	@Query ("FROM Order  WHERE customer.id=:param")
	List<Order> getOrderListByCustomer(@Param("param")Long id);
	
	List<Order> getByStatus(StatusOrder status);

}
