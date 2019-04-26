package se.lexicon.spring_data_workshop.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.lexicon.spring_data_workshop.repository.AppUserRepo;

@Component
public class UserValidator implements ConstraintValidator<ValidAppUser, String>{
	
	private AppUserRepo appUserRepo;
	
	@Autowired
	public UserValidator(AppUserRepo appUserRepo) {
		this.appUserRepo = appUserRepo;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return appUserRepo.existsById(value);
	}
}



