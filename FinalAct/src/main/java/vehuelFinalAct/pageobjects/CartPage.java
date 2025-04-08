package vehuelFinalAct.pageobjects;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import vehuelFinalAct.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent {
	
	WebDriver driver;
	
	public CartPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}


    @FindBy(css = ".shopping_cart_link")
    WebElement checkoutEle;

    @FindBy(css = ".inventory_item_name") 
    List<WebElement> cartItemNames;

    public List<String> getCartItemNames() {
        return cartItemNames.stream()
                            .map(WebElement::getText)
                            .collect(Collectors.toList());
    }

    // CART ICON --------------------------------------------------------------------------------------------
    @FindBy(css = ".add-to-cart-button") 
    WebElement addToCartButton; 

    @FindBy(css = ".shopping_cart_badge")
    WebElement cartCount;

    @FindBy(id = "react-burger-menu-btn")
    WebElement menuButton;

    @FindBy(id = "react-burger-cross-btn")
    WebElement closeMenuButton;

    @FindBy(css = ".bm-menu")
    WebElement sidebarMenu;
    
    @FindBy(css = ".bm-item.menu-item")
    private List<WebElement> sidebarMenuItems;

    // Method to get menu items as a list of Strings
    public List<String> getMenuTexts() {
        return sidebarMenuItems.stream()
                               .map(item -> item.getText().trim().toLowerCase())  // Normalize text
                               .collect(Collectors.toList());
    }

    
    @FindBy(css = ".btn_inventory")
    List <WebElement> addButtons;

    
	public String getCartCount() {
        try {
            String ItemCount = cartCount.getText();
            return ItemCount;
        } catch (Exception e) {
            return "0";
        }
    }

	public void addMultipleItemsToCart(int count) {
	    
	    for (int i = 0; i < count && i < addButtons.size(); i++) {
	        addButtons.get(i).click(); // Click a different "Add to Cart" button
	    }
	}

    public CheckoutPage goToCheckout() {
        checkoutEle.click();
        return new CheckoutPage(driver);
    }
    
    
    
    public void openMenu() {
    	
        menuButton.click();

    }
    
    

    public void closeMenu() throws InterruptedException {
    	
    	waitForWebElementToAppear(sidebarMenu);
        closeMenuButton.click();
        waitForElementToDisappear(sidebarMenu);
        
    }

    public boolean isMenuDisplayed() {
    	
        return sidebarMenu.isDisplayed();
    }
    
    public List <WebElement> isMenuItemDisplayed() {
    	
    	waitForWebElementToAppear(sidebarMenu);
        return sidebarMenuItems;
        
    }
    
    // CART PAGE -------------------------------------------------------------------------------------------------------------
    
    @FindBy(css = ".inventory_item_name")
	List<WebElement> productList;

	@FindBy(css = ".inventory_item_img") 
    WebElement productImage;

    @FindBy(css = ".inventory_item_name") 
    WebElement productName;
    
    @FindBy(css = ".cart_quantity") 
    WebElement productQuantity;

    @FindBy(css = ".inventory_item_desc") 
    WebElement productDescription;

    @FindBy(css = ".inventory_item_price") 
    WebElement productPrice;
    
    @FindBy(css = "btn.btn_secondary.btn_small cart_button") 
    WebElement addToCartOrRemoveButton; 
    
    @FindBy(id = "continue-shopping")
    WebElement continueShopButton;
    
    @FindBy(id = "checkout")
    WebElement checkoutButton;
    
    @FindBy(xpath="//button[contains(text(),'Add to cart') or contains(text(),'Remove')]")
	WebElement button;

    // Methods to interact with the page -----------------------------------------------------------------------------------------
    
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
    

    public boolean isProductQuantityDisplayed() {
        return productQuantity.isDisplayed();
    }

    public boolean isProductTitleDisplayed() {
        return productName.isDisplayed();
    }

    public boolean isProductImageDisplayed() {
        return productImage.isDisplayed();
    }

    public boolean isProductDescriptionDisplayed() {
        return productDescription.isDisplayed();
    }

    public boolean isProductPriceDisplayed() {
        return productPrice.isDisplayed();
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
    
    public int getProductQuantity() {
        return Integer.parseInt(productQuantity.getText().trim());
    }
    
    public boolean isButtonDisplayed() {
        String buttonText = button.getText();
        return buttonText.contains("Add to cart") || buttonText.contains("Remove");
    }

    public boolean isAddToCartButtonDisplayed() {
        return addToCartOrRemoveButton.getText().contains("Add to cart");
    }
    
    public boolean isAddToCartButtonClickable() {
        return addToCartOrRemoveButton.isDisplayed() && addToCartOrRemoveButton.isEnabled();
    }
    
    public boolean areAllProductDetailsDisplayed() {

        boolean isTitleDisplayed = isProductTitleDisplayed();
        //boolean isImageDisplayed = isProductImageDisplayed();
        boolean isQuantityDisplayed = isProductQuantityDisplayed();
        boolean isDescriptionDisplayed = isProductDescriptionDisplayed();
        boolean isPriceDisplayed = isProductPriceDisplayed();
        boolean isButtonDisplayed = isButtonDisplayed();
        
        return isTitleDisplayed && isQuantityDisplayed && 
               isDescriptionDisplayed && isPriceDisplayed && isButtonDisplayed;
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
    
    public boolean isContinueShoppingButtonDisplayed() {
        return continueShopButton.getText().contains("Continue Shopping");
    }
    
    public boolean isCheckoutButtonDisplayed() {
        return checkoutButton.getText().contains("Checkout");
    }
    
    public void clickContinueShoppingButton() {
    	
    	if (isContinueShoppingButtonDisplayed()) {
            continueShopButton.click();
        }
    }

//	public Object getProductImage(String item) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	// Locator for all "Remove" buttons in the cart
    @FindAll(@FindBy(xpath = "//button[contains(text(),'Remove')]"))
    private List<WebElement> removeButtons;

    // Method to remove a specific item by its name
    public void removeItemFromCart(String itemName) {
    	WebElement removeButton = driver.findElement(By.xpath("//div[text()='" + itemName + "']/ancestor::"
    			+ "div[contains(@class,'cart_item')]//button[contains(text(),'Remove')]"));
        removeButton.click();
    }

    // Method to remove all items in the cart
    public void removeAllItemsFromCart() {
        for (WebElement button : removeButtons) {
            button.click();
        }
    }
    
    public boolean isItemRemoved(String productName) {
        return driver.findElements(By.xpath("//div[text()='" + productName + "']")).isEmpty();
    }
    
    public boolean isCartEmpty() {
        return driver.findElements(By.className("cart_item")).isEmpty();
    }

    public void selectMenuOption(String option) {
        menuButton.click(); // Ensure menu opens

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement menuOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[text()='" + option + "']")));
        menuOption.click();
    }


	
}
