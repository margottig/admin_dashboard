package com.example.autorizacion.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.autorizacion.models.User;

public interface UserRepository  extends CrudRepository<User, Long>{
	User findByUsername(String username);

}
