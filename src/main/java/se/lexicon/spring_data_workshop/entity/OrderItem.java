package se.lexicon.spring_data_workshop.entity;

public class OrderItem implements Comparable<OrderItem>{
	
	private int id;
	private int quantity;
	private Product product;
	private Order order;
	
	public OrderItem(int quantity, Product product) {
		this.quantity = quantity;
		this.product = product;		
	}
	
	public OrderItem() {}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + quantity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		if (id != other.id)
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", quantity=" + quantity + ", product=" + product + ", order=" + order + "]";
	}
	
	public double getPrice() {
		if(product == null) {
			return 0;
		}else {
			return product.getPrice() * getQuantity();
		}		
	}

	@Override
	public int compareTo(OrderItem o) {
		return this.getQuantity() - o.getQuantity();
	}
}
