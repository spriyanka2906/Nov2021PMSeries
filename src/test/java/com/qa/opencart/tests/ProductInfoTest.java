package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.OpenCart.utils.Constants;

public class ProductInfoTest extends BaseTest{

	@BeforeClass
	public void productInfoSetUp() {
		accPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void productHeaderTest() {
		resultsPage=accPage.doSearch("MacBook");
		productInfoPage=resultsPage.selectProduct("MacBook Pro");
		Assert.assertEquals(productInfoPage.getProductHeaderName(), "MacBook Pro");
	}
	
	
	@DataProvider
	public Object[][] productData(){
		return new Object[][] {
			{"MacBook" , "MacBook Pro", Constants.MACBOOK_IMAGES_COUNT},
			{"MacBook" , "MacBook Air", Constants.MACBOOK_IMAGES_COUNT},
			{"iMac" , "iMac" , Constants.IMAC_IMAGES_COUNT},
		};
	}
	
	
	@Test(dataProvider="productData")
	public void productImageCountTest(String productName, String mainProductName, int imagesCount) {
		resultsPage=accPage.doSearch(productName);
		productInfoPage=resultsPage.selectProduct(mainProductName);
		int totalImages =productInfoPage.getProductImageCount();
		System.out.println("Total Images count for : " + mainProductName + ":" + imagesCount);
		Assert.assertEquals(totalImages, imagesCount);
	}
	
	
	
	
	@Test
	public void productDataTest() {
		resultsPage=accPage.doSearch("MacBook");
		productInfoPage=resultsPage.selectProduct("MacBook Pro");
		productInfoPage.getProductInfo();
		Map<String, String> actProductInfoMap=productInfoPage.getProductInfo();
		actProductInfoMap.forEach((k,v) -> System.out.println(k+":"+v));
		softAssert.assertEquals(actProductInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(actProductInfoMap.get("name"), "MacBook Pro");
		softAssert.assertEquals(actProductInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(actProductInfoMap.get("price"), "$2,000.00");
		softAssert.assertAll();
	}
	/**
	 * Output: productDataTest with HashMap<>
	 * Brand:Apple
	 * Availability:Out Of Stock
	 * totalimages:4
	 * price:Ex Tax: $2,000.00
	 * name:MacBook Pro
	 * Product Code:Product 18
	 * Reward Points:800
	 */
	
	//productDataMap Output with LinkedHashMap
//	name:MacBook Pro
//	totalimages:4
//	Brand:Apple
//	Product Code:Product 18
//	Reward Points:800
//	Availability:Out Of Stock
//	price:$2,000.00
//	ExTax price:Ex Tax: $2,000.00
	
	//productDataTest Output -- with TreeMap
//	Availability:Out Of Stock
//	Brand:Apple
//	ExTax price:Ex Tax: $2,000.00
//	Product Code:Product 18
//	Reward Points:800
//	name:MacBook Pro
//	price:$2,000.00
//	totalimages:4
	
	
	
}
