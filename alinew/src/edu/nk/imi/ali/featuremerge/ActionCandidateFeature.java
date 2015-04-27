package edu.nk.imi.ali.featuremerge;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.nk.imi.ali.util.UtilReader;
import edu.nk.imi.ali.util.UtilStr;

public class ActionCandidateFeature {
	
	public void assembleFeature(ArrayList<String> pairpath,ArrayList<String> userpath,
			ArrayList<String> itempath,ArrayList<String> cpairpath,ArrayList<String> catepath,
			String tmpPairpath,String tmpUserpath,String tmpItempath,
			String tmpCPairpath,String tmpCatepath)
	{
		System.out.println("----------assembleFeature start-----------------");
		try {
			
			//pair feature
			System.out.println("start : pair feature");
			assembleFeaturePair(pairpath, tmpPairpath);
			
			//cpair feature
			System.out.println("start : cpair feature");
			assembleFeaturePair(cpairpath, tmpCPairpath);
			
			//user feature
			System.out.println("start : user feature");
			assembleFeatureSingle(userpath, tmpUserpath);
			
			//item feature
			System.out.println("start : item feature");
			assembleFeatureSingle(itempath, tmpItempath);
			
			//cate feature
			System.out.println("start : category feature");
			assembleFeatureSingle(catepath, tmpCatepath);
			
			System.out.println("----------assembleFeature end-----------------");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void filterItemAndClear(String tmpItempath, String candidateItemPath, String filteritempath)
	{
		//filter item
		System.out.println("----------filterItemAndClear start-------");
		try {
			filterItem(tmpItempath, candidateItemPath, filteritempath);
			//UtilFile.deleFile(tmpItempath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("----------filterItemAndClear end-------");
	}
	
	/**
	 * assemble pair
	 * @param pairpath
	 * @param tmppath
	 * @throws IOException
	 */
	private void assembleFeaturePair(ArrayList<String> pairpath,String tmppath) throws IOException
	{
		FileWriter writer = new FileWriter(tmppath, true);
		
		int pairfileNum = pairpath.size();
		List<UtilReader> readerLs = new ArrayList<UtilReader>();
		List<String> lineLs = new ArrayList<String>();
		for(int i=0;i<pairfileNum;i++)
		{
			UtilReader util = new UtilReader();
			util.init(pairpath.get(i));
			readerLs.add(util);
			
			String line = null;
			lineLs.add(line);
		}

		int count = 0;
		while (true) {
			count++;
			boolean isFinish = false;
			for(int i=0;i<pairfileNum;i++)
			{
				String line = readerLs.get(i).nextLine();
				lineLs.set(i, line);
				if(line==null || line.equals(""))
				{
					isFinish = true;
					break;
				}
			}
			
			if(isFinish)
			{
				break;
			}
			
			//assemble
			String str = "";
			String lineID = "";
			for(int i=0;i<pairfileNum;i++)
			{
				String line = lineLs.get(i);
				String subLine = line.substring(line.indexOf(",")+1);
				
				String id1 = line.substring(0, line.indexOf(","));
				String id2 = subLine.substring(0, subLine.indexOf(","));
				String features = subLine.substring(subLine.indexOf(",")+1);
				
				if(i==0)
				{
					lineID = id1+"_"+ id2;
				}
				else if(!lineID.equals(id1+"_"+ id2))
				{
					System.out.println("error:pair id not match--"+lineID+","+id1+"_"+ id2+":"+i);
					break;
				}
				
				str = str+features+",";
			}
			writer.write(lineID+","+UtilStr.removeLastStr(str)+"\r\n");
			
		}
		writer.close();
		
		for(int i=0;i<pairfileNum;i++)
		{
			readerLs.get(i).closeReader();
		}
	}

	/**
	 * for single feature
	 * @param path
	 * @param tmppath
	 * @throws IOException
	 */
	private void assembleFeatureSingle(ArrayList<String> path,String tmppath) throws IOException
	{
		FileWriter writer = new FileWriter(tmppath, true);
		
		int fileNum = path.size();
		List<UtilReader> readerLs = new ArrayList<UtilReader>();
		List<String> lineLs = new ArrayList<String>();
		for(int i=0;i<fileNum;i++)
		{
			UtilReader util = new UtilReader();
			util.init(path.get(i));
			readerLs.add(util);
			
			String line = null;
			lineLs.add(line);
		}

		while (true) {
			
			boolean isFinish = false;
			for(int i=0;i<fileNum;i++)
			{
				String line = readerLs.get(i).nextLine();
				lineLs.set(i, line);
				if(line==null || line.equals(""))
				{
					isFinish = true;
					break;
				}
			}
			
			if(isFinish)
			{
				break;
			}
			
			//assemble
			String str = "";
			String lineID = "";
			for(int i=0;i<fileNum;i++)
			{
				String line = lineLs.get(i);
				String id = line.substring(0, line.indexOf(","));
				String features = line.substring(line.indexOf(",")+1);
				if(i==0)
				{
					lineID = id;
				}
				else if(!lineID.equals(id))
				{
					System.out.println(path.get(i));
					System.out.println("error:id not match");
					break;
				}
				
				str = str+features+",";
			}
			writer.write(lineID+","+UtilStr.removeLastStr(str)+"\r\n");
			
		}
		writer.close();
		
		for(int i=0;i<fileNum;i++)
		{
			readerLs.get(i).closeReader();
		}
	}


	/**
	 * filter candidate item
	 * @param itempath
	 * @param candidateItemPath
	 * @param outpath
	 * @throws IOException
	 */
	private void filterItem(String itempath,String candidateItemPath,String outpath) throws IOException
	{
		FileWriter writer = new FileWriter(outpath, true);
		
		UtilReader util = new UtilReader();
		util.init(candidateItemPath);
		
		String line = null;
		HashMap<String, String> map = new HashMap<String, String>();
		while((line = util.nextLine())!=null && !line.equals(""))
		{
			map.put(line, null);
		}
		util.closeReader();
		
		util = new UtilReader();
		util.init(itempath);
		
		line = null;
		while((line = util.nextLine())!=null && !line.equals(""))
		{
			String lineid = line.substring(0, line.indexOf(","));
			if(map.containsKey(lineid))
			{
				writer.write(line+"\r\n");
			}
		}
		util.closeReader();
		writer.close();
	}
}
