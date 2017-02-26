package org.launchcode.refExpenses.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.launchcode.refExpenses.models.Expenses;
import org.launchcode.refExpenses.models.User;
import org.launchcode.refExpenses.models.dao.ExpensesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReportController extends AbstractController {
	
	/*@Autowired
	private UserDao userDao;
	*/
	@Autowired
	private ExpensesDao expensesDao;
	
	//reporting page
	@RequestMapping(value = "/reporting", method = RequestMethod.GET)
	public String reporting(HttpServletRequest request, Model model){
		//pull in the user that is logged in
		
		//gets the user that is logged in
		User userInSession = userInSession(request);
				
			
		//pulls up all the expenses for the logged in user
		List<Expenses> expenses = expensesDao.findByUser(userInSession);
		
		//calculates the total expenses
		double totalExpenses = 0;
		Expenses temp;
		
		for(int i = 0; i < expenses.size(); i++){
			temp = expenses.get(i);
			totalExpenses = totalExpenses + temp.getAmount();
		}
		
		//format so expense is to 2 deceimal places
		totalExpenses = Math.round(totalExpenses * 100) / 100.00;
		
		model.addAttribute("totalExpenses", totalExpenses);
		
		return "reporting";
	}
	
		
	@RequestMapping(value = "/byexpensetype", method = RequestMethod.GET)
	public String byExpenseType(HttpServletRequest request, Model model){
		//pull in logged in user
		User userInSession = userInSession(request);
		
		//pull in logged users expenses
		List <Expenses> expenses = expensesDao.findByUser(userInSession);
		
		//pull each expense and total up by expenseType:  equipment, meals, fees, travel, mileage
		double equipmentTotal = 0;
		double mealsTotal = 0;
		double feesTotal = 0;
		double travelTotal = 0;
		double mileageTotal = 0;
		
		int expensesSize = expenses.size();
		
		Expenses temp;
		//loop through expenses
		for(int i = 0; i < expensesSize; i++){
			temp = expenses.get(i);
			
			if(temp.getExpenseType() == "equipment"){
				equipmentTotal = equipmentTotal + temp.getAmount();
			}
			
			if(temp.getExpenseType() == "meals"){
				mealsTotal = mealsTotal + temp.getAmount();
			}
			
			if(temp.getExpenseType() == "fees"){
				feesTotal = feesTotal + temp.getAmount();
			}
			
			if(temp.getExpenseType() == "travel"){
				travelTotal = travelTotal + temp.getAmount();
			}
			
			if(temp.getExpenseType() == "mileage"){
				mileageTotal = mileageTotal + temp.getAmount();
			}
		}
		
		//format to 2 decimals
		equipmentTotal = Math.round(equipmentTotal * 100) / 100.00;
		mealsTotal = Math.round(mealsTotal * 100) / 100.00;
		feesTotal = Math.round(feesTotal * 100) / 100.00;
		travelTotal = Math.round(travelTotal * 100) / 100.00;
		mileageTotal = Math.round(mileageTotal * 100) / 100.00;
		
		model.addAttribute("equipmentTotal", equipmentTotal);
		model.addAttribute("mealsTotal", mealsTotal);
		model.addAttribute("feesTotal", feesTotal);
		model.addAttribute("travelTotal", travelTotal);
		model.addAttribute("mileageTotal", mileageTotal);
		
		return "byexpensetype";
			
			
		}
		
	
	
	
	//method to pull in logged in user
	public User userInSession(HttpServletRequest request){
		User userInSession = getUserFromSession(request.getSession());
		return userInSession;
	}
	

}
