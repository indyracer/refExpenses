package org.launchcode.refExpenses.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RefExpensesController {
	
	//Homepage of site, links to other sections
	@RequestMapping(value="/")
	public String index(Model model){
		return "index";
	}
	
	//logout function, redirect to homepage
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
		request.getSession().invalidate();
		return "redirect:/";
	}

}
