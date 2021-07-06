package com.axsos.sys.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.axsos.sys.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	List<User> findAll();
	
	
	
	User findByEmail(String email);
	User findByUsername(String username);
	
	@Query("SELECT u FROM  Role r, User u JOIN u.userRole ur Where u.location=?1 and r.name=?2")
	List<User> getAllUsersFromLocationWithRole(String location, String role);
	
	
	
//	@Query("SELECT u.location, rol.name "
//			+ "FROM User u "
//			+ "JOIN u.role rol "
//			+ "WHERE rol.name IN ('ROLE_PHARMACY')")
//	List<User>  findUserByLocation(String locate);
	
	
}
