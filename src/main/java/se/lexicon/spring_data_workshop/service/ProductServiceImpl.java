package se.lexicon.spring_data_workshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.lexicon.spring_data_workshop.entity.Product;
import se.lexicon.spring_data_workshop.repository.ProductRepo;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	private ProductRepo productRepo;

	@Autowired
	public ProductServiceImpl(ProductRepo productRepo) {
		this.productRepo = productRepo;
	}
	
	
	public Product findById(int id) {
		Optional<Product> result = productRepo.findById(id);
		
		return result.orElseThrow(IllegalArgumentException::new);		
	}
	
	public List<Product> findAll() {
		return (List<Product>) productRepo.findAll();
	}
	
	public List<Product> findByName(String name){
		return productRepo.findByNameIgnoreCase(name);
	}
	
	public boolean removeProduct(int id) {
		productRepo.deleteById(id);		
		return productRepo.existsById(id);
	}
	
	public Product save(Product product) {
		return productRepo.save(product);
	}
}
