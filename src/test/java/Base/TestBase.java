package Base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utilities.EncodeDecode;
import utilities.TestUtil;

public class TestBase {

	
	// All initialising happens here. Common starting point for all test cases.
	
	
	public static String[] ArrayOfNames;
	public static List<List<MyTwitData>> myListofTweetDataforUsers=new ArrayList<List<MyTwitData>>();;
	public static List<List<FollowerData>> myListofFollowerData=new ArrayList<List<FollowerData>>();
	public static WebDriver driver;
	public static List<MyTwitData> myTOPtweets;
	public static List<FollowerData> myListofFoloowers_Data;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static String accessToken ;
	//public static Logger log = Logger.getLogger("devpinoyLogger");
	public static FileInputStream fi_OR;
	//public POIHelper myPOIHelper=null;
	public static WebDriverWait wait;
	
	public static JsonArray myJsonArray;

	
	public static List<MyTwitData> GetTop10Tweets(String Name) throws ParseException
	{
		
		TweetSorter tweetSorter;
		
		
		ArrayList<MyTwitData> myListOfTweets_century=new ArrayList<MyTwitData>();
		String BasicAuthentication = EncodeDecode.ReturnBasicAuth();
		Response respWithAccessToken = RestAssured.given()
				.formParam("User-Agent", "My Twitter App v1.0.23")
				.formParam("grant_type", "client_credentials")
				.formParam("Accept-Encoding", "gzip")
				.headers("Content-Type","application/x-www-form-urlencoded;charset=UTF-8")
				.headers( "Authorization", BasicAuthentication)
				.post("https://api.twitter.com/oauth2/token");
		
		accessToken="Bearer "+respWithAccessToken.jsonPath().get("access_token").toString();
	
		Response resp1 = RestAssured.given()
				.formParam("screen_name", Name)
				.formParam("count",100)
				.headers("Authorization", accessToken)
				.auth()
				.oauth2(respWithAccessToken.jsonPath().get("access_token").toString())
				.get("https://api.twitter.com/1.1/statuses/user_timeline.json");
		

		String myResponse = resp1.asPrettyString();
		System.out.println(myResponse);

		JSONParser parser = new JSONParser();
		JSONArray array = (JSONArray)parser.parse(myResponse);
		//ArrayList<JSONObject> myArrayLISTofObjects = mySortedJsonArray(array);
		JsonPath jsonPathEvaluator = resp1.jsonPath();
		for(int i=0;i<array.size();i++)
		{
			
			
			String TextVAL=((JSONObject)array.get(i)).get("text").toString();
			if(TextVAL.contains("@"))
			{
				continue;
			}
			MyTwitData myTWEETData = new MyTwitData();
			myTWEETData.id=((JSONObject)array.get(i)).get("id").toString();
			myTWEETData.name=jsonPathEvaluator.get("[" + i + "].user.name");
			myTWEETData.retweetCount=((JSONObject)array.get(i)).get("retweet_count").toString();
			myTWEETData.text=TextVAL;
			myTWEETData.favCount=((JSONObject)array.get(i)).get("favorite_count").toString();
			myTWEETData.CreatedAt=((JSONObject)array.get(i)).get("created_at").toString();
			myListOfTweets_century.add(myTWEETData);
		}
		List<MyTwitData> SortedListFinal=null;
		tweetSorter=new TweetSorter(myListOfTweets_century); 
		ArrayList<MyTwitData> Sorted_myListOfTweets_century=tweetSorter.getSortedJobCandidateByRetweet();
		if(Sorted_myListOfTweets_century.size()<10)
		{
			SortedListFinal = Sorted_myListOfTweets_century.subList(0, Sorted_myListOfTweets_century.size()-1);
		}
		else
		{
		SortedListFinal = Sorted_myListOfTweets_century.subList(0, 10);
		}
		for(int i=0; i<SortedListFinal.size();i++)
		{
			System.out.println(SortedListFinal.get(i).retweetCount);
		}
		return SortedListFinal;
	}
	
