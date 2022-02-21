package com.qa.OpenCart.Pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.OpenCart.utils.Constants;
import com.qa.OpenCart.utils.ElementUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By header=By.cssSelector("div#logo a");
	private By sections=By.cssSelector("div#content h2");
	private By logoutLink=By.linkText("Logout");
	private By search=By.name("search");
	private By searchIcon=By.cssSelector("div#search button");
	
	public AccountsPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	public String getAccountsPageTitle() {
		return eleUtil.doGetPageTitleIs(Constants.ACCOUNTS_PAGE_TITLE, Constants.DEFAULT_TIME_OUT);
	}
	
	public String getAccountsPageUrl() {
		return eleUtil.waitForUrlContains(Constants.ACCOUNTS_PAGE_URL_FRACTION, Constants.DEFAULT_TIME_OUT);
	}
	
	public String getAccountsPageHeader() {
		return eleUtil.doGetText(header);
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.DoIsDispalyed(logoutLink);
	}
	
	public boolean logout() {
		if(isLogoutLinkExist()) {
			eleUtil.doClick(logoutLink);
			return true;
		} 
		return false;	
	}
	
	public List<String> getAccountsPageSections() {
		List<WebElement> sectionsList=eleUtil.waitForElementsVisible(sections, 10);
		List<String> secValueList=new ArrayList<String>();
		for(WebElement e: sectionsList) {
			String val=e.getText();
			secValueList.add(val);
		}
		return secValueList;
	}
	
	public boolean searchExist() {
		return eleUtil.DoIsDispalyed(search);
	}
	
	public ResultsPage doSearch(String productName) {
		if(searchExist()) {
			eleUtil.doSendKeys(search, productName);
			eleUtil.doClick(searchIcon);
			
		}
		return new ResultsPage(driver);
	}
	
}
