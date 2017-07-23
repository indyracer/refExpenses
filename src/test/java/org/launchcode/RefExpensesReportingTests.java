package org.launchcode;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.launchcode.refExpenses.controllers.ReportController;
import org.launchcode.refExpenses.models.Expenses;
import org.launchcode.refExpenses.models.dao.ExpensesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RefExpensesReportingTests extends TestCase {
	
	//to be able to pull expenses from db
	@Autowired
	private ExpensesDao expensesDao;

	
	@Test
	public void testGetters(){
		
		//mileage expenses, Expense with ID 5
		Expenses temp = expensesDao.findByUid(5);
				
		assertEquals(5.4, temp.getAmount());
		assertEquals("1/1/17", temp.getDate());
		assertEquals("mileage", temp.getExpenseType());
		assertFalse(temp.getHaveReceipt());
		assertEquals(10.0, temp.getMiles());
		assertEquals("self", temp.getVendor());
		
		//item expense, Expense with ID 9
		temp = expensesDao.findByUid(9);
		assertEquals(5.0, temp.getAmount());
		assertEquals("1/10/17", temp.getDate());
		assertEquals("equipment", temp.getExpenseType());
		assertTrue(temp.getHaveReceipt());
		assertEquals(0.0, temp.getMiles());
		assertEquals("Total Hockey", temp.getVendor());
		
	}
	
	
	@Test
	public void TestTotalAmount(){
		
		//list of all expenses for user with ID 2
		
		//create list expenses for user with id 2 
		List<Expenses> testExpenses = expensesDao.findByUserUid(2);
				
		//test that loop works, check size == 10
		assertEquals(10, testExpenses.size());
		
		//total amount of user 2 expenses
		Expenses temp;
		double totalExpenses = 0.00;
		for(int i = 0; i < testExpenses.size(); i++){
			temp = testExpenses.get(i);
			totalExpenses = temp.getAmount() + totalExpenses;
		}
		
		//need to use the (expected, actual, acceptable delta) format to account for rounding	
		assertEquals(465.59, (totalExpenses), 0.5);		
	}
	
	@Test
	public void TestAmountByType(){
		//list of all expenses for user with ID 2
		
		//create list expenses for user with id 2 
		List<Expenses> testExpenses = expensesDao.findByUserUid(2);
		Expenses temp;
		
		double equipmentTotal = 0;
		for(int i = 0; i < testExpenses.size(); i++){
			temp = testExpenses.get(i);
			if(temp.getExpenseType().equals("equipment")){
				equipmentTotal = equipmentTotal + temp.getAmount();
			}			
		}
		
		assertEquals(155.00, equipmentTotal);
		
		double mileageTotal = 0;
		for(int i = 0; i < testExpenses.size(); i++){
			temp = testExpenses.get(i);
			if(temp.getExpenseType().equals("mileage")){
				mileageTotal = mileageTotal + temp.getAmount();
			}
		}
		mileageTotal = Math.round(mileageTotal * 100.0) / 100.0;
		assertEquals(38.50, mileageTotal);
		
		double mealsTotal = 0;
		for(int i = 0; i < testExpenses.size(); i++){
			temp = testExpenses.get(i);
			if(temp.getExpenseType().equals("meals")){
				mealsTotal = mealsTotal + temp.getAmount();
			}
		}
		mealsTotal = Math.round(mealsTotal * 100.0) / 100.0;
		assertEquals(12.00, mealsTotal);
		
		double feesTotal = 0;
		for(int i = 0; i < testExpenses.size(); i++){
			temp = testExpenses.get(i);
			if(temp.getExpenseType().equals("fees")){
				feesTotal = feesTotal + temp.getAmount();
			}
		}
		feesTotal = Math.round(feesTotal * 100.0) / 100.0;
		assertEquals(140.00, feesTotal);
		
		double travelTotal = 0;
		for(int i = 0; i < testExpenses.size(); i++){
			temp = testExpenses.get(i);
			if(temp.getExpenseType().equals("travel")){
				travelTotal = travelTotal + temp.getAmount();
			}
		}
		travelTotal = Math.round(travelTotal * 100.0) / 100.0;
		assertEquals(120.00, travelTotal);
		
		
	}
	
	@Test
	public void testMonth(){
		//list of all expenses for user with ID 2
		
		//create list expenses for user with id 2 
		List<Expenses> testExpenses = expensesDao.findByUserUid(2);
		Expenses temp;
		
		assertEquals(1, ReportController.month("1/1/17"));
		assertEquals(12, ReportController.month("12/1/17"));
		
		
	}
	

}
