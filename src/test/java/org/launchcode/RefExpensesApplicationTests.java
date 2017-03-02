package org.launchcode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.launchcode.refExpenses.controllers.ExpensesController;
import org.launchcode.refExpenses.models.Expenses;
import org.launchcode.refExpenses.models.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RefExpensesApplicationTests extends TestCase {
	
	

	@Test
	public void testUserInit(){
		
		User testUserInit = new User("testFirstName", "testLastName", "testUserName", "testpassword", 0.57);
		
		assertEquals("testFirstName", testUserInit.getFirstName());
		assertEquals("testLastName", testUserInit.getLastName());
		assertEquals(0.57, testUserInit.getMileageAllowance());
		
		//valid usernames
		assertTrue(User.isValidUsername("testUserName"));
		assertTrue(User.isValidUsername("test1"));
		assertTrue(User.isValidUsername("testUserName1234"));
		assertFalse(User.isValidUsername("tes"));
		assertFalse(User.isValidUsername("testUsername123456789"));
		
		//valid passwords
		assertTrue(User.isValidPassword("testpassword"));
		assertTrue(User.isValidPassword("testpa"));
		assertTrue(User.isValidPassword("testpassword12345678"));
		assertTrue(User.isValidPassword("1testpas"));
		assertFalse(User.isValidPassword("testpassword123456789"));
		assertFalse(User.isValidPassword("testp"));
		
		
		
	}
	
	@Test
	public void testExpensesInit(){
		//for use in creating new expense
		User testUser= new User("testFirstName", "testLastName", "testUserName", "testpassword", 0.57);
		
		//mileage expense
		Expenses testMileageExpense = new Expenses(testUser, "5/1/17", "mileage", 10.0, 10.0 * .57, "self", false);
		//item expense
		Expenses testItemExpense = new Expenses(testUser, "3/1/17", "equipment", 0.0, 50, "Total Hockey", true);
		
		//mileage expense tests
		assertEquals(testUser, testMileageExpense.getUser());
		assertEquals("5/1/17", testMileageExpense.getDate());
		assertEquals("mileage", testMileageExpense.getExpenseType());
		assertEquals(10.0, testMileageExpense.getMiles());
		assertEquals("self", testMileageExpense.getVendor());
		assertFalse(testMileageExpense.getHaveReceipt());
		
		//item expense tests
		assertEquals(testUser, testItemExpense.getUser());
		assertEquals("3/1/17", testItemExpense.getDate());
		assertEquals("equipment", testItemExpense.getExpenseType());
		assertEquals(0.0, testItemExpense.getMiles());
		assertEquals(50.0, testItemExpense.getAmount());
		assertEquals("Total Hockey", testItemExpense.getVendor());
		assertTrue(testItemExpense.getHaveReceipt());
		
	}
	
	@Test
	public void testIsValidDate(){
		assertTrue(ExpensesController.isValidDate("5/1/17"));
		assertTrue(ExpensesController.isValidDate("05/01/17"));
		assertTrue(ExpensesController.isValidDate("5/01/17"));
		assertTrue(ExpensesController.isValidDate("5/1/2017"));
		assertTrue(ExpensesController.isValidDate("05/01/2017"));
		assertTrue(ExpensesController.isValidDate("05/1/2017"));
		assertTrue(ExpensesController.isValidDate("5/01/2017"));
		assertFalse(ExpensesController.isValidDate("May 1, 2017"));
		assertFalse(ExpensesController.isValidDate("5//17"));
		assertFalse(ExpensesController.isValidDate("/1/17"));
		assertFalse(ExpensesController.isValidDate("5/1/"));
		assertFalse(ExpensesController.isValidDate("5/1"));
		
	}

}
