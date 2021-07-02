package com.axsos.sys.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.axsos.sys.models.PharmaRequest;
import com.axsos.sys.models.Product;
import com.axsos.sys.models.User;
import com.axsos.sys.repositories.PharmaRequestRepository;
import com.axsos.sys.repositories.ProductRepository;
import com.axsos.sys.repositories.UserRepository;

@Service
public class PharmaService {
	private final UserRepository repoUser;
	private final ProductRepository repoProd;
	private final PharmaRequestRepository repoPhReq;

	public PharmaService(UserRepository x, ProductRepository y, PharmaRequestRepository z) {
		repoUser = x;
		repoProd = y;
		repoPhReq = z;
	}
	public List<User> findAllUsers() {
		return repoUser.findAll();}
	public List<Product> findAllProducts() {
		return repoProd.findAll();}
	public List<PharmaRequest> findAllRequests() {
		return repoPhReq.findAll();}
}
