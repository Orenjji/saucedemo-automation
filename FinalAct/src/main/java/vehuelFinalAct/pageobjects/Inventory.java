package vehuelFinalAct.pageobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import vehuelFinalAct.AbstractComponents.AbstractComponent;

public class Inventory extends AbstractComponent {
	WebDriver driver;
	
	public Inventory(WebDriver driver) {
		
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// PageFactory
	@FindBy(css = ".inventory_item")
	List<WebElement> products;
	
	@FindBy(css = ".inventory_item_name")
	WebElement productName;	
	
	@FindBy(css = ".inventory_item_img")
	WebElement productImg;	
	
	@FindBy(css = ".inventory_item_desc")
	WebElement productDesc;
	
	@FindBy(css = ".inventory_item_price")
	WebElement productPrice;	
	
	By productsBy = By.cssSelector(".inventory_item");
	By productsByName = By.cssSelector(".inventory_item_name");
	By productsByImage = By.cssSelector(".inventory_item_img");
	By productsByDetails = By.cssSelector(".inventory_item_desc");
	By productsByItemPrice = By.cssSelector(".inventory_item_price");
	
	By addToCart = By.cssSelector(".btn.btn_primary.btn_small.btn_inventory");
	
	public List<WebElement> getProductList() {
		
		waitForElementToAppear(productsBy);
		return products;
		
	}
	
	public boolean verifyProductListDetails() {
	    List<WebElement> products = driver.findElements(productsBy);
	    
	    for (WebElement product : products) {
	        WebElement name = product.findElement(productsByName);
	        WebElement image = product.findElement(productsByImage);
	        WebElement description = product.findElement(productsByDetails);
	        WebElement price = product.findElement(productsByItemPrice);
	        WebElement addToCartButton = product.findElement(addToCart);
	        
	        if (name == null || image == null || description == null || price == null || addToCartButton == null) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public boolean verifyAddToCartFunctionality() {
	    List<WebElement> addToCartButtons = driver.findElements(addToCart);

	    for (WebElement button : addToCartButtons) {
	        button.click();
	        WebElement removeButton = driver.findElement(By.cssSelector(".btn.btn_secondary.btn_small.btn_inventory"));
	        if (!removeButton.getText().equals("Remove")) {
	            return false;
	        }
	        removeButton.click(); // Reset state by clicking 'Remove'
	    }
	    return true;
	}
	
	public boolean verifySortingByNameAscending() {
	    Select sortDropdown = new Select(driver.findElement(By.cssSelector(".product_sort_container")));
	    sortDropdown.selectByValue("az"); // Sorting option for A to Z

	    List<WebElement> productNames = driver.findElements(productsByName);
	    List<String> actualNames = productNames.stream().map(WebElement::getText).collect(Collectors.toList());
	    List<String> sortedNames = new ArrayList<>(actualNames);
	    Collections.sort(sortedNames);

	    return actualNames.equals(sortedNames);
	}

	public boolean verifySortingByNameDescending() {
	    Select sortDropdown = new Select(driver.findElement(By.cssSelector(".product_sort_container")));
	    sortDropdown.selectByValue("za"); // Sorting option for Z to A

	    List<WebElement> productNames = driver.findElements(productsByName);
	    List<String> actualNames = productNames.stream().map(WebElement::getText).collect(Collectors.toList());
	    List<String> sortedNames = new ArrayList<>(actualNames);
	    Collections.sort(sortedNames, Collections.reverseOrder());

	    return actualNames.equals(sortedNames);
	}
	
	public boolean verifySortingByPriceAscending() {
	    Select sortDropdown = new Select(driver.findElement(By.cssSelector(".product_sort_container")));
	    sortDropdown.selectByValue("lohi"); // Sorting option for Z to A

	    List<WebElement> productPrices = driver.findElements(productsByItemPrice);
	    List<Double> actualPrices = productPrices.stream().map(price -> Double.parseDouble(price.getText().replace("$", ""))).collect(Collectors.toList());
	    List<Double> sortedPrices = new ArrayList<>(actualPrices);
	    Collections.sort(sortedPrices);

	    return actualPrices.equals(sortedPrices);
	}
	
	public boolean verifySortingByPriceDescending() {
	    Select sortDropdown = new Select(driver.findElement(By.cssSelector(".product_sort_container")));
	    sortDropdown.selectByValue("hilo"); // Sorting option for Z to A

	    List<WebElement> productPrices = driver.findElements(productsByItemPrice);
	    List<Double> actualPrices = productPrices.stream().map(price -> Double.parseDouble(price.getText().replace("$", ""))).collect(Collectors.toList());
	    List<Double> sortedPrices = new ArrayList<>(actualPrices);
	    Collections.sort(sortedPrices, Collections.reverseOrder());

	    return actualPrices.equals(sortedPrices);
	}

	
	public WebElement getProductByName(String productName) {
		
		WebElement prod = products.stream().filter(product -> product.findElement(By.cssSelector(".inventory_item_name"))
				.getText().equals(productName)).findFirst().orElse(null);
		
		return prod;
		
	}
	
	public void addProductToCart(String productName) throws InterruptedException {
		
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();
		
	}
	
}
