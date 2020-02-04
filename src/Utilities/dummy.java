package Utilities;

public class dummy {

	public static void main(String[]args)throws Exception{
	
	String browsername=PropertyFileUtil.getValueForKey("Browser");
	System.out.println("browsername is"+browsername);
 }
}