package com.qait.automation.utils;

/**
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.lang3.time.DateFormatUtils;

public class ResourceLoader {
	static Properties properties = new Properties();

	private ResourceLoader() {
	}

	/**
	 *
	 * @param resourceName
	 * @return
	 * @throws IOException
	 */
	public static URL getResourceUrl(String resourceName) throws IOException {
		ClassLoader classLoader = ResourceLoader.class.getClassLoader();

		URL resourceUrl = null;

		if (classLoader != null) {
			resourceUrl = classLoader.getResource(resourceName);
		}

		if (resourceUrl == null) {
			classLoader = ClassLoader.getSystemClassLoader();
			if (classLoader != null) {
				resourceUrl = classLoader.getResource(resourceName);
			}
		}

		return resourceUrl;
	}// end loadResource

	/**
	 *
	 * @param resourceName
	 * @return
	 * @throws IOException
	 */
	public static InputStream loadResource(String resourceName) throws IOException {
		ClassLoader classLoader = ResourceLoader.class.getClassLoader();

		InputStream inputStream = null;

		if (classLoader != null) {
			inputStream = classLoader.getResourceAsStream(resourceName);
		}

		if (inputStream == null) {
			classLoader = ClassLoader.getSystemClassLoader();
			if (classLoader != null) {
				inputStream = classLoader.getResourceAsStream(resourceName);
			}
		}

		if (inputStream == null) {
			File file = new File(resourceName);
			if (file.exists()) {
				inputStream = new FileInputStream(file);
			}
		}

		return inputStream;
	}// end loadResource

	/**
	 *
	 * @param resourceName
	 * @return
	 * @throws IOException
	 */
	public static Properties loadProperties(String resourceName) throws IOException {
		Properties properties = null;
		InputStream inputStream = null;
		try {
			inputStream = loadResource(resourceName);
			if (inputStream != null) {
				properties = new Properties();
				properties.load(inputStream);
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return properties;
	}

	/*
	 * In writeProperties method we write the userdata.properties file for given
	 * key and value parameters in method definition.
	 */
	public static void writeProperties(String key, String value) {
		OutputStream output = null;

		try {
			InputStream inPropFile = new FileInputStream("userdata.properties");
			properties.load(inPropFile);
			inPropFile.close();
			OutputStream outPropFile = new FileOutputStream("userdata.properties");
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outPropFile, "UTF-8"));
			properties.setProperty(key, value);
			properties.store(bufferedWriter, null);
			outPropFile.close();
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * In readProperties method we read the userdata.properties file for given
	 * key parameter in method definition.
	 */
	public static String readProperties(String key) {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("userdata.properties");
			prop.load(new BufferedReader(new InputStreamReader(input)));
			return prop.getProperty(key).contains("\\") ? prop.getProperty(key).replace("\\", "")
					: prop.getProperty(key);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static void updateContentCode() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("./content_code.txt")));
			String cnt_code = null;
			ArrayList<String> cnt_CodeList = new ArrayList<String>();
			while ((cnt_code = bufferedReader.readLine()) != null) {
				cnt_CodeList.add(cnt_code);
			}
			cnt_CodeList.remove(0);
			bufferedReader.close();
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("./content_code.txt")));
			ListIterator<String> cnt_list = cnt_CodeList.listIterator();
			while (cnt_list.hasNext()) {
				String ct = cnt_list.next();
				System.out.println("Content Code:: " + ct);
				bufferedWriter.write(ct + "\n");
				bufferedWriter.flush();
			}
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void updateContentCodeDeployedBooks() {
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new FileReader(new File("./content_codeDeployedBooks.txt")));
			String cnt_code = null;
			ArrayList<String> cnt_CodeList = new ArrayList<String>();
			while ((cnt_code = bufferedReader.readLine()) != null) {
				cnt_CodeList.add(cnt_code);
			}
			cnt_CodeList.remove(0);
			bufferedReader.close();
			BufferedWriter bufferedWriter = new BufferedWriter(
					new FileWriter(new File("./content_codeDeployedBooks.txt")));
			ListIterator<String> cnt_list = cnt_CodeList.listIterator();
			while (cnt_list.hasNext()) {
				String ct = cnt_list.next();
				System.out.println("Content Code:: " + ct);
				bufferedWriter.write(ct + "\n");
				bufferedWriter.flush();
			}
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * In getContentCode method read the content_code.txt file and get the
	 * content code for a book & using updateContentCode method we remove that
	 * code from content_code.txt so that it can't be used second time.
	 */
	public static String getContentCode() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("./content_code.txt")));
			String cnt_code = bufferedReader.readLine();
			bufferedReader.close();
			if (cnt_code != null) {
				updateContentCode();
				return cnt_code;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getContentCodeDeployedBooks() {
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new FileReader(new File("./content_codeDeployedBooks.txt")));
			String cnt_code = bufferedReader.readLine();
			bufferedReader.close();
			if (cnt_code != null) {
				updateContentCode();
				return cnt_code;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * In getBooks method we read the books.csv file & put all those books in
	 * book_nm_id map object. This books are used to give permission to
	 * Instructor & Student for current smoke test.
	 */

	public static Map<String, String> getBooks() {
		Map<String, String> book_nm_id = new HashMap<String, String>();
		try {
			BufferedReader allBooks = new BufferedReader(new FileReader(new File("./books.csv")));
			String books = null;
			while ((books = allBooks.readLine()) != null) {
				String[] bookid = books.split("\\|");
				book_nm_id.put(bookid[1], bookid[0]);
			}
			allBooks.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return book_nm_id;
	}

	public static ArrayList<String> getAllDeployedHTMLBooks() {
		ArrayList<String> book_nm_id = new ArrayList<>();
		try {
			BufferedReader allBooks = new BufferedReader(new FileReader(new File("./htmlContent_codeDeployedBooks.txt")));
			String books = null;
			while ((books = allBooks.readLine()) != null) {
				book_nm_id.add(books.trim());
			}
			allBooks.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return book_nm_id;
	}

	/*
	 * In splitStringWithBrackets method with val parameter the string is split
	 * through brackets and return the sub string between them.
	 */
	public static String subStringAfterBrackets(String val) {
		String result = val.substring(val.lastIndexOf("(") + 1, val.lastIndexOf(")"));
		if (result == null) {
			return val;
		} else {
			return result;
		}
	}

	public static String subStringBeforeBrackets(String val) {
		String result = val.substring(0, val.lastIndexOf("("));
		if (result == null) {
			return val;
		} else {
			return result;
		}
	}

	public static String getKey(Map<String, String> books, int num) {
		return (String) books.keySet().toArray()[num];
	}

	public static String getValue(Map<String, String> books, int num) {
		return (String) books.values().toArray()[num];
	}

	public static int randomInt(int num) {
		Random rnd = new Random();
		return rnd.nextInt(num);
	}

	static int initialcheck = 0;

	public static String InitialEmptyLogFile() {
		try {
			if (initialcheck < 1) {
				File yourFile = new File(YamlReader.getYamlValue("logFilePath") + "/logs.txt");
				yourFile.delete();
				File yourNewFile = new File(YamlReader.getYamlValue("logFilePath") + "/logs.txt");
				yourNewFile.createNewFile();
				BufferedWriter output1 = null;
				try {
					output1 = new BufferedWriter(new FileWriter(yourNewFile, true));
					output1.newLine();
					output1.close();
				} catch (Exception e) {
					System.out.println("Exception in logOutputFile" + e.getStackTrace());
				}
				initialcheck = initialcheck + 1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Log file cleared";
	}

	public static String getCurrentFormatedTime() {
		return DateFormatUtils.format(System.currentTimeMillis(), "MMM dd,yyyy hh:mm:ss a, ");
	}

	public static String logOutputFile(String output) {
		File file = new File(YamlReader.getYamlValue("logFilePath") + "/logs.txt");

		output = getCurrentFormatedTime() + " " + output + "\n";
		BufferedWriter output1 = null;
		try {
			output1 = new BufferedWriter(new FileWriter(file, true));
			output1.append(output);
			output1.newLine();
			output1.flush();
			output1.close();
		} catch (Exception e) {
			System.out.println("Exception in logOutputFile " + file.getAbsolutePath());
		}

		return output;
	}

	public static void cleanHTMLDeployedBooksFile() {
		File file = new File("./htmlContent_codeDeployedBooks.txt");
		try {
			if (!file.createNewFile()) {
				file.delete();
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
