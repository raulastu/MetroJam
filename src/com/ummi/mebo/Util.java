package com.ummi.mebo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.util.Log;

public class Util {
	/*
	private static String host = "192.168.0.102";
	private static int port = 21;
	private static String ftpUser = "user1";
	private static String ftpPass = "user1";
	*/
	
	//private static String host = "ftp.kybaliondesk.com";
	private static String host = "192.168.0.101";
	private static int port = 21;
	private static String ftpUser = "movil";
	private static String ftpPass = "movil";
	
	private static int justACounter = 0;

	public static void sendFTPFile(String fileToSend) {
		Log.d("com.ummi.mebo2", "connect FTP");
		FTPClient mFTPClient;
		try {
			mFTPClient = new FTPClient();
			mFTPClient.connect(host, port);
			boolean status = false;
			if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {
				status = mFTPClient.login(ftpUser, ftpPass);
				// mFTPClient.enterLocalPassiveMode();
				// .setFileTransferMode(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);

			}

			if (status) {
				// upload file
				FileInputStream srcFileStream = new FileInputStream(fileToSend);
				mFTPClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				justACounter++;
				
				mFTPClient.changeWorkingDirectory("videos");
				
				//String filef = "mebo_upload" + justACounter + ".mp4";
				String filef = "angelhack88.mp4";
				status = mFTPClient.storeFile(filef, srcFileStream);
				Log.d("com.ummi.mebo2", "result of FTP transfer amigoooo = " + status);
				srcFileStream.close();
				
				//sent HTTP message to notify server
				//Util.sendHTTPHello("kybaliondesk.com", 80, "/uploadvid.asp?v=mebo_upload"+justACounter+".mp4", "mebo_upload"+justACounter+".mp4");
				//Util.sendHTTPGet("http://kybaliondesk.com/uploadvid.asp?v="+filef);
			}

			// now disconnect from FTP
			mFTPClient.logout();
			mFTPClient.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("com.ummi.mebo2", "Error: could not connect to host " + e.getMessage());
		}

	}

	public static void sendHTTPHello(String host, int port, String path, String postBody) {
		HttpHost target = new HttpHost(host, port);
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(path);
		HttpEntity results = null;
		try {
			HttpResponse response = client.execute(target, get);
			results = response.getEntity();
			// return EntityUtils.toString(results);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("com.ummi.mebo2", "Error: could not connect to HTTP " + e.getMessage());
		} finally {
			/*
			 * if (results!=null) try { results.consumeContent(); } catch
			 * (IOException e) { // empty, Checked exception but don't care }
			 */
		}
	}
	
	
   public static void sendHTTPGet(String url) {
      InputStream inputStream = null;
      try {
          HttpClient httpclient = new DefaultHttpClient();
          HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
          inputStream = httpResponse.getEntity().getContent();
      } catch (Exception e) {
         // Log.d("InputStream", e.getLocalizedMessage());
          Log.d("com.ummi.mebo2", "Error: could not connect to HTTP " + e.getMessage());
      }
      String inputStreamString = new Scanner(inputStream,"UTF-8").useDelimiter("\\A").next();
      try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			 
		}
      Log.d("com.ummi.mebo2", "respuesta de kybalion=" + inputStreamString);
  }
}
