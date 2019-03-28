package se.lexicon.spring_data_workshop.service;

import java.time.LocalDateTime;
import java.util.List;

import se.lexicon.spring_data_workshop.entity.OrderItem;
import se.lexicon.spring_data_workshop.entity.ProductOrder;

public interface OrderService {

	ProductOrder findOrderById(int id);

	List<ProductOrder> findAll();

	List<ProductOrder> findByDateTimeBefore(LocalDateTime date);

	OrderItem createOrderItem(int productId, int quantity);

	ProductOrder createOrder();

	ProductOrder createOrder(List<OrderItem> orderContent);

	ProductOrder save(ProductOrder order);

}