package edu.nk.imi.ali.feature;

import java.util.HashMap;

import edu.nk.imi.ali.util.UtilReader;

public class PairFeatureExtract {
	public void extractPairFeature(String originalPath,String pairFeaturePath)
	{
		UtilReader util = new UtilReader();
		util.init(originalPath);
		String line = "";
		//HashMap<String, PairFeature> pairMap = new HashMap<String, PairFeature>;
		while((line = util.nextLine())!= null)
		{
			//parts[0] userid;  parts[1] itemid ; parts[2] behavior type; parts[3] user_geohash;
			//parts[4] itemcategory; prats[5] time
			String[] parts = line.split(",");
		}
	}

}
