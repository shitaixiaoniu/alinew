package edu.nk.imi.ali.util;

import java.io.File;

public class UtilFile {
	
	public static void deleFile(String filepath)
	{
		File f = new File(filepath);  // 输入要删除的文件位置
		if(f.exists())
		    f.delete();
	}

}
