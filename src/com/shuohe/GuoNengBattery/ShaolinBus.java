package com.shuohe.GuoNengBattery;
public class ShaolinBus
{
	private String carid = null;
	private float gpsSignal;
	private float soc;
	private float batteryVoltage;
	private float batteryCurrent;
	private double gpsLongitude;
	private double gpsLatitude;
	
	public ShaolinBus(String carid,float gpsSignal,float soc,float batteryVoltage,float batteryCurrent,double gpsLongitude,double gpsLatitude)
	{
		this.carid = carid;
		this.gpsSignal = gpsSignal;
		this.soc = soc;
		this.batteryVoltage = batteryVoltage;
		this.batteryCurrent = batteryCurrent;
		this.gpsLongitude = gpsLongitude;
		this.gpsLatitude = gpsLatitude;
	}		
	public String getCarid() {
		return carid;
	}
	public float getGpsSignal() {
		return gpsSignal;
	}
	public float getSoc() {
		return soc;
	}
	public float getBatteryVoltage() {
		return batteryVoltage;
	}
	public float getBatteryCurrent() {
		return batteryCurrent;
	}
	public double getGpsLongitude() {
		return gpsLongitude;
	}
	public double getGpsLatitude() {
		return gpsLatitude;
	}	
	
}