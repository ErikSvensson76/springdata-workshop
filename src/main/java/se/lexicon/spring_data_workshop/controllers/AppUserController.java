package se.lexicon.spring_data_workshop.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import se.lexicon.spring_data_workshop.entity.AppUser;
import se.lexicon.spring_data_workshop.entity.ProductOrder;
import se.lexicon.spring_data_workshop.forms_and_views.AppUserForm;
import se.lexicon.spring_data_workshop.forms_and_views.AppUserUpdateForm;
import se.lexicon.spring_data_workshop.forms_and_views.AppUserWithOrders;
import se.lexicon.spring_data_workshop.repository.ProductOrderRepo;
import se.lexicon.spring_data_workshop.service.AppUserService;
import se.lexicon.spring_data_workshop.util.ValidateEmail;

@RestController
public class AppUserController {

	private AppUserService userService;
	private ProductOrderRepo orderRepo;

	@Autowired
	public AppUserController(AppUserService userService, ProductOrderRepo orderRepo) {
		this.userService = userService;
		this.orderRepo = orderRepo;
	}

	@GetMapping("/user")
	public ResponseEntity<List<AppUser>> getAll(){
		List<AppUser> allUsers = userService.findAll();
		
		return allUsers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(allUsers);
	}
	
	@PostMapping("/user")
	public ResponseEntity<AppUser> create(@RequestBody @Valid AppUserForm form){
		
		AppUser newAppUser = userService.save(new AppUser(
								form.getFirstName(),
								form.getLastName(),
								form.getEmail()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newAppUser);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<AppUser> findById(@PathVariable String id){
		return ResponseEntity.ok().body(userService.findById(id));		
	}
	
	@GetMapping("/user/{id}/all")
	public ResponseEntity<AppUserWithOrders> getUserAndHistory(@PathVariable String id){
		AppUserWithOrders userView = new AppUserWithOrders();
		AppUser user = userService.findById(id);
		List<ProductOrder> orders = orderRepo.findByCustomerId(id);
		userView.setId(user.getId());
		userView.setFirstName(user.getFirstName());
		userView.setLastName(user.getLastName());
		userView.setEmail(user.getEmail());
		userView.setOrderHistory(orders);
		
		return ResponseEntity.ok().body(userView);
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<AppUser> update(@PathVariable String id, @Valid @RequestBody AppUserUpdateForm form){
		if(id == null)
			throw new IllegalArgumentException("String id was " + id);
		
		return ResponseEntity.accepted().body(userService.update(id, form));
	}
	
	@PatchMapping("/user/{id}")
	public ResponseEntity<?> updateEmail(@PathVariable String id, @RequestBody String email ){
		
		//validEmailPattern checks correct syntax of the email
		boolean regexValid = ValidateEmail.validEmailPattern(email);
		//Checking if the email already exist in the database
		boolean emailExists = userService.emailExists(email);
		
		//Update only if syntax is correct and the email didn't exist in the database
		if(regexValid && !emailExists) {
			AppUser toUpdate = userService.findById(id);
			toUpdate.setEmail(email);
			return ResponseEntity.accepted().body(toUpdate);
		}else {
			Map<String, List<String>> errors = new HashMap<>();
			List<String> errorItems = new ArrayList<>();
			if(!regexValid) errorItems.add("must be a well-formed email address");
			if(emailExists) errorItems.add("There is already a user with this email");
			errors.put("email", errorItems);
			return ResponseEntity.badRequest().body(errors);
		}		
	}
	
	/*
	 * Exception handler for the validation of both AppUserForm and AppUserUpdateForm
	 * It handles the MethodArumentNotValidException that gets thrown when it finds fields
	 * in the Form object that breach the contract(s) that the validation annotations inside the 
	 * Form classes define.
	 * 
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> productValidationException(MethodArgumentNotValidException e){
		Map<String,String> errors = new HashMap<>();
		e.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return errors;
	}	
}
