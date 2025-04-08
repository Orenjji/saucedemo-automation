package vehuelFinalAct.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import vehuelFinalAct.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent {

	WebDriver driver;
	
	public CheckoutPage(WebDriver driver) {
		
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}

	
	@FindBy(id = "checkout")
    WebElement checkoutButton;
	
	@FindBy(id = "first-name")
	WebElement firstNameField;
	
	@FindBy(id = "last-name")
	WebElement lastNameField;
	
	@FindBy(id = "postal-code")
	WebElement zipCodeField;
	
	@FindBy(id = "react-burger-menu-btn")
	WebElement burgerMenuButton;
	
	@FindBy(css = ".title")
	WebElement title;
	
	@FindBy(css = "shopping_cart_link")
	WebElement shoppingCartIcon;
	
	@FindBy(id = "cancel")
	WebElement cancelButton;
	
	@FindBy(id = "continue")
	WebElement continueButton;

	
	public void goToCheckout() {
		
		if (isCheckoutButtonDisplayed()) {
            checkoutButton.click();
        }
    }
	
	
	public boolean isCheckoutButtonDisplayed() {
        return checkoutButton.getText().contains("Checkout");
    }
	
	public boolean isTitleDisplayed() {
		
		return title.getText().contains("Checkout: Your Information");
	}
	
	@SuppressWarnings("deprecation")
	public boolean isFirstNameFieldDisplayed() {
		
		return firstNameField.getAttribute("placeholder").contains("First Name");
	}
	
	@SuppressWarnings("deprecation")
	public boolean isLastNameFieldDisplayed() {
		
		return lastNameField.getAttribute("placeholder").contains("Last Name");
	}
	
	@SuppressWarnings("deprecation")
	public boolean isZipCodeFieldDisplayed() {
		
		return zipCodeField.getAttribute("placeholder").contains("Zip/Postal Code");
	}
	
	public boolean isCancelButtonDisplayed() {
        return cancelButton.getText().contains("Cancel");
    }
	
	public void cancelButtonClicked() {
		if (isCancelButtonDisplayed()) {
            cancelButton.click();
        }
		
	}
	
	@SuppressWarnings("deprecation")
	public boolean isContinueButtonDisplayed() {
	    return continueButton.getAttribute("value").equals("Continue");
	}
	
	public void continueButtonClicked() {
		if (isContinueButtonDisplayed()) {
            continueButton.click();
        }
		
	}
	
	public boolean isErrorMessageDisplayed(String expectedError) {
	    WebElement errorElement = driver.findElement(By.cssSelector(".error-message-container"));
	    return errorElement.getText().contains(expectedError);
	}
	
	public void enterDetails(String firstName, String lastName, String zipCode) {
		
        firstNameField.clear();
        lastNameField.clear();
        zipCodeField.clear();
        
        if (firstName != null && !firstName.isEmpty()) firstNameField.sendKeys(firstName);
        if (lastName != null && !lastName.isEmpty()) lastNameField.sendKeys(lastName);
        if (zipCode != null && !zipCode.isEmpty()) zipCodeField.sendKeys(zipCode);
        
        continueButton.click();
        
    }
	
	// Checkout Overview ---------------------------------------------------------------------------
	
	@FindBy(css = ".cart_item")
	WebElement productList;

	@FindBy(css = "div[data-test='payment-info-value']")
	WebElement paymentInfo;

	@FindBy(css = "div[data-test='shipping-info-value']")
	WebElement shippingInfo;

	@FindBy(css = ".summary_total_label")
	WebElement priceTotal;
	
	@FindBy(css = ".summary_subtotal_label")
	WebElement productPriceTotal;
	
	@FindBy(css = ".summary_tax_label")
    WebElement summaryTaxLabel;

	@FindBy(id = "finish")
	WebElement finishButton;
	
	@FindBy(css = ".inventory_item_price") 
    WebElement productPrice;
	
	@FindBy(css = ".inventory_item_desc") 
    WebElement productDescription;
	
	@FindBy(css = ".cart_quantity") 
    WebElement productQuantity;
	
	@FindBy(css = ".inventory_item_name") 
    WebElement productTitle;
	

	public boolean isProductDisplayed(String productName) {
	    return productList.getText().contains(productName);
	}

	public boolean isPaymentInfoDisplayed() {
	    return paymentInfo.isDisplayed();
	}

	public boolean isShippingInfoDisplayed() {
	    return shippingInfo.isDisplayed();
	}

	public boolean isPriceTotalDisplayed() {
	    return priceTotal.isDisplayed();
	}

	public boolean isFinishButtonDisplayed() {
	    return finishButton.isDisplayed();
	}
	
//	public boolean isImageDisplayed() {
//        return productImage.isDisplayed();
//    }
    

    public boolean isProductQuantityDisplayed() {
        return productQuantity.isDisplayed();
    }

    public boolean isProductTitleDisplayed() {
        return productTitle.isDisplayed();
    }

    public boolean isProductDescriptionDisplayed() {
        return productDescription.isDisplayed();
    }

    public boolean isProductPriceDisplayed() {
        return productPrice.isDisplayed();
    }
	
	public boolean areAllProductDetailsDisplayed() {

        boolean isTitleDisplayed = isProductTitleDisplayed();
        //boolean isImageDisplayed = isProductImageDisplayed();
        boolean isQuantityDisplayed = isProductQuantityDisplayed();
        boolean isDescriptionDisplayed = isProductDescriptionDisplayed();
        boolean isPriceDisplayed = isProductPriceDisplayed();
        
        return isTitleDisplayed && isQuantityDisplayed && 
               isDescriptionDisplayed && isPriceDisplayed;
    }
	
	public void clickedFinishButton() {
		
		if(isFinishButtonDisplayed()) {
			
			finishButton.click();
		}
	}
	
	public Double getPriceTotal() {
		return Double.parseDouble(productPriceTotal.getText().replace("Item total: ", "").replace("$", "").trim());
	}
	
	public Double getTaxTotal() {
		return Double.parseDouble(summaryTaxLabel.getText().replace("Tax: ", "").replace("$", "").trim());
	}
	
	public String calculateTotal() {
	    return String.valueOf(getPriceTotal() + getTaxTotal());
	}
	
	public String expectedTotal() {
		return String.valueOf(priceTotal.getText().replace("Total: ", "").replace("$", "").trim());
	}



}
