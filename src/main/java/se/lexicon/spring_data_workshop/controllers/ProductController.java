package se.lexicon.spring_data_workshop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import se.lexicon.spring_data_workshop.entity.Product;
import se.lexicon.spring_data_workshop.service.ProductService;

@RestController
public class ProductController {
	
	private ProductService service;

	@Autowired
	public ProductController(ProductService service) {
		this.service = service;
	}
	
	@GetMapping("/product")
	public ResponseEntity<List<Product>> get(){
		List<Product> products = service.findAll();
		
		if(products.isEmpty()) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.ok(products);
		}		
	}
	
	@PostMapping("/product")
	public ResponseEntity<Product> create(@RequestBody Product newProduct){
		if(newProduct == null) {
			return ResponseEntity.badRequest().build();
		}
		
		Product saved = service.save(newProduct);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);		
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<Product> update(@PathVariable int id, @RequestBody Product updated){
		if(updated == null) {
			return ResponseEntity.badRequest().build();
		}
		
		try {
			return ResponseEntity.ok(service.update(id, updated));
		}catch(IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}		
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable int id){		
		try {
			return ResponseEntity.ok(service.findById(id));
		}catch(IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}		
	}	
}
