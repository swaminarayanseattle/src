package com.baps.util;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
	Properties pro = null ;
	public PropertyUtil()
	{
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("connection.properties");  

		pro = new Properties();

		try {

			pro.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	public String propertyValue(String propName) {

		return pro.getProperty(propName);
	}


}
