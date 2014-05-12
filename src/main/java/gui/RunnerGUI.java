package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;

import main.java.console.CommandLineFormatException;
import main.java.console.TextAreaInOut;
import main.java.console.TextFieldIn;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.BadPathException;
import main.java.maps.Parameter;
import main.java.model.Model;
import main.java.model.Root;


public class RunnerGUI extends JApplet{



	//Invite of initScript in main.java.console
	public static final String INVITE = ">";


	protected Runner runner;

	protected ParameterCanvaPanel maps;
	protected Root root;
	protected ParameterTreePanel treeView;
	protected Thread computationThread;
	protected StatisticPanel stat;
	protected TextAreaInOut txtArea; //Display area
	protected TextFieldIn txtIn; //Input aera



	/**This lock is locked when the main.java.model is updating**/
	protected Lock lock;


	/**List of runner element updated when a parameter value change**/
	protected List<ParamUpdated> paramUpdated;

	/**Path of context files**/
	protected URL contextPath;
	/**Current charset**/
	protected Charset charset;


	/**The scenario parameter value**/
	protected String scenario;

	/**Display dimension**/
	protected Dimension dim;


	protected boolean showGui = false;

	//	/**Refreshing rate**/
	//	protected double refresh = 100; // ms

	public RunnerGUI(Runner runner,Root root, URL contextPath,Dimension dim){
		this.runner = runner;
		this.runner.setGui(this);
		this.root = root;
		this.root.addAllModel();
		this.contextPath = contextPath;
		this.lock = new ReentrantLock(true);
		this.dim = dim;
	}