	public static List<FollowerData> getFollowerData(String Name) throws ParseException, JsonMappingException, JsonProcessingException
	{
		
		ArrayList<FollowerData> myListofFollowers=new ArrayList<FollowerData>();
		FollowerSorter myFollowerSorter;
		
		
		String BasicAuthentication = EncodeDecode.ReturnBasicAuth();
		Response respWithAccessToken = RestAssured.given()
				.formParam("User-Agent", "My Twitter App v1.0.23")
				.formParam("grant_type", "client_credentials")
				.formParam("Accept-Encoding", "gzip")
				.headers("Content-Type","application/x-www-form-urlencoded;charset=UTF-8")
				.headers( "Authorization", BasicAuthentication)
				.post("https://api.twitter.com/oauth2/token");
		
		
		Response resp1 = RestAssured.given()
				.formParam("screen_name", Name)
				.formParam("count",100)
				.headers("Authorization", accessToken)
				.auth()
				.oauth2(respWithAccessToken.jsonPath().get("access_token").toString())
				.get("https://api.twitter.com/1.1/friends/list.json");
		
		
		
		
		String myResponseFriends=resp1.asPrettyString();
		System.out.println(myResponseFriends);
		JsonPath jsonPathEvaluator = resp1.jsonPath();
		
		for(int i=0;i<100;i++)
		{
			if(jsonPathEvaluator.get("users[" + i + "].screen_name").toString().length()==0)
			{
				break;
			}
			
			FollowerData myFollowerData = new FollowerData();
			myFollowerData.ScreenName=jsonPathEvaluator.get("users[" + i + "].screen_name").toString();
			myFollowerData.FriendCount=jsonPathEvaluator.get("users[" + i + "].friends_count").toString();
			myFollowerData.FollowerCount=jsonPathEvaluator.get("users[" + i + "].followers_count").toString();
			myListofFollowers.add(myFollowerData);
		}
		
		
		myFollowerSorter=new FollowerSorter(myListofFollowers); 
		ArrayList<FollowerData> Sorted_Followers_century=myFollowerSorter.getSortedJobCandidateByRetweet();
		List<FollowerData> SortedListFinal = Sorted_Followers_century.subList(0, 10);
		for(int i=0; i<SortedListFinal.size();i++)
		{
			System.out.println(SortedListFinal.get(i).FollowerCount);
		}
		return SortedListFinal;
	
	}
	
	
	
	public static List<String> getFollowerDataVerifiedAccountList(String Name) throws ParseException, JsonMappingException, JsonProcessingException
	{
		
		ArrayList<String> myListofFollowerswithVerifiedAccounts=new ArrayList<String>();
		FollowerSorter myFollowerSorter;
		
		
		String BasicAuthentication = EncodeDecode.ReturnBasicAuth();
		Response respWithAccessToken = RestAssured.given()
				.formParam("User-Agent", "My Twitter App v1.0.23")
				.formParam("grant_type", "client_credentials")
				.formParam("Accept-Encoding", "gzip")
				.headers("Content-Type","application/x-www-form-urlencoded;charset=UTF-8")
				.headers( "Authorization", BasicAuthentication)
				.post("https://api.twitter.com/oauth2/token");
		
		
		Response resp1 = RestAssured.given()
				.formParam("screen_name", Name)
				.formParam("count",200)
				.headers("Authorization", accessToken)
				.auth()
				.oauth2(respWithAccessToken.jsonPath().get("access_token").toString())
				.get("https://api.twitter.com/1.1/friends/list.json");
		
		
		
		
		String myResponseFriends=resp1.asPrettyString();
		System.out.println(myResponseFriends);
		JsonPath jsonPathEvaluator = resp1.jsonPath();
		
		for(int i=0;i<200;i++)
		{
			if(jsonPathEvaluator.get("users[" + i + "].screen_name").toString().length()==0)
			{
				break;
			}
			
			if(jsonPathEvaluator.get("users[" + i + "].verified").toString()=="false")
			{
				continue;
			}
			else
			{
				myListofFollowerswithVerifiedAccounts.add(jsonPathEvaluator.get("users[" + i + "].name").toString());
			}		
		}
	
		return myListofFollowerswithVerifiedAccounts;
	
	}
	
	@BeforeSuite
	public void setUP_Suite() throws IOException, ParseException
	{
	
		if(driver==null)
		{
			
			//log.debug("Execution started.");
			fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\Properties\\config.properties");
			fi_OR = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\Properties\\OR.properties");
			config.load(fis);
			//log.info("Config file loaded!!");
			OR.load(fi_OR);
			String ListofNAmes = OR.getProperty("Name");
			ArrayOfNames=ListofNAmes.split(",");
			for(int i=0;i<ArrayOfNames.length;i++)
			{
				myTOPtweets= GetTop10Tweets(ArrayOfNames[i]);
				myListofTweetDataforUsers.add(myTOPtweets);
				myListofFoloowers_Data=getFollowerData(ArrayOfNames[i]);
				myListofFollowerData.add(myListofFoloowers_Data);
				
			}
			if(config.getProperty("browser").equals("firefox"))
			{
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\Executables\\geckodriver.exe");
				driver=new FirefoxDriver();
			}
			else if(config.getProperty("browser").equals("chrome"))
			{
				//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\Executables\\chromedriver.exe");
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				//log.debug("Chrome launched..");
			}
			
			
			//log.debug("NAvigated the the required page.");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("waiting_time")), TimeUnit.SECONDS);
			wait=new WebDriverWait(driver,5);
			
		}
		
		
	}
	

	public static void verifyEquals(String actual,String Expected) throws IOException
	{
		try
		{
			Assert.assertEquals(actual, Expected);
		}
		catch(Exception e)
		{
			TestUtil.CAptureScreenShot();
		}
	}
	
	@AfterSuite
	public void tearDown()
	{
		if(driver!= null)
		{
			driver.close();
		}
	}

}













