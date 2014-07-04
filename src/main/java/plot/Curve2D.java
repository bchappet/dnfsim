package main.java.plot;

/**
 * Curve in 2D
 *
 * @author Jean-Charles Quinton
 * @version 30/11/09
 * creation 21/02/09
 */

/*
    Copyright 2010 Jean-Charles Quinton

    This file is part of DNF.

    DNF is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    DNF is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with DNF.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class Curve2D extends Plot {
	/** Variables to main.java.plot */
	private WTrace vars;
	/** 2 variables chosen to be displayed */
	private int vx, vy;
	/** potential variable for the color */
	private int vc;

	/** Connect the points or not */
	private boolean connect;
	public static final boolean LINES = true;
	public static final boolean POINTS = false;

	/** Line width for the curve */
	private static final float CURVE_WIDTH = 2.0f;
	private static final int SIZE_LIMIT = 1000;

	/** Display colors boundaries */
	protected Color color_min;
	protected Color color_max;

	/** Optimize the number of points to display ? */
	private boolean optimize = true;

	/** Constructor */
	public Curve2D(WTrace vars_, int vx_, int vy_, int vc_,
			Color color_min_, Color color_max_,
			boolean connect_) {
		vars = vars_;
		vx = vx_;
		vy = vy_;
		vc = vc_;
		color_min = color_min_;
		color_max = color_max_;
		connect = connect_;
	}

	/** Constructor */
	public Curve2D(WTrace vars_, int vx_, int vy_) {
		this(vars_,vx_,vy_,-1,Color.blue,Color.red,LINES);
	}

	/** Constructor */
	public Curve2D(WTrace vars_, int vx_, int vy_, boolean connect_) {
		this(vars_,vx_,vy_,-1,Color.blue,Color.red,connect_);
	}

	/** Constructor */
	public Curve2D(WTrace vars_, int vx_, int vy_, Color color_) {
		this(vars_,vx_,vy_,-1,color_,color_,LINES);
	}

	/** Constructor */
	public Curve2D(WTrace vars_, int vx_, int vy_, int vc_) {
		this(vars_,vx_,vy_,vc_,Color.blue,Color.red,POINTS);
	}

	/** Set optimization */
	public void setOptimization(boolean optimize_) {
		optimize = optimize_;
	}

	/** Get the variables */
	public WTrace getVars() {
		return vars;
	}

	/** Get the X coordinate variable */
	public int getX() {
		return vx;
	}

	/** Set the X coordinate variable */
	public void setX(int vx_) {
		vx = vx_;
	}

	/** Get the Y coordinate variable */
	public int getY() {
		return vy;
	}

	/** Set the Y coordinate variable */
	public void setY(int vy_) {
		vy = vy_;
	}

	/** Get the color of this curve */
	public Color getColor() {
		return color_min;
	}

	/** Get the controls to change the displayed variables */
	public JComponent getControls() {
		JPanel control_panel = new JPanel();
		control_panel.setLayout(new GridLayout(1,2));
		// X panel
		JPanel x_panel = new JPanel();
		x_panel.setLayout(new BorderLayout());
		x_panel.add(new JLabel(" X "),BorderLayout.WEST);
		JComboBox x_list = new JComboBox(vars.getCoords().toArray());
		
		JButton arrowBtnX = getButtonSubComponent(x_list);
	      if (arrowBtnX != null) {
	         arrowBtnX.addMouseListener(new JComboBoxMouseListener(x_list));
	      }
		
		x_list.setSelectedIndex(vx);
		x_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				
				vx = ((JComboBox)ev.getSource()).getSelectedIndex();
			}
		});
		x_panel.add(x_list,BorderLayout.CENTER);
		control_panel.add(x_panel);
		// Y panel
		JPanel y_panel = new JPanel();
		y_panel.setLayout(new BorderLayout());
		y_panel.add(new JLabel(" Y "),BorderLayout.WEST);
		JComboBox y_list = new JComboBox(vars.getCoords().toArray());
		JButton arrowBtnY = getButtonSubComponent(y_list);
	      if (arrowBtnY != null) {
	         arrowBtnY.addMouseListener(new JComboBoxMouseListener(y_list));
	      }
		y_list.setSelectedIndex(vy);
		y_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				vy = ((JComboBox)ev.getSource()).getSelectedIndex();
			}
		});
		y_panel.add(y_list,BorderLayout.CENTER);
		control_panel.add(y_panel);

		return control_panel;
	}
	
	protected static JButton getButtonSubComponent(Container container) {
	      if (container instanceof JButton) {
	         return (JButton) container;
	      } else {
	         Component[] components = container.getComponents();
	         for (Component component : components) {
	            if (component instanceof Container) {
	               return getButtonSubComponent((Container)component);
	            }
	         }
	      }
	      return null;
	   }
	
	protected class JComboBoxMouseListener implements MouseListener{
		protected JComboBox combo;
		public JComboBoxMouseListener(JComboBox combo){
			this.combo = combo;
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
		@Override
		public void mousePressed(MouseEvent e) {
			
			List<Trace> varL = vars.getCoords();
			for(int i = 0 ; i < varL.size() ;i++ ){
				Trace current = varL.get(i);
				if(i < combo.getItemCount()){
					Trace comboVar = (Trace) combo.getItemAt(i);
					//System.out.println("i : " + i + ";;;" + comboVar + " == " + current);
					if(!comboVar.equals(current)){
							combo.removeItemAt(i);
					}

				}else{
					combo.addItem(current);
				}
			}
			
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseClicked(MouseEvent e) {
		}
	}


	private JPopupMenu  menu;
	@Override
	public void interact(EventObject event) {
		// When a click is performed on the graph
		if (event instanceof MouseEvent) {
			// Clear the displayed trace

				 menu = new JPopupMenu();
				JMenuItem item = new JMenuItem("Replot");
				item.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						vars.clear(); //TODO

					}
				});
				menu.add(item);
				menu.show(((MouseEvent) event).getComponent(),
						((MouseEvent) event).getX(), ((MouseEvent) event).getY());
			}
	}
	
	@Override
	public void interactRelease(EventObject event){
		if(menu != null)
			menu.setVisible(false);
	}

	@Override
	public double[] getLimits() {
		// Check if the variables are not null
		if (vars==null || vx>=vars.getCoordsNb() || vx<0
				|| vy>=vars.getCoordsNb() || vy<0)
			return null;
		// Get the 2 traces to display
		Trace var_x = vars.getCoord(vx);
		Trace var_y = vars.getCoord(vy);
		return new double[]{var_x.getMin(),var_y.getMin(),
				var_x.getMax(),var_y.getMax()};
	}

	@Override
	public void render(Graphics2D g, int w, int h) {     

//		if(vars.length() > SIZE_LIMIT){
//			vars.clear(); //TODO
//		}

		// Get the 2 traces to display
		Trace var_x = vars.getCoord(vx);
		Trace var_y = vars.getCoord(vy);
		
//		System.out.println("render curve2D");
//		System.out.println("Tarce x : " + var_x);
//		System.out.println("Values : " + var_x.getMax());


		// Transform the trace main.java.coordinates
		ArrayList<Double> val_x = var_x.getValues();
		ArrayList<Double> val_y = var_y.getValues();
		int val_nb = Math.min(var_x.size(),var_y.size());
		double[] xy = new double[2*val_nb];
		for (int i=0; i<val_nb; i++) {
			xy[2*i]=val_x.get(i);
			xy[2*i+1]=val_y.get(i);
		}
		trans.transform(xy,0,xy,0,val_nb);

		// Display the trace
		Trace var_c = null;
		if (vc>=0)
			var_c = vars.getCoord(vc);
		else
			g.setColor(color_min);
		g.setStroke(new BasicStroke(CURVE_WIDTH));
		if (connect) {
			// No change in color along the curve
			if (var_c==null) {
				Path2D.Double curvep = new Path2D.Double();
				if (val_nb>0)
					curvep.moveTo(xy[0],xy[1]);
				for (int i=nextPoint(xy,0); i>=0; i=nextPoint(xy,i))
					curvep.lineTo(xy[2*i],xy[2*i+1]);
				g.draw(curvep);
			}
			// Else we draw each segment independently
			else {
				for (int i=0; i>=0; i=nextPoint(xy,i)) {
					g.setColor(meanColor(color_min,color_max,var_c.get(i)/var_c.getMax()));
					g.draw(new Line2D.Double(
							xy[2*(i-1)],xy[2*(i-1)+1],xy[2*i],xy[2*i+1]));
				}
			}
		} else {
			for (int i=0; i>=0; i=nextPoint(xy,i)) {
				if (var_c!=null)
					g.setColor(meanColor(color_min,color_max,var_c.get(i)/var_c.getMax()));
				g.draw(new Ellipse2D.Double(
						xy[2*i],xy[2*i+1],1,1));
			}
		}
	}

	/** Method that determine the next point to be displayed
	 * (to optimize and speed up the rendering */
	private int nextPoint(double[] xy, int i) {
		// Accept a point either if rendering optimization is not active
		// or if the points are far enough to be distinguishable
		int n = i;
		do {
			n++;
		} while (optimize && 2*n<xy.length
				&& Math.abs(xy[2*n]-xy[2*i])<CURVE_WIDTH
				&& Math.abs(xy[2*n+1]-xy[2*i+1])<CURVE_WIDTH);
		// Use -1 if we went out of range
		// (end condition for the iteration)
		if (2*n>=xy.length)
			n=-1;
		return n;
	}
}