package vehuelFinalAct;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import vehuelFinalAct.TestComponents.BaseTest;
import vehuelFinalAct.pageobjects.Inventory;
import vehuelFinalAct.pageobjects.ProductDetailsPage;
import vehuelstratpoint.data.dataDriven;

public class ProductDetails extends BaseTest {
	
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
	public void goToProductDetailsPage() {
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
		ProductDetailsPage productDetails = new ProductDetailsPage(driver);
        
        productDetails.clickItem(productName);
        
		String currentURL = driver.getCurrentUrl();

		Assert.assertEquals("https://www.saucedemo.com/inventory-item.html?id=4", currentURL);
		
	}
	
	@Test(priority = 2)
    public void verifyAllRequiredProductDetails() {
		
        Inventory productCatalogue = landingPage.loginApplication(username, password);
        ProductDetailsPage productDetails = new ProductDetailsPage(driver);
        
        productDetails.clickItem(productName);
        // Verify all required details are displayed
        Assert.assertTrue(productDetails.isImageDisplayed(), "Product image is missing.");
        Assert.assertEquals(productDetails.getProductName(), productName, "Product name mismatch.");
        Assert.assertTrue(productDetails.getProductDescription().length() > 0, "Description is missing.");
        Assert.assertTrue(productDetails.isAddToCartButtonDisplayed(), "'Add to Cart' button is missing.");
        
    }

    @Test(priority = 3)
    public void verifyRemoveButtonWhenItemInCart() {
    	
        Inventory productCatalogue = landingPage.loginApplication(username, password);
        ProductDetailsPage productDetails = new ProductDetailsPage(driver);
        
        productDetails.clickItem(productName);
        productDetails.clickAddToCart();
        

        Assert.assertTrue(productDetails.isRemoveButtonDisplayed(), "'Remove' button is not displayed after adding to cart.");

    }

    @Test(priority = 4)
    public void verifyRemoveButtonFunctionality() {

        Inventory productCatalogue = landingPage.loginApplication(username, password);
        ProductDetailsPage productDetails = new ProductDetailsPage(driver);

        productDetails.clickItem(productName);

        productDetails.clickAddToCart();

        int cartCountBefore = productDetails.getCartCount();
        System.out.println("Cart Count Before Removing: " + cartCountBefore);

        productDetails.clickRemoveButton();

        int cartCountAfter = productDetails.getCartCount();
        System.out.println("Cart Count After Removing: " + cartCountAfter);

        Assert.assertEquals(cartCountAfter, cartCountBefore - 1, "Cart count did not update correctly!");

        Assert.assertFalse(productDetails.isRemoveButtonDisplayed(), "'Remove' button is still displayed after removing item.");

    }


    @Test(priority = 5)
    public void verifyAddToCartButtonFunctionality() {

        Inventory productCatalogue = landingPage.loginApplication(username, password);
        ProductDetailsPage productDetails = new ProductDetailsPage(driver);

        productDetails.clickItem(productName);
        
        Assert.assertTrue(productDetails.isAddToCartButtonDisplayed(), "'Add to Cart' button is not visible.");
        Assert.assertTrue(productDetails.isAddToCartButtonClickable(), "'Add to Cart' button is not clickable.");

        int cartCountBefore = productDetails.getCartCount();
        System.out.println("Cart Count Before Clicking 'Add to Cart': " + cartCountBefore);

        productDetails.clickAddToCart();

        int cartCountAfter = productDetails.getCartCount();
        System.out.println("Cart Count After Clicking 'Add to Cart': " + cartCountAfter);

        Assert.assertEquals(cartCountAfter, cartCountBefore + 1, "Cart count did not update correctly!");

    }

}
