package se.lexicon.spring_data_workshop.repository_tests;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import se.lexicon.spring_data_workshop.entity.Product;
import se.lexicon.spring_data_workshop.repository.ProductRepo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class ProductRepoTest {
	
	@Autowired private ProductRepo testRepo;
	
	private Product testProduct;
	
	
	@Before
	public void init() {
		Product product = new Product("Computer", 9999.99);
		Product product2 = new Product("Samsung Galaxy 10", 12990);
		testProduct = testRepo.save(product);
		testRepo.save(product2);
	}
	
	@Test
	public void test_findByNameIgnoreCase() {
		List<Product> expected = Arrays.asList(testProduct);
		List<Product> actual = testRepo.findByNameIgnoreCase("computer");
		assertEquals(expected, actual);		
	}
	
	@Test
	public void test_findByNameIgnoreCase_all_returned_match_param() {
		String param = "SamSung GalAxy 10";
		List<Product> result = testRepo.findByNameIgnoreCase(param);		
		
		assertTrue(result.stream()
				.allMatch(product -> product.getName().equalsIgnoreCase(param)));
		
	}
	
	

}
