package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import main.java.plot.Curve2D;
import main.java.plot.Plotter;
import main.java.statistics.Statistics;

@Deprecated
public class StatisticPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6917569151278818495L;
	protected Statistics stats;
	protected String startY;
	protected Plotter plotter;
	protected int border = 10;

	public StatisticPanel(Statistics statistics,String startY) {
		this.startY = startY;
		this.stats = statistics;
		this.initGUI();
	}
	
	public  void changeStatistics(Statistics statistics,String startY) {
		this.stats = statistics;
		this.startY = startY;
		Curve2D curve = new Curve2D(stats.getWtrace(),stats.getIndex(Statistics.TIME),stats.getIndex(startY));
		removeAll();
		add(curve.getControls(),BorderLayout.NORTH);
		plotter = new Plotter(curve);
		add(plotter,BorderLayout.CENTER);
		
	}
	


	protected void initGUI() {
		// Statistics panel
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("Statistics"));
		// Curve displayer
		
		Curve2D curve = new Curve2D(stats.getWtrace(),stats.getIndex(Statistics.TIME),stats.getIndex(startY));
		add(curve.getControls(),BorderLayout.NORTH);
		plotter = new Plotter(curve);
		add(plotter,BorderLayout.CENTER);
		
		
		
		
//		setPreferredSize(new Dimension(400,400));
//		setMinimumSize(new Dimension(100,300));

//		JPanel save_panel = new JPanel();
//		save_panel.setLayout(new BorderLayout());
//		// Field to enter the file name
//		final JTextField filename_field = new JTextField(filename);
//		filename_field.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				filename = filename_field.getText();
//			}
//		});        
//		save_panel.add(filename_field,BorderLayout.CENTER);
//
//		JButton save = new JButton("Save main.java.statistics");
//		save.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				filename = filename_field.getText();
//				main.java.statistics.save();
//			}
//		});
//		save.setMnemonic(KeyEvent.VK_S);
//		save_panel.add(save,BorderLayout.WEST);
//
//		add(save_panel,BorderLayout.SOUTH);
		
	}

	

}
