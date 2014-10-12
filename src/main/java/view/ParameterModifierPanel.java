package main.java.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import main.java.controler.VarControler;
import main.java.maps.Var;

/**
 * Special modifier with button + - //TODO use min max step
 *TODO Should we use the interpreter?? yes
 * @author bchappet
 *
 */
public class  ParameterModifierPanel extends JPanel {

	public static final int START = 0;
	public static final int END = 1;

	protected JTextField txt;
	protected NumberFormat formater;
	protected VarControler var;
	/**Amount to add or remove when pushing + or - button**/
	protected double amount;


	public ParameterModifierPanel(VarControler var) {
		formater = new DecimalFormat("####.####");
		this.var = var;
		if(var.get() instanceof Number){
			if(var.getInterval() == null){
				amount = computeAmount(var);
			}else{
				amount = ((Number) var.getInterval().get(Var.STEP)).doubleValue();
			}
			initRunnerGUI();
		}
		
	}
	
	protected VarControler getvVarControler(){
		return var;
	}
	
	public void update()
	{
		if(var.get() instanceof Number){
			txt.setText(formater.format(getValue(var)));
		}
	}
	
	protected  Number getValue(VarControler param){
//		System.out.println("getVal : " + param.getName());
		return (Number) (param.get());
	}
	
	private static String getName(VarControler param){
		return param.toString();
	}
	
	

	protected void addToValue() {

        if(var.get() instanceof Integer) {
            var.set((Integer)((getValue(var)).intValue() + (int)amount));

        }else{
            var.set((getValue(var)).doubleValue() + amount);
        }
		
	}
	
	protected void subToValue() {
        if(var.get() instanceof Integer) {
            var.set((Integer)((getValue(var)).intValue() - (int)amount));

        }else{
            var.set((getValue(var)).doubleValue() - amount);
        }
		
	}

	protected void initRunnerGUI()
	{

		int delay = 200;

		JPanel pane = new JPanel();
		//pane.setLayout(new GridLayout(2,2));

		pane.add(new JLabel(getName(var) + ":"),BorderLayout.CENTER);

		JButton bp = new JButton("+");
		JButton bm = new JButton("-");


		ActionListener bpAdd = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addToValue();
				txt.setText(formater.format(getValue(var)));
			}

			
		};
		bp.addMouseListener(new TimerMouseListener( delay, bpAdd));
		bp.addActionListener(bpAdd);



		ActionListener bmAdd = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				subToValue();
				txt.setText(formater.format(getValue(var)));
			}
		};


		bm.addMouseListener(new TimerMouseListener( delay, bmAdd));
		bm.addActionListener(bmAdd);



		txt = new JTextField(formater.format(getValue(var)));
		txt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField src = (JTextField)e.getSource();
				var.set(Double.parseDouble(src.getText()));
				//System.out.println(param + " = " + param.get());
			}
		});

		JPanel mod = new JPanel();
		//mod.setLayout(new BoxLayout(mod, BoxLayout.LINE_AXIS));
		mod.setLayout(new GridLayout(1, 2));
		JPanel buttons = new JPanel();
		//buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
		buttons.setLayout(new GridLayout(2, 1));


		buttons.add(bp);
		buttons.add(bm);
		mod.add(txt);
		mod.add(buttons);
		//		Font font = new Font(Font.DIALOG, Font.BOLD, 4);
		//		bp.setFont(font);
		//		bm.setFont(font);
		bp.setPreferredSize(new Dimension(45, 20));
		bm.setPreferredSize(new Dimension(45, 20));



		pane.add(mod,BorderLayout.CENTER);


//		if(param instanceof VarDoubleInterval)
//		{
//			final VarDoubleInterval cons = (VarDoubleInterval) param;
//			MyJSlider slide = null;
//			if(param instanceof VarIntInterval )
//			{
//
//				slide = new MyJSliderInt(txt,(Parameter) param,JSlider.HORIZONTAL,((int)cons.getInf().get()),((int)cons.getSup().get()),
//						(int)cons.get()) ;
//				amount=1;
//			}
//			else
//			{
//				slide = new MyJSlider(txt,(Parameter) param,JSlider.HORIZONTAL,cons.getInf().get(),cons.getSup().get(),
//						cons.get()) ;
//			}
//			slide.setPreferredSize(new Dimension(200, 30));
//			pane.add(slide,BorderLayout.PAGE_END);
//		}

		this.add(pane);
	}


	/**
	 * Return a double str corresponding to 1*10^precision of the param
	 * @param param
	 * @return
	 */
	public  double computeAmount(VarControler param) {
		double val = Math.abs((getValue(param)).doubleValue());
		double ret = 0;

		//System.out.print(val + " ==> ");

		if(val == 0)
		{
			ret = 1; //Arbitrary
		}
		else
		{
			String str = ""+val;
			//System.out.println("Val : " + val + " Str : " + str);

			String[] split = str.split("\\.");
			String entPart = split[0];
			String realPart = split[1];

			int precision = 0;


			if(realPart.equals("0"))
			{

				precision = getNumberOf('0', entPart,END);
				ret = Math.pow(10,precision);
			}
			else
			{
				//System.out.println("real : " + realPart);
				precision = realPart.length() ;//-getNumberOf('0', realPart,START);
				ret = Math.pow(10,-precision);
			}

		}
		//System.out.println(ret);
		return ret;

	}

	/**
	 * Return the number of character target from the END or from the START
	 * @param target
	 * @param str
	 * @param from END or START
	 * @return
	 */
	public static int getNumberOf(char target, String str,int from) {
		int nb = 0;
		switch(from)
		{
		case END : 
			int i = str.length()-1;
			while(i >= 0 && str.charAt(i) == target)
			{
				i--;
				nb ++;
			}
			break;
		case START :
			i = 0;
			while(i < str.length()  && str.charAt(i) == target)
			{
				i++;
			}
			nb = i;
			break;
		}

		return nb;
	}

	/**
	 * This class will recall the action if the mouse stay pressed
	 * the interval time between the addition will accelerate if the mouse stay pressed
	 * When the mouse is realeased, the interval time is the base time again.
	 * @author bchappet
	 *
	 */
	protected class TimerMouseListener implements MouseListener
	{

		protected int baseDelay; //Start Delay between adding in ms
		protected int delay; //Real delay : will be risen if we stay pressed
		protected Timer timer;
		protected ActionListener fireListener;

		public TimerMouseListener( int delay,ActionListener action)
		{
			this.baseDelay = delay;
			this.delay = baseDelay;
			timer = new Timer(delay, action);
			
			fireListener = constructFirelistener();
			timer.addActionListener(fireListener);

		}
		
		public ActionListener constructFirelistener()
		{
			ActionListener fireListener = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(delay > 10)
					{
						delay -= 10;
						timer.setDelay(delay);
					}
					
				}
			};
			return fireListener;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			timer.start();

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			timer.stop();
			delay = baseDelay;

		}

	}

}
