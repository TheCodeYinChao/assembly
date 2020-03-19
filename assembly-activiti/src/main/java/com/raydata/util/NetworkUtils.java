package com.raydata.util;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class NetworkUtils {

	

	
	public final static String getClientIp(HttpServletRequest request) {
		
		String ip = request.getHeader("X-Forwarded-For");
		
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-Ip");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-Ip");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			
			
		}else if(ip.length() > 15) {
			
			String[] ips = ip.split(",");
			for (int i = 0; i < ips.length; i++) {
				String strIp = (String) ips[i];
				
				if(!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
				
			}
			
		
		}
		
		return ip;
		
	}
	
	/**
	 * getServerIps
	 */
	public static final String[] getServerIps() {
		
		List<String> ips = new ArrayList<String>();
		
		try {
			Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
			
			while(nets.hasMoreElements()) {
				NetworkInterface netint = nets.nextElement();
				Enumeration<InetAddress> addresses = netint.getInetAddresses();
				
				while(addresses.hasMoreElements()) {
					InetAddress inet = addresses.nextElement();
					if(inet != null && inet instanceof Inet4Address) {
						String ip = inet.getHostAddress();
						ips.add(ip);
					}
				}
			}
			
		} catch (SocketException e) {
			e.printStackTrace();
			return null;
		}
		
		
		return ips.toArray(new String[ips.size()]);
		
	}
	
	public static void main(String[] args) {
		System.out.println(Arrays.toString(getServerIps()));
	}
	
}
