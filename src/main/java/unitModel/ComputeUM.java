package main.java.unitModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.maps.Parameter;
import main.java.maps.Var;
import bsh.EvalError;
import bsh.Interpreter;

/**
 * Bash style script interpreter
 * $1,$2 are the parameter index in the list 
 * and the operator are standard + - * / ** 
 * The script can be modified during computation
 * @author benoit
 *
 * @param <T>
 */
public class ComputeUM<T extends Number> extends UnitModel<T> {

	//first param is the script
	public static final int SCRIPT = 0;


	public ComputeUM(T initActivity) {
		super(initActivity);
	}

	@Override
	protected T compute(BigDecimal time,int index, List<Parameter> params) {
		//TODO optimize, probabli super slow

		//"$1/($2^2)*(40^2)/$3";"
		String script = ((Var<String>)params.get(SCRIPT)).get();
		String replacement = new String();
		//System.out.println(script);


		Interpreter interpreter = new Interpreter();
		T result = null;
		String eval;
		try {
			Matcher m = Pattern.compile("\\$([0-9]+)").matcher(script);
			while(m.find()){
				int nb = Integer.parseInt(m.group(1));
				 eval = "var"+nb+"="+params.get(nb).getIndex(index)+";";
				//System.out.println("eval:" + eval);
				interpreter.eval(eval);
			}
			replacement = script.replaceAll("\\$([0-9]+)", "var$1");
			eval = "result=" + replacement + ";";
			//System.out.println("eval:" + eval);

			interpreter.eval(eval);
			result = (T) interpreter.get("result");
		} catch (EvalError e) {
			e.printStackTrace();
		}
//		System.out.println("Result = : " + result);
		return result;



	}





}
