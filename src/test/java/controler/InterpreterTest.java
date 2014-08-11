package test.java.controler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

import main.java.console.CNFTCommandLine;
import main.java.controler.Interpreter;
import main.java.maps.InfiniteDt;
import main.java.maps.MatrixDouble2D;
import main.java.maps.Parameter;
import main.java.maps.Trajectory;
import main.java.maps.Var;
import main.java.unitModel.ComputeUM;

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
		Var<BigDecimal> dt = new Var<BigDecimal>("dt",new BigDecimal("0"));
		
		MatrixDouble2D map = new MatrixDouble2D("mat",dt,new double[][]{{1d,2d},{3d,4d}});
		
		try {
			bsh.eval("import main.java.maps.*;");
			
			bsh.eval("foo=\"test\";");
			bsh.eval("print(foo);");
			bsh.eval("Var<BigDecimal> dt = new Var<BigDecimal>(\"dt\",new BigDecimal(\"0\"));");
			bsh.eval("MatrixDouble2D map = new MatrixDouble2D(\"mat\",dt,new double[][]{{1d,2d},{3d,4d}},new Parameter[0]);");
			bsh.eval("print(map);");
		} catch (EvalError e) {
			e.printStackTrace();
		}
	}
	
	
	
	

}
