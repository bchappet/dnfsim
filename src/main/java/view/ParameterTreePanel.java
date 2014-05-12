package main.java.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

import main.java.controler.ParameterControler;
import main.java.controler.VarControler;
import main.java.coordinates.NullCoordinateException;

/**
 * Define the TreePanel view and The DetailsPanel feeding. Maybe we should separate the two
 * @author benoit
 * @version 10/05/2014
 *
 */
public class ParameterTreePanel extends JScrollPane implements TreeSelectionListener,MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4334546971336119655L;

	protected JTree tree;
	/**Margin of the panel**/
	protected int margin = 10;
	/**
	 * When a parameter is selected, its informations are displayed in this
	 * panel
	 **/
	protected DetailsPanel detailsPanel;


	public ParameterTreePanel(JTree tree, DetailsPanel detailsPanel) {
		super(tree);
		//System.out.println("tree" +treeModel);
		this.tree = tree;
		this.detailsPanel = detailsPanel;
		//System.out.println(root.printTree(0));
		this.tree.setRootVisible(true);
		this.tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		/* Listen to the events */
		this.tree.addTreeSelectionListener(this);
		this.tree.addMouseListener(this);
		//System.out.println("Jtree" +tree);
	}
	
	

	
	
	public void delete()
	{
		this.tree.collapseRow(this.tree.getRowCount());
		this.tree = null;
	}



//	public void resize() {
//		//this.setPreferredSize(this.getParent().getPreferredSize());
//		Dimension dim = this.getParent().getPreferredSize();
//		treeView.setPreferredSize(new Dimension(dim.width-margin , (int)(dim.height/2d)-margin ));
//	}



	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		ParameterControler disp = (ParameterControler) tree.getLastSelectedPathComponent();
		if(disp != null)
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



	public JTree getTree() {
		return tree;
	}

	


	

}
