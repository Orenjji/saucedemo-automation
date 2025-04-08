package vehuelFinalAct.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import vehuelFinalAct.AbstractComponents.AbstractComponent;

public class ProductDetailsPage extends AbstractComponent {
	
	WebDriver driver;

	public ProductDetailsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = ".inventory_item_name")
	List<WebElement> productList;

	@FindBy(css = ".inventory_details_img") 
    WebElement productImage;

    @FindBy(css = ".inventory_details_name") 
    WebElement productName;

    @FindBy(css = ".inventory_details_desc") 
    WebElement productDescription;

    @FindBy(css = ".inventory_details_price") 
    WebElement productPrice;

    @FindBy(css = ".btn_inventory") 
    WebElement addToCartOrRemoveButton; 

    @FindBy(css = ".shopping_cart_badge") 
    WebElement cartCount;

    @FindBy(id = "back-to-products") 
    WebElement backButton;

    // Methods to interact with the page
    
    public void clickItem(String productName2) {
        for (WebElement item : productList) {
            if (item.getText().trim().equalsIgnoreCase(productName2)) {
                item.click();
                break;
            }
        }
    }

    
    public boolean isImageDisplayed() {
        return productImage.isDisplayed();
    }

    public String getProductName() {
        return productName.getText().trim();
    }

    public String getProductDescription() {
        return productDescription.getText().trim();
    }

    public String getProductPrice() {
        return productPrice.getText().trim();
    }

    public boolean isAddToCartButtonDisplayed() {
        return addToCartOrRemoveButton.getText().contains("Add to cart");
    }
    
    public boolean isAddToCartButtonClickable() {
        return addToCartOrRemoveButton.isDisplayed() && addToCartOrRemoveButton.isEnabled();
    }


    public void clickAddToCart() {
        if (isAddToCartButtonDisplayed()) {
            addToCartOrRemoveButton.click();
        }
    }

    public boolean isRemoveButtonDisplayed() {
        return addToCartOrRemoveButton.getText().contains("Remove");
    }

    public void clickRemoveButton() {
        if (isRemoveButtonDisplayed()) {
            addToCartOrRemoveButton.click();
        }
    }

    public int getCartCount() {
        try {
            return Integer.parseInt(cartCount.getText().trim());
        } catch (Exception e) {
            return 0; // If the cart is empty, return 0
        }
    }
	
}
