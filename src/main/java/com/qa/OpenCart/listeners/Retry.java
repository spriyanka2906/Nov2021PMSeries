package com.qa.OpenCart.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry  implements IRetryAnalyzer{

	private int count = 0;
	private static int maxTry =3;
	
	
	@Override
	public boolean retry(ITestResult iTestResult) {
		
		if(!iTestResult.isSuccess()) { // Check if test is not succeed
			if(count < maxTry) {//Check if maxTry count is reached
				count++; //increase the maxTry count by1
				iTestResult.setStatus(ITestResult.FAILURE); //maxTry test is failed
				return true; // tells TestNG to re-run the test
			} else {
				iTestResult.setStatus(ITestResult.FAILURE); // If maxCount reached, test marked as failed
			}
		} else {
			iTestResult.setStatus(ITestResult.SUCCESS); // If test passes, TestNG marks it as passed

		}
		return false;
	}

	
}
