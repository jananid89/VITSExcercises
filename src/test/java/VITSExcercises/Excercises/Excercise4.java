package VITSExcercises.Excercises;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

public class Excercise4 {
	WebDriver driver;
	ReadDetails read;

	@Parameters({ "browser" })
	@Test
	public void selectBrowser(String browser) {
		String propValue = ReadDetails.getProperty(browser);
		String[] details = propValue.split("::");
		System.setProperty(details[0].trim(), details[1].trim());
		switch (browser.toLowerCase().trim()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		default:
			driver = new ChromeDriver();
		}
	}

	@Test(dependsOnMethods = { "selectBrowser" })
	public void ebay() {
		driver.get(ReadDetails.getProperty("ebay"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}

	@Test(dependsOnMethods = { "ebay" })
	public void search() {
		read = new ReadDetails(driver);
		read.getElementInfo("searchText").sendKeys(ReadDetails.getProperty("search"));
		Select category = new Select(read.getElementInfo("categorySelect"));
		category.selectByVisibleText(ReadDetails.getProperty("category"));
		read.getElementInfo("submit").click();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("srp-controls__count-heading")));
			Thread.sleep(3000);
		}
		catch(Exception e) {}
	}

	@BeforeClass
	public void beforeClass() {
	}

	@AfterClass
	public void afterClass() {
		driver.close();
	}

}
