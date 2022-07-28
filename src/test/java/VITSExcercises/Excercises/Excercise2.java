package VITSExcercises.Excercises;

import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Excercise2 {
	WebDriver driver;
	ReadDetails read;

	@BeforeClass
	public void launch() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\janan\\eclipse-workspace\\Excercises\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(ReadDetails.getProperty("fbPage2"));
		System.out.println("Page Loaded : Facebook");
		driver.manage().window().maximize();
		/*
		 * try { Thread.sleep(3000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		waitCall(By.linkText(ReadDetails.getProperty("createLinkText")));

	}

	@Test
	public void checkTitle() {
		Assert.assertEquals(driver.getCurrentUrl(), ReadDetails.getProperty("URL2"));
	}

	@Test(dependsOnMethods = { "checkTitle" })
	public void createAccount() throws InterruptedException {
		// Create new account
		Assert.assertTrue(driver.findElement(By.linkText(ReadDetails.getProperty("createLinkText"))).isDisplayed(),
				"Create an Account Section Not Available!!!");
		if (driver.findElement(By.linkText(ReadDetails.getProperty("createLinkText"))).isDisplayed()) {
			System.out.println("Link visible : Create new account");
			driver.findElement(By.linkText(ReadDetails.getProperty("createLinkText"))).click();

			// Thread.sleep(3000);
			System.out.println("check create load");
			waitCall(By.name(ReadDetails.getProperty("btn_signUp").split(":")[1].trim()));
			read = new ReadDetails(driver);
			read.getElementInfo("firstName").sendKeys("Ann");
			read.getElementInfo("lastName").sendKeys("Ben");
			read.getElementInfo("mobileEmail").sendKeys("ben123@gmail.com");
			read.getElementInfo("retypeEmail").sendKeys("ben123@gmail.com");
			read.getElementInfo("password").sendKeys("abcxyz123");
			Select month = new Select(read.getElementInfo("select_month"));
			Select day = new Select(read.getElementInfo("select_day"));
			Select year = new Select(read.getElementInfo("select_year"));
			month.selectByValue("1");
			day.selectByValue("3");
			year.selectByValue("2000");
			List<WebElement> gender = driver.findElements(By.name("sex"));
			gender.get(0).click();
			read.getElementInfo("btn_signUp").click();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			System.out.println("check invisible of create load");
			try {
				wait.until(ExpectedConditions.urlContains("https://www.facebook.com/recover/"));

				 if (driver.getCurrentUrl().contains("https://www.facebook.com/recover/"))
				 System.out.println("Facebook Account Created!!");
				Assert.assertTrue(driver.getCurrentUrl().contains("https://www.facebook.com/recover/"),
						"Facebook Account Not Created!!");

			} catch (TimeoutException e) {
				// System.out.println("Exception : " + e.toString());
				if (read.getElementInfo("errorOut").isDisplayed()) {
					String errorMsg = read.getElementInfo("errorText").getText();
					System.out.println("Error Message in Page : " + errorMsg);
				}
				Assert.assertTrue(read.getElementInfo("errorOut").isDisplayed(), "Page Error not displayed!!");
			}

		}
	}

	public void waitCall(By byElement) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));
		}
		// catch(NoSuchElementException e) {
		catch (Exception e) {
			System.out.println("Exception in wait : " + e.toString());
		}
	}

	@AfterClass
	public void afterTest() {
		 driver.close();
	}

}
