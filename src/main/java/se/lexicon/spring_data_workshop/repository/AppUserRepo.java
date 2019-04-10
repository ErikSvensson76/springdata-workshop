package se.lexicon.spring_data_workshop.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import se.lexicon.spring_data_workshop.entity.AppUser;

public interface AppUserRepo extends CrudRepository<AppUser, String>{
	Optional<AppUser> findByEmail(String email);	
}
