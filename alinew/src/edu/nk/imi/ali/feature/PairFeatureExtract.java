package edu.nk.imi.ali.feature;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import edu.nk.imi.ali.util.UtilReader;

public class PairFeatureExtract {
	static final String[] featureName={
			"clickTotal",
			"favTotal",
			"cartTotal",
			"buyTotal",
			"cvr",
			"clickInterval",
			"buyInterval",
			"clickPerday",
			"favPerday",
			"cartPerday",
			"buyPerday",
			"clickLastThreeDayTotal",
			"favLastThreeDayTotal",
			"cartLastThreeDayTotal",
			"buyLastThreeDayTotal",
			"clickLastWeekTotal",
			"favLastWeekTotal",
			"cartLastWeekTotal",
			"buyLastWeekTotal",
			"clickWeekDayTotal",
			"favWeekDayTotal",
			"cartWeekDayTotal",
			"buyWeekDayTotal"	
	};
	public void extractPairFeature(String originalPath,String pairFeaturePath,String splitPointTime) throws Exception
	{
		UtilReader util = new UtilReader();
		util.init(originalPath);
		String line = "";
		String format = "yyyy-MM-dd hh";
		HashMap<String, PairFeature> pairMap = new HashMap<String, PairFeature>();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		//以天的零点算
		Date splitDate = sdf.parse(splitPointTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(splitDate);
		//分割点日期的 day of week
		int dayofweek = cal.DAY_OF_WEEK;
		//最近三天的基准
		cal.add(Calendar.DAY_OF_MONTH, -3);
		Date lastThreeDayDate = cal.getTime();
		
		//最近一周的基准
		cal.clear();
		cal.setTime(splitDate);
		
		cal.add(Calendar.DAY_OF_MONTH, -7);
		Date lastWeekDate = cal.getTime();
				
		while((line = util.nextLine())!= null)
		{
			//parts[0] userid;  parts[1] itemid ; parts[2] behavior type; parts[3] user_geohash;
			//parts[4] itemcategory; parts[5] time
			String[] parts = line.split(",");
			String pairKey = parts[0]+"_"+parts[1];
			//pairkey 不存在
			if(!pairMap.containsKey(pairKey))
			{
				pairMap.put(pairKey, new PairFeature());	
			}
			Date date = sdf.parse(parts[5]);
			cal.clear();
			cal.setTime(date);
			int dayofweekTmp = cal.DAY_OF_WEEK;
			switch (parts[2])
			{
			//click
			case "1":
				pairMap.get(pairKey).clickTotal ++;
				if(pairMap.get(pairKey).clickFtime.equals(""))
				{
					pairMap.get(pairKey).clickFtime = parts[5];
				}
				else
				{
					Date clickFTime = sdf.parse(pairMap.get(pairKey).clickFtime);
					//date 更小 更新最小值
					if(date.before(clickFTime))
					{
						pairMap.get(pairKey).clickFtime = sdf.format(date);
					}

				}
				if(pairMap.get(pairKey).clickLtime.equals(""))
				{
					pairMap.get(pairKey).clickLtime = parts[5];
				}
				else
				{
					Date clickLTime = sdf.parse(pairMap.get(pairKey).clickLtime);
					//date 更大 更新最大值
					if(date.after(clickLTime))
					{
						pairMap.get(pairKey).clickLtime = sdf.format(date);
					}
				}
				
				//在最近一周内
				if(date.after(lastWeekDate))
				{
					pairMap.get(pairKey).clickLastWeekTotal++;
				}
				//在最近3天
				if(date.after(lastThreeDayDate))
				{
					pairMap.get(pairKey).clickLastThreeDayTotal++;
				}
				
				//与分割点 在同一个day of week
				if(dayofweekTmp == dayofweek)
				{
					pairMap.get(pairKey).clickWeekdayTotal++;
				}
				break;
			//fav
			case "2":
				pairMap.get(pairKey).favTotal ++;
				if(pairMap.get(pairKey).favFtime.equals(""))
				{
					pairMap.get(pairKey).favFtime = parts[5];
				}
				else
				{
					Date favFTime = sdf.parse(pairMap.get(pairKey).favFtime);
					//date 更小 更新最小值
					if(date.before(favFTime))
					{
						pairMap.get(pairKey).favFtime = sdf.format(date);
					}
				}
				if(pairMap.get(pairKey).favLtime.equals(""))
				{
					pairMap.get(pairKey).favLtime = parts[5];
				}
				else
				{
					Date favLTime = sdf.parse(pairMap.get(pairKey).favLtime);
					//date 更大 更新最大值
					if(date.after(favLTime))
					{
						pairMap.get(pairKey).favLtime = sdf.format(date);
					}
				}
				
				//在最近一周内
				if(date.after(lastWeekDate))
				{
					pairMap.get(pairKey).favLastWeekTotal++;
				}
				if(date.after(lastThreeDayDate))
				{
					pairMap.get(pairKey).favLastThreeDayTotal++;
				}
				//与分割点 在同一个day of week
				if(dayofweekTmp == dayofweek)
				{
					pairMap.get(pairKey).favWeekdayTotal++;
				}
				break;
			//cart
			case "3":
				pairMap.get(pairKey).cartTotal ++;
				if(pairMap.get(pairKey).cartFtime.equals(""))
				{
					pairMap.get(pairKey).cartFtime = parts[5];
				}
				else
				{
					Date cartFTime = sdf.parse(pairMap.get(pairKey).cartFtime);
					//date 更小 更新最小值
					if(date.before(cartFTime))
					{
						pairMap.get(pairKey).cartFtime = sdf.format(date);
					}
				}
				if(pairMap.get(pairKey).cartLtime.equals(""))
				{
					pairMap.get(pairKey).cartLtime = parts[5];
				}
				else
				{
					Date cartLTime = sdf.parse(pairMap.get(pairKey).cartLtime);
					//date 更大 更新最大值
					if(date.after(cartLTime))
					{
						pairMap.get(pairKey).cartLtime = sdf.format(date);
					}
				}
				
				//在最近一周内
				if(date.after(lastWeekDate))
				{
					pairMap.get(pairKey).cartLastWeekTotal++;
				}
				//在最近3天
				if(date.after(lastThreeDayDate))
				{
					pairMap.get(pairKey).cartLastThreeDayTotal++;
				}
				//与分割点 在同一个day of week
				if(dayofweekTmp == dayofweek)
				{
					pairMap.get(pairKey).cartWeekdayTotal++;
				}
				break;
			//buy 
			case "4":
				pairMap.get(pairKey).buyTotal ++;
				//最小时间
				if(pairMap.get(pairKey).buyFtime.equals(""))
				{
					pairMap.get(pairKey).buyFtime = parts[5];
				}
				else
				{
					Date buyFTime = sdf.parse(pairMap.get(pairKey).buyFtime);
					//date 更小 更新最小值
					if(date.before(buyFTime))
					{
						pairMap.get(pairKey).buyFtime = sdf.format(date);
					}
				}
				//最大时间
				if(pairMap.get(pairKey).buyLtime.equals(""))
				{
					pairMap.get(pairKey).buyLtime = parts[5];
				}
				else
				{
					Date buyLTime = sdf.parse(pairMap.get(pairKey).buyLtime);
					//date 更大 更新最大值
					if(date.after(buyLTime))
					{
						pairMap.get(pairKey).buyLtime = sdf.format(date);
					}
				}
				
				//在最近一周内
				if(date.after(lastWeekDate))
				{
					pairMap.get(pairKey).buyLastWeekTotal++;
				}
				//在最近3天
				if(date.after(lastThreeDayDate))
				{
					pairMap.get(pairKey).buyLastThreeDayTotal++;
				}
				//与分割点 在同一个day of week
				if(dayofweekTmp == dayofweek)
				{
					pairMap.get(pairKey).buyWeekdayTotal++;
				}
				break;
			}
			
		}
		//遍历pair对 计算cvr ,actionperday, time interval to end,
		//计算scale所需的最大值，最小值
		HashMap<String, PairFeatureStatis> statisMap = new HashMap<String, PairFeatureStatis>();
		for (Iterator<Entry<String, PairFeature>> iterator = pairMap.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, PairFeature> entry = (Entry<String, PairFeature>) iterator.next();
			PairFeature pf = entry.getValue();
			//cvr
			if(pf.clickTotal != 0)
			{
				pf.cvr = (double)pf.buyTotal/(double)pf.clickTotal;
			}
			else
			{
				pf.cvr =  (double)pf.buyTotal;
			}
			//action perday
			cal.setTime(sdf.parse(pf.clickFtime));
			int fday = cal.DAY_OF_YEAR;
			cal.setTime(sdf.parse(pf.clickLtime));
			int lday = cal.DAY_OF_YEAR;
			if((lday-fday) != 0)
			{
				pf.clickPerday = (double)pf.clickTotal/(double)(lday-fday);
			}
			else
			{
				pf.clickPerday = (double)pf.clickTotal;
			}
			
			cal.setTime(sdf.parse(pf.favFtime));
			fday = cal.DAY_OF_YEAR;
			cal.setTime(sdf.parse(pf.favLtime));
			lday = cal.DAY_OF_YEAR;
			if((lday-fday) != 0)
			{
				pf.favPerday = (double)pf.favTotal/(double)(lday-fday);
			}
			else
			{
				pf.favPerday = (double)pf.favTotal;
			}
			
			cal.setTime(sdf.parse(pf.cartFtime));
			fday = cal.DAY_OF_YEAR;
			cal.setTime(sdf.parse(pf.cartLtime));
			lday = cal.DAY_OF_YEAR;
			if((lday-fday) != 0)
			{
				pf.cartPerday = (double)pf.cartTotal/(double)(lday-fday);
			}
			else
			{
				pf.cartPerday = (double)pf.cartTotal;
			}
			
			cal.setTime(sdf.parse(pf.buyFtime));
			fday = cal.DAY_OF_YEAR;
			cal.setTime(sdf.parse(pf.buyLtime));
			lday = cal.DAY_OF_YEAR;
			if((lday-fday) != 0)
			{
				pf.buyPerday = (double)pf.buyTotal/(double)(lday-fday);
			}
			else
			{
				pf.buyPerday = (double)pf.buyTotal;
			}
			
			//time interval to end
			pf.clickInterval = hourDiff(pf.clickLtime, splitPointTime, format);
			pf.buyInterval =  hourDiff(pf.buyLtime, splitPointTime, format);
			
			double[] pfArray = pf.toArray();
			//求各特征的最大值最小值
			for (int i = 0; i < featureName.length; i++) {
				if(!statisMap.containsKey(featureName[i]))
				{
					statisMap.put(featureName[i], new PairFeatureStatis(pfArray[i],pfArray[i]));
				}
				if(pfArray[i] < statisMap.get(featureName[i]).min)
				{
					statisMap.get(featureName[i]).min = pfArray[i];
				}
				if(pfArray[i] > statisMap.get(featureName[i]).max)
				{
					statisMap.get(featureName[i]).max = pfArray[i];
				}
			}
		}
		
		//输出特征文件
		FileWriter fw = new FileWriter(pairFeaturePath, true);
		for (Iterator<Entry<String, PairFeature>> iterator = pairMap.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, PairFeature> entry = (Entry<String, PairFeature>) iterator.next();
			String res = entry.getKey();
			double[] pfArray = entry.getValue().toArray();
			for (int i = 0; i < pfArray.length; i++) {
				double tmp = 0;
				if((statisMap.get(featureName[i]).max-statisMap.get(featureName[i]).min) != 0)
				{
					tmp = (pfArray[i]-statisMap.get(featureName[i]).min)/(statisMap.get(featureName[i]).max-statisMap.get(featureName[i]).min);

				}
				BigDecimal bg = new BigDecimal(tmp);  
		        double f1 = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		        res+=","+f1;

			}
			fw.write(res+"\r\n");
			
		}
		fw.close();
	}

	public int hourDiff(String startTime ,String endTime,String format) throws Exception
	{
		 SimpleDateFormat sd = new SimpleDateFormat(format); 
		 long diff;  
		 diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();   
		 int hour = (int) (diff % (1000 * 60 * 60));
		 return hour;
	}
}
