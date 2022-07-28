package VITSExcercises.Excercises;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;

public class Excercise3 {
	WebDriver driver;

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
	public void flipkart() {
		driver.get(ReadDetails.getProperty("flipkart"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}

	@Test(dependsOnMethods = { "selectBrowser", "flipkart" })
	public void noOfLinksHomePage() {
		List<WebElement> links = driver.findElements(By.tagName("a"));
		int count = links.size();
		System.out.println("Count of Links : " + count);
	}

	@Test(dependsOnMethods = { "selectBrowser", "flipkart" })
	public void printLinksURLsHomePage() {
		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println("Printing links and URLs : ");
		int i = 1;
		for (WebElement link : links) {
			System.out.println(i++ + " : " + link.getText() + " - " + link.getAttribute("href"));
		}

	}

	@BeforeTest
	public void beforeTest() {
	}

	@AfterClass
	public void afterTest() {
		driver.close();
	}

}
