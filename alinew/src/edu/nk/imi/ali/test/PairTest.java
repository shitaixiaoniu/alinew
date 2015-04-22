package edu.nk.imi.ali.test;



import edu.nk.imi.ali.feature.PairFeatureExtract;

public class PairTest {
	
	public static void main(String[] args) {
		PairFeatureExtract pfe = new PairFeatureExtract();
		String prefix="/Users/shitaixiaoniu/Desktop/alidata_new_newf/";
		String originalPath=prefix+"datasource/tianchi_mobile_recommend_train_user.txt";
		String pairFeaturePath = prefix+"/feature/online_test_pair.txt";
		String cpairFeaturePath = prefix+"/feature/online_test_cpair.txt";
		String splitPointTime="2014-12-19 00";
		
		
		try {
			pfe.extractPairFeature(originalPath, pairFeaturePath, splitPointTime, true);
			
			pfe.extractPairFeature(originalPath, cpairFeaturePath , splitPointTime, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
