package main.java.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import main.java.gui.DisplayMap;
import main.java.gui.QuickViewPanel;
import main.java.maps.Parameter;

/**
 * In this implementation there is a thread for each map main.java.view
 * @author Benoit
 *
 */
public class ParameterCanvaPanel extends JPanel {

	protected List<ParameterView> maps;
	
	//Setting of the GridLayout
	protected int nbRow;
	protected int nbCol;
	


	public ParameterCanvaPanel()
	{
		super(new GridLayout(1,1));
		nbCol = 1;
		nbRow = 1;
		this.maps = new ArrayList<ParameterView>();
	}
	

	/**
	 * Add a map in the display vue
	 * @param panel
	 */
	public synchronized void addView(ParameterView panel)
	{
//		System.out.println("panel : " + panel);
		//panel.setPreferredSize(new Dimension(300,300));
		maps.add(panel);
		calculateGridLayout(maps.size());
		this.add(panel.getBorderPane());
		this.validate();
	}
	
	public void removeView(ParameterView pane) {
		maps.remove(pane);
		calculateGridLayout(maps.size());
		this.remove(pane.getBorderPane());
		this.validate();
	}
	
	

	
	/**
	 * Recalculate the nbCol and nbRow and update the layout if necessary
	 * @param size : the new size
	 */
	private void calculateGridLayout(int size) {
		if(size == nbRow*nbCol)
		{
			//NoProblem
		}
		else if(size > nbRow*nbCol)
		{
			//We need to increase the capacity
			if(nbRow < nbCol)
			{
				nbRow ++;
			}
			else if(nbRow == nbCol)
			{
				nbCol ++;
			}
			else
			{
				throw new IllegalArgumentException("Impossible");
			}
			
			this.setLayout(new GridLayout(nbRow,nbCol));
		}
		else
		{
			
			//Do we need to decrease
			if(nbRow == nbCol)
			{//We test to decrease the number of row
				if(size == ((nbRow -1) * nbCol))
				{
					nbRow --;
					this.setLayout(new GridLayout(nbRow,nbCol));
				}
				else
				{
					//No problem;
				}
			}
			else if(nbRow < nbCol)
			{
				//We test to decrease the number of col
				if(size == ((nbCol -1) * nbRow))
				{
					nbCol --;
					this.setLayout(new GridLayout(nbRow,nbCol));
				}
				else
				{
					//No problem;
				}
			}
			else
			{
				throw new IllegalArgumentException("Impossible");
			}
		}
		
	}


	

	

	
	




	




	
}
