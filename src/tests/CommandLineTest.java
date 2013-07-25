package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import model.ModelCNFT;

import org.junit.Test;

import console.CNFTCommandLine;
import console.CommandLineFormatException;

public class CommandLineTest {

	protected CNFTCommandLine test;

	@Test
	public void testInitial() throws CommandLineFormatException {
		String str = "test=T;test2=F;val=2;int=3.2;str=str";
		test = new CNFTCommandLine(str,new ModelCNFT("cnft"));

		assertTrue(test.getBool("test"));
		assertFalse(test.getBool("test2"));
		assertTrue(test.get("val").get()==2);
		assertTrue(test.get("int").get()==3.2);
		assertTrue(test.getString("str").equals("str"));




	}

	@Test
	public void testModif() throws CommandLineFormatException
	{

		String str = "test=T;test2=F;val=2;int=3.2;str=str";
		test = new CNFTCommandLine(str,new ModelCNFT("cnft"));

		test.parseCommand("test=F;int=3");
		assertTrue(test.getBool("test"));
		assertFalse(test.getBool("test2"));
		assertTrue(test.get("val").get()==2);
		assertTrue(test.get("int").get()==3);
		assertTrue(test.getString("str").equals("str"));

	}

}
