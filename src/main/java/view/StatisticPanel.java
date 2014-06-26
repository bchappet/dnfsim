package main.java.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * The statistic panel will contains one Plot2D which plot one 
 * Parameter with an other one.
 * It will also contains {@JComboBox}  which will be used to choose the {@Parameter}
 * to display.
 * @author benoit
 *
 */
public class StatisticPanel extends ViewPanel {

	/**The stat will be displayed in this panel**/
	protected Plot2D plot2D;
	/**To choose the view to display**/
	private JComboBox<String> comboBox;

	public StatisticPanel(String name,ViewFactory vf) {
		super(name,vf);
		String[] options = getViewFactory().getViewConfiguration().getOptions(name);
		Vector<String> combo = new Vector<String>(options.length);
		combo.addAll(Arrays.asList(options));
		comboBox = new JComboBox<String>(combo);
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				String name = (String)cb.getSelectedItem();
				add(getViewFactory().constructViewPanel(name),BorderLayout.CENTER);
			}
		});
		this.setLayout(new BorderLayout());
		this.add(comboBox,BorderLayout.NORTH);
		comboBox.setSelectedIndex(0);
	}
	
	
	
	
	










}
