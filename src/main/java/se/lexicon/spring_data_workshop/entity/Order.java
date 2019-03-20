package se.lexicon.spring_data_workshop.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

public class Order {
	
	private int id;
	private Set<OrderItem> content = new TreeSet<>();
	private final LocalDateTime creationDateTime;
	
	public Order(LocalDateTime creationDateTime) {		
		this.creationDateTime = creationDateTime;
	}
	
	public Order() {
		creationDateTime = LocalDateTime.now();
	}

	public Set<OrderItem> getContent() {
		return content;
	}

	public void setContent(Set<OrderItem> content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationDateTime == null) ? 0 : creationDateTime.hashCode());
		result = prime * result + id;
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
		Order other = (Order) obj;
		if (creationDateTime == null) {
			if (other.creationDateTime != null)
				return false;
		} else if (!creationDateTime.equals(other.creationDateTime))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", creationDateTime=" + creationDateTime + "]";
	}
	
	
	
	

}