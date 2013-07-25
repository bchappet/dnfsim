package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/** Class for sliders with a label
 *  @author JC Quinton
 *  */
public abstract class SliderCommand extends JPanel {

	public SliderCommand(String title, int min, int max, int ticks, int value) {
		// Command panel
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(title));

		// Field to display/define the str
		final JFormattedTextField field = new JFormattedTextField(value);
		// Slider to change the str
		final JSlider slider = new JSlider(JSlider.HORIZONTAL,min,max,value);
		slider.setBackground(Color.white);

		field.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				slider.setValue((Integer)field.getValue());
			}
		});
		field.setColumns(3);

		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				field.setValue(source.getValue());
				if (!source.getValueIsAdjusting())
					valueChanged(source.getValue());
			}
		});
		slider.setMajorTickSpacing(ticks);
		//slider.setPaintLabels(true);

		add(field,BorderLayout.EAST);
		add(slider,BorderLayout.CENTER);
	}

	/** Actions to take when the str changed */
	abstract protected void valueChanged(int value);        
}
