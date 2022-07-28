package VITSExcercises.Excercises;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Excercise7 {
	WebDriver driver;
	ReadDetails read;
	String dataFile = "src\\test\\java\\dataValue.properties";
	
	@Parameters({ "browser" })
	@Test
	public void selectBrowser(String browser) {
		browser = "firefox";
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
	public void Launch() {
		driver.manage().window().maximize();
		driver.get(ReadDetails.getProperty("gmail"));
	}
	
	@Test (dependsOnMethods = {"Launch"})
	public void GmailLogin() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("identifierNext")));
		read = new ReadDetails(driver);
		read.getElementInfo("emailID").sendKeys(ReadDetails.getProperty("gmailID", dataFile));
		read.getElementInfo("next").click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("passwordNext")));
		//Thread.sleep(3000);
		read.getElementInfo("password").sendKeys(ReadDetails.getProperty("gmailPassword", dataFile));
		read.getElementInfo("next2").click();
		
	}
	
	@Test (dependsOnMethods= {"GmailLogin"})
	public void composeMail() throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Compose']")));
		read.getElementInfo("compose").click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='GS']//td[@class='eV']//textarea[@aria-label='To']")));
		read.getElementInfo("composeTo").sendKeys(ReadDetails.getProperty("toMailID", dataFile));
		read.getElementInfo("composeSubject").sendKeys(ReadDetails.getProperty("toSubject", dataFile));
		read.getElementInfo("composeBody").sendKeys(ReadDetails.getProperty("toMessageBody", dataFile));
		read.getElementInfo("composeSend").click();
	}
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
