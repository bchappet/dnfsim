package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import maps.AbstractMap;
import maps.AbstractUnitMap;
import maps.Parameter;
import maps.Track;
import maps.Unit;
import coordinates.NullCoordinateException;
import coordinates.Space;


public class DisplayMap extends QuickViewPanel implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -618931228063728674L;

	public static final int X = 0;
	public static final int Y = 1;



	/**Size of the display zone**/
	protected int dx,dy;

	/**The color map associated with view**/
	protected ColorMap colorMap;

	protected boolean isMouse = false;
	protected Double[] mouse = {0d,0d};

	double contrast = 1;

	protected double sx;
	protected double sy;
	protected double[][] buffer;
	protected double resolution; //TODO : resolution as a Var => avoid to recheck it at each update

	protected TrackHighlighting trackHigh; //shared between all the displayed map

	/**Margin ratio**/
	protected double margin = 0.95;
	/**Black border in pixel**/
	protected int blackBorder = 1; 

	/**Offset computed from margin ratio to place the image in the middle**/
	protected int offsetX ; 
	protected int offsetY ;
	
	/**Display space**/
	protected Space space;
	
	
	/**offset to see the simulation frame only**/
	protected int simuOffset;



	public DisplayMap(GUI gui,Parameter linked)
	{
		super(gui,linked);
		this.space = linked.getSpace();
		//System.out.println("Construct swingmap : " + displayed.getName());
		//((AbstractMap) this.displayed).initMemory();
		Color color[] = {Color.BLUE,Color.WHITE,Color.RED};
		colorMap = new AdaptiveAndEquilibratedColorMap(color);
		

		MouseAdapter adapter = new MouseInteract(this);
		addMouseMotionListener(adapter);
		addMouseListener(adapter);
		addMouseWheelListener(adapter);

		createBuffer();

		resolution = space.getSimulationSpace().getResolution();
		if(resolution != space.getResolution()){
			//We have to shift the frame
			simuOffset = (int) ((space.getResolution() - resolution)/2);
		//	System.out.println("Offset for map : " + displayed.getName() + " == " + simuOffset);
		}else{
			simuOffset = 0;
		}
		this.validate();
		


	}
	
	


	/**
	 * Copy constructor
	 * @param map
	 */
	public DisplayMap(DisplayMap map) {
		super(map); //Shared (runner,displayed)
		this.colorMap = map.colorMap.clone(); //Copy
		this.trackHigh = map.trackHigh; //shared
		MouseAdapter adapter = new MouseInteract(this);
		addMouseMotionListener(adapter);
		addMouseListener(adapter);
		addMouseWheelListener(adapter);

		createBuffer();

		resolution = displayed.getSpace().getSimulationSpace().getResolution();

		this.validate();

	}
	
	public void reset() {
		//colorMap.reset();
	}

	/**
	 * TODO swap charge instead of creating it
	 */
	protected void createBuffer()
	{
		Integer[] dim = displayed.getSpace().getSimulationSpace().getDiscreteSize();
		sx = dim[X];
		sy = dim[Y];
		this.buffer = new double[(int)sx][(int)sy];
		
		if(trackHigh != null)
			trackHigh.setDim((int)sx, (int)sy);
	}


	public void update() throws NullCoordinateException
	{
		resolution = displayed.getSpace().getSimulationSpace().getResolution();

		createBuffer();
		for(int i = 0 ; i < sx ; i++)
		{
			for(int j = 0 ; j < sy ; j++)
			{
				
				//Double[] coord = displayed.getSpace().continuousProj(new Double[]{(double)i,(double)j});
				
				this.buffer[i][j] = ((Parameter) displayed).getFast(i+simuOffset,j+simuOffset);
			}
		}




	}


	@Override
	public void render(Graphics2D g){


		//Dimension dim = this.getParent().getSize();
		Dimension dim = this.getSize();
		dx = (int) (dim.width*margin);
		dy = (int) (dim.height*margin);

		offsetX = (int) ((dim.width - dx)/2d);
		offsetY = (int) ((dim.height - dy)/2d);
		//		
		//		Dimension parent = this.getParent().getSize();
		//		dx = parent.width;
		//		dy = parent.height;

		//System.out.println(dim);



		Image img = createImage((int)resolution,(int)resolution);
		Graphics tmp = img.getGraphics();

		if(trackHigh == null || !trackHigh.isDisplayed())
		{
			for(int i = 0 ; i < sx ; i++)
			{
				for( int j = 0 ; j < sy ; j ++)
				{
					//					g.setColor(colorMap.getColor(charge[i][j]));
					//					g.fillRect(i*resolutionX-border,j*resolutionY-border,resolutionX-border,
					//							resolutionY-border);

					tmp.setColor(colorMap.getColor(buffer[i][j]));
					tmp.fillRect(i,j,1,1);
				}
			}
		}
		else
		{
			//We add a color depending on the hilighted map activity
			for(int i = 0 ; i < sx ; i++)
			{
				for( int j = 0 ; j < sy ; j ++)
				{
					//					g.setColor(ColorMap.merge(highlightColorMap.getColor(highlightedBuffer[i][j]),colorMap.getColor(charge[i][j])));
					//					g.fillRect(i*resolutionX-border,j*resolutionY-border,resolutionX-border,resolutionY-border);
					tmp.setColor(ColorMap.merge(trackHigh.getColor(i,j),colorMap.getColor(buffer[i][j])));
					tmp.fillRect(i,j,1,1);
				}
			}

		}
		if(isMouse){
			//Mouse pointer
			tmp.setColor(Color.BLACK);
			tmp.fillRect(mouse[X].intValue(), mouse[Y].intValue(), 1, 1);
		}

		Image scaled = img.getScaledInstance(dx, dy, Image.SCALE_FAST);
		g.drawImage(scaled,offsetX,offsetY,dx,dy,null);
		for(int i = 0 ; i < blackBorder ; i++)
			g.drawRect(offsetX-i, offsetY-i, dx+2*i, dy+2*i);
		//		for(int i = 0 ; i < dx ; i++)
		//		{
		//			for( int j = 0 ; j < dy ; j ++)
		//			{
		//				g.setColor(Color.black);
		//				g.fillRect(i, j, 1, 1);
		//			}
		//		}


	}


	/** Paint a square corresponding to a defined defined
	 *  @original g    graphics to paint on
	 *  @original x    x left coordinate
	 *  @original y    y top coordinate
	 *  @original s    size of the square
	 *  @original v    str to paint (color) */
	protected void setColor(Graphics2D g,  double v) {
		// Proceed only if the str is defined
		if (v==-Double.MAX_VALUE)
			return;
		// Apply the constrast
		v*=contrast;
		if (v<0) {
			if (v<-1) v=-1;
			g.setColor(meanColor(Color.white,Color.blue,-v));
		} else {
			if (v>1) v=1;
			g.setColor(meanColor(Color.white,Color.red,v));
		}

	}

	/** Compute the barycenter of 2 colors */
	public Color meanColor(Color c1, Color c2, double t) {
		return new Color((int)(t*c2.getRed()+(1-t)*c1.getRed()),
				(int)(t*c2.getGreen()+(1-t)*c1.getGreen()),
				(int)(t*c2.getBlue()+(1-t)*c1.getBlue()));
	}

	/** Key and mouse listeners and interactions */
	class MouseInteract extends MouseAdapter {

		protected DisplayMap map;

		protected MessageFormat format = new MessageFormat(
				"{0,choice,0#{0,number,'#,##0.####'}|99999<{0,number,'000000.####E0'}}",
				Locale.ENGLISH);

		public MouseInteract(DisplayMap map)
		{
			this.map = map;
		}

		public void mouseEntered(MouseEvent e){
			isMouse = true;
		}
		public void mouseExited(MouseEvent e){
			isMouse = false;
		}

		public void mouseMoved(MouseEvent e){
			double x = (double)e.getX();
			double y = (double)e.getY();
			x = (x-offsetX); 
			y = (y-offsetY); 



			//System.out.println(x + "," + y);
			if(x >=0 && y >= 0 && x < dx && y < dy)
			{
				//We are in the map

				double factX = resolution/dx;
				double factY = resolution/dy;

				double xx = x * factX;
				double yy = y * factY;


				mouse = new Double[]{xx,yy}; 
				map.setToolTipText(format.format(new Object[]{displayed.getFast((int) xx+simuOffset,(int) yy+simuOffset)}));

			}

		}

		public  void mouseDragged(MouseEvent e)
		{
			//			System.out.println("Drag");
			//			DisplayMap disp = (DisplayMap) e.getSource();
			//			trans.exportAsDrag(disp, e, TransferHandler.LINK);
		}


		public  void mousePressed(MouseEvent e) {

			int button = e.getButton();


			if(trackHigh != null && button == MouseEvent.BUTTON1)//Right click
			{


				double x = (double)e.getX();
				double y = (double)e.getY();
				x = (x-offsetX); 
				y = (y-offsetY); 

				if(x >=0 && y >= 0 && x < dx && y < dy)
				{
					//We are in the map

					double factX = resolution/dx;
					double factY = resolution/dy;

					double xx = x * factX;
					double yy = y * factY;

					Double[] click = new Double[]{xx,yy}; 
					//System.out.println("trans : " + Arrays.toString(click));

					//Get the trackable object from the model
					List<AbstractMap> tracks = gui.getRoot().getActiveModel().getTracks();
					//System.out.println("Tracks : " + tracks);

					//We find the closest
					double distMin = Double.MAX_VALUE;
					AbstractMap closest = null;

					Space space = displayed.getSpace().getSimulationSpace();

					//Lock for the closest track2D
					for(AbstractMap t : tracks)
					{
						double d = 0;
						try {
							d = Space.distance(space.continuousProj(click), ((Track) ((AbstractUnitMap) t).getUnitModel()).getCenter());
						} catch (NullCoordinateException e1) {
							e1.printStackTrace();
						}
						if(d < distMin)
						{
							distMin = d;
							closest = t;
						}
					}



					if(closest != null)
					{
						
						//There is at least one trackable target
						//TODO synchro
						if(trackHigh.isDisplayed() && trackHigh.isTheTrack(closest))
						{
							//we un-highlight the target
							trackHigh.setDisplayed(false);

						}
						else
						{
							trackHigh.setHilightedTrack(closest);
							closest.constructMemory();
							trackHigh.setDisplayed(true);
						}
					}
				}
			}
		}
	}

	public DisplayMap clone()
	{
		return new DisplayMap(this);
	}




	public void setTrackHigh(TrackHighlighting trackHigh) {
		this.trackHigh = trackHigh;
	}






}