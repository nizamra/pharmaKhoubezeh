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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "firstName is required!")
	@Size(min = 3, max = 30, message = "firstName must be between 3 and 30 characters")
	private String firstName;

	@NotEmpty(message = "moddleName is required!")
	@Size(min = 3, max = 30, message = "moddleName must be between 3 and 30 characters")
	private String moddleName;

	@NotEmpty(message = "lastName is required!")
	@Size(min = 3, max = 30, message = "lastName must be between 3 and 30 characters")
	private String lastName;
	
	@NotEmpty
	@Size(min = 3, max = 30)
	private String username;

	@NotEmpty(message = "Email is required!")
	@Email(message = "Please enter a valid email!")
	private String email;

	@NotEmpty(message = "location is required!")
	@Size(min = 3, max = 30, message = "location must be between 3 and 30 characters")
	private String location;

	private String googleLocation;

	@NotEmpty
	@Size(min = 8, max = 128)
	private String password;

	@Transient
	private String passwordConfirmation;

	@OneToMany(mappedBy = "requester", fetch = FetchType.LAZY)
	private List<PharmaRequest> pharmaRequests;

	@OneToMany(mappedBy = "ownerOfProduct", fetch = FetchType.LAZY)
	private List<Product> productsOwned;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> userRole;

	@Column(updatable = false)
	private Date createdAt;
	private Date updatedAt;

	public User() {}
	
	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getModdleName() {
		return moddleName;
	}

	public void setModdleName(String moddleName) {
		this.moddleName = moddleName;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getGoogleLocation() {
		return googleLocation;
	}

	public void setGoogleLocation(String googleLocation) {
		this.googleLocation = googleLocation;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String confirm) {
		this.passwordConfirmation = confirm;
	}

	public List<PharmaRequest> getPharmaRequests() {
		return pharmaRequests;
	}

	public void setPharmaRequests(List<PharmaRequest> pharmaRequests) {
		this.pharmaRequests = pharmaRequests;
	}

	public List<Product> getProductsOwned() {
		return productsOwned;
	}

	public void setProductsOwned(List<Product> productsOwned) {
		this.productsOwned = productsOwned;
	}

	public List<Role> getUserRole() {
		return userRole;
	}

	public void setUserRole(List<Role> userRole) {
		this.userRole = userRole;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
    
    
}
