package se.lexicon.spring_data_workshop.forms_and_views;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AppUserUpdateForm {
	
	private String id;
	@NotBlank(message = "This field is required")
	@Size(min = 2, message = "Need to have at least 2 characters")
	private String firstName;
	@NotBlank(message = "This field is required")
	@Size(min = 2, message = "Need to have at least 2 characters")
	private String lastName;
	
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
	
	

}
