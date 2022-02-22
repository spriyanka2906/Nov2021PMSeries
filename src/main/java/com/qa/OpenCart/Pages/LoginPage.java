package com.qa.OpenCart.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.OpenCart.utils.Constants;
import com.qa.OpenCart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//1. private By Locator
	private By emailId=By.id("input-email");
	private By password=By.id("input-password");
	private By loginbtn=By.xpath("//input[@value='Login']");
	private By forgotPwdLink=By.linkText("Forgotten Password");
	private By registerLink=By.linkText("Register");
	
	//2. Page Constructor
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	//3. public Page Actions/Methods:
	@Step("getting the login page title")
	public String getLoginPageTitle() {
		return eleUtil.doGetPageTitleIs(Constants.LOGIN_PAGE_TITLE, Constants.DEFAULT_TIME_OUT);
	}
	
	@Step("getting the login page url")
	public String getLoginPageUrl() {
		return eleUtil.waitForUrlContains(Constants.LOGIN_PAGE_URL_FRACTION, Constants.DEFAULT_TIME_OUT);
	}
	
	@Step("Check frgt pwd link displayed or not...")
	public boolean isForgotPasswordLinkExist() {
		return driver.findElement(forgotPwdLink).isDisplayed();
	}
	
	//This method is responsible for returning the next landing page
	@Step("Login with username : {0} and password : {1}")
	public AccountsPage doLogin(String userName, String pwd) {
		eleUtil.doSendKeys(emailId,userName);
		eleUtil.doSendKeys(password,pwd);
		eleUtil.doClick(loginbtn);
		return new AccountsPage(driver);
	}
	
	@Step("Navigating to Register Page....")
	public RegisterPage goToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}
	
}
