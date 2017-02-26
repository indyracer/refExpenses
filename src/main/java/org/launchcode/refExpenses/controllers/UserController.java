package org.launchcode.refExpenses.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController extends AbstractController{

	
	@RequestMapping(value="/expensehome", method = RequestMethod.GET)
	public String expenseHome(){
		return "expensehome";
	}
	
}
