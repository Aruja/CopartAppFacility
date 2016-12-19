package com.copart;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ZipCode {
	
	
	public static void main(String args[])
	{
		
		String file = "data/input.txt";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		String zipCode = null;
		try {
			zipCode = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		getZipCode(zipCode);
	}
	
	
	public static String getZipCode(String zipCode)
	{
		
		 try {
			 	String clientKey="SH8Be5Im18StbtqknXgm9a9aoJZsukum2CXhHGDJOvd0ZDknBAJKLSV9CVC6g6si";
			 	
				URL url = new URL("https://www.zipcodeapi.com/rest/"+clientKey+"/info.json/"+zipCode.trim()+"/degrees");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");

				if (conn.getResponseCode() != 200) {
					System.out.println(url.toString());
					throw new RuntimeException("Failed : HTTP error code : "
							+ conn.getResponseCode());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

				String jsonData="";
				String line;
				while ((line = br.readLine()) != null) {
					jsonData += line + "\n";
				}
				
				JSONParser parser = new JSONParser();
				try {
					parser.parse(jsonData);
					JSONObject jsonObject = new JSONObject(jsonData);
					String output=jsonObject.getString("city")+","+jsonObject.getString("state");
					System.out.println(output);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				conn.disconnect();

			  } catch (MalformedURLException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			  }
		
		
		return "";
	}

}
