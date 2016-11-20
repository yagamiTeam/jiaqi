package com.ant.jiaqi.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	private static String[] dateFormat = {"yyyy-MM-dd",	//0
			"yyyy/MM/dd",								//1
			"yyyyMMdd",									//2
			"yyyy-MM-dd HH:mm:ss",						//3
			"yyyy/MM/dd HH:mm:ss",						//4
			"yyyyMMddHHmmss",							//5
			"yyyyMMddHHmmssSSS"							//6
	};
	
	public Date getToday() {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat[0]);
		return null;
	}
	
	public Date getDate() {
		return null;
	}
	
}
