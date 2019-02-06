package com.qait.automation.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 
 * @author QA InfoTech
 * This class defines the methods required to perform IO tasks on the "filename.properties" file.
 * filename.properties is the default file which stores data which either is required by the
 * application or is generated by the application at the runtime.
 * 
 */
public class PropFileHandler {
	static Properties properties = new Properties();

	/**
	 * This method is used to read the value of the given property from the properties file.
	 * 
	 * @param property : the property whose value is to be fetched.
	 * @return the value of the given property.
	 */
	
	static String filePath = "./src/test/resources/testdata/DataFile.properties";
	
	
	public static String readProperty(String property) 
	{
		InputStream inPropFile = null;
		try {
			
			inPropFile = new FileInputStream(filePath);
			properties.load(inPropFile);
		} catch (IOException e) {
		}
		String value=properties.getProperty(property);	
		
		return value;
	}

/**
 * This method is used to write the value of the property in property file.
 * @param property
 * @param value
 * @throws IOException
 */
	public static void writeToFile(String property, String value)  {
		try {
			InputStream inPropFile = new FileInputStream(filePath);
			properties.load(inPropFile);
			inPropFile.close();
			OutputStream outPropFile = new FileOutputStream(filePath);
			properties.setProperty(property, value);
			properties.store(outPropFile, null);
			outPropFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
