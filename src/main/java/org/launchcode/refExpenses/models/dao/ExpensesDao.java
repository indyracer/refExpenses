package org.launchcode.refExpenses.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.refExpenses.models.Expenses;
import org.launchcode.refExpenses.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface ExpensesDao extends CrudRepository<Expenses, Integer>{
	
	List<Expenses> findAll();
	Expenses findByUid(int uid);
	List<Expenses> findByUser(User user);

}
