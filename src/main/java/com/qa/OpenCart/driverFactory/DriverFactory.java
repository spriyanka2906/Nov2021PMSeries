package com.qa.OpenCart.driverFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;

	public static String highlight;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal();

	/**
	 * This method is used to initialize the driver using browser name
	 * 
	 * @param browserName
	 * @return This method returns WebDriver
	 */
	public WebDriver init_driver(Properties prop) {

		String browserName = prop.getProperty("browser").trim();
		String browserVersion = prop.getProperty("browserversion").trim();

		highlight = prop.getProperty("highlight");
		System.out.println("Browser name: " + browserName);
		System.out.println("Browser Version: " + browserVersion);

		optionsManager = new OptionsManager(prop);

		if (browserName.equalsIgnoreCase("chrome")) {

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// remote execution
				init_remoteDriver("chrome");
			} else {
				// local execution
				WebDriverManager.chromedriver().setup();
				// driver=new ChromeDriver(optionsManager.getChromeOptions());
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
		} else if (browserName.equalsIgnoreCase("firefox")) {

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// remote execution
				init_remoteDriver("firefox");
			} else {
				// local execution
				WebDriverManager.firefoxdriver().setup();
				// driver=new FirefoxDriver(optionsManager.getFirefoxOptions());
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}
		} else if (browserName.equalsIgnoreCase("safari")) {
			// driver=new SafariDriver();
			tlDriver.set(new SafariDriver());
		} else {
			System.out.println("Please pass the right browser: " + browserName);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url").trim());

		return getDriver();
	}

	/**
	 * Run test cases on Remote Machine
	 * 
	 * @param browser
	 */
	private void init_remoteDriver(String browser) {

		System.out.println("Running test cases on remote grid server: " + browser);

		if (browser.equals("chrome")) {
			try {
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else if (browser.equals("firefox")) {
			try {
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * This will return ThreadLocal copy of the driver
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * This method is used to initialize the properties
	 * 
	 * @return This return Properties class reference
	 */
	public Properties init_prop() {

		prop = new Properties();
		FileInputStream ip = null;

		// mvn clean install -Denv="qa"
		String envName = System.getProperty("env"); // qa/dev/stage

		if (envName == null) {
			System.out.println("Running tests on PROD....");
			try {
				ip = new FileInputStream(".\\src\\test\\resources\\config\\config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Running tests on environment: " + envName);
			try {
				switch (envName.toLowerCase()) {
				case "qa":
					ip = new FileInputStream(".\\src\\test\\resources\\config\\qa.config.properties");
					break;

				case "dev":
					ip = new FileInputStream(".\\src\\test\\resources\\config\\dev.config.properties");
					break;

				case "stage":
					ip = new FileInputStream(".\\src\\test\\resources\\config\\stage.config.properties");
					break;

				default:
					System.out.println("Please pass the right environment");
					break;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				prop.load(ip);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return prop;
	}

	// ThreadLocal--- JDK 8---> create a local copy of driver
	// set driver with ThreadLocal
	// getDriver() --- driver

	// this solves--- driver null problem
	// u can take ur driver copy anywhere in ur framework
	// better thread mgmt
	// avoid the dead lock condition --- ThreadLocal driver copy
	// large test cases count --- 100, 300 TCs ---> proper test results.

	/**
	 * Take a Screenshot
	 */
	public String getScreenshot() {

		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}
}
