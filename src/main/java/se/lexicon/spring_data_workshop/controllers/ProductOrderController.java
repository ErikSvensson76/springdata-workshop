package se.lexicon.spring_data_workshop.controllers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import se.lexicon.spring_data_workshop.entity.OrderItem;
import se.lexicon.spring_data_workshop.entity.Product;
import se.lexicon.spring_data_workshop.entity.ProductOrder;
import se.lexicon.spring_data_workshop.forms_and_views.OrderItemForm;
import se.lexicon.spring_data_workshop.forms_and_views.ProductOrderForm;
import se.lexicon.spring_data_workshop.service.OrderService;
import se.lexicon.spring_data_workshop.validation.ValidAppUser;

@RestController
public class ProductOrderController {

	private OrderService orderService;

	@Autowired
	public ProductOrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping("/order")
	public ResponseEntity<List<ProductOrder>> getAll(){
		List<ProductOrder> orders = orderService.findAll();
		
		return orders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(orders);		
	}
	
	@PostMapping("/order")
	public ResponseEntity<ProductOrder> create(
			@RequestBody List<@Valid OrderItemForm> orderItemForms,
			@RequestAttribute @Valid @ValidAppUser String userId)
	{		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(orderService.createOrder(orderItemForms, userId));
	}
	
	
	
	
}
