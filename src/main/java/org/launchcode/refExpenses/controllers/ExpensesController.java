package org.launchcode.refExpenses.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.launchcode.refExpenses.models.Expenses;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ExpensesController extends AbstractController{
	
	
	@RequestMapping(value="/mileage", method = RequestMethod.GET)
	public String mileageForm(){
		return "mileage";
	}
	
	@RequestMapping(value="/mileage", method = RequestMethod.POST)
	public String mileage(HttpServletRequest request, Model model){
		//pull data from form
		String date = request.getParameter("date"); //update expense model to change date from Date to String
		String to = request.getParameter("to");
		String from = request.getParameter("from");
		String miles = request.getParameter("miles");
		
		//Validate all field filled in
		if(date == "" || date == null || to == "" || to == null || from == "" || from == null || miles == "" || miles == null){
			model.addAttribute("missing_field_error", "Missing information, please make sure all field are filled in");
			return "mileage";
		}
		
		if(!isValidMile(miles)){
			model.addAttribute("miles_error", "Miles may be input with a max of 1 decimal point.  Please try again");
			return "mileage";
		}
		
		if(!isValidDate(date)){
			model.addAttribute("date_error", "Date must be in mm/dd/yy format, please try again");
			return "mileage";
		}
		
		
		//input fields validated, add to database
		
		//convert miles string to double
		double mileage = Double.parseDouble(miles);
		//set defaults so expense can be added to db
		String expenseType = "mileage";
		double amount = 0.00;
		String vendor = "self";
		boolean haveReceipt = false;
		
		//create new expense
		Expenses mileageExpense = new Expenses(getUserFromSession(request.getSession()), date, expenseType, mileage, amount, vendor, haveReceipt);
		
		expensesDao.save(mileageExpense);
		
		return "redirect:/expensehome";		
	}
	
	@RequestMapping(value="/item", method = RequestMethod.GET)
	public String itemForm(){
		return "item";
	}
	
	//add item form post here
	@RequestMapping(value="/item", method = RequestMethod.POST)
	public String item(HttpServletRequest request, Model model){
		//pull data from form
		String date = request.getParameter("date");
		String expenseType= request.getParameter("expenseType");
		String vendor = request.getParameter("vendor");
		String amount = request.getParameter("amount");
		String receipt = request.getParameter("haveReceipt");
		
		//validate all fields filled in
		if(date == "" || date == null || expenseType == "" || expenseType == null || vendor == "" || vendor == null || amount == "" || amount == null || receipt == "" || receipt == null){
			model.addAttribute("missing_field_error", "Please make sure to fill in all fields");
			return "item";
		}
		
		//validate date is in correct format
		if(!isValidDate(date)){
			model.addAttribute("date_error", "Date must be in mm/dd/yy format, please try again");
			return "item";
		}
		
		//validate amount is in correct format
		if(!isValidAmount(amount)){
			model.addAttribute("amount_error", "Please enter amount in 0.00 format");
			return "item";
		}
		
		//all inputs are validated.  Parse to doubles and booleans as needed.  Defaults for non-applicable expense item (i.e. mileage)
		double dollarAmount = Double.parseDouble(amount);
		boolean haveReceipt = Boolean.parseBoolean(receipt);
		double mileage = 0.0;
		
		//create new expense
		Expenses expense = new Expenses(getUserFromSession(request.getSession()), date, expenseType, mileage, dollarAmount, vendor, haveReceipt);
		
		expensesDao.save(expense);
		
		return "redirect:/expensehome";
		
	}
	
	//validate date is input in "mm/dd/yy" format
	public static boolean isValidDate(String date){
		Pattern validDatePattern = Pattern.compile("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$");
		Matcher matcher = validDatePattern.matcher(date);
		return matcher.matches();
	}
	
	//validates miles are input in "0.0" format
	public static boolean isValidMile(String miles){
		Pattern validMilePattern = Pattern.compile("\\d+(\\.\\d{1})?");
		Matcher matcher = validMilePattern.matcher(miles);
		return matcher.matches();
	}
	
	//validate amount is input in correct format
	public static boolean isValidAmount(String amount){
		Pattern validAmountPattern = Pattern.compile("^\\d+\\.\\d{2}$");
		Matcher matcher = validAmountPattern.matcher(amount);
		return matcher.matches();
	}
	

}
