package com.qa.opencart.tests;

import java.util.Random;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.OpenCart.utils.Constants;
import com.qa.OpenCart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest{

	@BeforeClass
	public void regSetUp() {
		regPage=loginPage.goToRegisterPage();
	}
	
	public String getRandomNumber() {
		Random randomGen=new Random();
		String email ="testautomationNovBatch"+ randomGen.nextInt(1000) + "@gmail.com";
		return email;
	}
	
	@DataProvider
	public Object[][] getRegisterData() throws InvalidFormatException {
		Object regData[][]=ExcelUtil.getTestData(Constants.REGISTER_SHEET_NAME);
		return regData;
	}
	
	
	
	
	@Test(dataProvider="getRegisterData")
	public void userRegistrationTest(String firstName, String lastName, 
										String telephone,
										String password, String subscribe) {
		Assert.assertTrue(regPage.accountRegistration(firstName, lastName,getRandomNumber(),telephone, password, subscribe));
	}
	
	
	
	
	
}
