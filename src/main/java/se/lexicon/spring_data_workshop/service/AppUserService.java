package se.lexicon.spring_data_workshop.service;

import java.util.List;

import se.lexicon.spring_data_workshop.entity.AppUser;
import se.lexicon.spring_data_workshop.entity.ProductOrder;
import se.lexicon.spring_data_workshop.exception.EntityNotFoundException;
import se.lexicon.spring_data_workshop.forms_and_views.AppUserUpdateForm;

public interface AppUserService {

	/**
	 * 
	 * @param appUserId
	 * @return List of productOrders for AppUser where id equals appUserId
	 * @throws IllegalArgumentException when appUserId is null
	 */
	List<ProductOrder> getOrdersByAppUserId(String appUserId) throws IllegalArgumentException;

	/**
	 * 
	 * @param appUserId String id of original AppUser
	 * @param updated AppUser with updated data
	 * @return Updated AppUser
	 * @throws IllegalArgumentException when appUserId is null
	 * @throws EntityNotFoundException when AppUser can't be found with appUserId
	 */
	AppUser update(String appUserId, AppUserUpdateForm updated) throws IllegalArgumentException, EntityNotFoundException;

	/**
	 * 
	 * @param email String representing email address of AppUser
	 * @return AppUser with same unique email address
	 * @throws IllegalArgumentException when email address parameter is null
	 * @throws EntityNotFoundException when AppUser can't be found
	 */
	AppUser findByEmail(String email) throws IllegalArgumentException, EntityNotFoundException;

	List<AppUser> findAll();

	/**
	 * 
	 * @param id String
	 * @return AppUser with matching id
	 * @throws IllegalArgumentException when id is null
	 * @throws EntityNotFoundException when AppUser can't be found
	 */
	AppUser findById(String id) throws IllegalArgumentException, EntityNotFoundException;
	
	boolean emailExists(String email);

	AppUser save(AppUser appUser);



}
