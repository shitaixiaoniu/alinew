package edu.nk.imi.all.predict;

import java.util.HashMap;

import edu.nk.imi.ali.util.UtilReader;

public class TestComputeF {
	
	public void computeF(String predictpath,String realpath)
	{
		double precision = 0.0;
		double recall = 0.0;
		double F_value = 0.0;
		
		UtilReader util = new UtilReader();
		util.init(predictpath);
		
		String line = null;
		HashMap<String, String> map = new HashMap<String,String>();
		double count_predict=0.0;
		while((line=util.nextLine())!=null)
		{
			count_predict = count_predict+1.0;
			map.put(line, null);
		}
		util.closeReader();
		
		line = null;
		util.init(realpath);
		double count_real=0;
		double count_coincidence = 0.0;
		while((line=util.nextLine())!=null)
		{
			count_real = count_real+1.0;
			if(map.containsKey(line))
			{
				count_coincidence = count_coincidence+1.0;
			}
		}
		
		System.out.println(count_coincidence+","+count_predict+","+count_real);
		
		precision = count_coincidence/count_predict;
		recall = count_coincidence/count_real;
		F_value = 2*precision*recall/(precision+recall);
		
		
		System.out.println("f value:"+F_value);
		System.out.println("precision:"+precision);
		System.out.println("recall:"+recall);
	}

}
