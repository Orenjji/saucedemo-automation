package vehuelFinalAct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import vehuelFinalAct.TestComponents.BaseTest;
import vehuelFinalAct.pageobjects.CartPage;
import vehuelFinalAct.pageobjects.LandingPage;
import vehuelstratpoint.data.dataDriven;
import vehuelFinalAct.pageobjects.Inventory;

public class CartIconFunctionality extends BaseTest{
	
	private dataDriven d;
    private ArrayList<ArrayList<String>> userData;
    private String username;
    private String password;

    
    @BeforeClass
    public void setup() throws IOException {
        // Initialize dataDriven and fetch data only once
        d = new dataDriven();
        userData = d.getData();
        username = userData.get(0).get(0);
        password = userData.get(0).get(1);
        
    }

    String productName = "Sauce Labs Backpack";

    
    @Test(priority = 1)
	public void getCartPage() throws InterruptedException { // verify access to product listing page
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
		CartPage cartPage = productCatalogue.goToCartPage();
		String cartURL = driver.getCurrentUrl();
		Assert.assertEquals("https://www.saucedemo.com/cart.html", cartURL);
		Thread.sleep(2000);
		
	}
	

    @Test(priority = 2)
    public void CartCountUpdatesForSingleItem() throws InterruptedException {
    	
    	Inventory productCatalogue = landingPage.loginApplication(username, password);
    	
    	Thread.sleep(2000);
    	productCatalogue.addProductToCart(productName);
    	CartPage cartPage = new CartPage(driver);
    	String initialCount = cartPage.getCartCount();
        System.out.println(initialCount);
        Assert.assertEquals(initialCount, "1", "Cart count did not update correctly for a single item.");

    }

    @Test(priority = 3)
    public void CartCountUpdatesForMultipleItems() throws InterruptedException {
        
        // Login and go to product listing page
        Inventory productCatalogue = landingPage.loginApplication(username, password);

        Thread.sleep(1500); // Allow UI to load


        // Add multiple DIFFERENT items BEFORE navigating to cart
        CartPage cartPage = new CartPage(driver);
        cartPage.addMultipleItemsToCart(3);
        
        Thread.sleep(1500); 

        // Get updated cart count
        String updatedCountStr = cartPage.getCartCount();
        int updatedCount = updatedCountStr.isEmpty() ? 0 : Integer.parseInt(updatedCountStr);
        
        // Verify cart count
        Assert.assertEquals(updatedCount, 3, "Cart count did not update correctly for multiple items.");



        
    }


    
    @Test(priority = 4)
    public void HamburgerMenuDisplays() throws InterruptedException {
        Inventory productCatalogue = landingPage.loginApplication(username, password);
        CartPage cartPage = new CartPage(driver);
        Thread.sleep(1500);

        cartPage.openMenu();
        Thread.sleep(1000); // Ensures the menu fully loads

        // Retrieve and normalize menu texts
        List<String> menuTexts = cartPage.getMenuTexts();

        // Debugging: Print menu items
        System.out.println("Extracted Menu Items: " + menuTexts);

        // Expected menu options (normalized)
        List<String> expectedMenuItems = Arrays.asList("all items", "about", "logout", "reset app state");

        // Assert after normalizing both lists
        Assert.assertEquals(menuTexts, expectedMenuItems, "Menu items do not match expected values.");
    }


	  @Test(priority = 5)
	  public void SidebarOpenAndCloses() throws InterruptedException {
	    	
	     Inventory productCatalogue = landingPage.loginApplication(username, password);
	     CartPage cartPage = new CartPage(driver);
	     Thread.sleep(1500); 
	     
	     cartPage.openMenu();
	     Thread.sleep(1500); 
	     
	     System.out.println("Before closing, menu displayed: " + cartPage.isMenuDisplayed());
	     Assert.assertTrue(cartPage.isMenuDisplayed(), "Sidebar did not open.");
	     
	     cartPage.closeMenu();
	     Thread.sleep(1500); 
	     
	     System.out.println("After closing, menu displayed: " + cartPage.isMenuDisplayed());
	     Assert.assertFalse(cartPage.isMenuDisplayed(), "Sidebar did not close after clicking 'X' button.");
	       
	   }
}
