package vehuelFinalAct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import vehuelFinalAct.TestComponents.BaseTest;
import vehuelFinalAct.pageobjects.CartPage;
import vehuelFinalAct.pageobjects.Inventory;
import vehuelFinalAct.pageobjects.ProductDetailsPage;
import vehuelstratpoint.data.dataDriven;

public class CartPageVerification extends BaseTest {
	
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
	public void accessCartPage() throws InterruptedException { 
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
		CartPage cartPage = productCatalogue.goToCartPage();
		String cartURL = driver.getCurrentUrl();
		Assert.assertEquals("https://www.saucedemo.com/cart.html", cartURL);
		Thread.sleep(2000);
		
	}
	
	
	 @Test(priority = 2)
	 public void ItemsDetailedDisplayedInCartPage() throws InterruptedException {
	    	
		Inventory productCatalogue = landingPage.loginApplication(username, password);
	    Thread.sleep(2000);
	    productCatalogue.addProductToCart(productName);
	    CartPage cartPage = productCatalogue.goToCartPage();
	    	
	    // Verify all required details are displayed
	    Assert.assertTrue(cartPage.getProductQuantity() > 0, "Product Quantity is 0" );
	    Assert.assertTrue(cartPage.getProductDescription().length() > 0, "Description is missing.");
	    Assert.assertTrue(cartPage.isContinueShoppingButtonDisplayed(), "Continue Shopping Button is not visible.");
	    Assert.assertTrue(cartPage.isCheckoutButtonDisplayed(), "Checkout Button is not visible");

	}
	 
	
	@Test(priority = 3)
	public void verifyContinueShopping() throws InterruptedException {
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
	    Thread.sleep(2000);
	    productCatalogue.addProductToCart(productName);
	    CartPage cartPage = productCatalogue.goToCartPage();
		
	    cartPage.clickContinueShoppingButton();
	    String currentURL = driver.getCurrentUrl();
		Assert.assertEquals("https://www.saucedemo.com/inventory.html", currentURL);

	}
	
	@Test(priority = 4)
	public void verifyProductDisplayInCart() throws InterruptedException {
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
	    Thread.sleep(2000);
	    productCatalogue.addProductToCart(productName);
	    CartPage cartPage = productCatalogue.goToCartPage();

	    List<String>cartItems = cartPage.getCartItemNames();
	    Assert.assertTrue(cartItems.contains(productName), "Product is not displayed in the cart");
	    
	   
	}
	
	@Test(priority = 5)
	public void verifyMultipleProductDisplayInCart() throws InterruptedException {
		
        Inventory productCatalogue = landingPage.loginApplication(username, password);

        Thread.sleep(1500);

        // Expected items to be added
        List<String> expectedItems = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt");

        // Add multiple DIFFERENT items BEFORE navigating to cart
        CartPage cartPage = new CartPage(driver);
        cartPage.addMultipleItemsToCart(3);
        
        Thread.sleep(1500); 

        cartPage = productCatalogue.goToCartPage();

        List<String> actualItems = cartPage.getCartItemNames();

        Assert.assertEquals(actualItems, expectedItems, "Cart items do not match expected items.");
        System.out.println(actualItems);
        System.out.println(expectedItems);
		
	}
	
	@Test(priority = 6)
	public void verifyAllProductDetailsDisplayInCart() throws InterruptedException {
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
	    Thread.sleep(2000);
	    productCatalogue.addProductToCart(productName);
	    CartPage cartPage = productCatalogue.goToCartPage();
	    
	    Assert.assertTrue(cartPage.areAllProductDetailsDisplayed(), "Product Details is not displayed in the cart");

	}


	@Test(priority = 7)
	public void removeItemFromCartPage() throws InterruptedException {

	    Inventory productCatalogue = landingPage.loginApplication(username, password);
	    productCatalogue.addProductToCart(productName);

	    CartPage cartPage = productCatalogue.goToCartPage();
	    int initialCount = Integer.parseInt(cartPage.getCartCount());

	    cartPage.removeItemFromCart(productName);
	    
	    Thread.sleep(2000);
	    

	    Assert.assertTrue(cartPage.isItemRemoved(productName), "Item was not removed from the cart!");
	    
	    int updatedCount = Integer.parseInt(cartPage.getCartCount());
	    Assert.assertEquals(updatedCount, initialCount - 1, "Cart count did not update correctly!");
	}
	
	@Test(priority = 8)
	public void removeMultipleItemsFromCartPage() throws InterruptedException {

	    Inventory productCatalogue = landingPage.loginApplication(username, password);

	    List<String> products = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt");
	    for (String product : products) {
	        productCatalogue.addProductToCart(product);
	    }

	    CartPage cartPage = productCatalogue.goToCartPage();
	    
	    int initialCount = Integer.parseInt(cartPage.getCartCount());

	    for (String product : products) {
	    	cartPage.removeItemFromCart(product);
	        Assert.assertTrue(cartPage.isItemRemoved(product), "Item '" + product + "' was not removed from the cart!");

	        int updatedCount = Integer.parseInt(cartPage.getCartCount());
	        initialCount--; // Decrease expected count by 1
	        Assert.assertEquals(updatedCount, initialCount, "Cart count did not update correctly after removing '" + product + "'!");
	    }

	    Assert.assertTrue(cartPage.isCartEmpty(), "Cart is not empty after removing all items!");
	}
	
	@Test(priority = 9)
	public void itemsPersistInCartAfterReLogging() throws InterruptedException {
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
		productCatalogue.addProductToCart(productName);
		
		CartPage cartPage = new CartPage(driver);
		
		cartPage.selectMenuOption("Logout");
		landingPage.loginApplication(username, password);
		String initialCount = cartPage.getCartCount();
        System.out.println(initialCount);
        Assert.assertEquals(initialCount, "1", "Cart count did not update correctly for a single item.");
        
	}


	
	
}
