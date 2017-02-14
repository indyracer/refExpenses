package org.launchcode.refExpenses.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.refExpenses.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface UserDao extends CrudRepository<User, Integer>{
	
	List<User> findAll();
	
	User findByUid(int uid);
	User findByUsername(String username);

}
