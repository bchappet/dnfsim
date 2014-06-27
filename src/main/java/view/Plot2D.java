package main.java.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.UIManager;

import main.java.plot.JPanelDB;

/**
 * from JC Quinton
 * Contains 2 {@Curve2D}
 * @author benoit
 * @version 09/06/2014
 *
 */
public class Plot2D extends ViewPanel {

	private Curve2D curveX;
	private Curve2D curveY;

	


	/** String size (pixels) */
	public static final int FONT_SIZE = 12;
	/** Size of the marks put near text or information */
	public static final double MARK_SIZE = 4;

	/** Number format when displayed as a string */
	public static final DecimalFormat dformat = new DecimalFormat("#0.00");
	/** Font for displaying info on the graph */
	public static final Font font = new Font("",Font.PLAIN,(int)FONT_SIZE);

	/** Curve border (pourcentage) */
	protected static final double CURVE_BORDER = 0.05;


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
	
	/**Display button**/
	private JComboBox<String> jcbX;
	private JComboBox<String> jcbY;
	private JPanel dispPanel;


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
		UIManager.put("Panel.background", Color.white);
		this.connect = true;
		
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
				curveX = constructView(name, getViewFactory() );
			}
		});
		
		jcbY.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				String name = (String)cb.getSelectedItem();
				curveY = constructView(name,  getViewFactory() );
			}
		});
		
		jcbX.setSelectedIndex(0);
		jcbY.setSelectedIndex(1);
		
		
		
		dispPanel = new JPanelDB() {
			
			@Override
			public void render(Graphics2D g) {
				Dimension dim = this.getSize();
				int dx = (int) (dim.width*1);
				int dy = (int) (dim.height*1);
				
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, dim.width, dim.height);
				g.setColor(Color.BLACK);
//				System.out.println("dx : " + dx);	

				int offsetX = (int) ((dim.width - dx)/2d);
				int offsetY = (int) ((dim.height - dy)/2d);
				//		if(vars.length() > SIZE_LIMIT){
				//			vars.clear(); //TODO
				//		}

				// Get the 2 traces to display

				//		System.out.println("render curve2D");
				//		System.out.println("Tarce x : " + var_x);
				//		System.out.println("Values : " + var_x.getMax());

				// Transform the trace main.java.coordinates
				List<Double> val_x = curveX.getValues();
				List<Double> val_y = curveY.getValues();
				if(val_x.size() > 0){
					
					int val_nb = Math.min(val_x.size(),val_y.size());
					double[] xy = new double[2*val_nb];
					for (int i=0; i<val_nb; i++) {
						xy[2*i]=curveX.transformValue(val_x.get(i),dx);
						xy[2*i+1]=curveY.transformValue(val_y.get(i),dy);
					}
					//trans.transform(xy,0,xy,0,val_nb);

					// Display the trace
					g.setColor(color_min);
					g.setStroke(new BasicStroke(CURVE_WIDTH));
					if (connect) {
						Path2D.Double curvep = new Path2D.Double();
						if (val_nb>0)
							curvep.moveTo(xy[0],xy[1]);
						for (int i=nextPoint(xy,0); i>=0; i=nextPoint(xy,i))
							curvep.lineTo(xy[2*i],xy[2*i+1]);
						g.draw(curvep);
					} else {
						for (int i=0; i>=0; i=nextPoint(xy,i)) {
							g.draw(new Ellipse2D.Double(
									xy[2*i],xy[2*i+1],1,1));
						}
					}
				}
			}
			
			@Override
			public void interactRelease(EventObject event) {
			}
			
			@Override
			public void interact(EventObject event) {
			}
		};
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



	public void setCurveX(Curve2D view){
		this.curveX = view;
	}

	public void setCurveY(Curve2D view){
		this.curveY = view;
	}

	public double[] getLimits() {

		// Get the 2 traces to display
		return new double[]{curveX.getMin(),curveY.getMin(),
				curveX.getMax(),curveY.getMax()};
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

	/** Get the current transform */







	/** Draw a string on the graph */
	protected void drawText(Graphics2D g, String s, double x, double y, int offsetx, int offsety, boolean mark) {
		s = " " + s + " ";
		// Mark
		if (mark)
			g.fill(new Rectangle.Double(x-MARK_SIZE/2,y-MARK_SIZE/2,MARK_SIZE,MARK_SIZE));
		// Offsets for displaying on sides
		FontMetrics fm = g.getFontMetrics();
		offsetx *= fm.stringWidth(s); // tb should be equal to -1,0 or 1 (no offset if tb=0)
		offsety *= fm.getHeight(); // offsety should be equal to -1,0 or 1 (no offset if offsety=0)
		// Actual text
		g.drawString(s,(int)x+offsetx,(int)y+offsety);
	}

	/** Draw a string on the graph */
	protected void drawText(Graphics2D g, String s, double x, double y, int offsetx, int offsety) {
		drawText(g,s,x,y,offsetx,offsety,true);
	}

	/** Compute the barycenter of 2 colors */
	public static Color meanColor(Color c1, Color c2, double t) {
		//t = max(0.0,min(1.0,t));
		return new Color((int)(t*c2.getRed()+(1-t)*c1.getRed()),
				(int)(t*c2.getGreen()+(1-t)*c1.getGreen()),
				(int)(t*c2.getBlue()+(1-t)*c1.getBlue()));
	}

	

}
