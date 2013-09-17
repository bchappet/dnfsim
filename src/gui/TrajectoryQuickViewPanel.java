package gui;

import java.awt.BorderLayout;
import java.text.DecimalFormat;

import javax.swing.JTextField;
import javax.swing.tree.TreeNode;

import plot.Curve2D;
import plot.Plotter;
import plot.WTrace;
import dnf.Trajectory;

public class TrajectoryQuickViewPanel extends QuickViewPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7304502068759180089L;
	protected WTrace wtrace;
	protected DecimalFormat formater;
	protected JTextField txt;

	public TrajectoryQuickViewPanel(RunnerGUI gui,Trajectory function) {
		super(gui,(TreeNode) function);
		this.setLayout(new BorderLayout());
		this.formater = new DecimalFormat("#0.0000");
		trajectoryQuickView();
	}

	/**
	 * Depend only of time
	 */
	public void trajectoryQuickView()
	{
		 txt = new JTextField(formater.format(((Trajectory)displayed).get()));
		
		wtrace = new WTrace("Time","Value");
		Curve2D curve = new Curve2D(wtrace,0,1);
		Plotter plotter = new Plotter(new Curve2D[]{curve});
		
		this.add(plotter,BorderLayout.CENTER);
		this.add(txt,BorderLayout.SOUTH);
		
	    //this.add(curve.getControls(),BorderLayout.SOUTH);
		Thread th = new Thread(this);
		th.start();



	}

	@Override
	public void run() {
		while(true)
		{
			wtrace.add(System.currentTimeMillis(),((Trajectory)displayed).get());
			txt.setText(formater.format(((Trajectory)displayed).get()));
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}



}
