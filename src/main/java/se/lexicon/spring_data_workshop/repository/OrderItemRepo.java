package se.lexicon.spring_data_workshop.repository;

import org.springframework.data.repository.CrudRepository;

import se.lexicon.spring_data_workshop.entity.OrderItem;

public interface OrderItemRepo extends CrudRepository<OrderItem, Integer> {
	
}
