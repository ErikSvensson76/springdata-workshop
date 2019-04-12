package se.lexicon.spring_data_workshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.lexicon.spring_data_workshop.entity.AppUser;
import se.lexicon.spring_data_workshop.entity.ProductOrder;
import se.lexicon.spring_data_workshop.exception.EntityNotFoundException;
import se.lexicon.spring_data_workshop.forms_and_views.AppUserUpdateForm;
import se.lexicon.spring_data_workshop.repository.AppUserRepo;
import se.lexicon.spring_data_workshop.repository.ProductOrderRepo;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService{
	
	private AppUserRepo appUserRepo;
	private ProductOrderRepo productOrderRepo;
	
	@Autowired	
	public AppUserServiceImpl(AppUserRepo appUserRepo, ProductOrderRepo productOrderRepo) {
		this.appUserRepo = appUserRepo;
		this.productOrderRepo = productOrderRepo;
	}

	@Override
	public AppUser findById(String id) throws IllegalArgumentException, EntityNotFoundException{
		if(id == null) {
			throw new IllegalArgumentException("String id was null");
		}
		
		Optional<AppUser> result = appUserRepo.findById(id);
		
		return result.orElseThrow(() -> new EntityNotFoundException("Could not find AppUser with id " + id));	
	}
	
	@Override
	public List<AppUser> findAll(){
		return (List<AppUser>) appUserRepo.findAll();
	}
	
	@Override
	public AppUser findByEmail(String email) throws IllegalArgumentException, EntityNotFoundException{
		if(email == null) {
			throw new IllegalArgumentException("String email was null");
		}
		Optional<AppUser> result = appUserRepo.findByEmail(email);
		return result.orElseThrow(() -> new EntityNotFoundException("Could not find AppUser with email " + email));
	}
	
	@Override
	public AppUser update(String appUserId, AppUserUpdateForm updated) throws IllegalArgumentException, EntityNotFoundException{
		AppUser original = findById(appUserId);		
		original.setFirstName(updated.getFirstName());
		original.setLastName(updated.getLastName());
		return appUserRepo.save(original);
	}
	
	@Override
	public List<ProductOrder> getOrdersByAppUserId(String appUserId) throws IllegalArgumentException{
		if(appUserId == null) {
			throw new IllegalArgumentException("String appUserId was " + appUserId);
		}
		
		return productOrderRepo.findByCustomerId(appUserId);		
	}

	@Override
	public boolean emailExists(String email) {
		return appUserRepo.findEmail(email).isPresent();
	}	
	
	@Override
	public AppUser save(AppUser appUser) {
		
		
		return appUserRepo.save(appUser);
	}
	
	
}
