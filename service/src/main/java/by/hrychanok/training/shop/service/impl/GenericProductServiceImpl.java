package by.hrychanok.training.shop.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import by.hrychanok.training.shop.model.Product;
import by.hrychanok.training.shop.repository.GenericProductRepository;
import by.hrychanok.training.shop.service.GenericProductService;

@Service
@Transactional
public class GenericProductServiceImpl<T extends Product> extends BasicServiceImpl<T, GenericProductRepository<T>, Long>
		implements GenericProductService<T> {
}
