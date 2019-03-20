package se.lexicon.spring_data_workshop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import se.lexicon.spring_data_workshop.entity.ProductOrder;

public interface ProductOrderRepo extends CrudRepository<ProductOrder, Integer>{
	
	List<ProductOrder> findByCreationDateTimeBefore(LocalDateTime someDateTime);

}
