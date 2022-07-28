package VITSExcercises.Excercises;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Excercise6 {
	WebDriver driver;
	ReadDetails read;
	WebDriverWait wait;

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
	public void Launch() {
		driver.get(ReadDetails.getProperty("jqueryPage"));
		driver.manage().window().maximize();
	}

	@Test(dependsOnMethods = { "Launch" })
	public void DragandDrop() {
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("demo-frame")));
		driver.switchTo().frame(0);
		read = new ReadDetails(driver);
		WebElement drag, drop;
		drag = read.getElementInfo("drag");
		drop = read.getElementInfo("drop");
		Actions act = new Actions(driver);
		act.moveToElement(drag).dragAndDrop(drag, drop).build().perform();
	}

	@Test(dependsOnMethods = { "DragandDrop" })
	public void VerifyDrop() {
		WebElement drop = read.getElementInfo("drop");
		String cssClass = drop.getAttribute("class");
		Assert.assertTrue(cssClass.contains("ui-state-highlight"), "Droppable Box style not Changed!!");
		Assert.assertEquals(drop.getText(), ReadDetails.getProperty("droppedText"), "Text not changed to Dropped!");
		System.out.println("Droppable box text " + drop.getText());
		if (cssClass.contains("ui-state-highlight"))
			System.out.println("Droppable highlighted!!");
		else
			System.out.println("Droppable not highlighted!!");
	}
	
	@AfterClass
	public void TearDown() {
		driver.close();
	}
}
