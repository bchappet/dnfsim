package gui;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;

import model.Model;
import model.Root;
import coordinates.NullCoordinateException;


public class TreeParametersView extends JScrollPane implements TreeSelectionListener,MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4334546971336119655L;

	protected JTree tree;
	protected RunnerGUI gui;
	protected DisplayNode node;
	/**Margin of the panel**/
	protected int margin = 10;
	/**
	 * When a parameter is selected, its informations are displayed in this
	 * panel
	 **/
	protected ParameterView parameterView;

	protected Model model;

	public TreeParametersView(DisplayNode node,JTree tree,Root root, ParameterView parameterView,RunnerGUI gui) {
		super(tree);
		this.model = root.getActiveModel();
		this.parameterView = parameterView;
		this.gui = gui;
		this.tree = tree;
		this.node = node;
		
	
		//System.out.println(root.printTree(0));
		tree.setRootVisible(false);
		

		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		/* Listen to the events */
		tree.addTreeSelectionListener(this);
		tree.addMouseListener(this);
		
		//this.add(tree);

		

	}
	
	
	
	/**
	 * Recursivly look for the displayNode wich encappsulate the given TreeNode
	 * @param activeModel
	 */
	public DisplayNode getNode(Node treeNode) {
		//System.out.println("treeNode : " + treeNode.getName() + " node : " + node);
		return node.getDisplayNodeOfObject(treeNode);
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
		DisplayNode disp = (DisplayNode) tree.getLastSelectedPathComponent();
		if(disp != null)
		{
			try {
				disp.valueChanged(parameterView);
			} catch (NullCoordinateException e) {
				e.printStackTrace();
			}
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
//					boolean play = 	gui.pause();
//						TreeNode node = (TreeNode) tree
//								.getLastSelectedPathComponent();
//						node.duplicateParam();
//						if(play)
//							gui.play();
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
