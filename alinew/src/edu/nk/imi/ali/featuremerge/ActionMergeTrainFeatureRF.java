package edu.nk.imi.ali.featuremerge;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import edu.nk.imi.ali.util.UtilReader;

public class ActionMergeTrainFeatureRF {
	
//	static String defaultPairFeature = 
//			"-0.08776667,-0.00781667,-0.00728889,-0.00112727,-0.01935591," +
//			"-0.00702857,-0.00401053,-0.00094444,-0.00054643,-0.00040292,"+
//			"-0.01751548,-0.51484906,-0.53236457,0,0,0,0";
//	static String defaultCPairFeature =
//			"-0.01083981,-0.00225797,-0.001625,-0.00123196,-0.00208516,"+
//			"-0.00051337,-0.0001862,-0.00048051,-0.00256323,-0.00173176,"+
//			"-0.14634657,0,0,0,0,-0.44613188,-0.59247841";
//	
//	static String defaultPairFeature_noscale = 
//			"0,0,0,0,0,0,0,0,0,0,0,1416240000,1416240000,0,0,0,0";
//	static String defaultCPairFeature_noscale =
//			"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1416240000,1416240000";
	
	public static void main(String[] args) {
		String pathpre_feature_new = 
				"/Users/shitaixiaoniu/Desktop/alidata_new_newf/feature/new/";
		
		String pathpre_feature_old = 
				"/Users/shitaixiaoniu/Desktop/alidata_new_newf/feature/old/";
		
		String pathpre_train = 
				"/Users/shitaixiaoniu/Desktop/alidata_new_newf/online_testtrain_pair/";
		
		filterRecord(pathpre_feature_old+"old_online_train_pair.txt",
				pathpre_train+"train_online_all_new_sample_0.0113_50_item_feature_old.txt",
				pathpre_feature_old+"old_online_train_user.txt",
				pathpre_feature_old+"old_online_train_cpair.txt",
				pathpre_feature_old+"old_online_train_cate.txt",
				pathpre_train+"train_online_all_new_sample_0.0113_50.txt",
				pathpre_train+"train_online_all_new_sample_0.0113_50_oldfeature_rf.txt",0);
	}

	//scaleornot 0:scale 1:noscale
	public static void filterRecord(String pairpath,String itempath,String userpath,
			String cpairpath,String catepath,String trainPairPath,
			String outpath,int scaleornot)
	{
		System.out.println("-----------filterRecord start----------");
		try {
			
			UtilReader util = new UtilReader();
			
			//pair
			util.init(pairpath);
			String line = null;
			HashMap<String, String> map_pair = new HashMap<String, String>();
			while((line = util.nextLine())!=null && !line.equals(""))
			{
				String id = line.substring(0, line.indexOf(","));
				String feature = line.substring(line.indexOf(",")+1, line.length());
				
				map_pair.put(id,feature);
			}
			util.closeReader();
			System.out.println("read pair feature to memory done!");
			
			//item
			util.init(itempath);
			line = null;
			HashMap<String, String> map_item = new HashMap<String, String>();
			while((line = util.nextLine())!=null && !line.equals(""))
			{
				String id = line.substring(0, line.indexOf(","));
				String feature = line.substring(line.indexOf(",")+1, line.length());
				
				map_item.put(id,feature);
			}
			util.closeReader();
			System.out.println("read item feature to memory done!");

			//user
			util.init(userpath);
			line = null;
			HashMap<String, String> map_user = new HashMap<String, String>();
			while((line = util.nextLine())!=null && !line.equals(""))
			{
				String id = line.substring(0, line.indexOf(","));
				String feature = line.substring(line.indexOf(",")+1, line.length());
				
				map_user.put(id,feature);
			}
			util.closeReader();
			System.out.println("read user feature to memory done!");
			
			//cpair
			util.init(cpairpath);
			line = null;
			HashMap<String, String> map_cpair = new HashMap<String, String>();
			while((line = util.nextLine())!=null && !line.equals(""))
			{
				String id = line.substring(0, line.indexOf(","));
				String feature = line.substring(line.indexOf(",")+1, line.length());
				
				map_cpair.put(id,feature);
			}
			util.closeReader();
			System.out.println("read cpair feature to memory done!");
			
			//cate
			util.init(catepath);
			line = null;
			HashMap<String, String> map_cate = new HashMap<String, String>();
			while((line = util.nextLine())!=null && !line.equals(""))
			{
				String id = line.substring(0, line.indexOf(","));
				String feature = line.substring(line.indexOf(",")+1, line.length());
				
				map_cate.put(id,feature);
			}
			util.closeReader();
			System.out.println("read item feature to memory done!");
			
			
			//while target
			FileWriter writer = new FileWriter(outpath, true);
			
			util.init(trainPairPath);
			line = null;
			
			int count=0;
			while((line = util.nextLine())!=null && !line.equals(""))
			{
				count++;
				String [] parts = line.split(",");
				String userid = parts[0];//line.substring(0, line.indexOf(","));
				String itemid = parts[1];//line.substring(line.indexOf(",")+1,line.length());
				String cateid = parts[2];
				
				String ylable = "";
				ylable = parts[3];
//				if(parts.length==4)
//				{
//					ylable = parts[3];
//				}
//				else
//				{
//					ylable = count%2+"";
//				}
				
				String pairid = userid+"_"+itemid;
				String cpairid = userid+"_"+cateid;
				String lineFeature = "";
				
				//pair
				if(map_pair.containsKey(pairid))
				{
					lineFeature = map_pair.get(pairid)+",";
				}
				else
				{
					System.out.println("need default!");
					return;
//					if(scaleornot == 0)
//					{
//						lineFeature = defaultPairFeature+",";
//					}
//					else
//					{
//						lineFeature = defaultPairFeature_noscale+",";
//					}
				}
				
				//user
				if(map_user.containsKey(userid))
				{
					lineFeature = lineFeature + map_user.get(userid)+",";
				}
				else
				{
					System.out.println("error --  no user matched!");
				}
				
				//item
				if(map_item.containsKey(itemid))
				{
					lineFeature = lineFeature + map_item.get(itemid)+",";
				}
				else
				{
					System.out.println("error --  no item matched!");
				}
				
				//cpair
				if(map_cpair.containsKey(cpairid))
				{
					lineFeature = lineFeature + map_cpair.get(cpairid)+",";
				}
				else
				{
					System.out.println("need default!");
					return;
//					if(scaleornot == 0)
//					{
//						lineFeature = lineFeature + defaultCPairFeature+",";
//					}
//					else
//					{
//						lineFeature = lineFeature + defaultCPairFeature_noscale+",";
//					}
				}
				
				//cate
				//cate
				if(map_cate.containsKey(cateid))
				{
					lineFeature = lineFeature + map_cate.get(cateid);
				}
				else
				{
					System.out.println("error --  no cate matched!");
				}
				
				writer.write(userid+"_"+itemid+"_"+cateid+","+lineFeature+","+ylable+"\r\n");
				
			}
			util.closeReader();
			
			writer.close();
			//writer_lr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("-----------filterRecord end----------");
	}

}
