package edu.nk.imi.ali.feature;

import java.util.ArrayList;

public class PairFeature {

	
	public PairFeature()
	{
		clickTotal=0;
		favTotal=0;
		cartTotal=0;
		buyTotal=0;
		
		//action最开始发生的time
		clickFtime = "";
		favFtime= "" ;
		cartFtime = "";
		buyFtime ="";
		
		// action的最后发生的time
		clickLtime ="";
		favLtime ="";
		cartLtime ="";
		buyLtime ="";
		
		//最近3天内的action总数
		clickLastThreeDayTotal =0;
		favLastThreeDayTotal =0;
		cartLastThreeDayTotal =0;
		buyLastThreeDayTotal =0;
		
		//最近一周内的action总数
		clickLastWeekTotal = 0;
		favLastWeekTotal = 0;
		cartLastWeekTotal = 0;
		buyLastWeekTotal = 0;
		
		//与目标所在的星期几的action 数
		clickWeekdayTotal = 0;
		favWeekdayTotal = 0;
		cartWeekdayTotal = 0;
		buyWeekdayTotal = 0;
		
		//转化率 buytotal/clicktotal
		cvr = 0.0;
		
		//最后行为到分割点的时间间隔（单位 小时）
		clickInterval = 0;
		buyInterval = 0;
		
		//action perday
		clickPerday = 0.0;
		favPerday = 0.0;
		cartPerday = 0.0;
		buyPerday = 0.0;
	}
	
	public ArrayList<String> getMemberName()
	{
		ArrayList<String > list = new ArrayList<String>();
		list.add("clickTotal");
		list.add("favTotal");
		list.add("cartTotal");
		list.add("buyTotal");
		
		list.add("cvr");
		
		list.add("clickInterval");
		
		list.add("buyInterval");
		
		list.add("clickPerday");
		list.add("favPerday");
		list.add("cartPerday");
		list.add("buyPerday");
		
		list.add("clickLastThreeDayTotal");
		list.add("favLastThreeDayTotal");
		list.add("cartLastThreeDayTotal");
		list.add("buyLastThreeDayTotal");
		
		list.add("clickLastWeekTotal");
		list.add("favLastWeekTotal");
		list.add("cartLastWeekTotal");
		list.add("buyLastWeekTotal");
		

		list.add("clickWeekDayTotal");
		list.add("favWeekDayTotal");
		list.add("cartWeekDayTotal");
		list.add("buyWeekDayTotal");
		return list;
	}
	public double[] toArray()
	{
		double[] array={
				clickTotal,
				favTotal,
				cartTotal,
				buyTotal,
				cvr,
				clickInterval,
				buyInterval,
				clickPerday,
				favPerday,
				cartPerday,
				buyPerday,
				clickLastThreeDayTotal,
				favLastThreeDayTotal,
				cartLastThreeDayTotal,
				buyLastThreeDayTotal,
				clickLastWeekTotal,
				favLastWeekTotal,
				cartLastWeekTotal,
				buyLastWeekTotal,
				clickWeekdayTotal,
				favWeekdayTotal,
				cartWeekdayTotal,
				buyWeekdayTotal
				
		};
		return array;
	}
	//action总数
	public int clickTotal;
	public int favTotal;
	public int cartTotal;
	public int buyTotal;
	
	//action最开始发生的time
	public String clickFtime;
	public String favFtime;
	public String cartFtime;
	public String buyFtime;
	
	// action的最后发生的time
	public String clickLtime;
	public String favLtime;
	public String cartLtime;
	public String buyLtime;
	
	//最近3天内的action总数
	public int clickLastThreeDayTotal;
	public int favLastThreeDayTotal;
	public int cartLastThreeDayTotal;
	public int buyLastThreeDayTotal;
	
	//最近一周内的action总数
	public int clickLastWeekTotal;
	public int favLastWeekTotal;
	public int cartLastWeekTotal;
	public int buyLastWeekTotal;
	
	//与目标所在的星期几的action 数
	public int clickWeekdayTotal;
	public int favWeekdayTotal;
	public int cartWeekdayTotal;
	public int buyWeekdayTotal;
	
	//转化率 buytotal/clicktotal
	public double cvr;
	
	//最后行为到分割点的时间间隔（单位 小时）
	public int clickInterval;
	public int buyInterval;
	
	//action perday
	public double clickPerday;
	public double favPerday;
	public double cartPerday;
	public double buyPerday;
	

}
