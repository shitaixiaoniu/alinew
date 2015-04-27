package edu.nk.imi.all.predict;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.classifier.df.DecisionForest;
import org.apache.mahout.classifier.df.data.DataConverter;
import org.apache.mahout.classifier.df.data.Dataset;
import org.apache.mahout.classifier.df.data.Instance;
import org.apache.mahout.common.RandomUtils;

import edu.nk.imi.ali.util.UtilReader;


public class ActionMergeAndPredictRF {
	
	String defaultPairFeature = "-0.08776667,-0.00781667,-0.00728889,-0.00112727,-0.01935591," +
			"-0.00702857,-0.00401053,-0.00094444,-0.00054643,-0.00040292,-0.01751548,-0.51484906,"
			+"-0.53236457,0,0,0,0";
	String defaultCPairFeature ="-0.01083981,-0.00225797,-0.001625,-0.00123196,"+
			"-0.00208516,-0.00051337,-0.0001862,-0.00048051,-0.00256323,-0.00173176,"+
			"-0.14634657,0,0,0,0,-0.44613188,-0.59247841";
	
	//trainOrTest 0:train 1:test
	public void filterRecord(String pairpath,String itempath,String userpath,
			String cpairpath,String catepairpath,String testPairPath,
			String datasetpath,String forestpath,
			String outpath)
	{	
		System.out.println("-----------filterRecord start----------");
		
		
		
		try {
			
			DecisionForest forest =null;
			Dataset dataset = null;
			DataConverter converter = null;
			
			Configuration conf=new Configuration();
		    Path tpath =  new Path(forestpath);
		        
		    forest = DecisionForest.load(conf, tpath);
				
			if (forest == null) {
		        throw new InterruptedException("DecisionForest not found!");
		    }
				
			Path datasetPath = new Path(datasetpath);
			dataset = Dataset.load(conf, datasetPath);
			converter = new DataConverter(dataset);
			System.out.println("init decision tree");
			
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
			util.init(catepairpath);
			line = null;
			HashMap<String, String> map_cate = new HashMap<String, String>();
			while((line = util.nextLine())!=null && !line.equals(""))
			{
				String id = line.substring(0, line.indexOf(","));
				String feature = line.substring(line.indexOf(",")+1, line.length());
				
				map_cate.put(id,feature);
			}
			util.closeReader();
			System.out.println("read cate feature to memory done!");
			
			
			//while target
			FileWriter writer = new FileWriter(outpath, true);
			
			util.init(testPairPath);
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
				if(parts.length==4)
				{
					ylable = parts[3];
				}
				else
				{
					ylable = count%2+"";
				}
				
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
					lineFeature = defaultPairFeature+",";
				}
				
				//user
				lineFeature = lineFeature + map_user.get(userid)+",";
				
				//item
				lineFeature = lineFeature + map_item.get(itemid)+",";
				
				//cpair
				if(map_cpair.containsKey(cpairid))
				{
					lineFeature = lineFeature + map_cpair.get(cpairid)+",";
				}
				else
				{
					lineFeature = lineFeature + defaultCPairFeature+",";
				}
				
				//cate
				lineFeature = lineFeature + map_cate.get(cateid);
				
				String tobePredict = userid+"_"+itemid+"_"+cateid+","+lineFeature+","+ylable;
				
				Instance instance = converter.convert(tobePredict);
				Random rng = RandomUtils.getRandom();
				double prediction = forest.classify(dataset, rng, instance);
				
				int prediction_int = (int)prediction;
				//write file
				if(prediction_int==0)
				{
					writer.write(userid+","+itemid+"\r\n");
				}
				
			}
			util.closeReader();
			
			writer.close();
			//writer_lr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("-----------filterRecord end----------");
	}

}
