package com.qa.OpenCart.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.OpenCart.utils.Constants;
import com.qa.OpenCart.utils.ElementUtil;

public class RegisterPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By firstName=By.id("input-firstname");
	private By lastName=By.id("input-lastname");
	private By email=By.id("input-email");
	private By telephone=By.id("input-telephone");
	private By password=By.id("input-password");
	private By confirmPassword=By.id("input-confirm");
	
	private By subscribeYes=By.xpath("//*[@id=\"content\"]/form/fieldset[3]/div/div/label[1]/input");
	private By subscribeNo=By.xpath("//*[@id=\"content\"]/form/fieldset[3]/div/div/label[2]/input");
	
	private By agreeCheckBox=By.xpath("//*[@name=\"agree\"]");
	private By continueButton=By.cssSelector("#content > form > div > div > input.btn.btn-primary");
	private By successMessage=By.xpath("//*[@id=\"content\"]/h1");
	
	private By logoutLink=By.xpath("//*[@id=\"column-right\"]/div/a[13]");
	private By registerLink=By.xpath("//*[@id=\"column-right\"]/div/a[2]");
	
	public RegisterPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	public boolean accountRegistration(String firstName, String lastName, String email, String telephone, String password, String subscribe) {
		eleUtil.doSendKeys(this.firstName, firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);
		
		if(subscribe.equalsIgnoreCase("Yes")) {
			eleUtil.doClick(subscribeYes);
		} else {
			eleUtil.doClick(subscribeNo);
		}
		
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueButton);
		
		String successMsg=eleUtil.doGetText(successMessage);
		System.out.println(successMsg);
		
		if(successMsg.contains(Constants.REGISTER_SUCCESS_MESSAGE)) {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;
		}
		return false;
	}
	
	
}
