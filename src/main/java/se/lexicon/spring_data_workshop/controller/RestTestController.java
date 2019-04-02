package se.lexicon.spring_data_workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import se.lexicon.spring_data_workshop.entity.ProductOrder;
import se.lexicon.spring_data_workshop.service.OrderService;

@RestController
public class RestTestController {
	
	private OrderService orderService;

	@Autowired
	public RestTestController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping("order/{id}")
	public ResponseEntity<ProductOrder> get(@PathVariable("id") int id){
		
		try {
			ProductOrder result = orderService.findOrderById(id);
			return ResponseEntity.ok(result);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}			
	}
	
	
	
	

}
