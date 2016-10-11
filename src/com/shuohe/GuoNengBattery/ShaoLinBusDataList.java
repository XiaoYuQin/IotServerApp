package com.shuohe.GuoNengBattery;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shuohe.Debug;


//@SuppressWarnings("unused")
public class ShaoLinBusDataList {
	
	private static ShaoLinBusDataList instance;
	
	
	
	 
	private ArrayList<ShaolinBus> shaolinbusList;
	
	public ShaoLinBusDataList()
	{
		shaolinbusList = new ArrayList<ShaolinBus>();		
		instance = this;
	}	
	
	public static synchronized final ShaoLinBusDataList getInstance()
	{
		if (instance != null) return instance;
		throw new RuntimeException("ShaoLinBusDataList should be created before accessed");
	}
	
	public synchronized void set(ShaolinBus bus)
	{
		boolean checkResult = false;
		for(int i=0;i<this.shaolinbusList.size();i++)
		{
			if(this.shaolinbusList.get(i).getCarid().equals(bus.getCarid()))
			{
				this.shaolinbusList.set(i, bus);
				Debug.i("ShaoLinBusDataList:�޸�����"+this.shaolinbusList.get(i).getCarid());
				printAllData();
				return;
			}
		}	
		if(checkResult == false)
		{			
			this.shaolinbusList.add(bus);			
			Debug.i("ShaoLinBusDataList:�������"+bus.getCarid());
		}	

	}
	
	private void printAllData()
	{
		Debug.i("ShaoLinBusDataList:printAllData()");
		for(int i=0;i<this.shaolinbusList.size();i++)
		{			
			Debug.i("ShaoLinBusDataList "+i+" : getCarid = "+this.shaolinbusList.get(i).getCarid());
			Debug.i("ShaoLinBusDataList "+i+" : getBatteryCurrent = "+this.shaolinbusList.get(i).getBatteryCurrent());
			Debug.i("ShaoLinBusDataList "+i+" : getBatteryVoltage = "+this.shaolinbusList.get(i).getBatteryVoltage());
			Debug.i("ShaoLinBusDataList "+i+" : getGpsLatitude = "+this.shaolinbusList.get(i).getGpsLatitude());
			Debug.i("ShaoLinBusDataList "+i+" : getGpsLongitude = "+this.shaolinbusList.get(i).getGpsLongitude());
			Debug.i("ShaoLinBusDataList "+i+" : getGpsSignal = "+this.shaolinbusList.get(i).getGpsSignal());
			Debug.i("ShaoLinBusDataList "+i+" : getSoc = "+this.shaolinbusList.get(i).getSoc());
		}	
	}
	
	public String getDataByJson() 
	{
		String ret = null;
		ret = toJsonByPretty(this.shaolinbusList);		
		Debug.i("ShaoLinBusDataFormat getFromJson() = "+ret);
		return ret;
	}
	/**
	 * ת��Ϊ����ʽ�������Json�ַ���
	 * @author    ������
	 * @date      2016��4��13�� ����5:01:55 
	 * @param object
	 * @return
	 */
	private static String toJsonByPretty(Object object)
	{
		Gson gson = new GsonBuilder()  
		//.excludeFieldsWithoutExposeAnnotation() 						//������ʵ����û����@Exposeע�������  
		.enableComplexMapKeySerialization() 							//֧��Map��keyΪ���Ӷ������ʽ  
		.serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")		//ʱ��ת��Ϊ�ض���ʽ    
		//.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)		//����ֶ�����ĸ��д,ע:����ʵ����ʹ����@SerializedNameע��Ĳ�����Ч.  
		.setPrettyPrinting() 											//��json�����ʽ��.  
		//.setVersion(1.0)    											//�е��ֶβ���һ��ʼ���е�,�����Ű汾��������ӽ���,��ô�ڽ������л��ͷ����л���ʱ��ͻ���ݰ汾����ѡ���Ƿ�Ҫ���л�.  
																		//@Since(�汾��)��������ʵ���������.�����ֶο���,���Ű汾��������ɾ��,��ô  
																		//@Until(�汾��)Ҳ��ʵ���������,GsonBuilder.setVersion(double)������Ҫ����.  
		.create();  
		String str = gson.toJson(object);
		return str;
	}
	
	
}
