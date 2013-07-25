package plot;

/**
 * A single variable trace
 *
 * @author Jean-Charles Quinton
 * @version 21/02/09
 * creation 11/03/08
*/

/*
    Copyright 2010 Jean-Charles Quinton
    
    This file is part of DNF.
    
    DNF is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    DNF is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with DNF.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.text.DecimalFormat;
import java.util.ArrayList;

import statistics.Statistics;

public class Trace {
    /** Values array */
    private ArrayList<Double> values;
    /** Maximum */
    private double value_max;
    /** Minimum */
    private double value_min;
    /** Name */
    private String name;

    /** Constructor */
    public Trace(String name) {
        values = new ArrayList<Double>();
        setName(name);
        clear();
    }
    
    /** Constructor without a name */
    public Trace() {
        this(null);
    }
    
    /** Set the trace name */
    public void setName(String name_) {
        name = name_!=null ? name_ : "Undefined";
    }
    
    /** Get the trace name */
    public String getName() {
        return name;
    }
    
    /** Add a new str to the trace */
    public void add(double val) {
        values.add(val);
        value_max = Math.max(value_max,val);
        value_min = Math.min(value_min,val);
    }
    
    /** Get the matrix */
    public ArrayList<Double> getValues() {
        return values;
    }
    
    /** Get a str */
    public double get(int i) {
        return values.get(i);
    }
    
    /** Set a str */
    public void set(int i, double val) {
        values.set(i,val);
        value_max = Math.max(value_max,val);
        value_min = Math.min(value_min,val);
        
        if(!values.contains(value_max))
        {
        	compute_max();
        }
        
        if(!values.contains(value_min))
        {
        	compute_min();
        }
    }
    
    private void compute_min() {
    	double min = Double.MAX_VALUE;
    	for(double d : values)
    	{
    		if(d < min)
    			min = d;
    	}
		
	}

	private void compute_max() {
		double max = Double.MIN_VALUE;
		for(double d : values)
    	{
    		if(d > max)
    			max = d;
    	}
		
	}
    /** Get the number of matrix */
    public int size() {
        return values.size();
    }
    
    /** Get the maximum */
    public double getMax() {
        return value_max;
    }

    /** Get the minimum */
    public double getMin() {
        return value_min;
    }
    
    /** Clear the trace
     * (useful for computation depending on the whole trace,
     *  for example when using the extrema, or selecting elements) */
    public void clear() {
        values.clear();
        value_max = -Double.MAX_VALUE;
        value_min = Double.MAX_VALUE;
    }
    
    /** From array to Trace conversion */
    public void fromArray(double[] src) {
        clear();
        for (double v : src)
            add(v);
    }
    
    @Override
    public String toString() {
        return toString(false);
    }
    
    /** Number format when displayed as a string */
    public static final DecimalFormat dformat = new DecimalFormat("#0.00");
    
    /** Display the content or description of the trace */
    public String toString(boolean full) {
        String s = name;
        if (full) {
            s += "(" + values.size() + ") in ["
                + dformat.format(value_min) + ","
                + dformat.format(value_max) + "] (";
            for(double v : values)
            	s+= getValueWithNAs(v) + " ";
//            s += arrayToString(values);
            s += ")";
        }
        return s;
    }
    
    private double getValueWithNAs(double value){
    	if(value == Statistics.ERROR)
    		return Double.NaN;
    	else 
    		return value;
    }
    
    /** Formatted string from an array of floating matrix */
    public static String arrayToString(ArrayList<Double> values) {
        String s = "";
        for (int i=0; i<values.size(); i++) {
            if (i>0) s += " ";
            s += dformat.format(values.get(i));
        }
        return s;
    }
}