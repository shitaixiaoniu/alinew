package edu.nk.ini.all.predict;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import edu.nk.imi.ali.util.UtilReader;

public class TestComputeCoincidence {

	public static void main(String[] args) throws IOException {
		String path1 = 
				"/Users/shitaixiaoniu/Desktop/alidata_new_newf/predict/2015-4-24/online_newf_200_11_sample_50.txt";
		String path2 = 
				"/Users/shitaixiaoniu/Desktop/alidata_new_newf/predict/2015-4-24_mergeresult/online_mergef_200_11_sample_50.txt";
		String outpath = 
				"/Users/shitaixiaoniu/Desktop/alidata_new_newf/predict/2015-4-24_mergeresult/res.txt";
		
		UtilReader util = new UtilReader();
		util.init(path1);
		
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
		
		FileWriter writer = new FileWriter(outpath,true);
		line = null;
		util.init(path2);
		int count = 0;
		while((line = util.nextLine())!=null)
		{
			if(map.containsKey(line))
			{
				writer.write(line+"\r\n");
				count++;
			}
		}
		util.closeReader();
		System.out.println(count+"");
		
		writer.close();
		
	}

}
