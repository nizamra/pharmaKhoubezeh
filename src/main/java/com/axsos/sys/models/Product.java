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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message="name is required!")
	@Size(min=7, max=66, message="name must be between 7 and 66 characters")
	private String name;
	@NotEmpty(message="description is required!")
	@Size(min=12, max=222, message="description must be between 12 and 222 characters")
	private String description;
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pharmacist_id")
    private User ownerOfProduct;
	@OneToMany(mappedBy="product", fetch = FetchType.LAZY)
	private List<PharmaRequest> pharmaRequests;

	public Product() {}
}
