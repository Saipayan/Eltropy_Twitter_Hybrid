package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import Base.TestBase;

public class TestUtil extends TestBase {

	
	public static String  SCR_PATH=System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\";
	public static String Scr_Name="error.jpg";
	//public static POIHelper myPoiHelper = new POIHelper();
	
	public  static String path;
	public  static FileInputStream fis = null;
	public  static FileOutputStream fileOut =null;
	
	
	public static void CAptureScreenShot() throws IOException
	{
		File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(SCR_PATH+Scr_Name));
	}
	

	
}
