
package se.lexicon.spring_data_workshop.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.lexicon.spring_data_workshop.entity.AppUser;
import se.lexicon.spring_data_workshop.entity.OrderItem;
import se.lexicon.spring_data_workshop.entity.Product;
import se.lexicon.spring_data_workshop.entity.ProductOrder;
import se.lexicon.spring_data_workshop.exception.EntityNotFoundException;
import se.lexicon.spring_data_workshop.forms_and_views.OrderItemForm;
import se.lexicon.spring_data_workshop.forms_and_views.ProductOrderForm;
import se.lexicon.spring_data_workshop.repository.AppUserRepo;
import se.lexicon.spring_data_workshop.repository.ProductOrderRepo;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	private ProductOrderRepo orderRepo;
	private ProductService productService;
	private AppUserRepo appUserRepo;
	
	@Autowired	
	public OrderServiceImpl(ProductOrderRepo orderRepo, ProductService productService, AppUserRepo appUserRepo) {
		this.orderRepo = orderRepo;
		this.productService = productService;
		this.appUserRepo = appUserRepo;
	}

	@Override
	public ProductOrder findOrderById(int id) {
		return orderRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Could not find ProductOrder with id " + id));
	}
	
	@Override
	public List<ProductOrder> findAll() {
		return (List<ProductOrder>) orderRepo.findAll();
	}
	
	@Override
	public List<ProductOrder> findByDateTimeBefore(LocalDateTime date){
		return orderRepo.findByCreationDateTimeBefore(date);
	}
	
	public OrderItem createOrderItem(int productId, int quantity) {
		Product theProduct = productService.findById(productId);
		return new OrderItem(quantity, theProduct);
	}
	
	@Override
	public ProductOrder createOrder(LocalDateTime timeStamp) {
		ProductOrder newOrder = new ProductOrder(timeStamp);
		return orderRepo.save(newOrder);
	}
	
	@Override
	public ProductOrder createOrder(List<OrderItemForm> orderContent, String appUserId) {
		ProductOrder newOrder = new ProductOrder(LocalDateTime.now());
		AppUser client = appUserRepo.findById(appUserId)
				.orElseThrow(() -> new EntityNotFoundException("AppUser with id " + appUserId + " couldn't be found"));
		
		newOrder.setCustomer(client);
		
		Set<OrderItem> orderItems = orderContent.stream()
				.map(this::createFromForm)
				.collect(Collectors.toCollection(TreeSet::new));
		
		orderItems.forEach(orderItem -> newOrder.addOrderItem(orderItem));		
				
		return orderRepo.save(newOrder);		
	}
	
	@Override
	public OrderItem createFromForm(OrderItemForm form) {
		Product product = productService.findById(form.getProductId());
		int quantity = form.getQuantity();
		return new OrderItem(quantity, product);
	}
	
	@Override
	public ProductOrder save(ProductOrder order) {
		return orderRepo.save(order);
	}	
	
}
