package Rough;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TEstProperties {

	public static void main(String[] args) throws IOException
	{
		Properties config = new Properties();
		Properties OR = new Properties();
		
		try {
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\Properties\\config.properties");
			FileInputStream fi_OR = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\Properties\\OR.properties");
			config.load(fis);
			OR.load(fi_OR);
			
			String Browser=config.getProperty("browser");
			String OR_BankMAnagerLogin=config.getProperty("Bank_MAnager_Login_BTN_Home");
			System.out.println(OR_BankMAnagerLogin);
			System.out.println(Browser);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
	}
	
	
}
