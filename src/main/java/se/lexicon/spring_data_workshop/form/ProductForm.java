package se.lexicon.spring_data_workshop.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class ProductForm {
	
	
	private int id;
	
	@Size(min = 2, max = 50, message = "Must have between 2 and 50 characters")
	private String name;
	
	@Min(value = 1, message = "Price need to be bigger than 1")
	private double price;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
