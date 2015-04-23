package edu.nk.imi.ali.feature;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import edu.nk.imi.ali.util.UtilFile;
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
	
	HashMap<String,HashMap<Integer,Object>> map;// = new HashMap<String,HashMap<Integer,Object>>();
	HashMap<String,Object> map_for_distinct;// = new HashMap<String,Object>();
	
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
	public void extractSingleFeature(String path,String outpath,String filename,
			int type,String seperator,String week)
	{
		extractSingle(path,outpath, type, seperator, week,1);
		extractSingle(path,outpath, type, seperator, week,2);
		extractSingle(path,outpath, type, seperator, week,3);
		extractSingle(path,outpath, type, seperator, week,4);
		extractSingle(path,outpath, type, seperator, week,5);
		extractSingle(path,outpath, type, seperator, week,6);
		
		ArrayList<String> tmpath_Ls = new ArrayList<String>();
		tmpath_Ls.add(outpath+"1.txt");
		tmpath_Ls.add(outpath+"2.txt");
		tmpath_Ls.add(outpath+"3.txt");
		tmpath_Ls.add(outpath+"4.txt");
		tmpath_Ls.add(outpath+"5.txt");
		tmpath_Ls.add(outpath+"6.txt");
		
		try {
			new SingleFeatureFileAssemble().assembleFeatureSingle(tmpath_Ls, outpath+filename);
			
			UtilFile.deleFile(outpath+"1.txt");
			UtilFile.deleFile(outpath+"2.txt");
			UtilFile.deleFile(outpath+"3.txt");
			UtilFile.deleFile(outpath+"4.txt");
			UtilFile.deleFile(outpath+"5.txt");
			UtilFile.deleFile(outpath+"6.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param path
	 * @param outpath
	 * @param type 0:user 1:item 2:category
	 * @param seperator online-train:"2014-12-18 00" online-test:"2014-12-19 00"
	 * @param week if seperator = "2014-12-18 00" then Thu
	 * 				if seperator = "2014-12-19 00" then Fri
	 */
	private void extractSingle(String path,String outpath,int type,String seperator,String week,int part)
	{
		map = new HashMap<String,HashMap<Integer,Object>>();
		map_for_distinct = new HashMap<String,Object>();
		
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
			
			System.out.println("----start while:"+new Date().toString());
			String line = null;
			//util.nextLine();
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
					switch(part)
					{
					case 1:
						increaseTotalNum(lineid, TOTAL_ACTION_CLICK);
						break;
					case 2:
						increaseDistinctNum(lineid, DISTINCT_ACTION_CLICK, distinctid);
						break;
					case 3:
						increaseTotalNum(lineid, TOTAL_ACTION_CLICK);
						updateTime(lineid,ACTION_CLICK_FIRST_TIME,ACTION_CLICK_LAST_TIME,date);
						break;
					case 4:
						updateTimeAction(lineid,LATEST_3DAY_ACTION_CLICK,date_last3day,date);
						break;
					case 5:
						updateTimeAction(lineid,LATEST_WEEK_ACTION_CLICK,date_last1week,date);
						break;
					case 6:
						updateWeekAction(lineid,DAY_OF_WEEK_ACTION_CLICK,date);
						break;
					}
					break;
				case 2:
					switch(part)
					{
					case 1:
						increaseTotalNum(lineid, TOTAL_ACTION_FAV);
						break;
					case 2:
						increaseDistinctNum(lineid, DISTINCT_ACTION_FAV, distinctid);
						break;
					case 3:
						increaseTotalNum(lineid, TOTAL_ACTION_FAV);
						updateTime(lineid,ACTION_FAV_FIRST_TIME,ACTION_FAV_LAST_TIME,date);
						break;
					case 4:
						updateTimeAction(lineid,LATEST_3DAY_ACTION_FAV,date_last3day,date);
						break;
					case 5:
						updateTimeAction(lineid,LATEST_WEEK_ACTION_FAV,date_last1week,date);
						break;
					case 6:
						updateWeekAction(lineid,DAY_OF_WEEK_ACTION_FAV,date);
						break;
					}
					break;
				case 3:
					switch(part)
					{
					case 1:
						increaseTotalNum(lineid, TOTAL_ACTION_CART);
						break;
					case 2:
						increaseDistinctNum(lineid, DISTINCT_ACTION_CART, distinctid);
						break;
					case 3:
						increaseTotalNum(lineid, TOTAL_ACTION_CART);
						updateTime(lineid,ACTION_CART_FIRST_TIME,ACTION_CART_LAST_TIME,date);
						break;
					case 4:
						updateTimeAction(lineid,LATEST_3DAY_ACTION_CART,date_last3day,date);
						break;
					case 5:
						updateTimeAction(lineid,LATEST_WEEK_ACTION_CART,date_last1week,date);
						break;
					case 6:
						updateWeekAction(lineid,DAY_OF_WEEK_ACTION_CART,date);
						break;
					}
					break;
				case 4:
					switch(part)
					{
					case 1:
						increaseTotalNum(lineid, TOTAL_ACTION_BUY);
						break;
					case 2:
						increaseDistinctNum(lineid, DISTINCT_ACTION_BUY, distinctid);
						break;
					case 3:
						increaseTotalNum(lineid, TOTAL_ACTION_BUY);
						updateTime(lineid,ACTION_BUY_FIRST_TIME,ACTION_BUY_LAST_TIME,date);
						break;
					case 4:
						updateTimeAction(lineid,LATEST_3DAY_ACTION_BUY,date_last3day,date);
						break;
					case 5:
						updateTimeAction(lineid,LATEST_WEEK_ACTION_BUY,date_last1week,date);
						break;
					case 6:
						updateWeekAction(lineid,DAY_OF_WEEK_ACTION_BUY,date);
						break;
					}
					break;
				default:
					
					break;
				}
			}
			
			if(part==1)
			{
				System.out.println("start assemble CVR:"+new Date().toString());
				assembleCvr();
			}
			else if(part == 3)
			{
				System.out.println("start assemble:"+new Date().toString());
				assemblePerDay(TOTAL_ACTION_CLICK,ACTION_CLICK_FIRST_TIME,
						ACTION_CLICK_LAST_TIME,PER_DAY_ACTION_CLICK);
				assemblePerDay(TOTAL_ACTION_FAV,ACTION_FAV_FIRST_TIME,
						ACTION_FAV_LAST_TIME,PER_DAY_ACTION_FAV);
				assemblePerDay(TOTAL_ACTION_CART,ACTION_CART_FIRST_TIME,
						ACTION_CART_LAST_TIME,PER_DAY_ACTION_CART);
				assemblePerDay(TOTAL_ACTION_BUY,ACTION_BUY_FIRST_TIME,
						ACTION_BUY_LAST_TIME,PER_DAY_ACTION_BUY);
			}
			scaleAll(outpath,part,map);
			
			System.out.println("done!"+new Date().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void assembleCvr()
	{
		Iterator<String> ite = map.keySet().iterator();
		while(ite.hasNext())
		{
			String key = ite.next();
			Integer click = (Integer)map.get(key).get(TOTAL_ACTION_CLICK);
			Integer buy = (Integer)map.get(key).get(TOTAL_ACTION_BUY);

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
				double v_buy = buy+0.0;
				double v_click = click+0.0;
				map.get(key).put(CVR, v_buy/v_click);
			}
		}
	}
	
	private void assemblePerDay(int field_num,int field_fdate,
			int field_ldate,int field_perday)
	{
		Iterator<String> ite = map.keySet().iterator();
		while(ite.hasNext())
		{
			String key = ite.next();
			Object value = map.get(key).get(field_num);
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
					double vv = 0.0;
					if(value instanceof Integer)
					{
						vv = (int)value+0.0;
					}
					else
					{
						vv = (double)value;
					}
					map.get(key).put(field_perday, vv/day);
				}
			}
		}
	}
	
	private void writeFile(String outpath,ArrayList<Integer> ls)
	{
		try {
			FileWriter writer = new FileWriter(outpath, true);
			Iterator<String> ite = map.keySet().iterator();
			while(ite.hasNext())
			{
				String str = "";
				String key = ite.next();
				str = key;
				
				for(int i=0;i<ls.size();i++)
				{
					String f = String.format("%.4f",(double)map.get(key).get(ls.get(i)));
					
					if(f.equals("0.0000"))
					{
						f = "0.0";
					}
					str = str +","+f;
					map.get(key).put(ls.get(i),null);
				}
				
				writer.write(str+"\r\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println("----write done!");
	}
	
	private void scaleAll(String outdir,int part,HashMap<String,HashMap<Integer,Object>> map)
	{
		System.out.println("start scale:"+new Date().toString());
		ArrayList<Integer> ls = new ArrayList<Integer>();
		
		switch(part)
		{
		case 1:
			scale(TOTAL_ACTION_CLICK);
			scale(TOTAL_ACTION_FAV);
			scale(TOTAL_ACTION_CART);
			scale(TOTAL_ACTION_BUY);
			scale(CVR);
			
			ls.add(TOTAL_ACTION_CLICK);
			ls.add(TOTAL_ACTION_FAV);
			ls.add(TOTAL_ACTION_CART);
			ls.add(TOTAL_ACTION_BUY);
			ls.add(CVR);
			writeFile(outdir+"1.txt",ls);
			break;
		case 2:
			scale(DISTINCT_ACTION_CLICK);
			scale(DISTINCT_ACTION_FAV);
			scale(DISTINCT_ACTION_CART);
			scale(DISTINCT_ACTION_BUY);
			
			ls.clear();
			ls.add(DISTINCT_ACTION_CLICK);
			ls.add(DISTINCT_ACTION_FAV);
			ls.add(DISTINCT_ACTION_CART);
			ls.add(DISTINCT_ACTION_BUY);
			writeFile(outdir+"2.txt",ls);
			break;
		case 3:
			scale(PER_DAY_ACTION_CLICK);
			scale(PER_DAY_ACTION_FAV);
			scale(PER_DAY_ACTION_CART);
			scale(PER_DAY_ACTION_BUY);
			
			ls.clear();
			ls.add(PER_DAY_ACTION_CLICK);
			ls.add(PER_DAY_ACTION_FAV);
			ls.add(PER_DAY_ACTION_CART);
			ls.add(PER_DAY_ACTION_BUY);
			writeFile(outdir+"3.txt",ls);
			break;
		case 4:
			scale(LATEST_3DAY_ACTION_CLICK);
			scale(LATEST_3DAY_ACTION_FAV);
			scale(LATEST_3DAY_ACTION_CART);
			scale(LATEST_3DAY_ACTION_BUY);
			
			ls.clear();
			ls.add(LATEST_3DAY_ACTION_CLICK);
			ls.add(LATEST_3DAY_ACTION_FAV);
			ls.add(LATEST_3DAY_ACTION_CART);
			ls.add(LATEST_3DAY_ACTION_BUY);
			writeFile(outdir+"4.txt",ls);
			break;
		case 5:
			scale(LATEST_WEEK_ACTION_CLICK);
			scale(LATEST_WEEK_ACTION_FAV);
			scale(LATEST_WEEK_ACTION_CART);
			scale(LATEST_WEEK_ACTION_BUY);
			
			ls.clear();
			ls.add(LATEST_WEEK_ACTION_CLICK);
			ls.add(LATEST_WEEK_ACTION_FAV);
			ls.add(LATEST_WEEK_ACTION_CART);
			ls.add(LATEST_WEEK_ACTION_BUY);
			writeFile(outdir+"5.txt",ls);
			break;
		case 6:
			scale(DAY_OF_WEEK_ACTION_CLICK);
			scale(DAY_OF_WEEK_ACTION_FAV);
			scale(DAY_OF_WEEK_ACTION_CART);
			scale(DAY_OF_WEEK_ACTION_BUY);
			
			ls.clear();
			ls.add(DAY_OF_WEEK_ACTION_CLICK);
			ls.add(DAY_OF_WEEK_ACTION_FAV);
			ls.add(DAY_OF_WEEK_ACTION_CART);
			ls.add(DAY_OF_WEEK_ACTION_BUY);
			writeFile(outdir+"6.txt",ls);
			break;
		}
	}
	
	private void scale(int field)
	{
		HashMap<String,Object> mm = avgDvalue(field);
		Double min = (Double)mm.get("min");
		Double dvalue = (Double)mm.get("dvalue");
		
		System.out.println("scale:"+field+","+min+","+dvalue);
		
		if(dvalue==0.0)
		{
			System.out.println("error:dvalue=0!");
		}
		if(min==null || dvalue==null)
		{
			System.out.println("error!!!!!");
		}
		
		Iterator<String> ite = map.keySet().iterator();
		while(ite.hasNext())
		{
			String key = ite.next();
			Object value = map.get(key).get(field);
			
			if(value == null)
			{
				map.get(key).put(field, 0.0);
			}
			else
			{
				double vv = 0.0;
				if(value instanceof Integer)
				{
					vv = (int)value+0.0;
				}
				else
				{
					vv = (double)value;
				}
				map.get(key).put(field, (vv-min)/dvalue);
				
			}
			
		}
	}
	
	private HashMap<String,Object> avgDvalue(int field)
	{
		Iterator<String> ite = map.keySet().iterator();
		Double max = null;
		Double min = null;
		while(ite.hasNext())
		{
			
			String key = ite.next();
			
			Object value = map.get(key).get(field);
			
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
				double vv = 0.0;
				if(value instanceof Integer)
				{
					vv = (int)value+0.0;
				}
				else
				{
					vv = (double)value;
				}
				if(max==null || min==null)
				{
					max = vv;
					min = vv;
				}
				else
				{
					if(vv>max)
					{
						max = vv;
					}
					if(vv<min)
					{
						min = vv;
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
	
	private void increaseTotalNum(String map_key,Integer field)
	{
		Object obj = map.get(map_key).get(field);
		if(obj!=null)
		{
			map.get(map_key).put(field, (int)obj+1);
		}
		else
		{
			map.get(map_key).put(field, 1);
		}
	}
	
	private void increaseDistinctNum(String map_key,Integer field,String distinct_key)
	{
		if(!map_for_distinct.containsKey(distinct_key))
		{
			map_for_distinct.put(distinct_key, null);
			
			Object obj = map.get(map_key).get(field);
			if(obj!=null)
			{
				map.get(map_key).put(field, (int)obj+1);
			}
			else
			{
				map.get(map_key).put(field, 1);
			}
		}
		
	}

	private void updateTime(String map_key,Integer field1,Integer field2,Date date)
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
		
	}
	
	private void updateTimeAction(String map_key,Integer field,Date ideal_date,Date date)
	{
		if(date.after(ideal_date))
		{
			Object obj = map.get(map_key).get(field);
			if(obj!=null)
			{
				map.get(map_key).put(field, (int)obj+1);
			}
			else
			{
				map.get(map_key).put(field, 1);
			}
		}
	}
	
	private void updateWeekAction(String map_key,Integer field,Date date)
	{
		if(isDayOfWeek(date.toString()))
		{
			Object obj = map.get(map_key).get(field);
			if(obj!=null)
			{
				map.get(map_key).put(field, (int)obj+1);
			}
			else
			{
				map.get(map_key).put(field, 1);
			}
		}
	}
	
	private boolean isDayOfWeek(String str)
	{
		String week = str.substring(0, str.indexOf(" "));
		if(week.equals(targetweekStr))
		{
			return true;
		}
		return false;
	}

}
