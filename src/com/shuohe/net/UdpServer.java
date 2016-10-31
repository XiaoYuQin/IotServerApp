package com.shuohe.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class UdpServer {
	
	private int port;
	private byte[] buffer;
	private DatagramSocket socket;
	private DatagramPacket packet;
	
	public UdpServer(int port,int timeOut) throws Exception
	{
		this.port = port;
		buffer = new byte[1024];
		socket = new DatagramSocket(this.port); 
		packet = new DatagramPacket(buffer , buffer.length);  
		if(timeOut > 0)
			socket.setSoTimeout(timeOut);  
	}
	public String recive() throws Exception,SocketTimeoutException{
		
//		try{
			this.socket.receive(packet);
//		}
//		catch(SocketTimeoutException e)
//		{
//			e.printStackTrace();
//		}
   	 	String s = new String(packet.getData( ), 0, packet.getLength( )); 
   	 	return s;
	}
	
	public void send(String ip,byte[] data) throws Exception{
		packet = new DatagramPacket(data,data.length,InetAddress.getByName(ip),this.port);
		socket.send(packet);
	}
	public void close()
	{
		this.socket.close();
	}
	
	
	
}