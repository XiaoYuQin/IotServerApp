package com.shuohe.util;

import java.text.SimpleDateFormat;

public class Date {

	/**
	 * ���ϵͳʱ��
	 * @author    ������
	 * @date      2016��9��6�� ����4:43:23 
	 * @return
	 */
	public static String getSystemDateToString()
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = simpleDateFormat.format(new java.util.Date());
		return date;
	}
}
