package edu.nk.imi.ali.predict;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import edu.nk.imi.ali.util.UtilReader;

public class TestDeleteZero {
	
	public static void main(String[] args) {
	
		String path_source =
				"/Users/shitaixiaoniu/Desktop/alidata_new_newf/predict/2015-4-23/"
				+ "online_newf_200_10_sample_0.01.txt";
		
//		String path_todelete = 
//				"/Users/shitaixiaoniu/Desktop/alidata/2015-4-14/data_record_sampling/test/candidate_test_uic_result.txt";
//		
		String path_todelete = 
				"/Users/shitaixiaoniu/Desktop/alidata_new/predict/lastDayBuy.txt";
		String outpath = 
				"/Users/shitaixiaoniu/Desktop/alidata_new_newf/predict/2015-4-23/"
				+ "online_newf_200_10_sample_0.01_del.txt";
		
		
	}
	
	public void delPreDataBuy(String path_source,String path_todelete,String outpath)
	{
		try {
			FileWriter writer = new FileWriter(outpath, true);
			
			UtilReader util = new UtilReader();
			util.init(path_todelete);
			
			String line = null;
			HashMap<String,Integer> map = new HashMap<String,Integer>();
			while((line = util.nextLine())!=null)
			{
				if(!map.containsKey(line))
				{
					map.put(line, null);
				}
				
			}
			util.closeReader();
			
			line = null;
			util.init(path_source);
			int count = 0;
			while((line = util.nextLine())!=null)
			{
				if(map.containsKey(line))
				{
					count++;
				}
				else
				{
					writer.write(line+"\r\n");
				}
			}
			
			System.out.println("del num:"+count);
			util.closeReader();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
