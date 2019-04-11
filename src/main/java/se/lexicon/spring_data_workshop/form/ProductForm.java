package se.lexicon.spring_data_workshop.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ProductForm {
	
	@NotBlank(message = "This field is required")
	@Size(min = 2, message = "Name need to have at least 2 characters")
	private String name;
	@Positive(message = "Price must have a positive value")
	private double price;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	

}
