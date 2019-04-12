package se.lexicon.spring_data_workshop;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import se.lexicon.spring_data_workshop.entity.AppUser;
import se.lexicon.spring_data_workshop.entity.OrderItem;
import se.lexicon.spring_data_workshop.entity.Product;
import se.lexicon.spring_data_workshop.entity.ProductOrder;
import se.lexicon.spring_data_workshop.repository.AppUserRepo;
import se.lexicon.spring_data_workshop.repository.ProductOrderRepo;
import se.lexicon.spring_data_workshop.repository.ProductRepo;

@Component
@Transactional(rollbackFor = Exception.class)
public class TestCommandLine implements CommandLineRunner{
	
	private ProductRepo productRepo;
	private ProductOrderRepo orderRepo;
	private AppUserRepo userRepo;	

	public TestCommandLine(ProductRepo productRepo, ProductOrderRepo orderRepo, AppUserRepo userRepo) {
		this.productRepo = productRepo;
		this.orderRepo = orderRepo;
		this.userRepo = userRepo;
	}
	
	@Override
	public void run(String... args) throws Exception {
		AppUser u1 = new AppUser("Erik", "Svensson", "erik.svensson@lexicon.se");
		AppUser u2 = new AppUser("Selma", "Örnberg", "selma.ornberg14@gmail.com");
		userRepo.save(u2);
		
		Product p = productRepo.save(new Product("Brown socks", 10.90));
		Product p2 = productRepo.save(new Product("Spring Data for dummies", 399.99));
		
		OrderItem i1 = new OrderItem(14, p);
		OrderItem i2 = new OrderItem(13, p2);
		
		ProductOrder order = new ProductOrder(LocalDateTime.now().minusWeeks(1));
		
		order.addOrderItem(i1);
		order.addOrderItem(i2);
		order.setCustomer(u1);
		
		order = orderRepo.save(order);
		
		
		
		
	}

}
