package main.java.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * from JC Quinton
 * Contains 2 {@Curve2D}
 * @author benoit
 * @version 09/06/2014
 *
 */
public class Plot2D extends ViewPanel {


	
	
	/**Display button**/
	private JComboBox<String> jcbX;
	private JComboBox<String> jcbY;
	private PlotCoord2D dispPanel;


	/**
	 * The first and the second options will be X and Y
	 * @param name
	 * @param vc
	 * @param mc
	 * @param vf
	 * @param options
	 */
	public Plot2D(String name,ViewFactory vf_) {
		super(name,vf_);
		dispPanel = new PlotCoord2D("plotter"); 
		
		
		String[] options = getViewFactory().getViewConfiguration().getOptions(name);
//		String[] options = new String[]{"Time","error_dist" };
		Vector<String> combo = new Vector<String>(options.length);
		combo.addAll(Arrays.asList(options));
		jcbX = new JComboBox<String>(combo);
		jcbY = new JComboBox<String>(combo);
		
		jcbX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				String name = (String)cb.getSelectedItem();
				dispPanel.setCurveX(constructView(name, getViewFactory() ));
			}
		});
		
		jcbY.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				String name = (String)cb.getSelectedItem();
				dispPanel.setCurveY(constructView(name,  getViewFactory() ));
			}
		});
		
		jcbX.setSelectedIndex(0);
		jcbY.setSelectedIndex(1);
		
		
		
		
		this.setLayout(new BorderLayout());
		this.add(dispPanel,BorderLayout.CENTER);
		JPanel buttons = new JPanel();
		buttons.add(jcbX);
		buttons.add(jcbY);
		this.add(buttons,BorderLayout.NORTH);
		
		
		
		dispPanel.setSize(1000, 200);
		
	}
	
	

	private Curve2D constructView(String name,ViewFactory vf){
		ParameterView pw = vf.constructView(name, SingleValueParamCurve2DAdapter.class.getSimpleName());
		return (Curve2D) pw;
	}



	@Override
	public void reset() {
		dispPanel.reset();
		
	}



	



	







	

	

}
