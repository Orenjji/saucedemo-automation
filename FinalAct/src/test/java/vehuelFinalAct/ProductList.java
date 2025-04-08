package vehuelFinalAct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import vehuelFinalAct.TestComponents.BaseTest;
import vehuelFinalAct.pageobjects.Inventory;
import vehuelstratpoint.data.dataDriven;

public class ProductList extends BaseTest {
	
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
	
	@Test
	public void getProductPage() { // verify access to product listing page
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
		String inventoryURL = driver.getCurrentUrl();
		Assert.assertEquals("https://www.saucedemo.com/inventory.html", inventoryURL);
		
	}
	
	@Test
	public void getProducList() { // verify all products are displayed
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
		String inventoryURL = driver.getCurrentUrl();
		List<WebElement> productList = productCatalogue.getProductList();
		Assert.assertEquals(productList.size(), 6);
	}

	
	@Test
	public void ProductListDetailsDisplayed() { // verify all details are displayed
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
		String inventoryURL = driver.getCurrentUrl();
	    Assert.assertTrue(productCatalogue.verifyProductListDetails(), "Not all required product details are displayed.");
	}
	
	@Test
	public void AddToCartButton() {
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
		String inventoryURL = driver.getCurrentUrl();
	    Assert.assertTrue(productCatalogue.verifyAddToCartFunctionality(), "Add to Cart button is not working as expected.");
	}
	
	@Test
	public void SortingByNameAscending() {
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
		String inventoryURL = driver.getCurrentUrl();
	    Assert.assertTrue(productCatalogue.verifySortingByNameAscending(), "Products are not sorted correctly (Z to A).");
	}

	@Test
	public void SortingByNameDescending() {
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
		String inventoryURL = driver.getCurrentUrl();
	    Assert.assertTrue(productCatalogue.verifySortingByNameDescending(), "Products are not sorted correctly (Z to A).");
	}
	
	@Test
	public void SortingByPriceAscending() {
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
		String inventoryURL = driver.getCurrentUrl();
	    Assert.assertTrue(productCatalogue.verifySortingByPriceAscending(), "Prices are not sorted correctly (Z to A).");
	}
	
	@Test
	public void SortingByPriceDescending() {
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
		String inventoryURL = driver.getCurrentUrl();
	    Assert.assertTrue(productCatalogue.verifySortingByPriceDescending(), "Prices are not sorted correctly (Z to A).");
	}

}
