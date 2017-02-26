package org.launchcode.refExpenses.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "user")
public class User extends AbstractEntity {

	protected String firstName;
	protected String lastName;
	private String username;
	protected String pwHash;
	protected static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	protected double mileageAllowance;

	//no arg constructor so Hibernate will work
	public User(){}

	public User (String firstName, String lastName, String username, String password, double mileageAllowance){

		super();

		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.pwHash = hashPassword(password); 
		this.mileageAllowance = mileageAllowance;
	}

	@NotNull
	@Column(name = "firstName")
	public String getFirstName(){
		return this.firstName;
	}
	
	protected void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	@NotNull
	@Column(name = "lastName")
	public String getLastName(){
		return this.lastName;
	}
	
	protected void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	@NotNull
	@Column(name = "username", unique = true)
	public String getUsername(){
		return this.username;
	}
	
	protected void setUsername(String username){
		this.username = username;
	}
	
	@NotNull
	@Column(name = "pwHash")
	public String getPwHash(){
		return this.pwHash;
	}
	
	@SuppressWarnings("unused")
	private void setPwHash(String pwHash){
		this.pwHash = pwHash;
	}
	
	@Column(name = "mileageAllowance")
	public double getMileageAllowance(){
		return mileageAllowance;
	}
	
	protected void setMileageAllowance(double mileageAllowance){
		this.mileageAllowance = mileageAllowance;
	}
	
	//hash the password
	private static String hashPassword(String password){
		return encoder.encode(password);
	}
	
	//password meets minimum standards, must be between 6 and 20 characters
	public static boolean isValidPassword(String password){
		Pattern validPasswordPattern = Pattern.compile("(\\S){6,20}");
		Matcher matcher = validPasswordPattern.matcher(password);
		return matcher.matches();
	}
	
	//checks that passwords match
	public boolean isMatchingPassword(String password){
		return encoder.matches(password, pwHash);
	}
	
	//check that username meets minimum standards:  letters and numbers, between 4 and 15 characters
	public static boolean isValidUsername(String username){
		Pattern validUserNamePattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9_]{4,15}");
		Matcher matcher = validUserNamePattern.matcher(username);
		return matcher.matches();
	}


}




