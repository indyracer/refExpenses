package org.launchcode.refExpenses.controllers;

import org.launchcode.refExpenses.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController extends AbstractController{

	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value="/expensehome", method = RequestMethod.GET)
	public String expenseHome(){
		return "expensehome";
	}
	
	
	//add pages that pull up summaries by user
	
}
