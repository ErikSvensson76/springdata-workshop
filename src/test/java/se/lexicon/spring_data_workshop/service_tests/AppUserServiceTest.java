package se.lexicon.spring_data_workshop.service_tests;

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
import se.lexicon.spring_data_workshop.forms_and_views.AppUserUpdateForm;
import se.lexicon.spring_data_workshop.repository.AppUserRepo;
import se.lexicon.spring_data_workshop.repository.ProductOrderRepo;
import se.lexicon.spring_data_workshop.service.AppUserService;
import se.lexicon.spring_data_workshop.service.AppUserServiceImpl;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@RunWith(SpringRunner.class)
public class AppUserServiceTest {
	
	@TestConfiguration
	static class AppUserServiceTestConfiguration{
		@Bean
		public AppUserService appUserService(AppUserRepo userRepo, ProductOrderRepo orderRepo) {
			return new AppUserServiceImpl(userRepo, orderRepo);
		}
	}
	
	@Autowired
	private AppUserService testService;
	
	@MockBean
	private AppUserRepo userRepo;
	@MockBean
	private ProductOrderRepo orderRepo;

	private AppUser user = new AppUser("Test", "Testsson", "test@test.com");
	
	@Test
	public void test_findById_return_entity() {
		 
		
		when(userRepo.findById(anyString())).thenReturn(Optional.of(user));
		
		AppUser found = testService.findById("testId");
		
		assertEquals(user, found);		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_findById_with_null_throws_IllegalArgumentException() {
		String id = null;
		testService.findById(id);		
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void test_findById_throws_EntityNotFoundException() {		
		when(userRepo.findById(anyString())).thenReturn(Optional.empty());
		
		testService.findById("testId");		
	}
	
	@Test
	public void test_findByEmail_return_entity() {
		
		when(userRepo.findByEmail("test@test.com")).thenReturn(Optional.ofNullable(user));
		
		AppUser result = testService.findByEmail("test@test.com");
		
		assertEquals(user, result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_findByEmail_throws_IllegalArgumentException() {
		testService.findByEmail(null);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void test_findByEmail_throws_EntityNotFoundException() {
		when(userRepo.findByEmail(anyString())).thenReturn(Optional.empty());
		
		testService.findByEmail("testId");
	}
	
	@Test
	public void test_update() {
		AppUserUpdateForm formParam = new AppUserUpdateForm();
		formParam.setFirstName("Erik");
		formParam.setLastName("Testsson");
		
		String appUserId = "testId";
		
		AppUser expectedResult = new AppUser("Erik", "Testsson", "test@test.com");
		
		when(userRepo.findById(appUserId)).thenReturn(Optional.of(user));
		when(userRepo.save(user)).thenReturn(new AppUser("Erik", "Testsson", "test@test.com"));
		
		AppUser result = testService.update(appUserId, formParam);
		
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void test_getOrdersByAppUserId() {
		ProductOrder order = new ProductOrder();
		order.setCustomer(user);
		order.addOrderItem(new OrderItem(5, new Product("Spam", 10.90)));
		
		when(orderRepo.findByCustomerId(anyString())).thenReturn(Arrays.asList(order));
		
		List<ProductOrder> result = testService.getOrdersByAppUserId("someId");
		
		assertTrue(result.stream().allMatch(x -> x.getCustomer().equals(user)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_getOrdersByAppUserId_null_throws_IllegalArgumentException() {
		String param = null;
		testService.getOrdersByAppUserId(param);
	}
	
	@Test
	public void test_emailExists() {
		when(userRepo.findEmail("testEmail")).thenReturn(Optional.of("testEmail"));
		
		assertTrue(testService.emailExists("testEmail"));
	}
	
	
	

}