	@Override
	public void init() {
		try{

			this.paramUpdated = new LinkedList<ParamUpdated>();

			constructGUI();
			//System.setIn(getIn());
			//Redirect the flux
			//System.setOut(getOut());
			//			System.setErr(getOut());


			Thread console = new Thread(new Console());
			console.start();


		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Change the displayed main.java.model if it is different from the current one
	 * @param main.java.model
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 * @throws CommandLineFormatException 
	 * @throws MalformedURLException 
	 */
	public void displayModel(Model model)  {
		if(!(model==root.getActiveModel()))
		{
			lock.lock();

			try{
				DisplayNode node = treeView.getNode(model);
				try{
					model.initialize(Printer.readFile(new URL("file:"+contextPath.getPath()+model.getName()+".dnfs")));
				}catch (FileNotFoundException e) {
					System.out.println(e.getMessage());
					System.out.println("Launching with defaults parameters");

					model.initialize(null);

				}
				//	System.out.println("new tree");
				//	System.out.println(main.java.model.getRootParam().toStringRecursive(0));
				model.getCommandLine().setRunner(runner);
				runner.setModel(model);
				node.construct();
				root.setActiveModel(model);
				//execute the script in the main.java.model context file : //file format : contextPath/modelName.dnfs
				maps.clear();
				for(Parameter p : root.getActiveModel().getDefaultDisplayedParameter())
				{
					DisplayNode disp = treeView.getNode(p);
					maps.addView(disp.getQuickViewPanel());

				}
				maps.update();
				stat.changeStatistics(model.getStatistics(),model.getDefaultDisplayedStatistic());
				((ModelDisplayNode)node).signalTreeChanged();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (CommandLineFormatException e) {
				e.printStackTrace();
			} catch (NullCoordinateException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			finally{
				lock.unlock();
			}
		}

	}





	protected void constructGUI() throws MalformedURLException, CommandLineFormatException, IOException, NullCoordinateException
	{

		//execute the script in the main.java.model context file : //file format : contextPath/modelName.dnfs
		setPreferredSize(dim);

		UIManager.put("Panel.background", Color.white);

		JPanel paramView = new DetailsPanel();
		paramView.setPreferredSize(new Dimension(dim.width/4, dim.height/18*16)); 

		DisplayNode node = new DisplayNode(null,root,this);
		node.setVisible(true);
		JTree tree = new JTree(node);
		treeView = new ParameterTreePanel(node,tree,root, (DetailsPanel) paramView,this);
		treeView.setPreferredSize(new Dimension(dim.width/4,(dim.height/18*8)));
		treeView.setBorder(BorderFactory.createTitledBorder("Models tree"));
		treeView.setBackground(Color.white);


		JPanel leftPane = new JPanel();
		leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));
		leftPane.add(treeView);
		//leftPane.setPreferredSize(new Dimension(dim.width/4, dim.height/18*16));


		stat = new StatisticPanel(root.getActiveModel().getStatistics(),root.getActiveModel().getDefaultDisplayedStatistic());
		stat.setPreferredSize(new Dimension(dim.width/4,(dim.height/18*8)));
		leftPane.add(stat);

		maps = new ParameterCanvaPanel(this);

		JPanel header = new JPanel();
		initHeader(header);
		JPanel footer = new JPanel(new GridLayout(2,1));
		initFooter(footer);
		footer.setPreferredSize(new Dimension(dim.width, dim.height/18*2));





		Container mainPane = this.getContentPane();
		mainPane.add(leftPane, BorderLayout.LINE_START);
		mainPane.add(paramView, BorderLayout.LINE_END);
		mainPane.add(maps, BorderLayout.CENTER);
		mainPane.add(header, BorderLayout.PAGE_START);
		mainPane.add(footer,BorderLayout.PAGE_END);

		/*
		 * addWindowListener(new WindowAdapter() { public void
		 * windowClosing(WindowEvent e) { System.exit(0); } });
		 */

		//		addComponentListener(new ComponentAdapter() {
		//			public void componentResized(ComponentEvent e) {
		//				params.resize();
		//			}
		//		});

		for(Node p : root.getActiveModel().getDefaultDisplayedParameter())
		{
			//System.out.println("search : " + p);
			DisplayNode disp = treeView.getNode(p);
			maps.addView(disp.getQuickViewPanel());
		}
		maps.update();


		// params.resize();
		// pack();
		validate();
		setVisible(true);






		//Show the main.java.model param panel directly
		DisplayNode disp = treeView.getNode(root.getActiveModel());
		disp.valueChanged((DetailsPanel) paramView);
	}

	public Parameter searchParameter(String name) {
		return  root.getActiveModel().getParameter(name);
	}




	protected void initFooter(JPanel footer)
	{

		txtArea = new TextAreaInOut();
		JScrollPane scroll = new JScrollPane(txtArea);
		txtArea.setLineWrap(true);
		txtArea.setWrapStyleWord(true);
		txtArea.setEditable(false);

		txtIn = new TextFieldIn(INVITE);

		footer.add(txtIn);
		footer.add(scroll);
	}

	/**
	 * TODO
	 * Reconstruct the main.java.model in order to change the resolution for instance
	 * @throws Exception 
	 */
	public void resetHard() throws Exception{

		lock.lock();
		throw new Error("TODO reset hard");
//		try{
//			Model current = this.root.getActiveModel();
//			ModelDisplayNode node = (ModelDisplayNode) treeView.getNode(current);
//			Model newModel = Root.constructModel(current.getName());
//			newModel.initialize(current);
//			node.changeModel(newModel);
//			root.replaceModel(newModel);
//
//			main.java.maps.clear();
//
//			for(Parameter p : root.getActiveModel().getDefaultDisplayedParameter())
//			{
//				//System.out.println("Display : " + p.getName() + "@" +p.hashCode());
//				DisplayNode disp = treeView.getNode(p);
//				main.java.maps.addView(disp.getQuickViewPanel());
//			}
//			main.java.maps.update();
//			stat.changeStatistics(newModel.getStatistics());
//			stat.validate();
//			//((ModelDisplayNode)node).signalTreeChanged();
//		}catch(Exception e ){
//			e.printStackTrace();
//		}finally{
//			lock.unlock();
//		}

	}

	protected void initHeader(JPanel header) {

		JButton startButton = new JButton("Play/Pause");
		JButton nextButton = new JButton("Step");

		final JTextField stepValueTF = new JTextField("TimeStep (s)");
		stepValueTF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				double newVal = Double.parseDouble(stepValueTF.getText());
				runner.setTimeStep(newVal);
			}
		});


		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runner.play_pause();
			}
		});

		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runner.step();
			}
		});

		SliderCommand timeStepCommand = new SliderCommand("SimulationStep (ms)", 1,
				10000, 10, (int) (runner.getSimulationStep() * 1000)) {
			protected void valueChanged(int value) {
				runner.setSimulationStep(value / 1000d);
			}
		};

		//		SliderCommand refreshRateCommand = new SliderCommand("RefreshRate (ms)", 0,
		//				1000, 1, ((int) refresh )) {
		//			protected void valueChanged(int value) {
		//				refresh = value ;
		//			}
		//		};

		JButton saveButton = new JButton("SaveMap");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					root.getActiveModel().save("saveMap",maps.getDisplayedMaps());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (NullCoordinateException e) {
					e.printStackTrace();
				}

			}
		});

		JButton saveStatsButton = new JButton("SaveStats");
		saveStatsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					root.getActiveModel().getStatistics().save("saveStats.csv");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});



		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				lock.lock();
				try{
					runner.reset();
				}finally{
					lock.unlock();
				}

			}
		});

		JButton reinitButton = new JButton("Reinitialize");
		reinitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				runner.reinitialize();

			}
		});

		JButton testButton = new JButton("Test");
		testButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				lock.lock();
				runner.test();
				Model model = root.getActiveModel();

				try{
					DisplayNode node = treeView.getNode(model);
					try{
						model.initialize(Printer.readFile(new URL("file:"+contextPath.getPath()+model.getName()+".dnfs")));
					}catch (FileNotFoundException e) {
						System.out.println(e.getMessage());
						System.out.println("Launching with defaults parameters");
						model.initialize(null);
					}
					//	System.out.println("new tree");
					//	System.out.println(main.java.model.getRootParam().toStringRecursive(0));
					model.getCommandLine().setRunner(runner);
					runner.setModel(model);
					node.construct();
					root.setActiveModel(model);
					//execute the script in the main.java.model context file : //file format : contextPath/modelName.dnfs
					maps.clear();
					for(Parameter p : root.getActiveModel().getDefaultDisplayedParameter())
					{
						DisplayNode disp = treeView.getNode(p);
						maps.addView(disp.getQuickViewPanel());

					}
					maps.update();
					stat.changeStatistics(model.getStatistics(),model.getDefaultDisplayedStatistic());
					((ModelDisplayNode)node).signalTreeChanged();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (CommandLineFormatException e) {
					e.printStackTrace();
				} catch (NullCoordinateException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				lock.unlock();


			}
		});

		//header.setLayout(new GridLayout(1, 0));

		header.add(startButton);
		header.add(nextButton);
		header.add(stepValueTF);
		header.add(resetButton);
		header.add(reinitButton);

		header.add(timeStepCommand);
		header.add(saveButton);
		header.add(saveStatsButton);
		header.add(testButton);

		/*
		 * JPanel curseurs = new JPanel(); curseurs.setLayout(new
		 * GridLayout(3,3)); curseurs.add(size_commands);
		 */

	}



	public InputStream getIn() {
		return txtIn.getIn();
	}

	public PrintStream getOut() {
		return txtArea.getOut();
	}






	public Lock getLock() {
		return lock;
	}




	public void addParamUpdated(ParamUpdated p)
	{
		this.paramUpdated.add(p);
	}

	/**
	 * Update every parameter panel suscriber of paramUpdated list
	 */
	public void updateParams()
	{
		for(ParamUpdated p : paramUpdated)
			p.update();
	}


	public Root getRoot() {
		return root;
	}





	//	public void changeRes(double newRes)
	//	{
	//		//Safely transform the refSpace
	//		this.getLock().lock();
	//		try{
	//			root.getActiveModel().getSpace().setResolution(newRes);
	//		}
	//		finally{
	//			this.getLock().unlock();
	//		}
	//	}

	public ParameterCanvaPanel getDynamicParamView() {
		return maps;

	}


	//	//Update the main.java.gui only according to the refreshing rate
	//	@Override
	//	public void run() {
	//		long tb = 0;
	//		while(true){
	//			try{
	//				long ta = System.currentTimeMillis();
	//				long diff = ta-tb;
	//				Thread.sleep((long) Math.max(0, refresh-diff));
	//				repaint();
	//				tb = System.currentTimeMillis();
	//			}catch (InterruptedException e) {
	//				e.printStackTrace();
	//				System.exit(-1);
	//			}
	//		}
	//
	//
	//	}




	public class Console implements Runnable
	{
		@Override
		public void run() {
			Scanner sc = new Scanner(System.in);
			while(true)
			{
				try {
					String command = sc.next();
					//System.out.println(GUI.INVITE+initScript);
					String ret = root.getActiveModel().getCommandLine().parseCommand(command);
					if(ret != null && !ret.isEmpty())
						System.out.println(ret);
				} catch (CommandLineFormatException e) {
					e.printStackTrace();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (NullCoordinateException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public ParameterTreePanel getTreeView() {
		return treeView;
	}
































}
