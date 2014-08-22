package main.java.view;

import java.util.EventObject;

import main.java.console.CommandLineFormatException;
import main.java.controler.LearningWeightMatrixControler;
import main.java.maps.Var;
import main.java.reservoirComputing.ComputationOutOfMemoryError;
import main.java.reservoirComputing.DeterminantErrror;

public class LearningWeightMatrixView extends View1D {
	
	private LearningWeightMatrixControler vc;

	public LearningWeightMatrixView(String name, double[] initialState,
			ColorMap colorMap, Var<Boolean> grid,final LearningWeightMatrixControler vc) {
		super(name, initialState, colorMap, grid);
		this.vc = vc;
		
	}
	
	public void rightClick(EventObject event)
	{
		try {
			this.vc.learnWeights();
		} catch (CommandLineFormatException e1) {
			e1.printStackTrace();
		} catch (DeterminantErrror | ComputationOutOfMemoryError e2) {
			e2.printStackTrace();
		}
	}

	
	
	

}
