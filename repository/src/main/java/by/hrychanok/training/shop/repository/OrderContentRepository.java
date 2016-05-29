package by.hrychanok.training.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.model.OrderContent;
import by.hrychanok.training.shop.model.Product;

public interface OrderContentRepository extends JpaRepository<OrderContent, Long>,  JpaSpecificationExecutor<OrderContent> {

	List<Order> findByProductId(Long productId);
}
