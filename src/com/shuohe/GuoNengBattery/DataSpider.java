package com.shuohe.GuoNengBattery;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;




import com.shuohe.Debug;
import com.shuohe.util.Date;


public class DataSpider extends Thread {

	private String sessionId;
//	String saveDate="";
	
	public DataSpider(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while (true) {
			
//			String systemDate = Date.getSystemDateToString();
//			if(!saveDate.equals(systemDate))
//			{
//				saveDate = systemDate;
//				try {
//					sendSms();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}				
//			}		
			try {
				sendSms();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void sendSms() throws Exception {
		String qqCode = "416501600";// qq号码
		String urlString = "http://shaolinbus.intestcar.com/FullScreenService.svc";
		
//		File f = new File(DataSpider.class.getResource("").getFile()); 
//		String xml = f.getPath()+"\\SendInstantSms.xml";
		
		String xml = "E:\\国能电池\\SendInstantSms.xml";
		
		System.out.println("xml 1 = " + xml);
		String xmlFile = replace(xml, "qqCodeTmp", qqCode).getPath();
		String soapActionString = "http://tempuri.org/FullScreenService/GetAllSerializedRealTimeCarParameter";
		URL url = new URL(urlString);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		File fileToSend = new File(xmlFile);
		byte[] buf = new byte[(int) fileToSend.length()];
		new FileInputStream(xmlFile).read(buf);

		// httpConn.setRequestProperty("Accept-Encoding", "gzip,deflate");
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpConn.setRequestProperty("SOAPAction", soapActionString);
		httpConn.setRequestProperty("Cookie", "ASP.NET_SessionId="
				+ this.sessionId
				+ "; app.ui.login.account=luoxin; app.lockflag=0");
		httpConn.setRequestProperty("Content-Length",
				String.valueOf(buf.length));
		httpConn.setRequestProperty("Host", "shaolinbus.intestcar.com");
		httpConn.setRequestProperty("Proxy-Connection", "Keep-Alive");
		httpConn.setRequestProperty("User-Agent",
				"Apache-HttpClient/4.1.1 (java 1.5)");

		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		OutputStream out = httpConn.getOutputStream();

//		String s = new String(buf, "GB2312");
//		System.out.println("buf = " + s);

		out.write(buf);
		out.close();

		byte[] datas = readInputStream(httpConn.getInputStream());
		String result = new String(datas);
		// 打印返回结果
		// result = new String(result.getBytes("gbk"),"utf-8");
		System.out.println("result:" + result);

		xmlParser(result);
	}

	/**
	 * 文件内容替换
	 * 
	 * @param inFileName
	 *            源文件
	 * @param from
	 * @param to
	 * @return 返回替换后文件
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static File replace(String inFileName, String from, String to)
			throws IOException, UnsupportedEncodingException {
		File inFile = new File(inFileName);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(inFile), "utf-8"));
		File outFile = new File(inFile + ".tmp");
		PrintWriter out = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outFile), "utf-8")));
		String reading;
		while ((reading = in.readLine()) != null) {
			out.println(reading.replaceAll(from, to));
		}
		out.close();
		in.close();
		// infile.delete(); //删除源文件
		// outfile.renameTo(infile); //对临时文件重命名
		return outFile;
	}

	/**
	 * 从输入流中读取数据
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();// 网页的二进制数据
		outStream.close();
		inStream.close();
		return data;
	}

	private void xmlParser(String xml) throws Exception {
		SAXReader reader = new SAXReader();
		// Document document = reader.read(new File("books.xml"));
		// Document document = reader.read(xml);
		Document document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		Element root = document.getRootElement();

		Iterator it = root.elementIterator();
		while (it.hasNext()) {
			Element element = (Element) it.next();

			// 未知属性名称情况下
			Iterator attrIt = element.attributeIterator();
			while (attrIt.hasNext()) {
				Attribute a = (Attribute) attrIt.next();
				System.out.println("属性："+a.getValue());
			}

			// 已知属性名称情况下
			// System.out.println("id: " + element.attributeValue("id"));

			// 未知元素名情况下
			Iterator eleIt = element.elementIterator();
			while (eleIt.hasNext()) 
			{
				Element e = (Element) eleIt.next();
//				System.out.println("level1："+e.getName() + ": " + e.getText());
				
//				Element element1 = (Element) eleIt.next();
				Iterator eleIt1 = e.elementIterator();
				while (eleIt1.hasNext()) 
				{
					Element element2 = (Element) eleIt1.next();
//					System.out.println("level2："+element2.getName() + ": " + element2.getText());
					
					Iterator eleIt2 = element2.elementIterator();
					while (eleIt2.hasNext()) 
					{
						Element element3 = (Element) eleIt2.next();
//						System.out.println("level3："+element3.getName() + ": " + element3.getText());
						
						Iterator eleIt3 = element3.elementIterator();
						while (eleIt3.hasNext()) 
						{
							Element element4 = (Element) eleIt3.next();
							System.out.println("level4："+element4.getName() + ": " + element4.getText());

						    String[] aa = element4.getText().split("\\|"); 
						    Debug.i("length = "+aa.length);
							if(aa.length == 268)
							{
								Debug.i("设备码="+aa[0]);			
								Debug.i("GPS信号强度="+aa[2]);	
								Debug.i("SOC="+aa[43]);	
								Debug.i("电压="+aa[63]);
								Debug.i("电流="+aa[64]);
								Debug.i("GPS="+aa[12]);
								Debug.i("GPS="+aa[14]);
								Debug.i("时间="+aa[9]);
								
								GuonengDataBase.shaolingbusInsertData(
									aa[0], 
									Float.parseFloat(aa[2]), 
									Float.parseFloat(aa[43]), 
									Float.parseFloat(aa[63]), 
									Float.parseFloat(aa[64]), 
									Double.parseDouble(aa[12]), 
									Double.parseDouble(aa[14]) 
								);
								
								GuonengDataBase.shaolingbusUpdateData(
									aa[0], 
									Float.parseFloat(aa[2]), 
									Float.parseFloat(aa[43]), 
									Float.parseFloat(aa[63]), 
									Float.parseFloat(aa[64]), 
									Double.parseDouble(aa[12]), 
									Double.parseDouble(aa[14]) 
								);
								ShaolinBus shaolinBus = new ShaolinBus(
									aa[0], 
									Float.parseFloat(aa[2]), 
									Float.parseFloat(aa[43]), 
									Float.parseFloat(aa[63]), 
									Float.parseFloat(aa[64]), 
									Double.parseDouble(aa[12]), 
									Double.parseDouble(aa[14]) 										
								);
								ShaoLinBusDataList.getInstance().set(shaolinBus);
								
//								for(int i=0;i<aa.length;i++)
//								{
//									Debug.i("i = "+i+"  v = "+aa[i]);
//								}
							}
														
						}	
					}	
					
				}				
			}
			System.out.println();
			
			// 已知元素名情况下
			// System.out.println("title: " + element.elementText("title"));
			// System.out.println("author: " + element.elementText("author"));
			// System.out.println();
			
			
		}
	}

}
