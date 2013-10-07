package tests;

import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import maps.AbstractMap;
import maps.Map;
import maps.Parameter;
import maps.Var;

import org.junit.Before;
import org.junit.Test;

import unitModel.GaussianND;
import coordinates.DefaultRoundedSpace;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class GaussianMapTest {
	private Map map;
	@Before
	public void setUp() throws Exception {
		Space space2d = new DefaultRoundedSpace(new Var(10), 2, false);
		Var i = new Var(1);
		Var w = new Var(0.2);
		Var x = new Var(0.04);
		Var y = new Var(0.6);
		
		map = new Map("map",new GaussianND(new Var("dt",0.1), space2d, i,w ,x,y));
		map.constructMemory();
	}

	@Test
	public void test() throws NullCoordinateException, IOException {
		map.compute();
		System.out.println(map.display2D());
		save("src/tests/files/save",map);
		
		
	}
	
	private  void save(String file,Parameter p) throws IOException, NullCoordinateException
	{

		FileWriter fw = null;
		String ret = "[";

			String fileName = file+"_"+p.getName()+".csv";
			ret += fileName + ",";
			fw= new FileWriter(file+"_"+p.getName()+".csv",false);
			BufferedWriter out = new BufferedWriter(fw);
			out.write(((AbstractMap)p).displayMemory());
			out.close();
	}

}
