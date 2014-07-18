package main.java.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JComboBox;

/**
 * The statistic panel will contains one Plot2D which plot one 
 * Parameter with an other one.
 * It will also contains {@JComboBox}  which will be used to choose the {@Parameter}
 * to display.
 * @author benoit
 *
 */
public class StatisticPanel extends ViewPanel {

	/**To choose the view to display**/
	private JComboBox<String> comboBox;
	
	private ParameterView view;

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
				if(view != null)
					remove((Component)view);
				view =  getViewFactory().constructView(name);
//				System.out.println("View : " + view);
				add((Component) view ,BorderLayout.CENTER);
			}
		});
		this.setLayout(new BorderLayout());
		this.add(comboBox,BorderLayout.NORTH);
		comboBox.setSelectedIndex(0);
	}

	@Override
	public void reset() {
		view.reset();
		
	}
	
	
	
	
	










}
