package edu.nk.imi.ali.util;

public class UtilStr {

	public static String removeLastStr(String str)
	{
		if(str==null)
			return null;
		if(str.equals(""))
			return "";
		
		str = str.substring(0, str.length()-1);
		
		return str;
	}
}
