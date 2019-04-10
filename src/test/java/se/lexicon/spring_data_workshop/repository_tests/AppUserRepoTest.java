package se.lexicon.spring_data_workshop.repository_tests;

import java.util.Arrays;
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
import se.lexicon.spring_data_workshop.repository.AppUserRepo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class AppUserRepoTest {
	
	@Autowired private AppUserRepo testRepo;
	
	@Before
	public void init() {
		List<AppUser> users = Arrays.asList(
				new AppUser("test", "testsson", "test@test.com"),
				new AppUser("test2", "testsson", "test2@test.com"),
				new AppUser("test", "testsson2", "test3@test.com"));
		
		testRepo.saveAll(users);
	}
	
	@Test
	public void test_find_by_email_return_optional_with_present_value() {
		String emailParam = "test@test.com";
		
		Optional<AppUser> result = testRepo.findByEmail(emailParam);
		
		assertTrue(result.isPresent());
		
		assertEquals(emailParam, result.get().getEmail());
	}

}
