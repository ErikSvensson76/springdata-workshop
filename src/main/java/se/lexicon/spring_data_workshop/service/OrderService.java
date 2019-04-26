package se.lexicon.spring_data_workshop.service;

import java.time.LocalDateTime;
import java.util.List;

import se.lexicon.spring_data_workshop.entity.OrderItem;
import se.lexicon.spring_data_workshop.entity.ProductOrder;
import se.lexicon.spring_data_workshop.forms_and_views.OrderItemForm;
import se.lexicon.spring_data_workshop.forms_and_views.ProductOrderForm;

public interface OrderService {
	
	
	ProductOrder findOrderById(int id);

	List<ProductOrder> findAll();

	List<ProductOrder> findByDateTimeBefore(LocalDateTime date);

	OrderItem createOrderItem(int productId, int quantity);

	ProductOrder createOrder(LocalDateTime timeStamp);

	ProductOrder createOrder(List<OrderItemForm> orderContent, String appUserId);

	ProductOrder save(ProductOrder order);

	OrderItem createFromForm(OrderItemForm form);

}