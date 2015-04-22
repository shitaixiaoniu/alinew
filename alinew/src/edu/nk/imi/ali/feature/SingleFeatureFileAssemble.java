package edu.nk.imi.ali.feature;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nk.imi.ali.util.UtilReader;
import edu.nk.imi.ali.util.UtilStr;

public class SingleFeatureFileAssemble {
	
	public void assembleFeatureSingle(ArrayList<String> path,String outpath) throws IOException
	{
		FileWriter writer = new FileWriter(outpath, true);
		
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

}
