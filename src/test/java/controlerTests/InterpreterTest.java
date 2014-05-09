package test.java.controlerTests;

import java.io.FileNotFoundException;
import java.io.IOException;

import main.java.controler.Interpreter;
import main.java.maps.MatrixDouble2D;

import org.junit.Before;
import org.junit.Test;

import bsh.EvalError;

public class InterpreterTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testMyInstruction() throws EvalError, FileNotFoundException, IOException{
		Interpreter bsh = new Interpreter();
		bsh.eval("importCommands(\"main.java.controler.myCommands\");");
		bsh.eval("helloWorld();");
	}

	@Test
	public void testClassUtilization() {
		Interpreter bsh = new Interpreter();
		
		MatrixDouble2D map = new MatrixDouble2D("mat",new double[][]{{1d,2d},{3d,4d}});
		
		try {
			bsh.eval("import main.java.maps.*;");
			
			bsh.eval("foo=\"test\";");
			bsh.eval("print(foo);");
			
			bsh.eval("MatrixDouble2D map = new MatrixDouble2D(\"mat\",new double[][]{{1d,2d},{3d,4d}},new Parameter[0]);");
			bsh.eval("print(map);");
		} catch (EvalError e) {
			e.printStackTrace();
		}
	}

}
