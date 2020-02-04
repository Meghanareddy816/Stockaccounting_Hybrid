package CommonFunctionLibrary;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.PropertyFileUtil;

public class FunctionLibrary {

	public  static WebDriver startBrowser(WebDriver driver) throws Exception{
		
		
		
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Chrome")){
			
			System.setProperty("webdriver.chrome.driver","E:\\selenium 2020\\Stockaccounting_Hybrid\\drivers\\chromedriver.exe");
			
			driver=new ChromeDriver();
			
		}else{
			System.out.println("invalid browser");
		}
		
		return driver;
	}
	

	
	public static void OpenApplication(WebDriver driver)throws Exception{
	driver.get(PropertyFileUtil.getValueForKey("Url"));
	driver.manage().window().maximize();
	
  }

	public static void TypeAction(WebDriver driver,String locatortype,String locatorvalue,String testdata){
		
		if(locatortype.equalsIgnoreCase("id")){
			driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
			
		}else if (locatortype.equalsIgnoreCase("name")){
			driver.findElement(By.name(locatorvalue)).sendKeys(testdata);
			
		}else if (locatortype.equalsIgnoreCase("xpath")){
			driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);
			
		}else{
			System.out.println("Locator not matching for TypeAction method");
		}
			
	}

		public static void WaitForElement(WebDriver driver,String locatortype, String locatorvalue,String testdata){
			
			WebDriverWait mywait=new WebDriverWait(driver,Integer.parseInt(testdata));
			
			if(locatortype.equalsIgnoreCase("id")){
				
				mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
				
			}else if (locatortype.equalsIgnoreCase("name")) {
				
				mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
				
			}else if (locatortype.equalsIgnoreCase("xpath")) {
				
				mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
			
			}else{
				
				System.out.println("Unable to locate for WaitForElement method");
				

			}
			
		}

		
		public static void ClickAction(WebDriver driver,String locatortype, String locatorvalue,String testdata){
			
			if(locatortype.equalsIgnoreCase("id")){
				driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
				
			}else if (locatortype.equalsIgnoreCase("name")){
				driver.findElement(By.name(locatorvalue)).sendKeys(testdata);
				
			}else if (locatortype.equalsIgnoreCase("xpath")){
				driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);
				
			}else{
				System.out.println("Unable to locate for ClickAction method");
			}
		}
		
		
		public static void closeBrowser(WebDriver driver){
			driver.close();
		}
		
		public static String generateDate(){
			Date date=new Date(0);
			SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_dd_ss");
			return sdf.format(date);
			
			
		}
		
		public void CaptureData(WebDriver driver,String locatortype,String locatorvalue )throws Exception{
			
			String supplierdata="";
			if(locatortype.equalsIgnoreCase("id")){
				supplierdata=driver.findElement(By.id(locatorvalue)).getAttribute("value");
			}else if(locatortype.equalsIgnoreCase("xpath"))
			{
				supplierdata=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
			}
			
			FileWriter fw=new FileWriter("./CaptureData/suppnumber.txt");	
		
			BufferedWriter bw=new BufferedWriter(fw);
			
			bw.write(supplierdata);
			
			bw.flush();
			bw.close();
				
		}
		
		public void tableValidation(WebDriver driver,String column) throws Exception{
			
			FileReader fr=new FileReader("./CaptureData/suppnumber.txt");
			BufferedReader br=new BufferedReader(fr);
			
			String Exp_data=br.readLine();
			
			if(driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).isDisplayed()){
				driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).clear();
				driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).sendKeys(Exp_data);
				driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("click-search"))).click();
				
				
			}else{
				driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Click-searchpanel"))).click();
				driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).clear();
				driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).sendKeys(Exp_data);
				driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("click-search"))).click();
			}
			
			WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("sup-table")));
			
			List<WebElement>rows=table.findElements(By.tagName("tr"));
			
			for(int i=1;i<rows.size()-1;i++){
				
				String act_data=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column+"]/div/span/span")).getText();
				
				Assert.assertEquals(act_data, Exp_data);
				
				System.out.println(act_data+"   "+Exp_data);
				break;		
			}
			
		}
		
	
}	


	