package se.lexicon.spring_data_workshop.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.lexicon.spring_data_workshop.service.AppUserService;


/**
 * 
 * Implements the ConstraintValidator<constraint, type>
 * 
 *
 */
@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String>{

	
	private AppUserService userService;
	
	@Autowired	
	public UniqueEmailValidator(AppUserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean emailExistsinDb = userService.emailExists(value);
		
		return value != null && !emailExistsinDb;
	}

}
