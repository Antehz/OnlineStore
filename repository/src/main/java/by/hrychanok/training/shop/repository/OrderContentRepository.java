package by.hrychanok.training.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import by.hrychanok.training.shop.model.Order;
import by.hrychanok.training.shop.model.OrderContent;

public interface OrderContentRepository extends JpaRepository<OrderContent, Long> {

	List<Order> findByProductId(Long productId);
}
