package com.axsos.sys.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.axsos.sys.models.PharmaRequest;
import com.axsos.sys.models.Product;
import com.axsos.sys.models.User;
import com.axsos.sys.repositories.PharmaRequestRepository;
import com.axsos.sys.repositories.ProductRepository;
import com.axsos.sys.repositories.RoleRepository;
import com.axsos.sys.repositories.UserRepository;

@Service
public class PharmaService {
	private final UserRepository repoUser;
	private final RoleRepository repoRole;
	private final ProductRepository repoProd;
	private final PharmaRequestRepository repoPhReq;
	private final BCryptPasswordEncoder bCEncoder;

	public PharmaService(UserRepository x, ProductRepository y, PharmaRequestRepository z, BCryptPasswordEncoder w, RoleRepository uv) {
		repoUser = x;
		repoProd = y;
		repoPhReq = z;
		bCEncoder = w;
		repoRole = uv;
	}
	public List<User> findAllUsers() {
		return repoUser.findAll();}
	public List<Product> findAllProducts() {
		return repoProd.findAll();}
	public List<PharmaRequest> findAllRequests() {
		return repoPhReq.findAll();}
	
	// 1
    public void saveWithUserRole(User user) {
        user.setPassword(bCEncoder.encode(user.getPassword()));
        user.setUserRole(repoRole.findByName("ROLE_USER"));
        repoUser.save(user);
    }
     
     // 2 
    public void saveUserWithAdminRole(User user) {
        user.setPassword(bCEncoder.encode(user.getPassword()));
        user.setUserRole(repoRole.findByName("ROLE_ADMIN"));
        repoUser.save(user);
    }    
    public void saveUserWithPharmacistRole(User user) {
    	user.setPassword(bCEncoder.encode(user.getPassword()));
    	user.setUserRole(repoRole.findByName("ROLE_PHARMACIST"));
    	repoUser.save(user);
    }    
    
    // 3
    public User findByUsername(String username) {
        return repoUser.findByUsername(username);
    }
}
