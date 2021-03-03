package testcases;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import Base.MyTwitData;
import Base.TestBase;
import utilities.TestUtil;

public class Tweet_Testcase extends TestBase {

	

	@Test()
	public void AddCustomer() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException
	{
		
		List<String> myListofnameshavingverifiedAccounts=new ArrayList<String>();
		
		System.out.println("Hello");
		int countIndex=0;
		for(int ii=0;ii<myListofTweetDataforUsers.size();ii++)
		{
	
			for(int i=0;i<myListofTweetDataforUsers.get(ii).size();i++)
			{
				MyTwitData myListOFTWEETDATA=myListofTweetDataforUsers.get(ii).get(i);
				driver.get("https://twitter.com/"+ArrayOfNames[countIndex]);
				
				WebDriverWait wait = new WebDriverWait(driver,20);
				WebElement web1;
				web1= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Tweets') and @dir='auto' and @class ='css-901oao css-bfa6kz r-m0bqgq r-1qd0xha r-n6v787 r-16dba41 r-1sf4r6n r-bcqeeo r-qvutc0']")));
				captureScreenshot(ArrayOfNames[i]);
				
			
				String RetweetinCoolFormat;
				String FavcountCoolFormat;
				String text=myListOFTWEETDATA.text;
				driver.findElement(By.xpath(OR.getProperty("Search_Box"))).sendKeys(text);
				driver.findElement(By.xpath(OR.getProperty("Search_Box"))).sendKeys(Keys.ENTER);
				captureScreenshot(myListOFTWEETDATA.id);
				Long retweet = Long.parseLong(myListOFTWEETDATA.retweetCount);
				if(retweet>1000)
				{
				 RetweetinCoolFormat = coolFormat(retweet,i);
				}
				else
				{
					RetweetinCoolFormat=retweet.toString();
				}
				
				Long Favcount = Long.parseLong(myListOFTWEETDATA.favCount);
				
				if(Favcount>1000)
				{
					FavcountCoolFormat = coolFormat(Favcount,i);
				}
				else
				{
					FavcountCoolFormat=Favcount.toString();
				}
				
				String xpathRetweet="//span[ text()='"+RetweetinCoolFormat+"']";
				String xpathfollower="//span[ text()='"+FavcountCoolFormat+"']";
				
				if(!driver.findElement(By.xpath(xpathRetweet)).isDisplayed())
				{
					System.out.println(myListOFTWEETDATA.retweetCount);
					//Assert.fail("Retweetcount not matching");
				}
				if(!driver.findElement(By.xpath(xpathfollower)).isDisplayed())
				{
					System.out.println(myListOFTWEETDATA.retweetCount);
					
				}
				Assert.assertTrue(driver.findElement(By.xpath(xpathRetweet)).isDisplayed());
				Assert.assertTrue(driver.findElement(By.xpath(xpathfollower)).isDisplayed());
			}
			countIndex++;
		}
		
		
		driver.get("https://twitter.com/"+myListofFoloowers_Data.get(0).ScreenName);
		driver.findElement(By.xpath("//span[text()='Following']")).click();
		myListofnameshavingverifiedAccounts=getFollowerDataVerifiedAccountList(myListofFoloowers_Data.get(0).ScreenName);
		for(int i=0; i<myListofnameshavingverifiedAccounts.size();i++)
		{
			System.out.println(myListofnameshavingverifiedAccounts.get(i));
		}
		
	}
	
	private static char[] c = new char[]{'k', 'm', 'b', 't'};

	/**
	 * Recursive implementation, invokes itself for each factor of a thousand, increasing the class on each invokation.
	 * @param n the number to format
	 * @param iteration in fact this is the class from the array c
	 * @return a String representing the number n formatted in a cool looking way.
	 */
	private static String coolFormat(double n, int iteration) {
	    double d = ((long) n / 100) / 10.0;
	    boolean isRound = (d * 10) %10 == 0;//true if the decimal part is equal to 0 (then it's trimmed anyway)
	    return (d < 1000? //this determines the class, i.e. 'k', 'm' etc
	        ((d > 99.9 || isRound || (!isRound && d > 9.99)? //this decides whether to trim the decimals
	         (int) d * 10 / 10 : d + "" // (int) d * 10 / 10 drops the decimal
	         ) + "" + c[iteration]) 
	        : coolFormat(d, iteration+1));

	}
	
	
	
	
	
	
	
	
	public static void captureScreenshot(String Name) {

		
		String screenshotName="";
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		screenshotName = Name + ".jpg";
		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\reports\\" + screenshotName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}
	
	
	
	
	
	
}
