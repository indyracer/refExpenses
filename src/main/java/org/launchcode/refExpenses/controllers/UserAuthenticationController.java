package org.launchcode.refExpenses.controllers;

import javax.servlet.http.HttpServletRequest;

import org.launchcode.refExpenses.models.User;
import org.launchcode.refExpenses.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserAuthenticationController extends AbstractController{

	@Autowired
	private UserDao userDao;

	@RequestMapping(value="/signup", method = RequestMethod.GET)
	public String signupForm(){
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model){
		//implement signup

		//get parameters from the signup form

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String verify = request.getParameter("verify");
		String mileageRate = request.getParameter("mileageRate");

		//parse Mileage Allowance to a double
		double mileage = Double.parseDouble(mileageRate);

		//validate parameters supplied

		if(firstName == "" || firstName == null || username == "" || username == null || verify == "" || verify == null || mileageRate == "" || mileageRate == null){
			model.addAttribute("missing_field_error", "All fields must be filled in, please try again.");
			return "signup";
		}

		//validate username
		if(!User.isValidUsername(username)){
			model.addAttribute("username_error", "Username is not valid, must between 4 and 15 characters long.  Please try again.");
			return "signup";
		}

		//validate passwords match
		if(!password.equals(verify)){
			model.addAttribute("verify_error", "Passwords do not match, please try again.");
			return "signup";
		}

		//validate password 
		if(!User.isValidPassword(password)){
			model.addAttribute("password_error", "Password is not valid.  Must be between 6 and 20 characters, please try again");
			return "signup";
		}

		//create new User and save to db
		User newUser = new User(firstName, lastName, username, password, mileage);

		userDao.save(newUser);

		//logs in new user and sends to specified page
		setUserInSession(request.getSession(), newUser);

		return "redirect:/expensehome";

	}

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String loginForm(){
		return "login";
	}

	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model){

		//implement login
		//get parameters from login form
		String username = request.getParameter("username");
		String password = request.getParameter("password");


		//check username & password input in form
		if(username == "" || username == null || password == "" || password == null){
			model.addAttribute("missing_field_error", "Must supply username and password, please try again");
			return "login";
		}

		//validate parameters in order to login
		User user = userDao.findByUsername(username);

		//is user in db?
		if(user == null){
			model.addAttribute("username_error", "Username not found, please try again");
			return "login";
		}

		//check password matches
		if(!user.isMatchingPassword(password)){
			model.addAttribute("password_error", "Incorrect password, please try again");
			return "login";
		}

		//login by setting user in session
		setUserInSession(request.getSession(), user);
		return "redirect:/expensehome";



	}
}







