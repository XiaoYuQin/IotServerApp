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
				Debug.i("ShaoLinBusDataList:修改数据"+this.shaolinbusList.get(i).getCarid());
				printAllData();
				return;
			}
		}	
		if(checkResult == false)
		{			
			this.shaolinbusList.add(bus);			
			Debug.i("ShaoLinBusDataList:添加数据"+bus.getCarid());
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
	 * 转换为带格式化输出的Json字符串
	 * @author    秦晓宇
	 * @date      2016年4月13日 下午5:01:55 
	 * @param object
	 * @return
	 */
	private static String toJsonByPretty(Object object)
	{
		Gson gson = new GsonBuilder()  
		//.excludeFieldsWithoutExposeAnnotation() 						//不导出实体中没有用@Expose注解的属性  
		.enableComplexMapKeySerialization() 							//支持Map的key为复杂对象的形式  
		.serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")		//时间转化为特定格式    
		//.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)		//会把字段首字母大写,注:对于实体上使用了@SerializedName注解的不会生效.  
		.setPrettyPrinting() 											//对json结果格式化.  
		//.setVersion(1.0)    											//有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.  
																		//@Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么  
																		//@Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用.  
		.create();  
		String str = gson.toJson(object);
		return str;
	}
	
	
}
