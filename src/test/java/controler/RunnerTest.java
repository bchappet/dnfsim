package test.java.controler;

import java.util.ArrayList;

import javax.swing.JFrame;

import main.java.controler.*;

import javax.swing.JPanel;

import main.java.controler.Runner;
import main.java.view.GlobalView;

import org.junit.Before;
import org.junit.Test;

public class RunnerTest extends JFrame {
	
	private GlobalView applet;
	private Runner uut;

	@Before
	public void setUp() throws Exception {
		
		Printer printer = new Printer(0);
		uut = new Runner(printer,"ESN",null,null,true,new ArrayList<Integer>());
		JPanel pan = uut.getGlobalView();
		pan.setVisible(true);
		
		this.add(pan);
		this.setSize(pan.getSize()); 
		this.setVisible(true);
	}

	@Test
	public void test() throws InterruptedException {
		Thread th = new Thread(uut);
		th.start();
		Thread.sleep(10000000);
	}

}
