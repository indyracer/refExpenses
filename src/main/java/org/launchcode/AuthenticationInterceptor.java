package org.launchcode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.launchcode.refExpenses.controllers.AbstractController;
import org.launchcode.refExpenses.models.User;
import org.launchcode.refExpenses.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//develop his after classes and pages are completed.
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	UserDao userDao; //fix itself once userDao class created
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
	
		//list of restricted URLS goes here
		List<String> authPages = Arrays.asList("TBD");
		
		//Require sign-in for authPages
		if(authPages.contains(request.getRequestURI())){
			boolean isLoggedIn = false;
			User user;
			Integer userId = (Integer) request.getSession().getAttribute(AbstractController.userSessionKey);//will fix once Abstract controller is created
			
			if(userId != null){
				user = userDao.findByUid(userId);
				
				
				if(user != null){
					isLoggedIn = true;
				}
			}
			
			//if user not logged in, redirect to login page
			if (!isLoggedIn){
				response.sendRedirect("/login");//need to create this page
				return false;
			}
		}
		
		return true;
	
	
	}
	

}
