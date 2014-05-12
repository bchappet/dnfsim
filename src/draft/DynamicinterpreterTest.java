package draft;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DynamicinterpreterTest {

	public static void main(String[] args) throws FileNotFoundException, InterruptedException{
		Scanner sc = new Scanner(new File("test.txt"));
		while(true){
			while(!sc.hasNext()){
				Thread.sleep(100);
			}
			System.out.println(sc.nextLine());
		}
	}

}
