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

	@NotEmpty(message = "Email is required!")
	@Email(message = "Please enter a valid email!")
	private String email;

	@NotEmpty(message = "location is required!")
	@Size(min = 3, max = 30, message = "location must be between 3 and 30 characters")
	private String location;

	private String googleLocation;

	@NotEmpty(message = "Password is required!")
	@Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
	private String password;

	@Transient
	@NotEmpty
	@Size(min = 8, max = 128)
	private String confirm;

	@OneToMany(mappedBy = "requester", fetch = FetchType.LAZY)
	private List<PharmaRequest> pharmaRequests;

	@OneToMany(mappedBy = "ownerOfProduct", fetch = FetchType.LAZY)
	private List<Product> productsOwned;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> userRole;

	@Column(updatable = false)
	private Date createdAt;
	private Date updatedAt;

	public User() {
	}
}
