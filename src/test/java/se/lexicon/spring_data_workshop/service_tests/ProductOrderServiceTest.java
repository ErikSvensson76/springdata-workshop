package se.lexicon.spring_data_workshop.service_tests;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import se.lexicon.spring_data_workshop.entity.AppUser;
import se.lexicon.spring_data_workshop.entity.OrderItem;
import se.lexicon.spring_data_workshop.entity.Product;
import se.lexicon.spring_data_workshop.entity.ProductOrder;
import se.lexicon.spring_data_workshop.exception.EntityNotFoundException;
import se.lexicon.spring_data_workshop.forms_and_views.OrderItemForm;
import se.lexicon.spring_data_workshop.repository.AppUserRepo;
import se.lexicon.spring_data_workshop.repository.ProductOrderRepo;
import se.lexicon.spring_data_workshop.service.OrderService;
import se.lexicon.spring_data_workshop.service.OrderServiceImpl;
import se.lexicon.spring_data_workshop.service.ProductService;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ProductOrderServiceTest {
	
	@TestConfiguration
	static class OrderServiceTestConfig{
		@Bean
		public OrderServiceImpl orderService(ProductOrderRepo orderRepo, ProductService productService, AppUserRepo appUserRepo) {
			return new OrderServiceImpl(orderRepo, productService, appUserRepo);
		}
	}
	
	@Autowired
	private OrderService testService;	
	@MockBean
	private ProductService productService;	
	@MockBean
	private ProductOrderRepo orderRepo;
	@MockBean
	private AppUserRepo appUserRepo;
	
	private static final LocalDateTime CREATION_DATE_TIME = LocalDateTime.of(2019, 04, 12, 20, 06);
	
	private ProductOrder testOrder;
	
	@Before
	public void init() {
		ProductOrder order = new ProductOrder(CREATION_DATE_TIME);
		order.setCustomer(new AppUser("Test", "Testsson", "test@test.com"));
		order.addOrderItem(new OrderItem(100, new Product("TestProduct", 0.90)));
		testOrder = order;		
	}
	
	@Test
	public void test_findOrderById_return_testOrder() {
		when(orderRepo.findById(anyInt())).thenReturn(Optional.of(testOrder));
		
		assertEquals(testOrder, testService.findOrderById(12));
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void test_findOrderById_throws_EntityNotFoundException() {
		when(orderRepo.findById(anyInt())).thenReturn(Optional.empty());
		testService.findOrderById(1234556);
	}
	
	@Test
	public void test_findByDateTimeBefore() {
		LocalDateTime param = LocalDateTime.of(2019, 04, 14, 9, 00);
		when(orderRepo.findByCreationDateTimeBefore(param))
			.thenReturn(Arrays.asList(testOrder));
		
		List<ProductOrder> result = testService.findByDateTimeBefore(param);
		
		assertTrue(result.stream()
				.allMatch(order -> order.getCreationDateTime().isBefore(param)));		
	}
	
	@Test
	public void test_createOrder_with_LocalDateTime_param() {
		LocalDateTime param = LocalDateTime.of(2019, 04, 14, 9, 00);
		ProductOrder expected = new ProductOrder(param);
		
		when(orderRepo.save(any(ProductOrder.class))).thenReturn(expected);
		
		assertEquals(expected, testService.createOrder(param));
	}
	

	@Test
	public void test_createOrder_full() {
		AppUser customer = new AppUser("Nisse", "Nillson", "nisselito@gmail.com");
		Product orderItemProduct = new Product("Spam", 10);
		OrderItem item = new OrderItem(10, orderItemProduct);
		ProductOrder order = new ProductOrder(LocalDateTime.now());
		order.setCustomer(customer);
		order.addOrderItem(item);
		
		OrderItemForm inList = new OrderItemForm();
		inList.setProductId(0);
		inList.setQuantity(10);
		String appUserId = "test";
		List<OrderItemForm> formParam = Arrays.asList(inList);		
		
		when(appUserRepo.findById(anyString())).thenReturn(Optional.of(customer));
		when(productService.findById(anyInt())).thenReturn(orderItemProduct);
		when(orderRepo.save(any(ProductOrder.class))).thenReturn(order);
		
		ProductOrder result = testService.createOrder(formParam, appUserId);
		
		assertEquals(order, result);
		assertEquals(order.getContent(), result.getContent());
		assertEquals(order.getCustomer(), result.getCustomer());
	}
	
	
	@Test
	public void test_createOrderItem() {
		Product content = new Product("content", 10);
		OrderItem expected = new OrderItem(3, content);
		
		when(productService.findById(anyInt())).thenReturn(content);
		
		assertEquals(expected, testService.createOrderItem(1, 3));		
	}
	
	

}
