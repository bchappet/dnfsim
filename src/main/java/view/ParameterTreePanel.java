package main.java.view;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;

import main.java.controler.ParameterControler;

/**
 * Define the TreePanel view and The DetailsPanel feeding. Maybe we should separate the two
 * @author benoit
 * @version 20/06/2014
 *
 */
public class ParameterTreePanel extends ViewPanel  implements TreeSelectionListener,MouseListener {

	private static final long serialVersionUID = 4334546971336119655L;
	
	/**Scroll pane**/
	private JScrollPane scrollPane;
	
	private JTree jtree;
	/**Margin of the panel**/
	private int margin = 10;
	/**
	 * When a parameter is selected, its informations are displayed in this
	 * panel 
	 * Optional
	 **/
	protected DetailsPanel detailsPanel;

	/**
	 * 
	 * @param name
	 * @param vf
	 * @param detailsPanel (optional)
	 */
	public ParameterTreePanel(String name,ViewFactory vf, DetailsPanel detailsPanel) {
		super(name,vf);
		this.jtree = new JTree(vf.getParameterControlerTree());
		this.scrollPane = new JScrollPane(this.jtree);
		this.setLayout(new BorderLayout());
		this.add(scrollPane);
		//System.out.println("tree" +treeModel);
		this.detailsPanel = detailsPanel;
		//System.out.println(root.printTree(0));
		this.jtree.setRootVisible(true);
		this.jtree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		/* Listen to the events */
		this.jtree.addTreeSelectionListener(this);
		this.jtree.addMouseListener(this);
		//System.out.println("Jtree" +tree);
	}
	
	/**
	 * 
	 * @param name
	 * @param vf
	 */
	public ParameterTreePanel(String name,ViewFactory vf){
		this(name,vf,null);
	}
	
	
	public void delete()
	{
		this.jtree.collapseRow(this.jtree.getRowCount());
		this.jtree = null;
	}


//	public void resize() {
//		//this.setPreferredSize(this.getParent().getPreferredSize());
//		Dimension dim = this.getParent().getPreferredSize();
//		treeView.setPreferredSize(new Dimension(dim.width-margin , (int)(dim.height/2d)-margin ));
//	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		ParameterControler disp = (ParameterControler) jtree.getLastSelectedPathComponent();
		if(this.detailsPanel != null && disp != null)
		{
			detailsPanel.setDisplayedParam("Param selected" ,disp);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
//		 if (SwingUtilities.isRightMouseButton(e)) {
//		        int row = tree.getClosestRowForLocation(e.getX(), e.getY());
//		        tree.setSelectionRow(row);
//		        JPopupMenu popupMenu = new JPopupMenu();
//		        JMenuItem menuItem = new JMenuItem("Duplicate");
//		        menuItem.addActionListener(new ActionListener() {
//					
//					@Override
//					public void actionPerformed(ActionEvent arg0) {
//					boolean play = 	main.java.gui.pause();
//						TreeNode node = (TreeNode) tree
//								.getLastSelectedPathComponent();
//						node.duplicateParam();
//						if(play)
//							main.java.gui.play();
//						
//					//tree.updateUI();
//						
//					}
//				});
//		        popupMenu.add(menuItem);
//		        popupMenu.show(e.getComponent(), e.getX(), e.getY());
//		    }
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	public JTree getJTree() {
		return jtree;
	}

	public void setDetailsPanel(DetailsPanel detailsPanel2) {
		this.detailsPanel = detailsPanel2;
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	


	

}
