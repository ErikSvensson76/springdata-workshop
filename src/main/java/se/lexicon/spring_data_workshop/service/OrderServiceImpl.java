package se.lexicon.spring_data_workshop.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.lexicon.spring_data_workshop.entity.OrderItem;
import se.lexicon.spring_data_workshop.entity.Product;
import se.lexicon.spring_data_workshop.entity.ProductOrder;
import se.lexicon.spring_data_workshop.exception.EntityNotFoundException;
import se.lexicon.spring_data_workshop.repository.ProductOrderRepo;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	private ProductOrderRepo orderRepo;
	private ProductService productService;
	
	@Autowired
	public OrderServiceImpl(ProductOrderRepo orderRepo, ProductService productService) {
		this.orderRepo = orderRepo;
		this.productService = productService;
	}
	
	public ProductOrder findOrderById(int id) {
		return orderRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Could not find ProductOrder with id " + id));
	}
	
	public List<ProductOrder> findAll() {
		return (List<ProductOrder>) orderRepo.findAll();
	}
	
	public List<ProductOrder> findByDateTimeBefore(LocalDateTime date){
		return orderRepo.findByCreationDateTimeBefore(date);
	}
	
	public OrderItem createOrderItem(int productId, int quantity) {
		Product theProduct = productService.findById(productId);
		return new OrderItem(quantity, theProduct);
	}
	
	public ProductOrder createOrder(LocalDateTime timeStamp) {
		ProductOrder newOrder = new ProductOrder(timeStamp);
		return orderRepo.save(newOrder);
	}
	
	public ProductOrder createOrder(LocalDateTime timeStamp, List<OrderItem> orderContent) {
		ProductOrder newOrder = new ProductOrder(timeStamp);
		
		for(OrderItem item : orderContent) {
			newOrder.addOrderItem(item);
		}
		
		return orderRepo.save(newOrder);		
	}
	
	public ProductOrder save(ProductOrder order) {
		return orderRepo.save(order);
	}
}
