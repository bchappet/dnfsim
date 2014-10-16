package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public  class FluxUtils {


    public static final String SEP = ",";
	/**
	 * Transform a file into a string
	 * @param url
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String readFile(URL url) throws FileNotFoundException{
		File file = new File(url.getPath());
		StringBuilder fileContents = new StringBuilder((int)file.length());
		Scanner scanner = new Scanner(file);
		String command = null;

		try {
			while(scanner.hasNextLine()) {        
				fileContents.append(scanner.nextLine() );
			}
			command = fileContents.toString();
		} finally {
			scanner.close();
		}

		return command;

	}

}
