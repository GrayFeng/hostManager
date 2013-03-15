package cn.fjy.hostmanager;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 * Description: Switch.java
 * All Rights Reserved.
 * @version 1.0  2012-12-19 下午2:59:56  
 * @author Gray(jy.feng@zuche.com) 
 */

public class Switch {
	
	private StringBuffer template = new StringBuffer();
	
	private static Set<String> zucheDomainSet = new HashSet<String>();
	
	private static Set<String> zucheDomainSet4test = new HashSet<String>();
	
	static{
		zucheDomainSet.add("www.zuche.com");
		zucheDomainSet.add("order.zuche.com");
		zucheDomainSet.add("easyride.zuche.com");
		zucheDomainSet.add("huodong.zuche.com");
		zucheDomainSet.add("service.zuche.com");
		zucheDomainSet.add("help.zuche.com");
		zucheDomainSet.add("carbusiness.zuche.com");
		zucheDomainSet.add("static.zuchecdn.com");
		zucheDomainSet.add("static.zuche.com");
		zucheDomainSet.add("member.zuche.com");
		zucheDomainSet.add("login.zuche.com");
		zucheDomainSet.add("changzu.zuche.com");
		zucheDomainSet.add("passport.zuche.com");
		zucheDomainSet.add("mycar.zuche.com");
		zucheDomainSet.add("leasing.zuche.com");
		zucheDomainSet.add("kuangjia.zuche.com");
		zucheDomainSet.add("shouji.zuche.com");
		zucheDomainSet.add("en.zuche.com");
	
		zucheDomainSet4test.add("10.100.20.120 www.zuche.com");
		zucheDomainSet4test.add("10.100.20.120 order.zuche.com");
		zucheDomainSet4test.add("10.100.20.120 easyride.zuche.com");
		zucheDomainSet4test.add("10.100.20.121 changzu.zuche.com");
		zucheDomainSet4test.add("10.100.20.123 service.zuche.com");
		zucheDomainSet4test.add("10.100.20.124 static.zuchecdn.com");
		zucheDomainSet4test.add("10.100.20.125 mycar.zuche.com");
		zucheDomainSet4test.add("10.100.20.126 carbusiness.zuche.com");
		zucheDomainSet4test.add("10.100.20.126 leasing.zuche.com");
		zucheDomainSet4test.add("10.100.20.126 kuangjia.zuche.com");
		zucheDomainSet4test.add("10.100.20.127 huodong.zuche.com");
		zucheDomainSet4test.add("10.100.20.127 shouji.zuche.com");
		zucheDomainSet4test.add("10.100.20.127 en.zuche.com");
		zucheDomainSet4test.add("10.100.20.129 passport.zuche.com");
	}
	public static void main(String[] args) {
		Switch switchDNS = new Switch();
		switchDNS.readTemplate();
		if(switchDNS.template.length()>0){
			switchDNS.swDNS(args[0]);
			System.out.println("DNS切换完成");
		}else{
			System.err.println("初始化模板文件异常");
		}
	}
	
	private void swDNS(String ip){
		FileOutputStream fos = null;
		try{
			fos = new FileOutputStream(new File("C:\\Windows\\System32\\drivers\\etc\\hosts"));
			if("localhost".equals(ip)){
				ip = "127.0.0.1";
			}
			if("test".equals(ip)){
				for(String domain:zucheDomainSet4test){
					template.append(domain+"\r\n");
				}
			}else if(!"real".equals(ip)){
				for(String domain:zucheDomainSet){
					template.append(ip+"   "+domain+"\r\n");
				}
			}
			fos.write(template.toString().getBytes());
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void readTemplate(){
		DataInputStream dis = null;
		try{
			dis = new DataInputStream(Switch.class.getResourceAsStream("/hosts"));
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = dis.read(buffer))>-1){
				template.append(new String(buffer, 0, len));
			}
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
