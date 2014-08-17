package main.java.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class IndexSelectorViewPanel extends ViewPanel {
	
	protected Curve2D curve2D;
	private JComboBox<String> combo;

	public IndexSelectorViewPanel(String name, ViewFactory vf) {
		super(name, vf);
		this.curve2D=new Curve2D(name);
		
	
		
		
	}
	
	/**
	 * TODO dirty
	 * @param paramViewAdapter
	 */
	public void onParamViewConstruction(final MultivaluedParameterCurve2DAdapter paramViewAdapter){
		String[] availableIndexes = new String[paramViewAdapter.getMaxIndex()+1];
		for (int i = 0; i < availableIndexes.length; i++) {
			availableIndexes[i] = ""+i;
		}
		combo = new JComboBox<String>(availableIndexes);
		combo.setSelectedItem(0);
		combo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
		        int index = Integer.parseInt((String)cb.getSelectedItem());
		        paramViewAdapter.setDisplayedIndex(index);
				
			}
		});
		this.setLayout(new BorderLayout());
		this.add(combo,BorderLayout.PAGE_START);
		this.add(curve2D);
	}

	

	@Override
	public void reset() {
		this.curve2D.reset();
	}

	public void update(double doubleValue) {
		this.curve2D.update(doubleValue);
		
	}

}
