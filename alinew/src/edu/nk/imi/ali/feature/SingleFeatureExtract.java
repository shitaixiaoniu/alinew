package edu.nk.imi.ali.feature;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import edu.nk.imi.ali.util.UtilReader;

public class SingleFeatureExtract {
	
	public static int TOTAL_ACTION_CLICK = 1;
	public static int TOTAL_ACTION_FAV = 2;
	public static int TOTAL_ACTION_CART = 3;
	public static int TOTAL_ACTION_BUY = 4;
	
	public static int DISTINCT_ACTION_CLICK = 5;
	public static int DISTINCT_ACTION_FAV = 6;
	public static int DISTINCT_ACTION_CART = 7;
	public static int DISTINCT_ACTION_BUY = 8;
	
	public static int CVR = 9;
	
	public static int ACTION_CLICK_FIRST_TIME = 10;
	public static int ACTION_FAV_FIRST_TIME = 11;
	public static int ACTION_CART_FIRST_TIME = 12;
	public static int ACTION_BUY_FIRST_TIME = 13;
	
	public static int ACTION_CLICK_LAST_TIME = 14;
	public static int ACTION_FAV_LAST_TIME = 15;
	public static int ACTION_CART_LAST_TIME = 16;
	public static int ACTION_BUY_LAST_TIME = 17;
	
	public static int LATEST_3DAY_ACTION_CLICK = 18;
	public static int LATEST_3DAY_ACTION_FAV = 19;
	public static int LATEST_3DAY_ACTION_CART = 20;
	public static int LATEST_3DAY_ACTION_BUY = 21;
	
	public static int LATEST_WEEK_ACTION_CLICK = 22;
	public static int LATEST_WEEK_ACTION_FAV = 23;
	public static int LATEST_WEEK_ACTION_CART = 24;
	public static int LATEST_WEEK_ACTION_BUY = 25;
	
	public static int DAY_OF_WEEK_ACTION_CLICK = 26;
	public static int DAY_OF_WEEK_ACTION_FAV = 27;
	public static int DAY_OF_WEEK_ACTION_CART = 28;
	public static int DAY_OF_WEEK_ACTION_BUY = 29;
	
	public static int PER_DAY_ACTION_CLICK = 30;
	public static int PER_DAY_ACTION_FAV = 31;
	public static int PER_DAY_ACTION_CART = 32;
	public static int PER_DAY_ACTION_BUY = 33;
	
//	public static int PER_WEEK_ACTION_CLICK = 34;
//	public static int PER_WEEK_ACTION_FAV = 35;
//	public static int PER_WEEK_ACTION_CART = 36;
//	public static int PER_WEEK_ACTION_BUY = 37;
	
	HashMap<String,HashMap<Integer,Object>> map = new HashMap<String,HashMap<Integer,Object>>();
	HashMap<String,Object> map_for_distinct = new HashMap<String,Object>();
	
	String targetweekStr = "";//Mon Tue Wed Thu Fri Sat Sun
	
