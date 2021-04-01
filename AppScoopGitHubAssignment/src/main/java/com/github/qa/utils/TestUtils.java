package com.github.qa.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.util.concurrent.AtomicLongMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * Utility class for all convenience methods and global methods that might be
 * needed from test classes.
 *
 * @author Nischal Tygai
 */
public class TestUtils {

	/** The Log. */
	private static Logger Log = LogManager.getLogger(TestUtils.class.getName());

	/**
	 * Instantiates a new test utils.
	 */
	private TestUtils() {
		Log.info("Initialized BuilderEndpoints");
	}

	/** The random. */
	private static Random random = new Random();

	/** The date time format. */
	private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	/** The date format. */
	private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	/** The day format. */
	private static DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("dd");

	/** The Constant counts. */
	private static final AtomicLongMap<String> counts = AtomicLongMap.create();

	/** The props. */
	private static Properties props = new Properties();
	static {

		try (final InputStreamReader reader = new InputStreamReader(
				TestUtils.class.getResourceAsStream("/stage.properties"), "UTF-8")) {
			props.load(reader);
		} catch (IOException e) {
			Log.error("unable to read properties file", e);
		}
	}

	/**
	 * Gets the property.
	 *
	 * @param key the key
	 * @return the property
	 */
	public static String getProperty(String key) {
		return props.getProperty(key);
	}

	/**
	 * Sets the property.
	 *
	 * @param key   the key
	 * @param value the value
	 */
	public static void setProperty(String key, String value) {
		props.setProperty(key, value);
	}

	/**
	 * Gets the property.
	 *
	 * @param key          the key
	 * @param defaultValue the default value
	 * @return the property
	 */
	public static String getProperty(String key, String defaultValue) {
		String value = getProperty(key);
		return (value == null) ? defaultValue : value;
	}

	/**
	 * Gets the base url.
	 *
	 * @return base url
	 */
	public static String getbaseUrl() {
		return props.getProperty("engineer.ai.builder.baseUrl");
	}

	/**
	 * Unique text generator.
	 *
	 * @param text the text
	 * @return the string
	 */
	public static String uniqueTextGenerator(String text) {

		Date current = new Date();
		long unique = current.getTime();
		text = text + Long.toString(unique);
		return text;
	}

	/**
	 * Lorem ipsum generator.
	 *
	 * @param noofchars the noofchars
	 * @return the string
	 */
	public static String loremIpsumGenerator(int noofchars) {
		String loremstring = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ornare, eros ut suscipit lobortis, arcu orci auctor massa, non accumsan metus eros nec diam. Etiam nec lacus pellentesque, consectetur leo at, tincidunt sem. Donec faucibus, libero et bibendum ornare, nulla lacus eleifend nibh, a ornare risus erat porttitor metus. Vivamus placerat ornare pellentesque. Donec imperdiet viverra euismod. Etiam dapibus, diam vitae aliquam viverra, diam quam molestie quam, aliquam facilisis velit arcu ac urna. In vel porttitor velit. Nam est mi, ornare vitae eros ut, consectetur consectetur est. Praesent ac dictum nunc, eu pretium leo. Phasellus at malesuada ligula. Pellentesque vel lacus viverra elit accumsan gravida vulputate id lacus. Mauris sit amet dolor varius, ultrices felis id, ultricies metus. Nam vel lacus nisi.";
		return loremstring.substring(0, noofchars);

	}

	/**
	 * Gets the username from email.
	 *
	 * @param text the text
	 * @return the username from email
	 */
	public static String getUsernameFromEmail(String text) {
		String[] s = text.split("@");
		return s[0];
	}

	/**
	 * Gets the value as an integer for the provided key from the property file(s).
	 *
	 * @param key the key
	 * @return the property as int
	 */
	public static int getPropertyAsInt(String key) {
		return Integer.parseInt(getProperty(key));
	}

	/**
	 * Gets the value as a boolean for the provided key from the property file(s).
	 *
	 * @param key the key
	 * @return the property as boolean
	 */
	public static boolean getPropertyAsBoolean(String key) {
		return Boolean.valueOf(getProperty(key));
	}

	/**
	 * Convert string to int.
	 *
	 * @param number the number as a string
	 * @return the int
	 */
	public static int convertStringToInt(String number) {
		String actualNumber = number == null ? "" : number;
		String stripped = stripAll(actualNumber.trim(), ",", "\\$", "\\)", "\\(", "%");
		stripped = stripped.isEmpty() ? "0" : stripped;
		return Integer.parseInt(stripped);
	}

