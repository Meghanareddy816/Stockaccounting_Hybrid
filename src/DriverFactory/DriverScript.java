package DriverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunctionLibrary.FunctionLibrary;
import Utilities.ExcelFileUtils;

public class DriverScript {
	
	ExtentReports report;
	ExtentTest test;
	WebDriver driver;

  @Test
  public void startTest() throws Throwable {
	  ExcelFileUtils excel=new ExcelFileUtils();
	  for(int i=1;i<=excel.rowcount("MasterTestCases");i++){
		  
		  String ModuleStatus="";
		  if(excel.getData("MasterTestCases",i,2).equalsIgnoreCase("Y")){
			  
			  //Store module name into TCModule
			  String TCModule=excel.getData("MasterTestCases", i, 1);
			  
			  report=new ExtentReports("E:\\selenium 2020\\Stockaccounting_Hybrid\\Reports\\"+TCModule+FunctionLibrary.generateDate()+".html");
			  
			  for(int j=1;j<=excel.rowcount(TCModule);j++)
				  
			  {
				  
				test=report.startTest(TCModule);
				String Description=excel.getData(TCModule, j, 0);
				String Function_Name=excel.getData(TCModule, j, 1);
				String Locator_Type=excel.getData(TCModule, j, 2);
				String Locator_Value=excel.getData(TCModule, j,3);
				String Test_Data=excel.getData(TCModule, j, 4);
				
				try{
				
				if(Function_Name.equalsIgnoreCase("startBrowser")){
					FunctionLibrary.startBrowser(driver);
					test.log(LogStatus.INFO, Description);
				}
				
				if(Function_Name.equalsIgnoreCase("OpenApplication")){
					FunctionLibrary.OpenApplication(driver);
					test.log(LogStatus.INFO, Description);
				
	         }
				
				if(Function_Name.equalsIgnoreCase("TypeAction")){
					FunctionLibrary.TypeAction(driver,Locator_Type,Locator_Value,Test_Data);
					test.log(LogStatus.INFO, Description);
					
					
            }
				
				if(Function_Name.equalsIgnoreCase("WaitForElement")){
					FunctionLibrary.WaitForElement(driver, Locator_Type, Locator_Value, Test_Data);
					test.log(LogStatus.INFO, Description);
						
              }
				
				if(Function_Name.equalsIgnoreCase("ClickAction")){
					FunctionLibrary.ClickAction(driver, Locator_Type, Locator_Value, Test_Data);
					test.log(LogStatus.INFO, Description);
							
       }
				
				if(Function_Name.equalsIgnoreCase("closeBrowser")){
					FunctionLibrary.closeBrowser(driver);
					test.log(LogStatus.INFO, Description);
					  
				}
			  }catch(Exception e){
				ModuleStatus="false"; 
				File srcFile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(srcFile,new File ("E:\\selenium 2020\\Stockaccounting_Hybrid\\Screenshots\\"+Description+FunctionLibrary.generateDate()));
				excel.setData(TCModule, j,5,"FAIL");
				break;
				
		  }
				
				
	  }
			  
			  if(ModuleStatus.equalsIgnoreCase("true")){
				  excel.setData("MasterTestCases", i, 3, "Pass");
			  }else if (ModuleStatus.equalsIgnoreCase("false")) {
				  excel.setData("MasterTestCases",i, 3, "Fail");
				  
				
			}
			  
			  report.flush();
			  report.endTest(test);
			  
		 }
		  else{
			  excel.setData("MasterTestCases",i, 3, "Not Executed");
		  }
		  
 
	  }					
  }
}