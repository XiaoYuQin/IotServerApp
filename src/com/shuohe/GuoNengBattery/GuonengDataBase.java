package com.shuohe.GuoNengBattery;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.shuohe.database.DBhelper;

public class GuonengDataBase {

	
	public static boolean shaolingbusInsertData(String carid,float gpsSignal,float soc,float batteryVoltage,float batteryCurrent,double gpsLongitude,double gpsLatitude) 
	{
	    String sql = null;  
	    DBhelper db1 = null;  
		sql = "insert into shaolincarparamspider values(0"+","
				+"'"+carid+"'"+","
				+"'"+String.valueOf(gpsSignal)+"'"+","				
				+"'"+String.valueOf(soc)+"'"+","
				+"'"+String.valueOf(batteryVoltage)+"'"+","
				+"'"+String.valueOf(batteryCurrent)+"'"+","
				+"'"+String.valueOf(gpsLongitude)+"'"+","
				+"'"+String.valueOf(gpsLatitude)+"'"+","
				+"'"+getSystemDateToString()+"'"+")";
		 System.out.println(sql);
        db1 = new DBhelper(sql);//����DBHelper����  
        try {  
            boolean ret = db1.pst.execute();//ִ����䣬�õ������         
            db1.close();//�ر�����        
            return true;
        } catch (SQLException e) {  
            e.printStackTrace();  
            return false;
        } 
	}
	/**
	 * ���ϵͳʱ��
	 * @author    ������
	 * @date      2016��9��6�� ����4:43:23 
	 * @return
	 */
	private static String getSystemDateToString()
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = simpleDateFormat.format(new java.util.Date());
		return date;
	}
	
}
