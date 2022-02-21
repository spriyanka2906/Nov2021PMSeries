package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.OpenCart.listeners.AnnotationTransformer;
import com.qa.OpenCart.listeners.TestAllureListener;
import com.qa.OpenCart.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EPIC - 100 : Design Login page for Open Cart Application...")
@Story("US - 100 : Login page Features")
@Listeners({AnnotationTransformer.class, TestAllureListener.class})
public class LoginPageTest extends BaseTest {

	@Description("Login Page  title test")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageTitleTest() {
		String title=loginPage.getLoginPageTitle();
		System.out.println("Login Page Title is:" + title);
		Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE);
	}
	
	@Description("TC_01 : Login Page  url test")
	@Severity(SeverityLevel.NORMAL)
	@Test(enabled = false)
	public void loginPageURLTest() {
		String url=loginPage.getLoginPageUrl();
		System.out.println("Login Page URL is:" + url);
		Assert.assertTrue(url.contains(Constants.LOGIN_PAGE_URL_FRACTION));
	}
	
	@Description("This is testing forgot pwd link on Login Page")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void forgotPwdLinkTest() {
		Assert.assertTrue(loginPage.isForgotPasswordLinkExist());
	}
	
	@Description("Positive test case for login with correct credentials...")
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Login test with valid credentials")
	public void loginTest() {
		accPage=loginPage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim());
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
}

