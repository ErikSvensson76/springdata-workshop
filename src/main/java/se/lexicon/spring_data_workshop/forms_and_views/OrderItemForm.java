package se.lexicon.spring_data_workshop.forms_and_views;

import javax.validation.constraints.Positive;

public class OrderItemForm {
	
	@Positive
	private int quantity;
	
	private int productId;
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
}
