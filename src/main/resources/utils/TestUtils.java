package main.resources.utils;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TestUtils {
	
	/**
	 * Test if the content of to character file is the same
	 * @param msg
	 * @param expected
	 * @param toTest
	 * @throws IOException
	 */
	public static void assertFileEquals(String msg,File expected,File toTest) throws IOException{
		String expectedStr,outputStr;
		BufferedReader brExc = new BufferedReader(new FileReader(expected));
		BufferedReader brOut = new BufferedReader(new FileReader(toTest));
		while((expectedStr = brExc.readLine()) != null){
			outputStr = brOut.readLine();
			assertEquals(msg,expectedStr,outputStr);
		}
		brExc.close();
		brOut.close();
		
	}

}
