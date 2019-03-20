package se.lexicon.spring_data_workshop.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import se.lexicon.spring_data_workshop.entity.Product;

public interface ProductRepo extends CrudRepository<Product, Integer>{
	
	List<Product> findByNameIgnoreCase(String name);
	
}
