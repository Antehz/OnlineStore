package by.hrychanok.training.shop.service;

import by.hrychanok.training.shop.model.Product;

public interface GenericProductService<T extends Product> extends BasicService<T, Long>{

}