	/**
	 * Extract digit from string.
	 *
	 * @param word the word
	 * @return the string as number
	 */
	public static String extractNumberFromString(String word) {

		return word.replaceAll("[^0-9?!\\.]", "");
	}

	/**
	 * Convert string to double.
	 *
	 * @param number the number
	 * @return the double
	 */
	public static double convertStringToDouble(String number) {
		String actualNumber = number == null ? "" : number;
		String stripped = stripAll(actualNumber.trim(), ",", "\\$", "\\)", "\\(", "%");
		stripped = stripped.isEmpty() ? "0" : stripped;
		return Double.parseDouble(stripped);
	}
	
	public static String removeAllSpecialChar(String str) {

		return str.replaceAll("[^a-zA-Z0-9]", "");
		
//		
//		
//		String actualNumber = str == null ? "" : str;
//		String stripped = stripAll(str.trim(), ",", "\\$", "\\)", "\\(", "%", "[\">],");
//		stripped = stripped.isEmpty() ? "0" : stripped;
//		return stripped.replaceAll("\\r\\n|\\r|\\n", " ");
	}
	
	public static String roundOfDoubleAndConverToString(double number) {
		double roundOff = (double) Math.round(number * 100) / 100;
		
		return new Double(roundOff).toString();
	}

	/**
	 * Convert string to float.
	 *
	 * @param number the number
	 * @return the double
	 */
	public static float convertStringToFloat(String number) {
		String actualNumber = number == null ? "" : number;
		String stripped = stripAll(actualNumber.trim(), ",", "\\$", "\\)", "\\(", "%");
		stripped = stripped.isEmpty() ? "0" : stripped;
		return Float.parseFloat(stripped);
	}
	
	public static String convertIntToString(int number) {
		String stringNumber = String.valueOf(number);
		return stringNumber;
	}
	

	/**
	 * Convert string to long.
	 *
	 * @param number the number
	 * @return the long
	 */
	public static long convertStringToLong(String number) {
		String actualNumber = number == null ? "" : number;
		String stripped = stripAll(actualNumber.trim(), ",", "\\$", "\\)", "\\(", "%");
		stripped = stripped.isEmpty() ? "0" : stripped;
		return Long.parseLong(stripped);
	}

	/**
	 * To unicode.
	 *
	 * @param text the text
	 * @return the string
	 */
	public static String toUnicode(String text) {
		try {
			return new String(text.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return text;
		}
	}

	/**
	 * Strip all the strings indicated by the other arguments from the first
	 * argument.
	 *
	 * @param actualString    the actual string
	 * @param stringsToRemove the strings to remove
	 * @return a new string with all the provided strings removed from the original
	 *         string
	 */
	public static String stripAll(String actualString, String... stringsToRemove) {
		String stripped = actualString;

		for (String string : stringsToRemove) {
			stripped = stripped.replace(string, "");
			stripped = stripped.replaceAll(string, "");
		}

		return stripped;
	}

	/**
	 * Normalize text. In a few places, there is a difference in how a particular
	 * option is displayed vs how it is actually present in the DOM. This causes
	 * different issues when trying to click on it and when trying to verify its
	 * presence. So, we try to represent it in the same way it is present in the DOM
	 *
	 * @param text the intention
	 * @return the string
	 */
	public static String normalizeText(String text) {
		switch (text) {
		case "Save Some Money And Lower My Payments":
			return "Save Some Money and Lower My Payments";
		case "On The Spot":
			return "On the Spot";
		case "Increase My Line Of Credit":
			return "Increase My Line of Credit";
		case "A little bit of everything":
			return "A Little Bit Of Everything";
		default:
			return text;
		}
	}

	/**
	 * Sleep in millis.
	 *
	 * @param timeInMillis the time in millis
	 * @return true, if successful
	 */
	public static boolean sleepInMillis(long timeInMillis) {
		if (timeInMillis > 0) {
			try {
				Thread.sleep(timeInMillis);
			} catch (Exception e) {
				Log.info(e.getMessage());
			}
		}
		return true;
	}

	/**
	 * Sleep.
	 *
	 * @param timeInSeconds the time in seconds
	 * @return true, if successful
	 */
	public static boolean sleep(double timeInSeconds) {
		return sleepInMillis((long) (timeInSeconds * 1000));
	}

	/**
	 * Gets the random boolean.
	 *
	 * @return the random boolean
	 */
	public static boolean getRandomBoolean() {
		return random.nextBoolean();
	}

	/**
	 * Gets the unique email.
	 *
	 * @return the unique email
	 */
	public static String getUniqueEmail() {
		return "email" + System.currentTimeMillis() + "@engineer.ai";
	}
	
	/**
	 * Gets the unique email.
	 *
	 * @return the unique email
	 */
	public static String getUniqueTempEmail(String emailprefix) {
		return emailprefix + System.currentTimeMillis() + "@gmail.com";
	}

	/**
	 * Gets the random string.
	 *
	 * @param stringPrefix the string prefix
	 * @return the random string
	 */
	public static String getUniqueString(String stringPrefix) {
		return stringPrefix + System.currentTimeMillis();
	}
	
	
	public static String randomString() {
		
		Random r = new Random();
		List<Integer> id = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			int x = r.nextInt(9999);
			while (id.contains(x))
				x = r.nextInt(9999);
			id.add(x);
		}
		String emailId = String.format("sm2"+id.get(0)+"view");
		return emailId;
	}

