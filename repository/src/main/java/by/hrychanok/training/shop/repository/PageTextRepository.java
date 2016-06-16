package by.hrychanok.training.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import by.hrychanok.training.shop.model.PageText;

public interface PageTextRepository extends JpaRepository<PageText, Long>, JpaSpecificationExecutor<PageText> {

	List<PageText> findByTitle(String title);
}
