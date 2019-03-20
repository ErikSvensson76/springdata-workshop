package se.lexicon.spring_data_workshop;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import se.lexicon.spring_data_workshop.entity.OrderItem;
import se.lexicon.spring_data_workshop.entity.Product;
import se.lexicon.spring_data_workshop.entity.ProductOrder;
import se.lexicon.spring_data_workshop.repository.ProductOrderRepo;
import se.lexicon.spring_data_workshop.repository.ProductRepo;

@Component
@Transactional(rollbackFor = Exception.class)
public class TestCommandLine implements CommandLineRunner{
	
	private ProductRepo repo;
	private ProductOrderRepo orderRepo;	

	public TestCommandLine(ProductRepo repo, ProductOrderRepo orderRepo) {
		this.repo = repo;
		this.orderRepo = orderRepo;
	}

	@Override
	public void run(String... args) throws Exception {
		Product p = repo.save(new Product("Brown socks", 10.90));
		Product p2 = repo.save(new Product("Spring Data for dummies", 399.99));
		
		OrderItem i1 = new OrderItem(14, p);
		OrderItem i2 = new OrderItem(13, p2);
		
		ProductOrder order = new ProductOrder(LocalDateTime.now().minusWeeks(1));
		
		order.addOrderItem(i1);
		order.addOrderItem(i2);
		
		order = orderRepo.save(order);
		
		System.out.println(order.getPriceTotal());
		
		
	}

}
