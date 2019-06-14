package nhs.ui;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.google.common.io.Files;

public class AddPatient {
	// Global variable
	static WebDriver driver;

	@Before
	public void startBrowser() throws MalformedURLException {
		// for initializations or keeping the system ready-Testing application on local machine
		//System.setProperty("webdriver.chrome.driver", "P:\\Selenium\\chromedriver.exe");
		//driver = new ChromeDriver();
		//Testing application on remote server
		DesiredCapabilities capability = DesiredCapabilities.chrome();
		driver = new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), capability);
		driver.get("http://hms-qa.herokuapp.com/");
		driver.manage().window().maximize();
	}

	public void testAddPatient(String firstName, String LastName, String hosiptal_Number, String dateofbirth,
			String sex) throws IOException, InterruptedException {
		// for initializations or keeping the system ready
		String email = "tester3";
		String pwd = "tester3";
		driver.findElement(By.id("inputEmail")).sendKeys(email);
		driver.findElement(By.id("inputPassword")).sendKeys(pwd);
		//Clicking Sign-in button
		WebElement element = driver.findElement(By.xpath("//*[@class='btn btn-lg btn-primary btn-block btn-signin']"));
		element.click();
		Thread.sleep(1000);
		//Filling first_name,Last_name,hosiptal_number,date_of_birth,sex 
		driver.findElement(By.xpath("//*[@id=\"side-menu\"]/li[2]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"first-name\"]")).sendKeys(firstName);
		driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/form/div[2]/div/input")).sendKeys(LastName);
		driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/form/div[3]/div/input"))
				.sendKeys(hosiptal_Number);
		driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/form/div[4]/div/input"))
				.sendKeys(dateofbirth);
		if (sex == "male") {
			WebElement male = driver
					.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/form/div[5]/div/div/label[1]"));
			male.click();
		} else {
			WebElement female = driver
					.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/form/div[5]/div/div/label[2]"));
			female.click();
		}
		// Selecting diseases in Add patient table
		driver.findElement(By.xpath("//*[@id=\"add-new-patient\"]/tbody/tr[3]/td[3]/input")).click();
		driver.findElement(By.xpath("//*[@id=\"add-new-patient\"]/tbody/tr[4]/td[3]/input")).click();
		Thread.sleep(2000);
		// clicking Add patient button
		driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/form/input")).click();
		// Going back to dashboard
		driver.findElement(By.xpath("//*[@id=\"side-menu\"]/li[1]/a")).click();
		Thread.sleep(3000);
		//File src1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// Now copy the screenshot to the desired location using copyfile method
		//Files.copy(src1, new File("P:\\togetherleap\\Screenshots_NHS\\Table.jpg"));
		findPatientWaiting(firstName);

	}

	public void findPatientWaiting(String firstName) {
		WebElement waitingTable = driver.findElement(By.xpath("//*[@id=\"patients-waiting\"]"));
		// Get all rows
		List<WebElement> rows = waitingTable.findElements(By.tagName("tr"));
		// assertEquals(3, rows.size());
		System.out.println("rows.size():" + rows.size());
		// Print data from each row
		for (WebElement row : rows) {
			List<WebElement> cols = row.findElements(By.tagName("td"));
			for (WebElement col : cols) {
				System.out.print(col.getText() + "\t");
				if (col.getText().contains(firstName)) {
					assertTrue(true);
					System.out.println("Patient found");
					return;
				}
			}
			System.out.println();
		}
		System.out.println("Patient not found");
		assertTrue(false);
	}

	@Test
	public void testAddPatient1() throws IOException, InterruptedException {
		testAddPatient("Lussi", "Kite", "HMS2345", "09/2/2016", "female");
		Thread.sleep(3000);
		//File src2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		//Files.copy(src2, new File("P:\\togetherleap\\Screenshots_NHS\\Table2.jpg"));

	}

	@After
	public void closeBrowser() {
		driver.quit();
	}

}
