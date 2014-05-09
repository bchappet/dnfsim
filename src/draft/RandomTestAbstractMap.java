package draft;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

import main.java.console.CommandLineFormatException;
import main.java.coordinates.DefaultRoundedSpace;
import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.gui.Printer;
import main.java.maps.AbstractMap;
import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.model.Model;
import main.java.model.ModelCNFT;
import main.java.unitModel.RandTrajUnitModel;

/**
 * Map de test pour remplir l'Ã©numÃ©ration InputsMaps
 * 
 * @author nicolas
 * 
 */
public class RandomTestAbstractMap extends Map {

	public final static int NOISE_AMP = 0;

	public RandomTestAbstractMap(String name, Var dt, Space space,
			Parameter... params) {
		super(name,new RandTrajUnitModel(dt, space, new Var(0),
				params[NOISE_AMP]), dt, space, params);
		// Construct noise map
		//		UnitModel noise = new RandTrajUnitModel(dt, main.java.space, new Var(0),
		//				params[NOISE_AMP]);
		// Map mNoise = new Map("Noise", noise);
		// mNoise.constructMemory(); // otherwise the noise is changed at each
		// // computation step
		// // Construct the main.java.input as a sum of theses params
		// main.java.unitModel = new Sum(dt, main.java.space, mNoise);
		//		main.java.unitModel = noise;
	}
	
	public void compute(){
		super.compute();
		
	}
	
	

	public static void main(String[] args){



		Model cnft = new ModelCNFT("CNFT_test");
		URL contextPath = null;
		try {
			contextPath = new URL("file:./src/tests/context/");

			String contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+cnft.getName()+".dnfs"));
			cnft.initialize(contextScript);

			

			AbstractMap input = (AbstractMap) cnft.getParameter(ModelCNFT.INPUT);
			
			input.constructMemory();
			
			cnft.update();
			System.out.println(((AbstractMap) cnft.getParameter(ModelCNFT.INPUT)).display2D());
			System.out.println(((AbstractMap) cnft.getParameter(ModelCNFT.POTENTIAL)).display2D());
			
			Map map = new RandomTestAbstractMap(input.getName(),new Var("dt",0.1),new DefaultRoundedSpace(new Var(7),2,true),
					new Var(0),new Var("noise_amp",1));

			for(AbstractMap p : input.getParents()){
				p.replaceParameter(map);
			}
			map.constructMemory();



			cnft.update();
			System.out.println(((AbstractMap) cnft.getParameter(ModelCNFT.INPUT)).display2D());
			System.out.println(((AbstractMap) cnft.getParameter(ModelCNFT.POTENTIAL)).display2D());
			cnft.update();
			System.out.println(((AbstractMap) cnft.getParameter(ModelCNFT.INPUT)).display2D());
			System.out.println(((AbstractMap) cnft.getParameter(ModelCNFT.POTENTIAL)).display2D());
			
			Map potential = (Map) cnft.getParameter(ModelCNFT.POTENTIAL);
			
			Map res = (Map) potential.getParameter(ModelCNFT.INPUT);
			System.out.println(res.display2D());
			



		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NullCoordinateException e) {
			e.printStackTrace();
		} catch (CommandLineFormatException e) {
			e.printStackTrace();
		}

	}

}