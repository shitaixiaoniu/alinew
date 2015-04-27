package edu.nk.imi.ali.test;



import edu.nk.imi.ali.feature.PairFeatureExtract;
import edu.nk.imi.ali.feature.SingleFeatureExtract;

public class PairTest {
	
	public static void main(String[] args) {
		PairFeatureExtract pfe = new PairFeatureExtract();
		//在线train
		String prefix="/Users/shitaixiaoniu/Desktop/alidata_new_newf";
		String originalPath=prefix+"/pair/original/original_offline_train_1118_1215.txt";
		//String originalPath_test=prefix+"/pair/original/original_online_test_1118_1218.txt";
		
		String pairFeaturePath = prefix+"/feature/offline_train_pair_15.txt";
		String cpairFeaturePath = prefix+"/feature/offline_train_cpair_15.txt";
		String splitPointTime="2014-12-16 00";
		
		
		try {
			pfe.extractPairFeature(originalPath, pairFeaturePath, splitPointTime, true);
			
			pfe.extractPairFeature(originalPath, cpairFeaturePath , splitPointTime, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new SingleFeatureExtract().extractSingleFeature(
				originalPath,
				prefix+"/feature/","offline_train_user_15.txt",0,splitPointTime,"Tue");
		
		new SingleFeatureExtract().extractSingleFeature(
				originalPath,
				prefix+"/feature/","offline_train_cate_15.txt",2,splitPointTime,"Tue");
		
		new SingleFeatureExtract().extractSingleFeature(
				originalPath,
				prefix+"/feature/","offline_train_item_15.txt",1,splitPointTime,"Tue");
		
	}
}
