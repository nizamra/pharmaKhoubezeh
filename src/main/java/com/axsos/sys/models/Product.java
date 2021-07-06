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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name="products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Size(min=2, max=66, message="name must be between 7 and 66 characters")
	private String name;
	@Column(nullable = true, length = 64)
	private String photos;
	@Transient
	public String getPhotosImagePath() {
		if (photos == null || id == null) return null;   
	        return "/product-photos/" + id + "/" + photos;
	    }
	@Size(min=12, max=222, message="description must be between 12 and 222 characters")
	private String description;
	@Size(min=3,message="Symptoms must be at least 3 characters!")
	private String symptom;
	private String category;
	private String price;
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pharmacist_id")
    private User ownerOfProduct;
    
    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "carts", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "cart_id"))
    private List<PharmaRequest> cart_requests;

	public Product() {}
	
	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getOwnerOfProduct() {
		return ownerOfProduct;
	}

	public void setOwnerOfProduct(User ownerOfProduct) {
		this.ownerOfProduct = ownerOfProduct;
	}

	

	public List<PharmaRequest> getCart_requests() {
		return cart_requests;
	}

	public void setCart_requests(List<PharmaRequest> cart_requests) {
		this.cart_requests = cart_requests;
	}

	public Long getId() {
		return id;
	}


	public String getSymptom() {
		return symptom;
	}

	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
    
    
}
