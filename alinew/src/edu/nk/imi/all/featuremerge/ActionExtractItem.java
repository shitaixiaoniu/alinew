package edu.nk.imi.all.featuremerge;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;

import edu.nk.imi.ali.util.UtilFile;
import edu.nk.imi.ali.util.UtilReader;

public class ActionExtractItem {

	public static void extractItemFromPair(String path,String outpath)
	{
		try {
			FileWriter writer = new FileWriter(outpath, true);
			
			UtilReader util = new UtilReader();
			util.init(path);
			
			String line = null;
			HashMap<String,Integer> map = new HashMap<String,Integer>();
			while((line=util.nextLine())!=null)
			{
				String [] parts = line.split(",");
				if(!map.containsKey(parts[1]))
				{
					map.put(parts[1], null);
				}
			}
			util.closeReader();
			
			Iterator ite = map.keySet().iterator();
			while(ite.hasNext())
			{
				writer.write(ite.next()+"\r\n");
			}
			
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		String pathpre = 
				"/Users/shitaixiaoniu/Desktop/alidata_new_newf/online_testtrain_pair/";
		String pathpre_feature_new = 
				"/Users/shitaixiaoniu/Desktop/alidata_new_newf/feature/new/";
		
		String pathpre_feature_old = 
				"/Users/shitaixiaoniu/Desktop/alidata_new_newf/feature/old/";
		
		extractItemFromPair(pathpre+"online_test_uic_filter.txt",
				pathpre+"online_test_uic_filter_item.txt");
		
		//filter train item
		new ActionCandidateFeature().filterItemAndClear(
				pathpre_feature_old+"old_online_test_item.txt", 
				pathpre+"online_test_uic_filter_item.txt",
				pathpre+"online_test_uic_filter_item_feature_old.txt");
		
		UtilFile.deleFile(pathpre+"online_test_uic_filter_item.txt");
	}
	
}
