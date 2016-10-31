package com.shuohe.ServerMonitor;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.shuohe.database.DBhelper;

public class ServerMonitor extends Thread{

	boolean isRun = true;
	
	String saveDate = "";
	public ServerMonitor()
	{
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		System.out.println("ServerMonitor:run");
		
		while(isRun)
		{
			//每秒时间更新
			String systemDate = getSystemDateToString();
			if(!saveDate.equals(systemDate))
			{
				saveDate = systemDate;
				MonitorServiceImpl monitorServiceImpl = new MonitorServiceImpl(); 
				try {
					
					MonitorInfoBean monitorInfoBean = new MonitorInfoBean();
					monitorInfoBean = monitorServiceImpl.getMonitorInfoBean();
					System.out.println(monitorInfoBean.getCpuRatio());
					System.out.println(monitorInfoBean.getFreeMemory());
					System.out.println(monitorInfoBean.getFreePhysicalMemorySize());
					System.out.println(monitorInfoBean.getMaxMemory());
					System.out.println(monitorInfoBean.getOsName());
					System.out.println(monitorInfoBean.getUsedMemory());
					System.out.println(monitorInfoBean.getTotalMemorySize());
					
					float memory_utilization = (float)((float)monitorInfoBean.getUsedMemory()/monitorInfoBean.getTotalMemorySize());
					System.out.println("memory_utilization = "+memory_utilization);
					serverMointorUpdateData((float)monitorInfoBean.getCpuRatio(),memory_utilization);
					serverMonitorInsertData((float)monitorInfoBean.getCpuRatio(),memory_utilization);
									
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(300);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		System.out.println("ServerMonitor:destroy");
		isRun = false;
	}
	
	
	public static boolean serverMointorUpdateData(float cpu,float memory) 
	{
	    String sql = null;  
	    DBhelper db1 = null;  
		sql = "update hardware_monitor set "
//				+ "carID="+carid+","
				+ "cpu_utilization="+"'"+String.valueOf(cpu)+"'"+","
				+ "memory_utilization="+"'"+String.valueOf(memory)+"'"+","
				+ "date="+"'"+getSystemDateToString()+"'"+" "
				+ "where id="+"'0'";

		 System.out.println(sql);
        db1 = new DBhelper(sql);//创建DBHelper对象  
        try {  
            boolean ret = db1.pst.execute();//执行语句，得到结果集         
            db1.close();//关闭连接        
            return true;
        } catch (SQLException e) {  
            e.printStackTrace();  
            return false;
        } 
	}
	public static boolean serverMonitorInsertData(float cpu,float memory) 
	{
	    String sql = null;  
	    DBhelper db1 = null;  
		sql = "insert into hardware_monitor_history values(0"+","
				+"'"+String.valueOf(cpu)+"'"+","				
				+"'"+String.valueOf(memory)+"'"+","
				+"'"+getSystemDateToString()+"'"+")";
		 System.out.println(sql);
        db1 = new DBhelper(sql);//创建DBHelper对象  
        try {  
            boolean ret = db1.pst.execute();//执行语句，得到结果集         
            db1.close();//关闭连接        
            return true;
        } catch (SQLException e) {  
            e.printStackTrace();  
            return false;
        } 
	}
	/**
	 * 获得系统时间
	 * @author    秦晓宇
	 * @date      2016年9月6日 下午4:43:23 
	 * @return
	 */
	private static String getSystemDateToString()
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = simpleDateFormat.format(new java.util.Date());
		return date;
	}
}
