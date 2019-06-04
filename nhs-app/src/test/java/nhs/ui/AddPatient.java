package nhs.ui;

import static org.junit.Assert.assertTrue;

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

public class AddPatient {
	// Global variable
	static WebDriver driver;

	@Before
	public void startBrowser() {
		// for initializations or keeping the system ready
		System.setProperty("webdriver.chrome.driver", "P:\\Selenium\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://hms-qa.herokuapp.com/");
		driver.manage().window().maximize();
	}

	@Test
	public void testAddPatient() throws IOException, InterruptedException {
		// for initializations or keeping the system ready
		String email = "tester3";
		String pwd = "tester3";
		driver.findElement(By.id("inputEmail")).sendKeys(email);
		driver.findElement(By.id("inputPassword")).sendKeys(pwd);
		WebElement element = driver.findElement(By.xpath("//*[@class='btn btn-lg btn-primary btn-block btn-signin']"));
		element.click();
		driver.findElement(By.xpath("//*[@id=\"side-menu\"]/li[2]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"first-name\"]")).sendKeys("abcd");
		driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/form/div[2]/div/input")).sendKeys("1234");
		driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/form/div[3]/div/input")).sendKeys("HMS234");
		driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/form/div[4]/div/input"))
				.sendKeys("05/11/2014");
		driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/form/div[5]/div/div/label[1]")).click();
		driver.findElement(By.xpath("//*[@id=\"add-new-patient\"]/tbody/tr[3]/td[3]/input")).click();
		driver.findElement(By.xpath("//*[@id=\"add-new-patient\"]/tbody/tr[4]/td[3]/input")).click();
		driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/form/input")).click();
		driver.findElement(By.xpath("//*[@id=\"side-menu\"]/li[1]/a")).click();
		Thread.sleep(3000);
		File src1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// Now copy the screenshot to the desired location using copyfile method
		Files.copy(src1, new File("P:\\togetherleap\\Screenshots_NHS\\Table.jpg"));
		patientWaiting();

	}

	public void patientWaiting() {
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
				if (col.getText().contains("Abcd")) {
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

	@After
	public void closeBrowser() {
		driver.quit();
	}

}
