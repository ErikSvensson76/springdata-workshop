package se.lexicon.spring_data_workshop.forms_and_views;

import java.util.List;

import se.lexicon.spring_data_workshop.entity.ProductOrder;

public class AppUserWithOrders {
	
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private List<ProductOrder> orderHistory;
	
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
	public List<ProductOrder> getOrderHistory() {
		return orderHistory;
	}
	public void setOrderHistory(List<ProductOrder> orderHistory) {
		this.orderHistory = orderHistory;
	}
	
	

}
