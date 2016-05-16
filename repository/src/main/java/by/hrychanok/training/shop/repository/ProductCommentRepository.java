package by.hrychanok.training.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import by.hrychanok.training.shop.model.ProductComment;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {

    List<ProductComment> getByProductId(Long id);
    List<ProductComment> getByCustomerId(Long id);
	
}
