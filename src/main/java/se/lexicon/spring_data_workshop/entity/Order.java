package se.lexicon.spring_data_workshop.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
			mappedBy = "order", fetch = FetchType.EAGER)
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
	
	public boolean addOrderItem(OrderItem item) {
		if(item.getOrder() != null) {
			throw new IllegalArgumentException();
		}
		
		item.setOrder(this);		
		return content.add(item);
				
	}
	
	public boolean removeOrderItem(OrderItem item) {
		if(!content.contains(item)) {
			throw new IllegalArgumentException();
		}
		
		item.setOrder(null);
		return content.remove(item);
	}
	
	public double getPriceTotal() {
		return content.stream()
				.mapToDouble(OrderItem::getPrice)
				.sum();
	}
	

}
