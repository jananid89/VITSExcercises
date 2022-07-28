package VITSExcercises.Excercises;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Excercise1 {
	WebDriver driver;

	@BeforeClass
	public void launch() {
		System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\janan\\eclipse-workspace\\Excercises\\Drivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.get(ReadDetails.getProperty("vitsPage"));
		System.out.println("Page Loaded : VITSHR");
		driver.manage().window().maximize();
	}

	@Test
	public void checkTitle() {
		Assert.assertEquals(driver.getTitle(), ReadDetails.getProperty("title"));
	}

	@Test(dependsOnMethods = { "checkTitle" })
	public void navigationTest() throws InterruptedException {
		driver.navigate().to(ReadDetails.getProperty("fbPage1"));
		waitCall(By.id("email"));
		System.out.println("Page Loaded : Facebook");
		driver.navigate().back();
		System.out.println("Page Loaded : VITSHR (Back)");
		waitCall(By.linkText("Login"));
		String url = driver.getCurrentUrl();
		System.out.println("URL of Current Page : " + url);
		driver.navigate().forward();
		System.out.println("Page Loaded : Facebook (Forward)");
		waitCall(By.id("email"));
		driver.navigate().refresh();
		System.out.println("Page Loaded : Facebook (Refresh)");
	}
	public void waitCall(By byElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));
	}

	

	@AfterClass
	public void tearDown() {
		// driver.close();
		driver.close();

	}

}
