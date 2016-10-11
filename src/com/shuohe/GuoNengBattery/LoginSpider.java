package com.shuohe.GuoNengBattery;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.shuohe.Debug;






public class LoginSpider extends Thread{

	public int isResult = 0;
	public String sessionId;

	
	
	
	
	public synchronized int getIsResult() {
		return isResult;
	}
	private synchronized void setIsResult(int isResult) {
		this.isResult = isResult;
	}
	public synchronized String getSessionId() {		
		return this.sessionId;
	}
	private synchronized void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}




	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		try {
			sendSms();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

	//POST http://shaolinbus.intestcar.com/Login/SubmitLogin HTTP/1.1
	//Host: shaolinbus.intestcar.com
	//Connection: keep-alive
	//Content-Length: 35
	//Origin: http://shaolinbus.intestcar.com
	//X-Requested-With: XMLHttpRequest
	//User-Agent: Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.154 Safari/537.36 LBBROWSER
	//Content-Type: application/x-www-form-urlencoded; charset=UTF-8
	//Accept: */*
	//Referer: http://shaolinbus.intestcar.com/
	//Accept-Encoding: gzip, deflate
	//Accept-Language: zh-CN,zh;q=0.8
	//Cookie: app.ui.login.account=luoxin; app.lockflag=0
	//
	//account=luoxin&password=18631739396
	
	public void sendSms() throws Exception {
		String namePassword = "account=luoxin&password=18631739396";
        String urlString = "http://shaolinbus.intestcar.com/Login/SubmitLogin";
//        String xml = DataSpider.class.getClassLoader().getResource("SendInstantSms.xml").getPath()/*+"com/shuohe/GuoNengBattery/SendInstantSms.xml"*/;
//        String xml = "E:\\开发\\代码\\javaSoap\\src\\SendInstantSms.xml";
//        System.out.println("xml = "+xml);
//        String soapActionString = "http://tempuri.org/FullScreenService/GetAllSerializedRealTimeCarParameter";
        URL url = new URL(urlString);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//        byte[] buf = new byte[(int) fileToSend.length()];


//        httpConn.setRequestProperty("Accept-Encoding", "gzip,deflate");
        httpConn.setRequestProperty("Host", "shaolinbus.intestcar.com");
        httpConn.setRequestProperty("Connection", "keep-alive");
        httpConn.setRequestProperty("Content-Length", namePassword.length()+"");
        httpConn.setRequestProperty("Origin", "http://shaolinbus.intestcar.com");
        httpConn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        httpConn.setRequestProperty("User-Agent", "Apache-HttpClient/4.1.1 (java 1.5)");
        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpConn.setRequestProperty("Accept", "*/*");      
        httpConn.setRequestProperty("Referer", "http://shaolinbus.intestcar.com/");      
        httpConn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");      
        httpConn.setRequestProperty("Cookie", "app.ui.login.account=luoxin; app.lockflag=0");
        
        Debug.i(namePassword.length()+"");

//        httpConn.setRequestProperty("Proxy-Connection", "Keep-Alive");
        
        
        
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        OutputStream out = httpConn.getOutputStream();                        
        
        
        out.write(namePassword.getBytes());
        out.close();

        byte[] datas=readInputStream(httpConn.getInputStream());
                
        String result=new String(datas,"UTF-8");
        //打印返回结果
        System.out.println("result:" + result);

        
        String cookieval = httpConn.getHeaderField("set-cookie"); 
        String sessionid = null; 
        if(cookieval != null) 
        { 
        	Debug.i("cookieval = "+cookieval);
        	sessionid = cookieval.substring(cookieval.indexOf("ASP.NET_SessionId=")+"ASP.NET_SessionId=".length(), cookieval.indexOf(";")); 
        	setIsResult(1);
        	setSessionId(sessionid);
        } 
        else {
        	setIsResult(-1);
		}
        Debug.i("sessionid = "+this.sessionId);
        
    }
	
    /**
     * 从输入流中读取数据
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len = inStream.read(buffer)) !=-1 ){
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();//网页的二进制数据
        outStream.close();
        inStream.close();
        return data;
    }
	
}
