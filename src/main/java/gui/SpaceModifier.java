package main.java.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.coordinates.Space;

/**
 * TODO
 * @author bchappet
 *
 */
public class SpaceModifier extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1812066774759343573L;
	protected Space space;
	protected RunnerGUI gui;

	public SpaceModifier(RunnerGUI gui,Space space) {
		this.space = space;
		this.gui = gui;
		
		initGUI();
		
	}
	
	protected void initGUI()
	{
		this.setBorder(BorderFactory.createTitledBorder("Space:"));
		
		final NumberFormat formater = new DecimalFormat("#000");
		JPanel pane = new JPanel();
		
		pane.add(new JLabel("Res" + ":"));
			final JTextField txt = new JTextField(formater.format((space.getResolution())));
			txt.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JTextField src = (JTextField)e.getSource();
					//Safely transform the refSpace
					gui.changeRes(Double.parseDouble(src.getText()));
				}
			});
			
			pane.add(txt);
			
			JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 200,(int) space.getResolution());
			slider.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					gui.changeRes(source.getValue());
					txt.setText(formater.format((space.getResolution())));
					
				}
			});
		this.add(pane);
		this.add(slider);
	}
	
	

}
