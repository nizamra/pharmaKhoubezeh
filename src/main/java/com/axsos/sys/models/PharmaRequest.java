package com.axsos.sys.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pharmaRequests")
public class PharmaRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User requester;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "carts", joinColumns = @JoinColumn(name = "cart_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List<Product> products;
	
	
	@Column(updatable = false)
	private Date createdAt;

	public PharmaRequest() {}

	public User getRequester() {
		return requester;
	}

	public void setRequester(User requester) {
		this.requester = requester;
	}

	public List<Product> getProduct() {
		return products;
	}

	public void setProduct(List<Product> product) {
		this.products = product;
	}

	public Long getId() {
		return id;
	}
	
	
}
