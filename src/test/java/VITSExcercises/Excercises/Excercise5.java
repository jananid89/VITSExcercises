package VITSExcercises.Excercises;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

public class Excercise5 {
	WebDriver driver;
	ReadDetails read;
	WebDriverWait wait;
	String dataFile = "src\\test\\java\\dataValue.properties";
	
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
	public void vitsHR() {
		driver.get(ReadDetails.getProperty("vitsPage"));
		driver.manage().window().maximize();
		// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		// wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		// wait.until(ExpectedConditions.visibilityOf(read.getElementInfo("Register")));
	}

	@Test(dependsOnMethods = { "vitsHR" })
	public void registration() throws InterruptedException {
		
		read = new ReadDetails(driver);
		read.getElementInfo("Register").click();
		waitCall(read.getElementInfo("companyName"));
		read.getElementInfo("companyName").sendKeys(ReadDetails.getProperty("companyName", dataFile));
		read.getElementInfo("companyWebsite").sendKeys(ReadDetails.getProperty("companyWebsite", dataFile));
		read.getElementInfo("phNumber").sendKeys(ReadDetails.getProperty("phNumber", dataFile));
		// address code
		read.getElementInfo("companyAddr").click();
		Thread.sleep(3000);
	//	waitCall(read.getElementInfo("location"));
		read.getElementInfo("location").sendKeys(ReadDetails.getProperty("companyAddr", dataFile));
	//	waitCall(read.getElementInfo("itemLocation"));
		Thread.sleep(2000);
		WebElement ele = read.getElementInfo("itemLocation");
		Actions act = new Actions(driver);
		if (ele != null) {
			act.moveToElement(ele).click().build().perform();
		}
		Thread.sleep(2000);
		//waitCall(read.getElementInfo("addrDone"));
		read.getElementInfo("addrDone").click();
		waitCall(read.getElementInfo("otherNo"));
		read.getElementInfo("otherNo").sendKeys(ReadDetails.getProperty("otherNo", dataFile));
		read.getElementInfo("zipCode").sendKeys(ReadDetails.getProperty("zipCode", dataFile));
		read.getElementInfo("fedEmpID").sendKeys(ReadDetails.getProperty("fedEmpID", dataFile));
		read.getElementInfo("empID").sendKeys(ReadDetails.getProperty("empID", dataFile));
		read.getElementInfo("email").sendKeys(ReadDetails.getProperty("email", dataFile));
		read.getElementInfo("loginID").sendKeys(ReadDetails.getProperty("loginID", dataFile));
		read.getElementInfo("password").sendKeys(ReadDetails.getProperty("password", dataFile));
		read.getElementInfo("signUp").click();
		Thread.sleep(3000);
//		waitCall(read.getElementInfo("signUpMsg"));
	//	if (read.getElementInfo("signUpMsg") != null) {
		//	System.out.println("Message : " + read.getElementInfo("signUpMsg").getText());
		//}	
	}

	@Test(dependsOnMethods = { "registration" })
	public void login() {
		driver.navigate().to(ReadDetails.getProperty("vitsPage"));
		waitCall(read.getElementInfo("HomeLogin"));
		read.getElementInfo("HomeLogin").click();
		waitCall(read.getElementInfo("userName"));
		read.getElementInfo("userName").sendKeys(ReadDetails.getProperty("loginID", dataFile));
		read.getElementInfo("userPassword").sendKeys(ReadDetails.getProperty("password", dataFile));
		read.getElementInfo("signIN").click();
	//	waitCall(read.getElementInfo("errorMsg"));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String errTxt = read.getElementInfo("errorMsg").getText();
		System.out.println("Error Message : " + errTxt);
		
	}

	public void waitCall(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
		}
		// catch(NoSuchElementException e) {
		catch (Exception e) {
			System.out.println("Exception in wait : " + e.toString());
		}
	}

	@BeforeClass
	public void beforeClass() {
	}

	@AfterClass
	public void afterClass() {
		driver.close();
	}

}
