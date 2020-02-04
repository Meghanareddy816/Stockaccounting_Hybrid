package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class PropertyFileUtil {

	public static String  getValueForKey(String key) throws Exception{
		
		Properties configProperties=new Properties();
		
		FileInputStream fis=new FileInputStream("E:\\selenium 2020\\Stockaccounting_Hybrid\\PropertiesFile\\Environment.propertie");
		
				configProperties.load(fis);
				
				return configProperties.getProperty(key);
	}
}
