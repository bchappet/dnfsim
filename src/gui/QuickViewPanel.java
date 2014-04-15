package gui;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import maps.Parameter;
import plot.JPanelDB;
import coordinates.NullCoordinateException;


public   class QuickViewPanel extends JPanelDB  implements Updated {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8836535197104696167L;
	//@Deprecated ??
	protected RunnerGUI gui;
	protected Parameter displayed;
	protected boolean isDisplayed; //Is displayed in the center panel
	protected JPanel borderPanel;

	public QuickViewPanel(RunnerGUI gui,Parameter displayed)
	{
		super();
		this.gui = gui;
		this.displayed = displayed;
		this.isDisplayed = false;
		displayed.addVue(this);
		MouseAdapter adapter = new MouseInteract(this);
		addMouseListener(adapter);
	}

	public JPanel getBorderPane(){
		if(borderPanel == null){
			borderPanel = new JPanel();
			borderPanel.setLayout(new BoxLayout(borderPanel, BoxLayout.PAGE_AXIS));
			borderPanel.setBorder(BorderFactory.createTitledBorder(displayed.getName()));
			borderPanel.add(this);
		}
		return borderPanel;
	}
	/**
	 * Copy constructor
	 * @param pane
	 */
	public QuickViewPanel(QuickViewPanel pane) {
		this.gui = pane.gui; //shared
		this.displayed = pane.displayed;//shared
		MouseAdapter adapter = new MouseInteract(this);
		addMouseListener(adapter);
	}

	@Override
	public void update() throws NullCoordinateException
	{
		//Nothing
	}
	
	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub

	}

	public Parameter getDisplayed()
	{
		return displayed;
	}

	public QuickViewPanel clone()
	{
		return new QuickViewPanel(this);
	}

	/** Key and mouse listeners and interactions */
	protected class MouseInteract extends MouseAdapter {

		private JPopupMenu menu;

		

		

		protected QuickViewPanel pane;

		public MouseInteract(QuickViewPanel pane)
		{
			this.pane = pane;
		}


		public void mousePressed(MouseEvent e) {

			int button = e.getButton();
			if (button == MouseEvent.BUTTON3) //Left click
			{

				final DynamicParamsView view = gui.getDynamicParamView();
				if(!pane.isDisplayed)
				{

					menu = new JPopupMenu();
					JMenuItem item = new JMenuItem("Add");
					item.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							view.addView(pane.clone());

						}
					});
					menu.add(item);
					menu.show(e.getComponent(), e.getX(), e.getY());



				}
				else
				{
					
					menu = new JPopupMenu();
					JMenuItem item = new JMenuItem("Remove");
					item.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							view.removeView(pane);

						}
					});
					menu.add(item);
					menu.show(e.getComponent(), e.getX(), e.getY());
					
				}
			}
		}

		public void mouseReleased(MouseEvent e){
			if(menu != null )//Right click
				menu.setVisible(false);
		}

		
	}



	public boolean isDisplayed() {
		return isDisplayed;
	}
	public void setDisplayed(boolean isDisplayed) {
		this.isDisplayed = isDisplayed;
	}
	@Override
	public void interact(EventObject event) {
		// TODO Auto-generated method stub

	}
	

	@Override
	public void interactRelease(EventObject event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}





}
