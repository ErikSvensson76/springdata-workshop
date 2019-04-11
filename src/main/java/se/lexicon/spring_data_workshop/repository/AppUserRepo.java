package se.lexicon.spring_data_workshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import se.lexicon.spring_data_workshop.entity.AppUser;

public interface AppUserRepo extends CrudRepository<AppUser, String>{
	Optional<AppUser> findByEmail(String email);
	
	@Query("SELECT user.email FROM AppUser user WHERE user.email = :email")	
	Optional<String> findEmail(@Param("email") String email);
	
}
