package se.lexicon.spring_data_workshop.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import se.lexicon.spring_data_workshop.entity.Product;
import se.lexicon.spring_data_workshop.form.ProductForm;
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
	public ResponseEntity<Product> create(@Valid @RequestBody ProductForm newProduct){
		if(newProduct == null) {
			throw new IllegalArgumentException("@RequestBody ProductForm newProduct was " + newProduct);
		}
		
		Product saved = service.save(ProductService.convertFromForm(newProduct));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);		
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<Product> update(@PathVariable int id, @RequestBody Product updated){
		if(updated == null) {
			throw new IllegalArgumentException("@RequestBody Product updated was " + updated);
		}
		
		return ResponseEntity.ok(service.update(id, updated));
				
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable int id){		
		return ResponseEntity.ok(service.findById(id));		
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> productValidationException(MethodArgumentNotValidException e){
		Map<String,String> errors = new HashMap<>();
		e.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return errors;
	}
}
