package main.java.view;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * 
 * @author Benoit
 * @version 18/06/14
 *
 */
public class CanvaPanel extends ViewPanel {

	
	private Map<String,JPanel> views;
	
	//Setting of the GridLayout
	private int nbRow;
	private int nbCol;
	


	public CanvaPanel(String name,ViewFactory vf)
	{
		super(name,vf,new GridLayout(1,1));
		nbCol = 1;
		nbRow = 1;
		this.views = new HashMap<String,JPanel>();
		String[] options = getViewFactory().getViewConfiguration().getOptions(name);
		calculateGridLayout(options.length);
		for(String opt : options){
//			System.out.println("Options : " + opt);
			//we could call addView but we prefer to calculate the grid layout before and avoid recalculating it several time
			
			this.addView(opt);
		}
	}
	
	

	/**
	 * Factorization function to make border of a panel
	 * @param pv
	 * @return
	 */
	private static JPanel constructBorderPane(ParameterView pv){
		JPanel borderPanel = new JPanel();
		borderPanel.setLayout(new BoxLayout(borderPanel, BoxLayout.PAGE_AXIS));
		borderPanel.setBorder(BorderFactory.createTitledBorder(pv.getName()));
		borderPanel.add((Component) pv);
		return borderPanel;
	}

	/**
	 * Add a map in the display vue
	 * @param panel
	 */
	public synchronized void addView(String viewName)
	{
		ParameterView vp = this.getViewFactory().constructView(viewName); 
		if(vp == null){
			throw new IllegalArgumentException("The construction of " +  viewName + " failed ");
		}
		
//		System.out.println("panel : " + panel);
		//panel.setPreferredSize(new Dimension(300,300));
		
		calculateGridLayout(views.size());
		JPanel borderP = constructBorderPane(vp);
		views.put(viewName, borderP);
		this.add(borderP);
		this.validate();
	}
	
	public synchronized void removeView(String viewName) {
		this.remove(views.get(viewName));
		views.remove(viewName);
		calculateGridLayout(views.size());
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
				nbCol++;
			}
			else
			{
				throw new IllegalArgumentException("Impossible");
			}
			
			this.setLayout(new GridLayout(nbCol,nbRow));
		}
		else
		{
			
			//Do we need to decrease
			if(nbRow == nbCol)
			{//We test to decrease the number of row
				if(size == ((nbRow -1) * nbCol))
				{
					nbRow --;
					this.setLayout(new GridLayout(nbCol,nbRow));
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
					this.setLayout(new GridLayout(nbCol,nbRow));
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



	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}


	

	

	
	




	




	
}
