package edu.nk.imi.ali.util;

import java.io.File;

public class UtilFile {
	
	public static void deleFile(String filepath)
	{
		File f = new File(filepath);  // ����Ҫɾ�����ļ�λ��
		if(f.exists())
		    f.delete();
	}

}
