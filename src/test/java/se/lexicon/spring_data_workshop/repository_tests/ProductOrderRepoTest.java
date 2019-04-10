package se.lexicon.spring_data_workshop.repository_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import se.lexicon.spring_data_workshop.entity.AppUser;
import se.lexicon.spring_data_workshop.entity.OrderItem;
import se.lexicon.spring_data_workshop.entity.Product;
import se.lexicon.spring_data_workshop.entity.ProductOrder;
import se.lexicon.spring_data_workshop.repository.OrderItemRepo;
import se.lexicon.spring_data_workshop.repository.ProductOrderRepo;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class ProductOrderRepoTest {
	
	@Autowired private ProductOrderRepo testRepo;
	@Autowired private OrderItemRepo orderItemRepo;
	
	private int order1Id;
	private int order2Id;
	private OrderItem orderItemToRemove;
	private ProductOrder order1;
	private ProductOrder order2;
	private String user1Id;
	
	
	@Before
	public void init() {
		//Create some AppUsers
		AppUser user1 = new AppUser("test", "testsson","test@test.com");
		AppUser user2 = new AppUser("test2", "testsson2", "test2@test.com");
		
		//Create some Products
		Product spam = new Product("Spam", 10.90);
		Product computer = new Product("Computer", 5990);	
		
		//Create some OrderItems
		OrderItem orderItem = new OrderItem(5, spam);
		this.orderItemToRemove = orderItemRepo.save(orderItem);
		OrderItem orderItem2 = new OrderItem(7, computer);
		OrderItem orderItem3 = new OrderItem(2, spam);
		OrderItem orderItem4 = new OrderItem(18, computer);
		
		//Create some ProductOrders
		ProductOrder order1 = new ProductOrder(LocalDateTime.of(2018, 9, 11, 13, 43));
		order1.addOrderItem(orderItem);
		order1.addOrderItem(orderItem2);
		order1.setCustomer(user2);
		
		ProductOrder order2 = new ProductOrder(LocalDateTime.of(2019, 3, 15, 18, 33));
		order2.addOrderItem(orderItem3);
		order2.addOrderItem(orderItem4);
		order2.setCustomer(user1);
		
		//Save ProductOrders
		this.order1 = testRepo.save(order1);
		this.order1Id = this.order1.getId();
		
		this.order2 = testRepo.save(order2);
		this.order2Id = this.order2.getId();
		this.user1Id = this.order2.getCustomer().getId();
	}
	
	@Test
	public void test_make_sure_order1_was_successfully_created() {
		int expectedNumOfOrderItems = 2;
		Optional<ProductOrder> result = testRepo.findById(order1Id);
		
		assertTrue(result.isPresent());
		
		ProductOrder order = result.get();
		
		assertEquals(order, this.order1);
		assertEquals(expectedNumOfOrderItems, order.getContent().size());		
	}
	
	@Test
	public void test_orphanRemoval_of_orderItem_from_order1() {
		order1.removeOrderItem(orderItemToRemove);		
		testRepo.save(order1);
		
		assertFalse(orderItemRepo.existsById(orderItemToRemove.getId()));
	}
	
	@Test
	public void test_findByCreationDateTimeBefore() {
		LocalDateTime param = LocalDateTime.of(2018, 12, 31, 23, 59);
		int expectedSize = 1;
		
		List<ProductOrder> result = testRepo.findByCreationDateTimeBefore(param);
		
		assertEquals(expectedSize, result.size());
		
		assertTrue(result.stream()
				.map(ProductOrder::getCreationDateTime)
				.allMatch(ldt -> ldt.isBefore(param)));		
	}
	
	@Test
	public void test_findByCustomerId_will_return_ProductOrders_matching_AppUser() {
		int expectedSize = 1;
		
		List<ProductOrder> result = testRepo.findByCustomerId(user1Id);
		
		assertEquals(expectedSize, result.size());
		
		assertTrue(result.stream()
				.allMatch(order -> order.getCustomer().getId().equals(user1Id)));
		
	}
}
