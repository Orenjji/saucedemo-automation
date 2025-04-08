package vehuelFinalAct.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import vehuelFinalAct.AbstractComponents.AbstractComponent;

public class OrderPage extends AbstractComponent {
	
	WebDriver driver;

	
	public OrderPage(WebDriver driver) {
		
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
    @FindBy(id = "finish")
    WebElement finishButton;

    @FindBy(css = ".complete-header")
    WebElement thankYouMessage;

    @FindBy(id = "back-to-products")
    WebElement backHomeButton;

    @FindBy(css = ".order_confirmation")
    WebElement orderConfirmation;

    public void clickFinishButton() {
        if (isFinishButtonDisplayed()) {
            finishButton.click();
        }
    }

    public boolean isFinishButtonDisplayed() {
        return finishButton.isDisplayed();
    }

    public boolean isThankYouMessageDisplayed() {
        return thankYouMessage.getText().contains("Thank you for your order!");
    }

    public boolean isBackHomeButtonDisplayed() {
        return backHomeButton.isDisplayed();
    }


    public void clickBackHomeButton() {
        if (isBackHomeButtonDisplayed()) {
            backHomeButton.click();
        }
    }

    public boolean areRequiredDetailsDisplayed() {
        return isThankYouMessageDisplayed() && isBackHomeButtonDisplayed();
    }
	
}
