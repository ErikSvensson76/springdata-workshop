package se.lexicon.spring_data_workshop.controller_tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import se.lexicon.spring_data_workshop.controllers.ProductController;
import se.lexicon.spring_data_workshop.entity.Product;
import se.lexicon.spring_data_workshop.exception.EntityNotFoundException;
import se.lexicon.spring_data_workshop.exception.MyExceptionHandler;
import se.lexicon.spring_data_workshop.forms_and_views.ProductForm;
import se.lexicon.spring_data_workshop.service.ProductService;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

public class ProductControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private ProductService productService;
	
	@InjectMocks
	private ProductController productController;
	
	private ObjectMapper jsonMapper;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(productController)	
				.setControllerAdvice(MyExceptionHandler.class)
				.build();
		
		jsonMapper = new ObjectMapper();
	}
	
	@Test
	public void test_get() throws Exception {
		List<Product> products = Arrays.asList(
				new Product("Test product 1", 10),
				new Product("Test product 2", 12)
				);
		
		when(productService.findAll()).thenReturn(products);
		
		mockMvc.perform(get("/product"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$", hasSize(2)));		
	}
	
	@Test
	public void test_get_return_noContent() throws Exception{
		List<Product> emptyList = new ArrayList<>();
		
		when(productService.findAll()).thenReturn(emptyList);
		
		mockMvc.perform(get("/product"))
			.andExpect(status().isNoContent());
	}
	
	@Test
	public void test_create_Product_success() throws JsonProcessingException, Exception {
		ProductForm requestBody = new ProductForm();
		requestBody.setName("Test Product 1");
		requestBody.setPrice(10);		
		
		Product product = new Product("Test Product 1", 10);
		
		when(productService.save(ProductService.convertFromForm(requestBody)))
			.thenReturn(product);
		
		mockMvc.perform(post("/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(requestBody)))
		.andExpect(status().isCreated());		
	}
	
	@Test
	public void test_create_Product_fails() throws JsonProcessingException, Exception {
		ProductForm requestBody = new ProductForm();
		requestBody.setName("x");
		requestBody.setPrice(0);
		
		mockMvc.perform(post("/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(requestBody)))
		.andExpect(status().isBadRequest());
	}	
	
	@Test
	public void test_getProduct_success() throws Exception {
		Product product = new Product("Test product", 10);
		when(productService.findById(anyInt())).thenReturn(product);
		
		mockMvc.perform(get("/product/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$.name", is("Test product")))
			.andExpect(jsonPath("$.price", is(10d)));			
	}	
	
	@Test
	public void test_getProduct_notFound() throws Exception {
		
		when(productService.findById(anyInt())).thenThrow(EntityNotFoundException.class);
		
		mockMvc.perform(get("/product/{id}", 1))
			.andExpect(status().isNotFound());		
	}
	
	@Test
	public void test_update_success() throws JsonProcessingException, Exception {
		Product updated = new Product("Updated product", 15.90);
				
		when(productService.update(2, updated)).thenReturn(updated);
		
		mockMvc.perform(put("/product/{id}", 2, updated)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(updated)))
		.andExpect(status().isOk());		
	}
}
