package se.lexicon.spring_data_workshop.service;

import java.util.List;

import se.lexicon.spring_data_workshop.entity.Product;
import se.lexicon.spring_data_workshop.exception.EntityNotFoundException;

public interface ProductService {

	Product findById(int id);

	List<Product> findAll();

	List<Product> findByName(String name);

	boolean removeProduct(int id);

	Product save(Product product);

	Product update(int productId, Product updated) throws EntityNotFoundException;

}