package de.wagentim.collector.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NetUtils
{
	private static Logger logger = LoggerFactory.getLogger(NetUtils.class);

	public String getHostMac() throws UnknownHostException, SocketException
	{
		InetAddress localhost = InetAddress.getLocalHost();
		NetworkInterface ni = NetworkInterface.getByInetAddress(localhost);

		if (ni == null)
		{
			logger.info("Cannot get Host Network Interface");
			return IConstants.TXT_EMPTY_STRING;
		}

		byte[] hardwareAddress = ni.getHardwareAddress();

		if (hardwareAddress == null || hardwareAddress.length < 1)
		{
			logger.info("Cannot get Host Network Mac Address");
			return IConstants.TXT_EMPTY_STRING;
		}

		return formatHardwareAddress(hardwareAddress);
	}

	public void downloadFile(String url, String fileName)
	{
		try {
			logger.info("Download File: {}", fileName);
			URL link = new URL(url);
			ReadableByteChannel readableByteChannel = Channels.newChannel(link.openStream());
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			FileChannel fileChannel = fileOutputStream.getChannel();
			fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	public String getMacByLocalIP(String ip) throws UnknownHostException, SocketException
	{
		InetAddress localIP = InetAddress.getByName(ip);
		NetworkInterface ni = NetworkInterface.getByInetAddress(localIP);

		if (ni == null)
		{
			logger.info("Cannot get Network Interface with IP: " + ip);
			return IConstants.TXT_EMPTY_STRING;
		}

		byte[] macAddress = ni.getHardwareAddress();

		if (macAddress == null || macAddress.length < 1)
		{
			logger.info("Cannot get Host Network Mac Address");
			return IConstants.TXT_EMPTY_STRING;
		}
		return formatHardwareAddress(macAddress);
	}

	public Map<String, String> getAllNetworkInterfaceMacAddress() throws SocketException
	{
		Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
		Map<String, String> macs = new HashMap<String, String>();
		
		while (networkInterfaces.hasMoreElements())
		{
			NetworkInterface ni = networkInterfaces.nextElement();
			
			if (ni == null)
			{
				logger.info("Cannot get Network Interface");
				continue;
			}
			
			byte[] hardwareAddress = ni.getHardwareAddress();
			
			if (hardwareAddress != null)
			{
				macs.put(ni.getDisplayName(), formatHardwareAddress(hardwareAddress));
			}
		}
		
		return macs;
	}

	private String formatHardwareAddress(byte[] hardwareAddress)
	{
		String[] hexadecimal = new String[hardwareAddress.length];
		for (int i = 0; i < hardwareAddress.length; i++)
		{
			hexadecimal[i] = String.format("%02X", hardwareAddress[i]);
		}
		return String.join("-", hexadecimal);
	}

	public static void main(String[] args)
	{
		NetUtils nu = new NetUtils();
		try
		{
			Map<String, String> result = nu.getAllNetworkInterfaceMacAddress();
			Iterator<String> it = result.keySet().iterator();
			while(it.hasNext())
			{
				String name = it.next();
				logger.info(name + " -> " + result.get(name));
			}
			
		} catch (SocketException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
