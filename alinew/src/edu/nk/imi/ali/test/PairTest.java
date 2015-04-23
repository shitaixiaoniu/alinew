package edu.nk.imi.ali.test;



import edu.nk.imi.ali.feature.PairFeatureExtract;
import edu.nk.imi.ali.feature.SingleFeatureExtract;

public class PairTest {
	
	public static void main(String[] args) {
		PairFeatureExtract pfe = new PairFeatureExtract();
		//在线train
		String prefix="/Users/shitaixiaoniu/Desktop/alidata_new_newf";
		String originalPath=prefix+"/pair/original/original_online_train_1118_1217.txt";
		String originalPath_test=prefix+"/pair/original/original_online_test_1118_1218.txt";
		
		String pairFeaturePath = prefix+"/feature/online_train_pair.txt";
		String cpairFeaturePath = prefix+"/feature/online_train_cpair.txt";
		String splitPointTime="2014-12-19 00";
		
		
//		try {
//			pfe.extractPairFeature(originalPath, pairFeaturePath, splitPointTime, true);
//			
//			pfe.extractPairFeature(originalPath, cpairFeaturePath , splitPointTime, false);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		new SingleFeatureExtract().extractSingleFeature(
				originalPath_test,
				prefix+"/feature/","online_test_user.txt",0,splitPointTime,"Fri");
		
		new SingleFeatureExtract().extractSingleFeature(
				originalPath_test,
				prefix+"/feature/","online_test_cate.txt",2,splitPointTime,"Fri");
		
		new SingleFeatureExtract().extractSingleFeature(
				originalPath_test,
				prefix+"/feature/","online_test_item.txt",1,splitPointTime,"Fri");
		
	}
}
