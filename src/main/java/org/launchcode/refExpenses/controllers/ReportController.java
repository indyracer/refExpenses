package org.launchcode.refExpenses.controllers;

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
		User userInSession = getUserFromSession(request.getSession());
		int userUid = userInSession.getUid();
				
			
		//pulls up all the expenses for the logged in user
		List<Expenses> expenses = expensesDao.findByUserUid(userUid);
		
		//calculates the total expenses
		double totalExpenses = 0.00;
		Expenses temp;
		
		for(int i = 0; i < expenses.size(); i++){
			temp = expenses.get(i);
			totalExpenses = totalExpenses + temp.getAmount();
		}
		
		//format so expense is to 2 decimal places
		totalExpenses = Math.round(totalExpenses * 100) / 100.00;
		
		model.addAttribute("totalExpenses", totalExpenses);
		
		return "reporting";
	}
	
		
	@RequestMapping(value = "/byexpensetype", method = RequestMethod.GET)
	public String byExpenseType(HttpServletRequest request, Model model){
		//pull in logged in user
		User userInSession = getUserFromSession(request.getSession());
		int userUid = userInSession.getUid();
		
		//pull in logged users expenses
		List <Expenses> expenses = expensesDao.findByUserUid(userUid);
		
		//pull each expense and total up by expenseType:  equipment, meals, fees, travel, mileage
		double equipmentTotal = 0.00;
		double mealsTotal = 0.00;
		double feesTotal = 0.00;
		double travelTotal = 0.00;
		double mileageTotal = 0.00;
		
		int expensesSize = expenses.size();
		
		Expenses temp;
		//loop through expenses
		for(int i = 0; i < expensesSize; i++){
			temp = expenses.get(i);
			
			if(temp.getExpenseType().equals("equipment")){
				equipmentTotal = equipmentTotal + temp.getAmount();
			}
			
			if(temp.getExpenseType().equals("meals")){
				mealsTotal = mealsTotal + temp.getAmount();
			}
			
			if(temp.getExpenseType().equals("fees")){
				feesTotal = feesTotal + temp.getAmount();
			}
			
			if(temp.getExpenseType().equals("travel")){
				travelTotal = travelTotal + temp.getAmount();
			}
			
			if(temp.getExpenseType().equals("mileage")){
				mileageTotal = mileageTotal + temp.getAmount();
			}
		}
		
		//format to 2 decimals...why not working??????
		equipmentTotal = Math.round(equipmentTotal * 100.0) / 100.0;
		mealsTotal = Math.round(mealsTotal * 100.0) / 100.0;
		feesTotal = Math.round(feesTotal * 100.0) / 100.0;
		travelTotal = Math.round(travelTotal * 100.0) / 100.0;
		mileageTotal = Math.round(mileageTotal * 100.0) / 100.0;
		
		model.addAttribute("equipmentTotal", equipmentTotal);
		model.addAttribute("mealsTotal", mealsTotal);
		model.addAttribute("feesTotal", feesTotal);
		model.addAttribute("travelTotal", travelTotal);
		model.addAttribute("mileageTotal", mileageTotal);
		
		return "byexpensetype";
			
			
		}
	
	@RequestMapping(value="/bymonth", method=RequestMethod.GET)
	public String byMonth(HttpServletRequest request, Model model){
		
		//pull in logged in user
		User userInSession = getUserFromSession(request.getSession());
		int userUid = userInSession.getUid();
				
		//pull in logged users expenses
		List <Expenses> expenses = expensesDao.findByUserUid(userUid);
		
		Expenses temp;
		
		double janTotal = 0.00;
		double febTotal = 0.00;
		double marTotal = 0.00;
		double aprTotal = 0.00;
		double mayTotal = 0.00;
		double junTotal = 0.00;
		double julTotal = 0.00;
		double augTotal = 0.00;
		double sepTotal = 0.00;
		double octTotal = 0.00;
		double novTotal = 0.00;
		double decTotal = 0.00;
		
		for(int i = 0; i < expenses.size(); i++){
			temp = expenses.get(i);
			String date = temp.getDate();
			
			int dateMonth = month(date);
			
			if(dateMonth == 1){
				janTotal = janTotal + temp.getAmount();
			}
			
			if(dateMonth == 2){
				febTotal = febTotal + temp.getAmount();
			}
			
			if(dateMonth == 3){
				marTotal = marTotal + temp.getAmount();
			}
			
			if(dateMonth == 4){
				aprTotal = aprTotal + temp.getAmount();
			}
			
			if(dateMonth == 5){
				mayTotal = mayTotal + temp.getAmount();
			}
			
			if(dateMonth == 6){
				junTotal = junTotal + temp.getAmount();
			}
			
			if(dateMonth == 7){
				julTotal = julTotal + temp.getAmount();
			}
			
			if(dateMonth == 8){
				augTotal = augTotal + temp.getAmount();
			}
			
			if(dateMonth == 9){
				sepTotal = sepTotal + temp.getAmount();
			}
			
			if(dateMonth == 10){
				octTotal = octTotal + temp.getAmount();
			}
			
			if(dateMonth == 11){
				novTotal = novTotal + temp.getAmount();
			}
			
			if(dateMonth == 12){
				decTotal = decTotal + temp.getAmount();
			}
		}
		
		model.addAttribute("janTotal", janTotal);
		model.addAttribute("febTotal", febTotal);
		model.addAttribute("marTotal", marTotal);
		model.addAttribute("aprTotal", aprTotal);
		model.addAttribute("mayTotal", mayTotal);
		model.addAttribute("junTotal", junTotal);
		model.addAttribute("julTotal", julTotal);
		model.addAttribute("augTotal", augTotal);
		model.addAttribute("sepTotal", sepTotal);
		model.addAttribute("octTotal", octTotal);
		model.addAttribute("novTotal", novTotal);
		model.addAttribute("decTotal", decTotal);
		
		return "/bymonth";
	}
	
	//method to determine which month expense is in
	public int month(String date){
		String match = "/";
		int index = date.indexOf(match);
		int month = 0;
		
		//index == 1 means month is only 1 digit, use the character at index 0
		if(index == 1){
			month = Character.getNumericValue(date.charAt(index - 1));
		}
		
		//index == 2, then the month is 2 digits.  month will be 10, 11 or 12.  so, get digit at index 1 and add 10
		if(index == 2){
			month = (Character.getNumericValue(date.charAt(index - 1))) + 10;
		}
		return month;
	}
	
	
	
	

}
