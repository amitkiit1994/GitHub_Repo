package org.ridecell.assignment.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * Hello world!
 *
 */
public class TestBase

{
	public static Properties prop;
	public static WebDriver driver;


	public TestBase() {
		// TODO Auto-generated constructor stub
		try {
			prop = new Properties();
			String propertiesFilePath = System.getProperty("user.dir")
					+ "\\src\\main\\java\\org\\ridecell\\assignment\\resources\\config.properties";
			FileInputStream fis = new FileInputStream(propertiesFilePath);
			;
			prop.load(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public WebDriver initializeSeleniumWebDriver() {
		String browserName = prop.getProperty("BROWSER");
		String chromeDriverPath = System.getProperty("user.dir")
				+ "/src/main/java/org/ridecell/assignment/resources/chromedriver.exe";
		if (browserName.contains("chrome")) {
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			driver = new ChromeDriver();

		} else if (browserName.contains("firefox")) {
			driver = new FirefoxDriver();

		} else if (browserName.equalsIgnoreCase("internetexplorer") || browserName.equalsIgnoreCase("ie")) {
			driver = new InternetExplorerDriver();
		}
		// Setting a minimum implicit time period before an exception is thrown
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

}
