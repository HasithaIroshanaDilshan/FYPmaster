package com.server.master.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.server.master.User;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {

	
	
	@Query("SELECT u FROM User u WHERE u.id = ?1")
	User findAllActiveUsers(Integer id);
	
	@Query("SELECT u FROM User u WHERE u.username = ?1 and u.password = ?2")
	User findUser(String username, String pass);
	
}