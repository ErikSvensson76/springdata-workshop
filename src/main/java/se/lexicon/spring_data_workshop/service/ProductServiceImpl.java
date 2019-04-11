package se.lexicon.spring_data_workshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.lexicon.spring_data_workshop.entity.Product;
import se.lexicon.spring_data_workshop.exception.EntityNotFoundException;
import se.lexicon.spring_data_workshop.repository.ProductRepo;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService {
	
	private ProductRepo productRepo;

	@Autowired
	public ProductServiceImpl(ProductRepo productRepo) {
		this.productRepo = productRepo;
	}
	
	
	public Product findById(int id) {
		Optional<Product> result = productRepo.findById(id);
		
		return result.orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " could not be found"));		
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
	
	/**
	 * 
	 * @param productId 
	 * @param updated
	 * @return Product original updated 
	 * @throws EntityNotFoundException when original product could not be found with int productId
	 */
	@Override
	public Product update(int productId, Product updated) throws EntityNotFoundException{
		Product original = findById(productId);		
		original.setName(updated.getName());
		original.setPrice(updated.getPrice());
		return productRepo.save(original);
	}
}
