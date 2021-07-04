package com.axsos.sys.services;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.axsos.sys.models.Category;
import com.axsos.sys.models.PharmaRequest;
import com.axsos.sys.models.Product;
import com.axsos.sys.models.Role;
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
	
	public List<Product> searchProduct(String search) {
		return repoProd.findBySymptomContaining(search);
	}
	
	public List<Product> findCategory(Category category) {
		return repoProd.findByCategory(category);
	}
	
    public void saveWithUserRole(User user) {
        user.setPassword(bCEncoder.encode(user.getPassword()));
        user.setUserRole(repoRole.findByName("ROLE_USER"));
        repoUser.save(user);
    }
     
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
    
    public Product saveProduct(Product product) {
    	return repoProd.save(product);
    }
    
    public User findByUsername(String username) {
        return repoUser.findByUsername(username);
    }
    public User findByEmail(String email) {
    	return repoUser.findByEmail(email);
    }
    public void updateUser(User user) {
    	repoUser.save(user);
    	
    }
    public void createUser(User user) {
    	repoUser.save(user);
    }
    public User getUserById(Long id) {
    	return repoUser.findById(id).orElse(null);
    }
    
    public void deleteById(Long id) {
    	repoUser.deleteById(id);
    }
    
    
    public boolean checkIfAdmin(User user) {
    	List<Role> roles = user.getUserRole();
    	for (int i = 0 ; i < roles.size(); i++) {
    		if(roles.get(i).getName().equals("ROLE_ADMIN")) {
    			return true;
    		}
    	}
    	return false;
    	
    }
    
    public List<Role> findAll(){
    	return (List<Role>) repoRole.findAll();
    }
    
    public Role findByName(String name) {
    	return (Role) repoRole.findByName(name);
    }
    
    public void createRole(Role role) {
    	repoRole.save(role);
    }
    public void updateRole(Role role) {
    	repoRole.save(role);
    }
    public void destroyRole(Long id) {
    	repoRole.deleteById(id);
    	
    }
}
