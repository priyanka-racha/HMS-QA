package nhs.ui;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.io.Files;

public class NHS_Login {

	// Global variable
	static WebDriver driver;

	 @Before
	public void startBrowser() {
			// for initializations or keeping the system ready
		System.setProperty("webdriver.chrome.driver", "P:\\Selenium\\chromedriver.exe");
		driver = new ChromeDriver();
	}

	// Testcase:1- Verifying sign-in page
	void login(String email, String pwd) throws InterruptedException, IOException {
		// System.out.println("email:" + email \n "pwd:" + pwd);
		System.out.println("Inside Login");
		driver.get("http://hms-qa.herokuapp.com/");
		// To maximize the screen
		driver.manage().window().maximize();
		// Passing Username and Password
		driver.findElement(By.id("inputEmail")).sendKeys(email);
		driver.findElement(By.id("inputPassword")).sendKeys(pwd);
		// Take screenshot and store as file format
		File src1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// Now copy the screenshot to the desired location using copyfile method
		Files.copy(src1, new File("P:\\togetherleap\\Screenshots_NHS\\Capture1.jpg"));
		// Click on the "SUBMIT button" for NHS login page
		WebElement element = driver.findElement(By.xpath("//*[@class='btn btn-lg btn-primary btn-block btn-signin']"));
		element.click();
		Thread.sleep(5000);
		File src2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Files.copy(src2, new File("P:\\togetherleap\\Screenshots_NHS\\Capture2.jpg"));
		// Stopping the screen for 5 seconds
		Thread.sleep(2000);
		// Kill the browser
		System.out.println("Login Successful");

	}

	
	@Test
	public void testLogin() throws InterruptedException, IOException {
		NHS_Login obj = new NHS_Login();
		// Test case:2 - Verify with registered username & password
		obj.login("tester3", "tester3");
		List<WebElement> elements = driver.findElements(By.id("inputEmail"));
		//System.out.println("elements: " + elements.size());
		assertEquals(0, elements.size());

		// Test case:3 - Verify with registered username & invalid password
		//obj.login("tester3", "test@#$");
		 //elements = driver.findElements(By.id("inputEmail"));
		//assertEquals(1, elements.size());

		// test case:4 - Verify with invalid username & invalid password (Unregistered)
		// obj.login("sdfg&", "34rf6y");

	}
	
	
	@Test
	public void testLogin2() throws InterruptedException, IOException {
		NHS_Login obj1 = new NHS_Login();
		// Test case:3 - Verify with registered username & invalid password
		obj1.login("tester3", "test@#$");
		List<WebElement> elements = driver.findElements(By.id("inputEmail"));
		assertEquals(0, elements.size());

		// test case:4 - Verify with invalid username & invalid password (Unregistered)
		// obj.login("sdfg&", "34rf6y");

	}

	@After
	public void closeBrowser() throws InterruptedException {
		// finalize method to destroy any object or resources like browser
		System.out.println("In finalize method");
		Thread.sleep(1000);
		driver.quit();

	}

}
