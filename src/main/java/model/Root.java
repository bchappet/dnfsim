package main.java.model;



import java.util.LinkedList;
import java.util.List;

import main.java.gui.Node;





public class Root implements Node {

	//List of Model
	public static final String CNFT = "CNFT";
	public static final String CNFT_SLOW = "CNFTAssynch";
	public static final String GSPIKE = "GSpike";
	public final static String ESPIKE = "ESpike";
	public final static String NSPIKE = "NSpike";
	public static final String NSPIKE2 = "NSpikeAssynch";
	public final static String BILAYER_SPIKE = "BilayerSpike";
	public final static String RSDNF = "RSDNF";
	public static final String RSDNF_MIXTE = "RSDNF_Mixte";
	public static final String NSPIKE_PRECISION = "NSpikePrecision";

	protected Model activeModel; //Current visualized main.java.model
	protected String name;
	protected List<Model> models;

	public Root()
	{
		this.name = "Root                                         ";//TODO fins another way to display the tree corectly
		this.models = new LinkedList<Model>();
	}

	//	public Root(Root root) {
	//		super(root);
	//		activeModel = root.activeModel;
	//	}

	public Model getActiveModel()
	{
		return activeModel;
	}

	public void setActiveModel(String model) throws Exception
	{
		//activeModel.delete();
		activeModel = getModel(model);
	}

	public void setActiveModel(Model model) {
		activeModel = model;
	}


	public Model getModel(String model) throws Exception {
		for(Model m : models){
			if(m.getName().equals(model)){
				return m;
			}
		}
		throw new Exception("Model " + model + " not found");
	}

	public void addModel(Model model)
	{
		models.add(model);
		//System.out.println("add : " + main.java.model.getParamNodes());
	}


	public void setActiveModel(int i) {
		activeModel = models.get(i);
	}

	//	public Root clone()
	//	{
	//		return new Root(this);
	//	}

	public String toString()
	{
		return "Root                                             ";
	}

	public List<Model> getModels() {
		return models;
	}

	@Override
	public String getName() {
		return toString();
	}

	public static Model constructModel(String model) throws Exception {
		return Models.getModel(model).construct();
	}

	public void addAllModel() {
		for(Models m : Models.values()){
			if(!m.getName().equals(activeModel.getName())){
				addModel(m.construct());
			}
		}
		
	}

	public void replaceModel(Model current) throws Exception {
		if(activeModel.getName().equals(current.getName())){
			activeModel = current;
		}
		
		Model previous = getModel(current.getName());
		int index = models.indexOf(previous);
		models.set(index, current);
		
	}







}

