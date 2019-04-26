package se.lexicon.spring_data_workshop.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UserValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface ValidAppUser {
	 String message() default "Invalid AppUser, could not be found in the database";
	 
	 public Class<?>[] groups() default {};
	 
	 public Class<? extends Payload>[] payload() default{};
}



