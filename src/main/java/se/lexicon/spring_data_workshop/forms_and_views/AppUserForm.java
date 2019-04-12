package se.lexicon.spring_data_workshop.forms_and_views;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import se.lexicon.spring_data_workshop.validation.UniqueEmail;

public class AppUserForm {	
	
	private String id;
	
	@NotBlank(message = "This field is required")
	@Size(min = 2, message = "Need to have at least 2 characters")
	private String firstName;
	
	@NotBlank(message = "This field is required")
	@Size(min = 2, message = "Need to have at least 2 characters")
	private String lastName;
	
	@NotBlank(message = "This field is required")
	@UniqueEmail //Custom annotation i created that checks if email is already in the database 
	@Email(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", flags = Pattern.Flag.CASE_INSENSITIVE)
	private String email;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
