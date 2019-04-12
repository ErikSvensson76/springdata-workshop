package se.lexicon.spring_data_workshop.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


/**
 * 
 * Definition of the customer annotation @UniqueEmail
 *
 */
@Constraint(validatedBy = UniqueEmailValidator.class) //Class that does the actual validation implementation
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface UniqueEmail {
	
	 String message() default "There is already a user with this email";
	 
	 public Class<?>[] groups() default {};
	 
	 public Class<? extends Payload>[] payload() default{};
	
}
