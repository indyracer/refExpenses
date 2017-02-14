package org.launchcode.refExpenses.controllers;

import javax.servlet.http.HttpSession;

import org.launchcode.refExpenses.models.User;
import org.launchcode.refExpenses.models.dao.ExpensesDao;
import org.launchcode.refExpenses.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractController {
	
	@Autowired
	protected UserDao userDao;
	
	@Autowired
	protected ExpensesDao expensesDao;//will fix once ExpenseDao created
	
	public static final String userSessionKey = "user_id";
	
	protected User getUserFromSession(HttpSession session){
		Integer userId = (Integer) session.getAttribute(userSessionKey);
		return userId == null ? null : userDao.findByUid(userId); 
	}
	
	protected void setUserInSession(HttpSession session, User user){
		session.setAttribute(userSessionKey, user.getUid());
	}

}
