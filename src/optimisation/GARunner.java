package optimisation;

import gui.Printer;
import gui.Runner;
import maps.Parameter;
import model.Model;
import model.Root;

/**
 * There will be one runner per core
 * The ga launcher will schedule the tasks
 * @author bchappet
 *
 */
public class GARunner extends Runner {


	protected Model model; //uniq
	protected String params;



	public GARunner(Model model,String params,String scenario,GAScenarioPrinter gaLauncher){
		super(model,scenario,gaLauncher);
		this.model= model;
		model.getCommandLine().setRunner(this);
		this.params = params;
	}

	@Override
	public void run() {
		try{
			
			System.out.println("start : " + Thread.currentThread());
			model.reset();
			model.getCommandLine().reinitialize();
			model.getCommandLine().parseCommand(params+scenario);
			((GAScenarioPrinter)printer).computeScenarioFitness();
			System.out.println("end : " + Thread.currentThread());
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	



	





}
