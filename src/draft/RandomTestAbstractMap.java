package draft;

import gui.Printer;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

import maps.AbstractMap;
import maps.Map;
import maps.Parameter;
import maps.Var;
import model.Model;
import model.ModelCNFT;
import unitModel.RandTrajUnitModel;
import console.CommandLineFormatException;
import coordinates.DefaultRoundedSpace;
import coordinates.NullCoordinateException;
import coordinates.Space;

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
		//		UnitModel noise = new RandTrajUnitModel(dt, space, new Var(0),
		//				params[NOISE_AMP]);
		// Map mNoise = new Map("Noise", noise);
		// mNoise.constructMemory(); // otherwise the noise is changed at each
		// // computation step
		// // Construct the input as a sum of theses params
		// unitModel = new Sum(dt, space, mNoise);
		//		unitModel = noise;
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