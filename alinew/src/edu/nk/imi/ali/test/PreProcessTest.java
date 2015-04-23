package edu.nk.imi.ali.test;

import edu.nk.imi.ali.preprocess.DataPreProcess;

public class PreProcessTest {
	public static void main(String[] args) {
		String prefix="/Users/shitaixiaoniu/Desktop/alidata_new_newf/datasource/";
		String outPrefix = "/Users/shitaixiaoniu/Desktop/alidata_new_newf/pair/";
		String originalPath=prefix+"tianchi_mobile_recommend_train_user.txt";
		String originalCandidateItemPath =prefix+"candidate_item_distinct.txt";
		
		//offline test = online train
		
		String onlineTestOriginalPath=outPrefix+"original/original_online_test_1118_1218.txt";
		String onlineTrainOfflineTestOriginalPath=outPrefix+"original/original_online_train_1118_1217.txt";
		String offlineTrainOriginalPath=outPrefix+"original/original_offline_train_1118_1216.txt";
		String onlineTrainPredicOriginaltPath=outPrefix+"original/original_1218.txt";
		String offlineTrainPredictOriginalPath=outPrefix+"original/original_1217.txt";
		
		String onlineTrainOfflineTestUICPairPath = outPrefix+"original/online_train_offline_test_uic.txt";
		String offlineTrainUICPairPath = outPrefix+"original/original_offline_train_uic.txt";
		
		String onlineTestUICPairPath = outPrefix+"online_test_uic.txt";
		String onlineTestUICPairFilterPath = outPrefix+"online_test_uic_filter.txt";
		
		String onlineTrainPositivePairPath = outPrefix+"online_train_positive_pair_1218.txt";	
		String offlineTrainPositivePairPath = outPrefix+"offline_train_positive_pair_1217.txt";
		
		String onlineTrainUICYPairPath = outPrefix+"online_train_uicy.txt";
		String offlineTrainUICYPairPath = outPrefix+"offline_train_uicy.txt";
		

		DataPreProcess dpp = new DataPreProcess();
		try {
//			//online test 11-18~12-18
//			dpp.getOriginalDataByRange("2014-11-18 00", "2014-12-18 23", originalPath, onlineTestOriginalPath);
//			//online train 11-18 ~ 12-17 //也是offline的 test
//			dpp.getOriginalDataByRange("2014-11-18 00", "2014-12-17 23", originalPath, onlineTrainOfflineTestOriginalPath);
//			//online train 11-18~12-16
//			dpp.getOriginalDataByRange("2014-11-18 00", "2014-12-16 23", originalPath, offlineTrainOriginalPath);
//			//1218号数据 用于线上的y标签 用于线下的预测
//			dpp.getOriginalDataByRange("2014-12-18 00", "2014-12-18 23", originalPath, onlineTrainPredicOriginaltPath);
//			//1217号数据 用于线下的y标签
//			dpp.getOriginalDataByRange("2014-12-17 00", "2014-12-17 23", originalPath, offlineTrainPredictOriginalPath);
			
//			//获得 train 、test的uic pair
//			dpp.getUICPairFromOriginal(onlineTrainOfflineTestOriginalPath, onlineTrainOfflineTestUICPairPath);
//			dpp.getUICPairFromOriginal(onlineTestOriginalPath, onlineTestUICPairPath);
//			dpp.getUICPairFromOriginal(offlineTrainOriginalPath, offlineTrainUICPairPath);
//			
//			//获得train 的正例
//			dpp.getPositiveUIPair(onlineTrainPredicOriginaltPath, onlineTrainPositivePairPath);
//			dpp.getPositiveUIPair(offlineTrainPredictOriginalPath, offlineTrainPositivePairPath);
//			
//			//将train 拼接为uicy
//			dpp.getTrainUICYPair(onlineTrainOfflineTestUICPairPath, onlineTrainPositivePairPath, onlineTrainUICYPairPath);
//			dpp.getTrainUICYPair(offlineTrainUICPairPath, offlineTrainPositivePairPath, offlineTrainUICYPairPath);
			//将线上test uic用 candidateitem 过滤
			dpp.filterPairByCandidate(originalCandidateItemPath, onlineTestUICPairPath, onlineTestUICPairFilterPath );
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
