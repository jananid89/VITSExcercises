package VITSExcercises.Excercises;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ReadDetails {
	WebDriver driver;

	public ReadDetails(WebDriver driver) {
		this.driver = driver;
	}

	public static String getProperty(String key) {
		String propValue = null;
		try {
			FileInputStream fis = new FileInputStream("src\\test\\java\\Locators.properties");
			Properties prop = new Properties();
			prop.load(fis);
			propValue = prop.getProperty(key);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propValue;
	}

	public static String getProperty(String key, String filePath) {
		String propValue = null;
		try {
			FileInputStream fis = new FileInputStream(filePath);
			Properties prop = new Properties();
			prop.load(fis);
			propValue = prop.getProperty(key);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propValue;
	}

	public WebElement getElementInfo(String key) {
		WebElement element = null;
		String elementInfo = null;
		String[] elementStr;
		elementInfo = getProperty(key);
		elementStr = elementInfo.split(":");
		switch (elementStr[0].trim().toLowerCase()) {
		case "id":
			element = driver.findElement(By.id(elementStr[1].trim()));
			break;
		case "name":
			element = driver.findElement(By.name(elementStr[1].trim()));
			break;
		case "tagname":
			element = driver.findElement(By.tagName(elementStr[1].trim()));
			break;
		case "xpath": {
			String xpath = "";
			xpath += elementStr[1].trim();
			if (elementStr.length > 2) {
				for (int i = 2; i < elementStr.length; i++) {
					if (!elementStr[i].trim().isBlank()) {
						xpath += "::";
						xpath += elementStr[i].trim();
					}
				}
			}
			element = driver.findElement(By.xpath(xpath));
			break;
		}
		case "cssselector":
			element = driver.findElement(By.cssSelector(elementStr[1].trim()));
			break;
		case "classname":
			element = driver.findElement(By.className(elementStr[1].trim()));
			break;
		case "linktext":
			element = driver.findElement(By.linkText(elementStr[1].trim()));
			break;
		case "partiallinktest":
			element = driver.findElement(By.partialLinkText(elementStr[1].trim()));
			break;
		default:
			System.out.println("Invalid keyword - Method : getElementInfo!!!");
		}

		return element;
	}

}
