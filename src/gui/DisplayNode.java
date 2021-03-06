package gui;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import maps.AbstractMap;
import maps.AbstractUnitMap;
import maps.Leaf;
import maps.NeighborhoodMap;
import maps.Parameter;
import maps.Var;
import model.Model;
import model.Root;
import statistics.CharacteristicsCNFT;
import unitModel.SomUM;
import coordinates.NullCoordinateException;
import coordinates.Space;
import unitModel.SomUM;

public class DisplayNode implements javax.swing.tree.TreeNode{

	protected List<DisplayNode> childrens;
	protected DisplayNode parent;
	protected Node linked;
	protected RunnerGUI gui;
	protected boolean visible;




	public DisplayNode(DisplayNode parent,Node linked,RunnerGUI gui)
	{
		this.gui = gui;
		this.childrens = new ArrayList<DisplayNode>();
		this.parent = parent;
		this.linked = linked;
		this.visible = false;
		this.construct();
	}

	/**
	 * Construct children nodes
	 */
	public void construct()
	{

		//System.out.println(linked +" ==> childrens : " +getAllChildren());
		for(Object obj : getAllChildren() )
		{
			Node node = (Node) obj;
			DisplayNode son = null;
			if((node instanceof Var))
			{
				//son = new VariableDisplayNode(this,node);
			}
			else if(node instanceof AbstractMap)
			{
				son = new MapDisplayNode(this,(AbstractMap) node,gui);

			}
			else if(node instanceof Model)
			{
				son = new ModelDisplayNode(this,(Model)node,gui);

			}
			else if(node instanceof Space)
			{
				son = new SpaceDisplayNode(this,(Space) node,gui);
			}
			else if(node instanceof Leaf)
			{
				son = new LeafDisplayNode(this ,(Leaf) node , gui);
			}
			else
			{
				son = new DisplayNode(this,node,gui);
			}

			if(son != null)
			{
				//System.out.println("linked : " + son.linked + " list : " +son.linked.getParamNodes());
				this.childrens.add(son);
			}else{
			}
		}
	}

	/**
	 * Add here the Object that you want to display in the GUI tree
	 * @return
	 */
	public List getAllChildren()
	{
		if(linked instanceof AbstractUnitMap){
			List<Parameter> l = new LinkedList<Parameter>();
			l.add(((AbstractMap) linked).getDt());
			l.addAll(((AbstractMap)linked).getParams());
			l.addAll((Collection<? extends Parameter>) ((AbstractUnitMap)linked).getSubUnitMaps());
			return l;
		}
		else if(linked instanceof AbstractMap)
		{
			//System.out.println("linked : " + linked);
			List<Parameter> l = new LinkedList<Parameter>();
			l.add(((AbstractMap) linked).getDt());
			l.addAll(((AbstractMap)linked).getParams());
			return l;

		}
		else if(linked instanceof Model )
		{
			if(((Model) linked).isInitilized()){
				List<Object> l = new LinkedList<Object>();
				//l.add(((Model) linked).getRefSpace()); TODO
				l.add(((Model) linked).getRootParam());
				l.addAll(((Model)linked).getParameters());
				return l;
			}else{
				return new LinkedList();
			}
		}
		else if(linked instanceof Root)
		{
			return ((Root)linked).getModels();
		}
		else
		{
			return new LinkedList();
		}
	}

	/**
	 * Recursively look for the given node and return the Display node handler
	 * @param node
	 * @return
	 */
	public DisplayNode getDisplayNodeOfObject(Node node)
	{
		DisplayNode ret = null;
		
		if(node.equals(linked))
		{
			ret = this;
		}
		else
		{
			//System.out.println("This : " + linked.getName());
			//System.out.println("Childrens : " + childrens);
			int i =0;
			while(ret == null && i < childrens.size() )
			{
				ret = childrens.get(i).getDisplayNodeOfObject(node);
				i++;
			}
			//ret != null || i = children size
		}
		return ret;
	}

	/**
	 * 
	 * @param visibility
	 */
	public void setTreeVisibility(boolean visibility)
	{

	}

	/**
	 * Return a constructed quick view panel
	 * @return
	 * @throws NullCoordinateException 
	 */
	public QuickViewPanel getQuickViewPanel() throws NullCoordinateException
	{
		return new ParameterQuickViewPanel(gui,(Parameter) linked);
	}

	/**
	 * Method called the treeNode is selected
	 * @param parameterView
	 * @throws NullCoordinateException
	 */
	public void valueChanged(ParameterView parameterView) throws NullCoordinateException {
		if(!(linked instanceof Root || linked instanceof CharacteristicsCNFT))
		{
			parameterView.setDisplayedParam("Param selected" ,
					new ParamHeaderPanel(gui,(Parameter) linked),
					this.getParamPanel(),
					getQuickViewPanel());
		}
	}

	public JPanel getParamPanel()
	{
		JPanel ret = new JPanel();
		ret.setBorder(BorderFactory.createTitledBorder("Parameters:"));
		ret.setLayout(new BoxLayout(ret,BoxLayout.PAGE_AXIS));
		for(Object n : getAllChildren())
		{
			if(n instanceof Var)
			{
				ParameterModifier spm = new ParameterModifier(gui, (Var) n);
				ret.add(spm);
			}
		}
		return ret;
	}

	public String toString()
	{
		return ((Node)linked).getName();
	}



	@Override
	public Enumeration<DisplayNode> children() {
		return new EnumChildren(this);
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public DisplayNode getChildAt(int arg0) {
		return childrens.get(arg0);
	}

	@Override
	public int getChildCount() {
		return childrens.size();
	}


	@Override
	public DisplayNode getParent() {
		return parent;
	}

	@Override
	public boolean isLeaf() {
		return childrens.size() == 0;// || !visible;
	}

	public List<DisplayNode> getChildren() {
		return childrens;
	}

	@Override
	public int getIndex(javax.swing.tree.TreeNode node) {
		return childrens.indexOf(node);
	}

	public Object getLinked() {
		return linked;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}










}
