package vehuelFinalAct;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import vehuelFinalAct.TestComponents.BaseTest;
import vehuelFinalAct.pageobjects.LandingPage;
import vehuelstratpoint.data.dataDriven;
import vehuelFinalAct.pageobjects.Inventory;

public class LoginPage extends BaseTest {
	
	private dataDriven d;
    private ArrayList<ArrayList<String>> userData;
    
    @BeforeClass
    public void setup() throws IOException {
        // Initialize dataDriven and fetch data only once
        d = new dataDriven();
        userData = d.getData();
    }
	
	@Test
	public void displayedLogin() { // check whether the required fields are displayed
		
		
		Boolean check = driver.findElement(By.cssSelector(".login-box")).isDisplayed();
		System.out.println(check);
	}
	
	@Test
	public void successLogin() throws IOException { // using standard user, login successfully
		
		String Username = userData.get(0).get(0);
		String Password = userData.get(0).get(1);
		
		Inventory productCatalogue = landingPage.loginApplication(Username, Password);

	}
	
	@Test
	public void missingUsername() throws IOException { // username field is left blank
		
		String Password = userData.get(0).get(1);
		
		Inventory productCatalogue = landingPage.loginApplication("", Password);
		Assert.assertEquals("Epic sadface: Username is required", landingPage.getErrorMessage());
		
	}
	
	@Test
	public void missingPassword() throws IOException { // password field is left blank
		
		String Username = userData.get(0).get(0);
		
		Inventory productCatalogue = landingPage.loginApplication(Username, "");
		Assert.assertEquals("Epic sadface: Password is required", landingPage.getErrorMessage());
		
	}
	
	@Test
	public void incorrectUsername() throws IOException { // incorrect username 
		String Username = userData.get(2).get(0);
		String Password = userData.get(2).get(1);
		
		Inventory productCatalogue = landingPage.loginApplication(Username, Password);
		Assert.assertEquals("Epic sadface: Username and password do not match any user in this service", landingPage.getErrorMessage());
		
	}
	
	@Test
	public void incorrectPassword() throws IOException { // incorrect password 
		
		String Username = userData.get(3).get(0);
		String Password = userData.get(3).get(1);
		Inventory productCatalogue = landingPage.loginApplication(Username, Password);
		Assert.assertEquals("Epic sadface: Username and password do not match any user in this service", landingPage.getErrorMessage());
		
	}
	
	@Test
	public void lockedAccount() throws IOException { // Locked Account
		
		String Username = userData.get(1).get(0);
		String Password = userData.get(1).get(1);
		
		Inventory productCatalogue = landingPage.loginApplication(Username, Password);
		Assert.assertEquals("Epic sadface: Sorry, this user has been locked out.", landingPage.getErrorMessage());
		
	}
	
}
