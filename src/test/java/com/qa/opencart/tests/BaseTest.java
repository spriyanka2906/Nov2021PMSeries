package com.qa.opencart.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.OpenCart.Pages.AccountsPage;
import com.qa.OpenCart.Pages.CartPage;
import com.qa.OpenCart.Pages.LoginPage;
import com.qa.OpenCart.Pages.ProductInfoPage;
import com.qa.OpenCart.Pages.RegisterPage;
import com.qa.OpenCart.Pages.ResultsPage;
import com.qa.OpenCart.driverFactory.DriverFactory;


public class BaseTest 
{
	WebDriver driver;
	DriverFactory df;
	Properties prop;
	
	LoginPage loginPage;
	AccountsPage accPage;
	RegisterPage regPage;
	ResultsPage resultsPage;
	ProductInfoPage productInfoPage;
	CartPage cartPage;
	
	SoftAssert softAssert;
	
   @BeforeTest
   public void setUp() {
	   df=new DriverFactory();
	   prop=df.init_prop();
	  driver= df.init_driver(prop);
	  loginPage=new LoginPage(driver);
	  softAssert=new SoftAssert();
   }
	
   @AfterTest
   public void tearDown() {
	   driver.quit();
   }
	
}
