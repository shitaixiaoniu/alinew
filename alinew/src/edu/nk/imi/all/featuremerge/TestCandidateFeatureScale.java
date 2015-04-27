package edu.nk.imi.all.featuremerge;

import java.util.ArrayList;
import java.util.Date;

public class TestCandidateFeatureScale {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		String pathpre_feature_scale = 
				"/Users/shitaixiaoniu/Desktop/alidata_new_newf/feature/old/nomerge/11-18-12-15/";
		
		//pair feature file path
		ArrayList<String> pairpath = new ArrayList<String>();
		String pair_1 = pathpre_feature_scale + "pair_action_perday_scale.txt";
		String pair_2 = pathpre_feature_scale + "pair_action_scale.txt";
		String pair_3 = pathpre_feature_scale + "pair_cvr_perday_scale_nonull.txt";
		String pair_4 = pathpre_feature_scale + "pair_cvr_scale_nonull.txt";
		String pair_5 = pathpre_feature_scale + "zbefore_feature_pair_active_range_table.txt";
		String pair_6 = pathpre_feature_scale + "zbefore_feature_pair_maxmin_action_table.txt";
		String pair_7 = pathpre_feature_scale + "zbefore_pair_distinct_matrix.txt";
		String pair_8 = pathpre_feature_scale + "feature_pair_maxmin_buy_table.txt";
		pairpath.add(pair_1);
		pairpath.add(pair_2);
		pairpath.add(pair_3);
		pairpath.add(pair_4);
		pairpath.add(pair_5);
		pairpath.add(pair_6);
		pairpath.add(pair_7);
		
		//user feature file path
		ArrayList<String> userpath = new ArrayList<String>();
		String user_1 = pathpre_feature_scale + "user_action_perday_scale.txt";
		String user_2 = pathpre_feature_scale + "user_action_perday_peritem_scale.txt";
		String user_3 = pathpre_feature_scale + "user_action_scale.txt";
		String user_4 = pathpre_feature_scale + "user_cvr_perday_scale_null.txt";
		String user_5 = pathpre_feature_scale + "user_cvr_scale_nonull.txt";
		String user_6 = pathpre_feature_scale + "zbefore_feature_user_distinct_action_table.txt";
		String user_7 = pathpre_feature_scale + "zbefore_feature_user_distinct_cvr_table.txt";
		String user_8 = pathpre_feature_scale + "zbefore_feature_user_maxmin_action_table.txt";
		String user_9 = pathpre_feature_scale + "zbefore_user_flag_matrix.txt";
		String user_10 = pathpre_feature_scale + "feature_user_maxmin_buy_table.txt";
		userpath.add(user_1);
		userpath.add(user_2);
		userpath.add(user_3);
		userpath.add(user_4);
		userpath.add(user_5);
		userpath.add(user_6);
		userpath.add(user_7);
		userpath.add(user_8);
		userpath.add(user_9);
		
		//item feature file path
		ArrayList<String> itempath = new ArrayList<String>();
		String item_1 = pathpre_feature_scale + "item_action_perday_scale.txt";
		String item_2 = pathpre_feature_scale + "item_action_perday_peruser_scale.txt";
		String item_3 = pathpre_feature_scale + "item_action_scale.txt";
		String item_4 = pathpre_feature_scale + "item_cvr_perday_scale_nonull.txt";
		String item_5 = pathpre_feature_scale + "item_cvr_scale_nonull.txt";
		String item_6 = pathpre_feature_scale + "zbefore_feature_item_distinct_action_table.txt";
		String item_7 = pathpre_feature_scale + "zbefore_feature_item_distinct_cvr_table.txt";
		String item_8 = pathpre_feature_scale + "zbefore_feature_item_frequent_ratio_all_table.txt";
		String item_9 = pathpre_feature_scale + "zbefore_item_flag_matrix.txt";
		String item_10 = pathpre_feature_scale + "feature_item_maxmin_buy_table.txt";
		itempath.add(item_1);
		itempath.add(item_2);
		itempath.add(item_3);
		itempath.add(item_4);
		itempath.add(item_5);
		itempath.add(item_6);
		itempath.add(item_7);
		itempath.add(item_8);
		itempath.add(item_9);
		

		//cpair feature file path
		ArrayList<String> cpairpath = new ArrayList<String>();
		String cpair_1 = pathpre_feature_scale + "cpair_action_perday_scale.txt";
		String cpair_2 = pathpre_feature_scale + "cpair_action_scale.txt";
		String cpair_3 = pathpre_feature_scale + "cpair_cvr_perday_scale_nonull.txt";
		String cpair_4 = pathpre_feature_scale + "cpair_cvr_scale_nonull.txt";
		String cpair_5 = pathpre_feature_scale + "zbefore_feature_cpair_active_range_table.txt";
		String cpair_6 = pathpre_feature_scale + "zbefore_cpair_distinct_matrix.txt";
		String cpair_7 = pathpre_feature_scale + "zbefore_feature_cpair_maxmin_action_table.txt";
		cpairpath.add(cpair_1);
		cpairpath.add(cpair_2);
		cpairpath.add(cpair_3);
		cpairpath.add(cpair_4);
		cpairpath.add(cpair_5);
		cpairpath.add(cpair_6);
		cpairpath.add(cpair_7);

		//catefory feature file path
		ArrayList<String> catepath = new ArrayList<String>();
		String cate_1 = pathpre_feature_scale + "category_action_perday_peruser_scale.txt";
		String cate_2 = pathpre_feature_scale + "category_action_perday_scale.txt";
		String cate_3 = pathpre_feature_scale + "category_action_scale.txt";
		String cate_4 = pathpre_feature_scale + "category_cvr_perday_scale_nonull.txt";
		String cate_5 = pathpre_feature_scale + "category_cvr_scale_nonull.txt";
		String cate_6 = pathpre_feature_scale + "zbefore_feature_cate_distinct_action_table.txt";
		String cate_7 = pathpre_feature_scale + "zbefore_feature_cate_distinct_cvr_table.txt";
		String cate_8 = pathpre_feature_scale + "zbefore_feature_cate_frequent_ratio_all_table.txt";
		String cate_9 = pathpre_feature_scale + "zbefore_cate_flag_matrix.txt";
		catepath.add(cate_1);
		catepath.add(cate_2);
		catepath.add(cate_3);
		catepath.add(cate_4);
		catepath.add(cate_5);
		catepath.add(cate_6);
		catepath.add(cate_7);
		catepath.add(cate_8);
		catepath.add(cate_9);
		
		String pathpre_feature_scale_merged = 
				"/Users/shitaixiaoniu/Desktop/alidata_new_newf/feature/old/";
		
		/***
		 * assemble features
		 */
		System.out.println("start time : "+new Date().toString());
		new ActionCandidateFeature().assembleFeature(pairpath,userpath,itempath,
				cpairpath,catepath,
				pathpre_feature_scale_merged+"15_old_offline_train_pair.txt", 
				pathpre_feature_scale_merged+"15_old_offline_train_user.txt", 
				pathpre_feature_scale_merged+"15_old_offline_train_item.txt",
				pathpre_feature_scale_merged+"15_old_offline_train_cpair.txt", 
				pathpre_feature_scale_merged+"15_old_offline_train_cate.txt");
		
		

		
		
	}
}
