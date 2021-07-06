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
	
//	SELECT *  FROM users u, roles r, users_roles ur Where u.id = ur.user_id and r.id = ur.role_id and u.location="Bethlehem" and r.name='ROLE_USER';
	
	@Query(value="SELECT * FROM users u JOIN users_roles ur ON u.id = ur.user_id JOIN roles r ON r.id = ur.role_id where u.location=?1 and r.name=?2  ",nativeQuery = true)
//	 @Query(value = "SELECT * FROM houses where location=?1",nativeQuery = true)
	List<User> getAllUsersFromLocationWithRole(String location, String name);
	
	
	
//	@Query("SELECT u.location, rol.name "
//			+ "FROM User u "
//			+ "JOIN u.role rol "
//			+ "WHERE rol.name IN ('ROLE_PHARMACY')")
//	List<User>  findUserByLocation(String locate);
	
	
}