	/**
	 * Gets the random int.
	 *
	 * @param upperLimit the upper limit
	 * @return the random int
	 */
	public static int getRandomInt(int upperLimit) {
		return random.nextInt(upperLimit);
	}

	/**
	 * Gets the random element from list.
	 *
	 * @param      <T> the generic type
	 * @param list the list
	 * @return the random element from list
	 */
	public static <T extends Object> T getRandomElementFromList(List<T> list) {
		return list.get(random.nextInt(list.size()));
	}

	/**
	 * Gets the random json element from array.
	 *
	 * @param list the list
	 * @return the random json element from array
	 */
	public static JsonElement getRandomJsonElementFromArray(JsonArray list) {
		if (list.size() > 0) {
			return list.get(random.nextInt(list.size()));
		}

		return null;
	}

	/**
	 * Gets the random string from json array.
	 *
	 * @param list the list
	 * @return the random string from json array
	 */
	public static String getRandomStringFromJsonArray(JsonArray list) {
		if (list.size() > 0) {
			return list.get(random.nextInt(list.size())).getAsString();
		}

		return null;
	}

	/**
	 * Gets the random enum.
	 *
	 * @param       <T> the generic type
	 * @param clazz the clazz
	 * @return the random enum
	 */
	public static <T extends Enum<?>> T getRandomEnum(Class<T> clazz) {
		T[] enumArray = clazz.getEnumConstants();
		int randomIndex = random.nextInt(enumArray.length);
		return enumArray[randomIndex];
	}

	/**
	 * Gets the difference.
	 *
	 * @param           <T> the generic type
	 * @param firstSet  the first set
	 * @param secondSet the second set
	 * @return the difference
	 */
	public static <T> Set<T> getDifference(Set<T> firstSet, Set<T> secondSet) {
		Set<T> temp = new HashSet<>(firstSet);
		for (T value : secondSet) {
			if (!temp.add(value)) {
				temp.remove(value);
			}
		}
		return temp;
	}

	/**
	 * Gets the date time as string.
	 *
	 * @return the date time as string
	 */
	public static String getDateTimeAsString() {
		return LocalDateTime.now().format(dateTimeFormat);
	}

	/**
	 * Gets the date as string.
	 *
	 * @return the date as string
	 */
	public static String getDateAsString() {
		return LocalDateTime.now().format(dateFormat);
	}

	/**
	 * Gets the utc date as string.
	 *
	 * @return the utc date as string
	 */
	public static String getUtcDateAsString() {
		return ZonedDateTime.now(ZoneOffset.UTC).format(dateFormat);
	}
	
	public static String getNextDate() {
        String nextDate = "";
        try {
            Calendar today = Calendar.getInstance();
            DateFormat format = new SimpleDateFormat("dd");
           Date date = format.parse(LocalDateTime.now().format(dayFormat));
            today.setTime(date);
            today.add(Calendar.DAY_OF_YEAR, 1);
            nextDate = format.format(today.getTime());
        } catch (Exception e) {
            return nextDate;
        }
        return nextDate;
    }

