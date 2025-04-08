Here’s the complete and updated `README.md` with all the sections including **ChromeDriver setup**, **how to run tests**, and **test reports**:

---

## 🧪 saucedemo-automation

**Final activity project** for Selenium-based web test automation using Java and Maven.

This repository includes:
- ✅ Automated test cases for the [Saucedemo](https://www.saucedemo.com) website  
- 🛒 Cart functionality testing  
- 🔐 Login validation  
- ⚙️ ChromeDriver setup and browser configuration  

---

### 🛠 Tools & Technologies
- Java  
- Selenium WebDriver  
- ChromeDriver  
- Maven  
- TestNG *(for test structure and execution)*  
- ExtentReports *(for beautiful HTML test reports)*

---

### 🚗 ChromeDriver Setup Instructions

To run the tests, you’ll need to ensure that **ChromeDriver** is set up properly on your system.

#### 1. **Download ChromeDriver**

- **Option 1: Manually download ChromeDriver**  
  Go to the [ChromeDriver Downloads page](https://sites.google.com/a/chromium.org/chromedriver/downloads) and download the version of ChromeDriver that matches your installed version of Google Chrome.

- **Option 2: Use WebDriverManager** (Recommended)  
  If you prefer to **automate** the ChromeDriver setup, you can use **WebDriverManager**. This is already included in your project if you're following the Selenium setup.

#### 2. **Set Up ChromeDriver Path**

- **Option 1: Use WebDriverManager**  
  WebDriverManager will automatically download and manage ChromeDriver for you. This way, you don’t need to manually set the path.

  Add the WebDriverManager dependency in your `pom.xml` (if not already present):

  ```xml
  <dependency>
      <groupId>io.github.bonigarcia</groupId>
      <artifactId>webdrivermanager</artifactId>
      <version>5.3.0</version>
  </dependency>
  ```

  In your Java code, initialize WebDriverManager like this:

  ```java
  WebDriverManager.chromedriver().setup();
  ```

- **Option 2: Manually set the ChromeDriver path**  
  If you're **not using WebDriverManager**, you will need to specify the location of the downloaded `chromedriver` executable.

  Set the system property for the `chromedriver` path before initializing the driver:

  ```java
  System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
  ```

  Example:
  ```java
  System.setProperty("webdriver.chrome.driver", "C:/path/to/chromedriver.exe");
  ```

#### 3. **Run the Tests**

Once you’ve set up ChromeDriver, you can proceed to run the tests using Maven:

```bash
mvn clean install
mvn test
```

---

### 🚀 How to Run the Tests

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/saucedemo-automation.git
   cd saucedemo-automation
   ```

2. **Import the project into an IDE**  
   Recommended: IntelliJ IDEA or Eclipse

3. **Download dependencies**
   ```bash
   mvn clean install
   ```

4. **Execute the tests**
   ```bash
   mvn test
   ```

---

### 📄 View Test Reports

> This project uses **ExtentReports** for rich test reporting.

After running tests:

- Navigate to the report directory:
  ```
  test-output/ExtentReports/
  ```

- Open the report file in your browser:
  ```
  index.html
  ```

---

### 🔄 **Ensure Chrome and ChromeDriver Compatibility**

- Ensure that your **ChromeDriver** version matches the version of **Google Chrome** installed on your machine. If there's a version mismatch, you may encounter errors.
- If you're using **WebDriverManager**, it automatically handles version compatibility for you.

---
