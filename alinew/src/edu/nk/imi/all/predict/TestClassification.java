package edu.nk.imi.all.predict;

import java.util.Date;

public class TestClassification {
	
	public static void main(String[] args) {
		
		String pathpre_testdata_uicpair = 
				"/Users/shitaixiaoniu/Desktop/alidata_new_newf/online_testtrain_pair/";
		
		String pathpre_feature_scale_merged = 
				"/Users/shitaixiaoniu/Desktop/alidata_new_newf/feature/old/";
		
		String pathpre_forest=
				"/Users/shitaixiaoniu/Desktop/alidata_new_newf/predict/2015-4-24_mergeresult/";
		
		String pathpre_res=
				"/Users/shitaixiaoniu/Desktop/alidata_new_newf/offline_traintest_pair/offline_model/res_old/";
		System.out.println("start time : "+new Date().toString());
		//predict
		new ActionMergeAndPredictRF().filterRecord(
				pathpre_feature_scale_merged+"old_online_test_pair.txt",
				pathpre_testdata_uicpair+"online_test_uic_filter_item_feature_old.txt",
				pathpre_feature_scale_merged+"old_online_test_user.txt",
				pathpre_feature_scale_merged+"old_online_test_cpair.txt",
				pathpre_feature_scale_merged+"old_online_test_cate.txt",
				pathpre_testdata_uicpair+"online_test_uic_filter.txt",
				pathpre_forest+"train_online_all_new_sample_0.0113_50_oldfeature_rf_describe.info",
				pathpre_forest+"forest.seq",
				pathpre_forest+"online_mergef_200_11_sample_50.txt");
		
//		new TestComputeF().computeF(pathpre_forest+"predict17_offline15_newf_200_11_sample_50.txt",
//				pathpre_testdata_uicpair+"offline_train_positive_pair_1217_filter.txt");
//		
//		new TestDeleteZero().delPreDataBuy(
//				pathpre_res+"offline_oldf_300_11_sample_50.txt", 
//				pathpre_testdata_uicpair+"offline_train_positive_pair_1217.txt", 
//				pathpre_res+"offline_oldf_300_11_sample_50_del.txt");
//		
//		new TestComputeF().computeF(pathpre_res+"offline_oldf_300_11_sample_50_del.txt",
//				pathpre_testdata_uicpair+"online_train_positive_pair_1218_filter.txt");
		
		
		System.out.println("end time : "+new Date().toString());
	}

}
