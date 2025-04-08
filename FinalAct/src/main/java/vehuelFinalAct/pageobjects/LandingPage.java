package vehuelFinalAct.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import vehuelFinalAct.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {
	
	WebDriver driver;
	
	public LandingPage(WebDriver driver) {
		
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//WebElement userEmail = driver.findElement(By.id("userEmail"));
	
	// PageFactory
	@FindBy(id = "user-name")
	WebElement userName;
	
	@FindBy(id = "password")
	WebElement userPassword;
	
	@FindBy(id = "login-button")
	WebElement submit;
	
	@FindBy(css = "h3[data-test='error']")
	WebElement errorMessage;

	
	public Inventory loginApplication(String email, String password) {
		
		userName.sendKeys(email);
		userPassword.sendKeys(password);
		submit.click();
		Inventory productCatalogue = new Inventory(driver);
		return productCatalogue;
		
	}
	
	public String getErrorMessage() {
		
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}
	
	public void goTo() {
		
		driver.get("https://www.saucedemo.com/");
	}
}
