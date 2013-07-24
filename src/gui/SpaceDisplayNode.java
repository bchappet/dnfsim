package gui;

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

import coordinates.Space;

public class SpaceDisplayNode extends DisplayNode {
	protected Space space;

	public SpaceDisplayNode(DisplayNode parent, Space space, GUI gui) {
		super(parent, space, gui);
		this.space = space;
	}

	public void valueChanged(ParameterView parameterView) {
		parameterView.setDisplayedParam("Space selected" ,
				new SpaceHeaderPanel(gui, (Space)linked),
				this.getParamPanel());
	}

	public JPanel getParamPanel()
	{
		JPanel ret = new JPanel();
		ret.setBorder(BorderFactory.createTitledBorder("Space:"));

		final NumberFormat formater = new DecimalFormat("#000");
		JPanel pane = new JPanel();

		pane.add(new JLabel("Res" + ":"));
		final JTextField txt = new JTextField(formater.format((space.getResolution())));
		txt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField src = (JTextField)e.getSource();
				//Safely transform the refSpace
				try {
					changeRes(Double.parseDouble(src.getText()));
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		pane.add(txt);

		JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 200,(int) space.getResolution());
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				txt.setText(formater.format(source.getValue()));
				if(!source.getValueIsAdjusting())
					try {
						changeRes(source.getValue());
					} catch (Exception e1) {
						e1.printStackTrace();
					}

			}
		});

		ret.add(pane);
		ret.add(slider);

		return ret;
	}



	protected void changeRes(double newRes) throws Exception
	{
	
		//Safely transform the refSpace
		
			space.setResolution(newRes);
			gui.resetHard();
	}

}
