package vehuelFinalAct.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import vehuelFinalAct.pageobjects.LandingPage;

public class BaseTest {
	
	public static WebDriver driver;
	public static LandingPage landingPage;
	
	public static WebDriver initializeDriver() throws IOException {
		
		// properties class
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\vehuelstratpoint\\resources\\GlobalData.properties");
		prop.load(fis);
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser") :prop.getProperty("browser");
		
		//prop.getProperty("browser");
		
		if (browserName.contains("chrome")) {
		    ChromeOptions options = new ChromeOptions();
		    WebDriverManager.chromedriver().setup();

		    // Disable Google Password Manager
		    Map<String, Object> prefs = new HashMap<>();
		    prefs.put("credentials_enable_service", false);
		    prefs.put("profile.password_manager_enabled", false);
		    options.setExperimentalOption("prefs", prefs);

		    // Optional: Run in headless mode
		    if (browserName.contains("headless")) {
		        options.addArguments("headless");
		    }

		    // Optional: Incognito mode (extra safe)
		    options.addArguments("--incognito");

		    driver = new ChromeDriver(options);
		    driver.manage().window().setSize(new Dimension(1440, 900)); // full screen
			
		} else if(browserName.equalsIgnoreCase("firefox")) {
			
		} else if (browserName.equalsIgnoreCase("edge")) {
			
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filepath) throws IOException {
		
		// read json to string
		String jsonContent = FileUtils.readFileToString(new File(filepath),
				StandardCharsets.UTF_8);
		
		// String to hashMap jackson Databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){
			
		});
		return data;
				
				
	}
	
	@BeforeMethod(alwaysRun = true)
	public static LandingPage launchApplication() throws IOException {
		
		WebDriver driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
		
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		
		driver.close();
		
	}

}