	/**
	 * Gets the first value or.
	 *
	 * @param              <T> the generic type
	 * @param array        the array
	 * @param defaultValue the default value
	 * @return the first value or
	 */
	public static <T> T getFirstValueOr(T[] array, T defaultValue) {
		if ((array != null) && (array.length > 0) && (array[0] != null)) {
			return array[0];
		}
		return defaultValue;
	}

	/**
	 * Gets the random int.
	 *
	 * @param limit the limit
	 * @return the random int
	 */
	public static int getRandomInt(Integer... limit) {
		Integer bound;
		if ((bound = getFirstValueOr(limit, null)) != null) {
			return random.nextInt(bound);
		}

		return random.nextInt();
	}

	/**
	 * Gets the count for.
	 *
	 * @param key the key
	 * @return the count for
	 */
	public static Integer getCountFor(String key) {
		return (int) counts.get(key);
	}

	/**
	 * Gets the and increment count for.
	 *
	 * @param key the key
	 * @return the and increment count for
	 */
	public static int getAndIncrementCountFor(String key) {
		return (int) counts.getAndIncrement(key);
	}

	/**
	 * Gets the and decrement count for.
	 *
	 * @param key the key
	 * @return the and decrement count for
	 */
	public static int getAndDecrementCountFor(String key) {
		return (int) counts.getAndDecrement(key);
	}

	/**
	 * Increment count for.
	 *
	 * @param key the key
	 * @return the int
	 */
	public static int incrementCountFor(String key) {
		return (int) counts.incrementAndGet(key);
	}

	/**
	 * Decrement count for.
	 *
	 * @param key the key
	 * @return the int
	 */
	public static int decrementCountFor(String key) {
		return (int) counts.decrementAndGet(key);
	}

	/**
	 * Delete counts for.
	 *
	 * @param key the key
	 */
	public static void deleteCountsFor(String key) {
		counts.remove(key);
	}

	/**
	 * Gets the counts as map.
	 *
	 * @return the counts as map
	 */
	public static Map<String, Long> getCountsAsMap() {
		return counts.asMap();
	}

	/**
	 * Gets the current date time.
	 *
	 * @return the current date time
	 */
	public static String getCurrentDateTime() {
		DateFormat sdf = new SimpleDateFormat("dd_MMMM_HH_mm_ss");
		Date date = new Date();
		return sdf.format(date);
	}
	
	public static String getSubstring(String completeString, String startingchar , String endchar) {
		String result = completeString.split(startingchar)[1].split(endchar)[0].replaceAll("[\">]","");
		Log.info("Confirmation token is: "+ result);
		return result;
	}
	
	public static String getStringUpToChar(String str, int n) {
		String substring =  str.substring(0, Math.min(str.length(), n));
		Log.info("Stripped String is: "+substring);
		return substring;
	}
	
	

	public static String decodeText(String text) {
		Base64.Decoder decoder = Base64.getDecoder();
		String decodedText = new String(decoder.decode(text));
		return decodedText;
	}
	
	public static String getUniqueTempEmail() {
		return "email" + System.currentTimeMillis() + "@yopmail.com";
	}
	public static String getCurrentMonth() {
		String month = Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
		Log.info("Current Month Name is:"+month);
		return month;
	}
	public static String getCurrentYear() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String currentyear = Integer.toString(year);
		Log.info("Current Year is:"+year);
		return currentyear;
	}
	
	public static String getPreviousMonth(int n) {
		YearMonth thisMonth    = YearMonth.now();
		YearMonth lastMonth    = thisMonth.minusMonths(n);
		
		DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMMM");
		
		Log.info("Last Month: %s\n", lastMonth.format(monthYearFormatter));
		return lastMonth.format(monthYearFormatter);
	}
	
	
	
	// Get absolute path
				public static String getPath() {
					String path = "";
					File file = new File("");
					String absolutePathOfFirstFile = file.getAbsolutePath();
					path = absolutePathOfFirstFile.replaceAll("\\\\+", "/");
					return path;
				}

				// Creating file name
				public static String getFileName(String file) {
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
					Calendar cal = Calendar.getInstance();
					String fileName = file + dateFormat.format(cal.getTime());
					return fileName;
				}
				
				public static void getSeperateDigit(int number) {
					
					int digit1 = 0, digit2 = 0;
					digit1 = number % 10;
					number = number / 10;
					digit2 = number % 10;
					
					System.out.println("digits 1" + digit1);
					System.out.println("digits 2" + digit2);
				}
}
