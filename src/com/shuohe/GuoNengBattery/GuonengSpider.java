package com.shuohe.GuoNengBattery;

import com.shuohe.Debug;

public class GuonengSpider extends Thread{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		ShaoLinBusDataList shaoLinBusDataList = new ShaoLinBusDataList();
		
		Debug.i("init DataSpider");
		//��¼���ֿͳ�ϵͳ
		LoginSpider loginSpider = new LoginSpider();
		loginSpider.start();
		//ѭ���ȴ�
		while(true)
		{
			if(loginSpider.getIsResult()!=0) break;
		}
		
		if(loginSpider.getIsResult()==-1) return;
		else if(loginSpider.getIsResult()==1)
		{
			DataSpider dataSpider = new DataSpider(loginSpider.getSessionId());
			dataSpider.start();
		}
	}
	
}
