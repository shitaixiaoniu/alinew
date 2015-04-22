package edu.nk.imi.ali.test;

import edu.nk.imi.ali.feature.SingleFeatureExtract;

public class Test {

	public static void main(String[] args) {
		String pathpre_source = "/Users/shitaixiaoniu/Desktop/alidata_new_newf/datasource/";
		String pathpre_feature = "/Users/shitaixiaoniu/Desktop/alidata_new_newf/feature/";
		
		System.out.println("------------start--------");
		new SingleFeatureExtract().extractSingleFeature(
				pathpre_source+"tianchi_mobile_recommend_train_user.txt",
				pathpre_feature,"online_test_item",1,"2014-12-19 00","Fri");
		System.out.println("------------end--------");
	}

}
