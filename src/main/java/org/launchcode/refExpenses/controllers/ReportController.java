package org.launchcode.refExpenses.controllers;

import java.util.List;
import java.text.NumberFormat;

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
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		
		formatter.format(totalExpenses);

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
		
		//Used to format totals into currency
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		
		model.addAttribute("equipmentTotal", formatter.format(equipmentTotal));
		model.addAttribute("mealsTotal", formatter.format(mealsTotal));
		model.addAttribute("feesTotal", formatter.format(feesTotal));
		model.addAttribute("travelTotal", formatter.format(travelTotal));
		model.addAttribute("mileageTotal", formatter.format(mileageTotal));

		return "byexpensetype";


	}

	@RequestMapping(value="/bymonth", method=RequestMethod.GET)
	public String byMonth(HttpServletRequest request, Model model){

		//pull in logged in user
		User userInSession = getUserFromSession(request.getSession());
		int userUid = userInSession.getUid();

		//pull in logged users expenses
		List <Expenses> expenses = expensesDao.findByUserUid(userUid);
		
		/*List <Double> expenses2017 = monthlyTotals(expenses);
		
		model.addAttribute("monthlyTotal", expenses2017);
		*/
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
	
		//Used to format totals into currency
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		
		model.addAttribute("janTotal", formatter.format(janTotal));
		model.addAttribute("febTotal", formatter.format(febTotal));
		model.addAttribute("marTotal", formatter.format(marTotal));
		model.addAttribute("aprTotal", formatter.format(aprTotal));
		model.addAttribute("mayTotal", formatter.format(mayTotal));
		model.addAttribute("junTotal", formatter.format(junTotal));
		model.addAttribute("julTotal", formatter.format(julTotal));
		model.addAttribute("augTotal", formatter.format(augTotal));
		model.addAttribute("sepTotal", formatter.format(sepTotal));
		model.addAttribute("octTotal", formatter.format(octTotal));
		model.addAttribute("novTotal", formatter.format(novTotal));
		model.addAttribute("decTotal", formatter.format(decTotal));
		

		return "/bymonth";
	}

	//method to determine which month expense is in
	public static int month(String date){
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

	//method to calculate monthly totals by year, return list of totals
	/*not yet ready to implement
	public List<Double> monthlyTotals(List<Expenses> expenses){


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
		//add totals to new list
		List<Double> expensesByYear = new ArrayList<Double>();
		expensesByYear.add(janTotal);
		expensesByYear.add(febTotal);
		expensesByYear.add(marTotal);
		expensesByYear.add(aprTotal);
		expensesByYear.add(mayTotal);
		expensesByYear.add(junTotal);
		expensesByYear.add(julTotal);
		expensesByYear.add(augTotal);
		expensesByYear.add(sepTotal);
		expensesByYear.add(octTotal);
		expensesByYear.add(novTotal);
		expensesByYear.add(decTotal);

		return expensesByYear;
	}*/
}
