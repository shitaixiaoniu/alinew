package edu.nk.imi.ali.preprocess;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import edu.nk.imi.ali.util.UtilReader;

public class DataPreProcess {
	/**
	 * 从原始数据得到[starttime,endtime]区间的数据
	 * @param startTime
	 * @param endTime
	 * @param inputPath
	 * @param outputPath
	 * @throws Exception
	 */
	public void getOriginalDataByRange(String startTime,String endTime,String inputPath,String outputPath) throws Exception
	{
		String format = "yyyy-MM-dd hh";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		UtilReader util = new UtilReader();
		util.init(inputPath);
		Date startDate = sdf.parse(startTime);
		Date endDate = sdf.parse(endTime);
		FileWriter fw = new FileWriter(outputPath, true);
		//跳过第一行 标题行
		String line = util.nextLine();
		while((line = util.nextLine())!= null)
		{
			String curTime = line.substring(line.lastIndexOf(",")+1);
			Date curDate = sdf.parse(curTime);
			//curDate在区间内
			if( curDate.compareTo(startDate)>=0 && curDate.compareTo(endDate) <=0)
			{
				fw.write(line+"\r\n");
			}
		}
		fw.close();
		util.closeReader();
		
	}

	/**
	 * 从input文件中保存 distinct的<user,item,category>
	 * @param inputPath
	 * @param outPath
	 * @throws Exception 
	 */
	public void getUICPairFromOriginal(String inputPath ,String outPath) throws Exception
	{
		UtilReader util = new UtilReader();
		util.init(inputPath);
		String line="";
		HashMap<String,Integer> uicPairMap = new HashMap<String,Integer>();
		while((line = util.nextLine())!= null)
		{
			String[] parts = line.split(",");
			String uic=parts[0]+","+parts[1]+","+parts[4];
			if(!uicPairMap.containsKey(uic))
			{
				uicPairMap.put(uic, 1);
			}
		}
		util.closeReader();
		FileWriter fw = new FileWriter(outPath, true);
		for (Iterator<Entry<String, Integer>> iterator = uicPairMap.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Integer> entry = (Entry<String, Integer>) iterator.next();
			fw.write(entry.getKey()+"\r\n");

		}
		fw.close();
	}
	/**
	 * 从input文件中筛选 有过购买行为的<user,item>pair 正例
	 * @param inputPath
	 * @param outPath
	 * @throws Exception 
	 */
	public void getPositiveUIPair(String inputPath,String outPath) throws Exception
	{
		UtilReader util = new UtilReader();
		util.init(inputPath);
		String line="";
		HashMap<String,Integer> uiPairMap = new HashMap<String,Integer>();
		while((line = util.nextLine())!= null)
		{
			String[] parts = line.split(",");
			if(parts[2].equals("4"))
			{
				String ui=parts[0]+","+parts[1];
				if(!uiPairMap.containsKey(ui))
				{
					uiPairMap.put(ui, 1);
				}
			}
			
		}
		util.closeReader();
		FileWriter fw = new FileWriter(outPath, true);
		for (Iterator<Entry<String, Integer>> iterator = uiPairMap.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Integer> entry = (Entry<String, Integer>) iterator.next();
			fw.write(entry.getKey()+"\r\n");

		}
		fw.close();
	}
	/**
	 * 为训练样本标上标签<user,item,category,y>
	 * @param uicPairPath
	 * @param positivePairPath
	 * @param outPath
	 * @throws Exception 
	 */
	public void getTrainUICYPair(String uicPairPath,String positivePairPath,String outPath) throws Exception
	{
		UtilReader util = new UtilReader();
		util.init(positivePairPath);
		HashMap<String, Integer> positivePairMap = new HashMap<String, Integer>();
		String line = "";
		while((line = util.nextLine())!= null)
		{
			positivePairMap.put(line, 1);
		}
		util.closeReader();
		util.init(uicPairPath);
		FileWriter fw = new FileWriter(outPath, true);
		while((line = util.nextLine())!= null)
		{
			String[] parts = line.split(",");
			//y=1
			if(positivePairMap.containsKey(parts[0]+","+parts[1]))
			{
				fw.write(line+",1"+"\r\n");
			}
			else
			{
				fw.write(line+",0"+"\r\n");
			}
		}
		fw.close();
		util.closeReader();
	}
	public void filterPairByCandidate(String itemPath, String pairPath, String outPath) throws Exception
	{
		UtilReader utilItem = new UtilReader();
		utilItem.init(itemPath);
		HashMap<Integer,Integer> itemC = new HashMap<Integer,Integer>();
		String item = null;
		while((item = utilItem .nextLine()) != null)
		{
			int itemInt = Integer.parseInt(item);
			if( !itemC.containsKey(itemInt))
			{
				itemC.put(itemInt, 0);
			}
		}
		utilItem.closeReader();
		
		UtilReader utilPair = new UtilReader();
		utilPair.init(pairPath);
		String line = null;
		FileWriter writer = new FileWriter(outPath, true);
		while((line = utilPair .nextLine()) != null)
		{
			String []parts = line.split(",");
			int itemTmp = Integer.parseInt(parts[1]);
			if(itemC.containsKey(itemTmp))
			{
				writer.write(line+"\r\n");
			}
		}
		utilPair.closeReader();
		writer.close();
	}
}