	/**
	 * 
	 * @param path
	 * @param outpath
	 * @param type 0:user 1:item 2:category
	 * @param seperator online-train:"2014-12-18 00" online-test:"2014-12-19 00"
	 * @param week if seperator = "2014-12-18 00" then Thu
	 * 				if seperator = "2014-12-19 00" then Fri
	 */
	public void extractSingle(String path,String outpath,int type,String seperator,String week)
	{
		int index_pos = 0;
		int index_pos_distinct = 1;
		
		switch(type)
		{
		case 0:
			index_pos = 0;
			index_pos_distinct = 1;
			break;
		case 1:
			index_pos = 1;
			index_pos_distinct = 0;
			break;
		case 2:
			index_pos = 4;
			index_pos_distinct = 0;
			break;
		}
		
		UtilReader util = new UtilReader();
		util.init(path);
		
		try {
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH" );
			
			Date seperator_date = sdf.parse(seperator);
			
			long dif_3day = seperator_date.getTime()-3*24*3600*1000;
			long dif_1week = seperator_date.getTime()-7*24*3600*1000;
			
			Date date_last3day = new Date();
			Date date_last1week = new Date();
			date_last3day.setTime(dif_3day);
			date_last1week.setTime(dif_1week);
			
			targetweekStr = week;
			
			System.out.println("start while"+new Date().toString());
			String line = null;
			util.nextLine();
			while((line=util.nextLine())!=null)
			{
				String [] parts = line.split(",");
				
				String lineid = parts[index_pos];
				String distinctid = parts[index_pos_distinct];
				int behaviour_type = Integer.parseInt(parts[2]);
				Date date = sdf.parse(parts[5]);
				
				if(!map.containsKey(lineid))
				{
					HashMap<Integer,Object> mm = new HashMap<Integer,Object>();
					map.put(lineid, mm);
				}
				
				switch(behaviour_type)
				{
				case 1:
					increaseTotalNum(lineid, TOTAL_ACTION_CLICK);
					increaseDistinctNum(lineid, DISTINCT_ACTION_CLICK, distinctid);
					updateTime(lineid,ACTION_CLICK_FIRST_TIME,ACTION_CLICK_LAST_TIME,date);
					updateTimeAction(lineid,LATEST_3DAY_ACTION_CLICK,date_last3day,date);
					updateTimeAction(lineid,LATEST_WEEK_ACTION_CLICK,date_last1week,date);
					updateWeekAction(lineid,DAY_OF_WEEK_ACTION_CLICK,date);
					break;
				case 2:
					increaseTotalNum(lineid, TOTAL_ACTION_FAV);
					increaseDistinctNum(lineid, DISTINCT_ACTION_FAV, distinctid);
					updateTime(lineid,ACTION_FAV_FIRST_TIME,ACTION_FAV_LAST_TIME,date);
					updateTimeAction(lineid,LATEST_3DAY_ACTION_FAV,date_last3day,date);
					updateTimeAction(lineid,LATEST_WEEK_ACTION_FAV,date_last1week,date);
					updateWeekAction(lineid,DAY_OF_WEEK_ACTION_FAV,date);
					break;
				case 3:
					increaseTotalNum(lineid, TOTAL_ACTION_CART);
					increaseDistinctNum(lineid, DISTINCT_ACTION_CART, distinctid);
					updateTime(lineid,ACTION_CART_FIRST_TIME,ACTION_CART_LAST_TIME,date);
					updateTimeAction(lineid,LATEST_3DAY_ACTION_CART,date_last3day,date);
					updateTimeAction(lineid,LATEST_WEEK_ACTION_CART,date_last1week,date);
					updateWeekAction(lineid,DAY_OF_WEEK_ACTION_CART,date);
					break;
				case 4:
					increaseTotalNum(lineid, TOTAL_ACTION_BUY);
					increaseDistinctNum(lineid, DISTINCT_ACTION_BUY, distinctid);
					updateTime(lineid,ACTION_BUY_FIRST_TIME,ACTION_BUY_LAST_TIME,date);
					updateTimeAction(lineid,LATEST_3DAY_ACTION_BUY,date_last3day,date);
					updateTimeAction(lineid,LATEST_WEEK_ACTION_BUY,date_last1week,date);
					updateWeekAction(lineid,DAY_OF_WEEK_ACTION_BUY,date);
					break;
				default:
					
					break;
				}
			}
			
			System.out.println("start assemble:"+new Date().toString());
			assembleCvr();
			
			assemblePerDay(TOTAL_ACTION_CLICK,ACTION_CLICK_FIRST_TIME,
					ACTION_CLICK_LAST_TIME,PER_DAY_ACTION_CLICK);
			assemblePerDay(TOTAL_ACTION_FAV,ACTION_FAV_FIRST_TIME,
					ACTION_FAV_LAST_TIME,PER_DAY_ACTION_FAV);
			assemblePerDay(TOTAL_ACTION_CART,ACTION_CART_FIRST_TIME,
					ACTION_CART_LAST_TIME,PER_DAY_ACTION_CART);
			assemblePerDay(TOTAL_ACTION_BUY,ACTION_BUY_FIRST_TIME,
					ACTION_BUY_LAST_TIME,PER_DAY_ACTION_BUY);
			
//			assemblePerWeek(TOTAL_ACTION_CLICK, ACTION_CLICK_FIRST_TIME, 
//					ACTION_CLICK_LAST_TIME, PER_WEEK_ACTION_CLICK);
//			assemblePerWeek(TOTAL_ACTION_FAV,ACTION_FAV_FIRST_TIME,
//					ACTION_FAV_LAST_TIME,PER_WEEK_ACTION_FAV);
//			assemblePerWeek(TOTAL_ACTION_CART,ACTION_CART_FIRST_TIME,
//					ACTION_CART_LAST_TIME,PER_WEEK_ACTION_CART);
//			assemblePerWeek(TOTAL_ACTION_BUY,ACTION_BUY_FIRST_TIME,
//					ACTION_BUY_LAST_TIME,PER_WEEK_ACTION_BUY);
			
			System.out.println("start scale:"+new Date().toString());
			scaleAll();
			
			System.out.println("start write:"+new Date().toString());
			FileWriter writer = new FileWriter(outpath, true);
			Iterator<String> ite = map.keySet().iterator();
			while(ite.hasNext())
			{
				String str = "";
				String key = ite.next();
				str = key+","+getOneLineFeature(key)+"\r\n";
				writer.write(str);
			}
			writer.close();
			System.out.println("done!"+new Date().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getOneLineFeature(String key)
	{
		String str = "";
		
        double f1 = new BigDecimal((Double)map.get(key).get(TOTAL_ACTION_CLICK)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f2 = new BigDecimal((Double)map.get(key).get(TOTAL_ACTION_FAV)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f3 = new BigDecimal((Double)map.get(key).get(TOTAL_ACTION_CART)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f4 = new BigDecimal((Double)map.get(key).get(TOTAL_ACTION_BUY)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f5 = new BigDecimal((Double)map.get(key).get(DISTINCT_ACTION_CLICK)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f6 = new BigDecimal((Double)map.get(key).get(DISTINCT_ACTION_FAV)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f7 = new BigDecimal((Double)map.get(key).get(DISTINCT_ACTION_CART)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        
        double f8 = new BigDecimal((Double)map.get(key).get(DISTINCT_ACTION_BUY)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f9 = new BigDecimal((Double)map.get(key).get(CVR)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f10 = new BigDecimal((Double)map.get(key).get(PER_DAY_ACTION_CLICK)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f11 = new BigDecimal((Double)map.get(key).get(PER_DAY_ACTION_FAV)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f12 = new BigDecimal((Double)map.get(key).get(PER_DAY_ACTION_CART)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f13 = new BigDecimal((Double)map.get(key).get(PER_DAY_ACTION_BUY)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f14 = new BigDecimal((Double)map.get(key).get(LATEST_3DAY_ACTION_CLICK)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        
        double f15 = new BigDecimal((Double)map.get(key).get(LATEST_3DAY_ACTION_FAV)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f16 = new BigDecimal((Double)map.get(key).get(LATEST_3DAY_ACTION_CART)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f17 = new BigDecimal((Double)map.get(key).get(LATEST_3DAY_ACTION_BUY)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f18 = new BigDecimal((Double)map.get(key).get(LATEST_WEEK_ACTION_CLICK)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f19 = new BigDecimal((Double)map.get(key).get(LATEST_WEEK_ACTION_FAV)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f20 = new BigDecimal((Double)map.get(key).get(LATEST_WEEK_ACTION_CART)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f21 = new BigDecimal((Double)map.get(key).get(LATEST_WEEK_ACTION_BUY)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        
        double f22 = new BigDecimal((Double)map.get(key).get(DAY_OF_WEEK_ACTION_CLICK)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f23 = new BigDecimal((Double)map.get(key).get(DAY_OF_WEEK_ACTION_FAV)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f24 = new BigDecimal((Double)map.get(key).get(DAY_OF_WEEK_ACTION_CART)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f25 = new BigDecimal((Double)map.get(key).get(DAY_OF_WEEK_ACTION_BUY)).
        		setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		str = f1+","+f2+","+f3+","+f4+","+f5+","+f6+","+f7+","+f8+","+f9+","+f10+","+f11+","+
				f12+","+f13+","+f14+","+f15+","+f16+","+f17+","+f18+","+f19+","+f20+","+f21+","+
				f22+","+f23+","+f24+","+f25;
				
		return str;
	}
	
	public void assembleCvr()
	{
		Iterator<String> ite = map.keySet().iterator();
		while(ite.hasNext())
		{
			String key = ite.next();
			Double click = (Double)map.get(key).get(TOTAL_ACTION_CLICK);
			Double buy = (Double)map.get(key).get(TOTAL_ACTION_BUY);
			
			if(buy==null)
			{
				map.get(key).put(CVR, 0.0);
			}
			else if(click==null || click==0)
			{
				map.get(key).put(CVR, buy);
			}
			else
			{
				map.get(key).put(CVR, buy/click);
			}
		}
	}
	
	public void assemblePerDay(int field_num,int field_fdate,int field_ldate,int field_perday)
	{
		Iterator<String> ite = map.keySet().iterator();
		while(ite.hasNext())
		{
			String key = ite.next();
			Double value = (Double)map.get(key).get(field_num);
			Date fdate = (Date)map.get(key).get(field_fdate);
			Date ldate = (Date)map.get(key).get(field_ldate);

			if(value==null)
			{
				map.get(key).put(field_perday, 0.0);
			}
			else
			{
				if(fdate==null || ldate==null)
				{
					System.out.println("error null date!");
					return;
				}
				int day = (int) ((ldate.getTime()-fdate.getTime())/(24*3600000));
				
				if(day<0)
				{
					System.out.println("error date!");
					return;
				}
				
				
				else if(day==0)
				{
					map.get(key).put(field_perday, value);
				}
				else
				{
					map.get(key).put(field_perday, value/day);
				}
			}
		}
	}
	
//	public void assemblePerWeek(int field_num,int field_fdate,int field_ldate,int field_perweek)
//	{
//		Iterator<String> ite = map.keySet().iterator();
//		while(ite.hasNext())
//		{
//			String key = ite.next();
//			Double value = (Double)map.get(key).get(field_num);
//			Date fdate = (Date)map.get(key).get(field_fdate);
//			Date ldate = (Date)map.get(key).get(field_ldate);
//			
//			int week = (int) ((ldate.getTime()-fdate.getTime())/(7*24*3600000));
//			
//			map.get(key).put(field_perweek, value/week);
//		}
//	}
	
	public void scaleAll()
	{
		System.out.println("start scale 1"+new Date().toString());
		scale(TOTAL_ACTION_CLICK);
		scale(TOTAL_ACTION_FAV);
		scale(TOTAL_ACTION_CART);
		scale(TOTAL_ACTION_BUY);
		
		System.out.println("start scale 2_1"+new Date().toString());
		scale(DISTINCT_ACTION_CLICK);
		System.out.println("start scale 2_2"+new Date().toString());
		scale(DISTINCT_ACTION_FAV);
		System.out.println("start scale 2_3"+new Date().toString());
		scale(DISTINCT_ACTION_CART);
		System.out.println("start scale 2_4"+new Date().toString());
		scale(DISTINCT_ACTION_BUY);
		
		System.out.println("start scale 3"+new Date().toString());
		scale(CVR);
		
		System.out.println("start scale 4"+new Date().toString());
		scale(PER_DAY_ACTION_CLICK);
		scale(PER_DAY_ACTION_FAV);
		scale(PER_DAY_ACTION_CART);
		scale(PER_DAY_ACTION_BUY);
		
//		scale(PER_WEEK_ACTION_CLICK);
//		scale(PER_WEEK_ACTION_FAV);
//		scale(PER_WEEK_ACTION_CART);
//		scale(PER_WEEK_ACTION_BUY);
		
		System.out.println("start scale 5"+new Date().toString());
		scale(LATEST_3DAY_ACTION_CLICK);
		scale(LATEST_3DAY_ACTION_FAV);
		scale(LATEST_3DAY_ACTION_CART);
		scale(LATEST_3DAY_ACTION_BUY);
		
		System.out.println("start scale 6"+new Date().toString());
		scale(LATEST_WEEK_ACTION_CLICK);
		scale(LATEST_WEEK_ACTION_FAV);
		scale(LATEST_WEEK_ACTION_CART);
		scale(LATEST_WEEK_ACTION_BUY);
		
		System.out.println("start scale 7"+new Date().toString());
		scale(DAY_OF_WEEK_ACTION_CLICK);
		scale(DAY_OF_WEEK_ACTION_FAV);
		scale(DAY_OF_WEEK_ACTION_CART);
		scale(DAY_OF_WEEK_ACTION_BUY);
	
	}
	
	public void scale(int field)
	{
		HashMap<String,Object> mm = avgDvalue(field);
		Double min = (Double)mm.get("min");
		Double dvalue = (Double)mm.get("dvalue");
		
		Iterator<String> ite = map.keySet().iterator();
		while(ite.hasNext())
		{
			String key = ite.next();
			Double value = (Double)map.get(key).get(field);
			
			if(value == null)
			{
				map.get(key).put(field, 0.0);
			}
			else
			{
				map.get(key).put(field, (value-min)/dvalue);
			}
			
		}
	}
	
	public HashMap<String,Object> avgDvalue(int field)
	{
		Iterator<String> ite = map.keySet().iterator();
		Double max = null;
		Double min = null;
		while(ite.hasNext())
		{
			
			String key = ite.next();
			
			Double value = (Double)map.get(key).get(field);
			
			if(value == null)
			{
				if(max==null || min==null)
				{
					max = 0.0;
					min = 0.0;
				}
			}
			else
			{
				if(max==null || min==null)
				{
					max = value;
					min = value;
				}
				else
				{
					if(value>max)
					{
						max = value;
					}
					if(value<min)
					{
						min = value;
					}
				}
			}
			
		}
		
		Double dvalue = max - min;
		
		HashMap<String,Object> mm = new HashMap<String, Object>();
		
		
		mm.put("min", min);
		mm.put("dvalue", dvalue);
		
		return mm;
	}
	
	public void increaseTotalNum(String map_key,Integer field)
	{
		if(map.get(map_key).containsKey(field))
		{
			map.get(map_key).put(field, (Double)map.get(map_key).get(field)+1.0);
		}
		else
		{
			map.get(map_key).put(field, 1.0);
		}
	}
	
	public void increaseDistinctNum(String map_key,Integer field,String distinct_key)
	{
		if(!map_for_distinct.containsKey(distinct_key))
		{
			map_for_distinct.put(distinct_key, null);
			
			if(map.get(map_key).containsKey(field))
			{
				map.get(map_key).put(field, (Double)map.get(map_key).get(field)+1.0);
			}
			else
			{
				map.get(map_key).put(field, 1.0);
			}
		}
		
	}

	public void updateTime(String map_key,Integer field1,Integer field2,Date date)
	{
		Date fdate = (Date)map.get(map_key).get(field1);
		Date ldate = (Date)map.get(map_key).get(field2);
		
		if(date==null)
		{
			System.out.println("updateTime date null error");
		}
		
		if(fdate!=null && ldate!=null)
		{
			if(date.before(fdate))
			{
				map.get(map_key).put(field1,date);
				return;
			}
			
			if(date.after(ldate))
			{
				map.get(map_key).put(field2,date);
				return;
			}
		}
		else if(fdate==null && ldate==null)
		{
			map.get(map_key).put(field1,date);
			map.get(map_key).put(field2,date);
			return;
		}
		else
		{
			System.out.println("updateTime error");
		}
//		else if(fdate==null && ldate!=null)
//		{
//			if(date.after(ldate))
//			{
//				map.get(map_key).put(field1,ldate);
//				map.get(map_key).put(field2,date);
//				return;
//			}
//			else
//			{
//				map.get(map_key).put(field1,date);
//				return;
//			}
//		}
//		else if(fdate!=null && ldate==null)
//		{
//			if(date.before(fdate))
//			{
//				map.get(map_key).put(field1,date);
//				map.get(map_key).put(field2,fdate);
//				return;
//			}
//			else
//			{
//				map.get(map_key).put(field2,date);
//				return;
//			}
//		}
		
	}
	
	public void updateTimeAction(String map_key,Integer field,Date ideal_date,Date date)
	{
		if(date.after(ideal_date))
		{
			if(map.get(map_key).containsKey(field))
			{
				map.get(map_key).put(field, (Double)map.get(map_key).get(field)+1.0);
			}
			else
			{
				map.get(map_key).put(field, 1.0);
			}
		}
	}
	
	public void updateWeekAction(String map_key,Integer field,Date date)
	{
		if(isDayOfWeek(date.toString()))
		{
			if(map.get(map_key).containsKey(field))
			{
				map.get(map_key).put(field, (Double)map.get(map_key).get(field)+1.0);
			}
			else
			{
				map.get(map_key).put(field, 1.0);
			}
		}
	}
	
	public boolean isDayOfWeek(String str)
	{
		String week = str.substring(0, str.indexOf(" "));
		if(week.equals(targetweekStr))
		{
			return true;
		}
		return false;
	}

}
