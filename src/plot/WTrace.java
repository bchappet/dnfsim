package plot;

/**
 * MemVariable traces
 *
 * @author Jean-Charles Quinton
 * @version 17/12/09
 * creation 10/03/08
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import statistics.StatisticsCNFT;

public class WTrace {
	/** Coordinates arrays */
	protected List<Trace> coords;
	/** Number of coordinates */
	protected int coords_nb;

	/**Save the old traces**/
	protected List<Trace> removedTrace;

	/** Constructor */
	public WTrace(String... names) {
		coords_nb = names.length;
		coords = new ArrayList<Trace>(coords_nb);
		removedTrace = new ArrayList<Trace>();

		for (int i=0; i<coords_nb; i++)
			coords.add(i, new Trace(names[i]));
	}

	/**
	 * Get the last value of specified trace
	 * @param name
	 * @return
	 */
	public double getLast(String name)
	{
		int index = getIndex(name);
		if(index != -1){
			//	System.out.println("rtrrrrrrrrrrrrrrrrrrrrrdhgfjfjkf");
			//	System.out.println(this);
			Trace tr = coords.get(index);
			return tr.get(tr.size()-1);
		}else{
			return StatisticsCNFT.ERROR;
		}

	}

	public void addTrace(String name) {
		Trace tr = new Trace(name);
		if(!coords.isEmpty()){
			for(int i = 0 ; i < coords.get(0).size() ; i++)
				tr.add(StatisticsCNFT.ERROR);
		}

		coords.add(tr);
		coords_nb++;
		//System.err.println("Add : " + name + " coords_nb = " + coords_nb);


	}



	public void removeTrace(String name) {
		Trace tr = getCoord(name);
		coords.remove(tr);
		removedTrace.add(tr);
		coords_nb--;


	}
	/** Constructor with trace names */
	public WTrace(int coords_nb_) {
		this(new String[coords_nb_]);
	}

	/** Get the number of coordinates */
	public int getCoordsNb() {
		return coords_nb;
	}

	/** Return a variable trace */
	public Trace getCoord(int var) {
		return coords.get(var);
	}

	/** Return a variable trace */
	public Trace getCoord(String name) {
		int c = getIndex(name);
		if (c>=0)
			return coords.get(c);
		else
			return null;
	}

	/** Retrieve a trace index by its name */
	public int getIndex(String name) {
		for (int c=0; c<coords.size(); c++) {
			if (coords.get(c).getName().equals(name))
				return c;
		}
		return -1;
	}

	/** Return the traces as an array */
	public List<Trace> getCoords() {
		return coords;
	}

	/** Get a state of this trace */
	public double[] get(int i) {
		double[] state = new double[coords.size()];
		for (int c=0; c<coords.size(); c++) {
			state[c]=coords.get(c).get(i);
		}
		return state;
	}

	/** Get a particular str of a trace */
	public double get(int var, int i) {
		return coords.get(var).get(i);
	}



	/** Set a particular str of a trace */
	public void set(int var, int i, double val) {
		coords.get(var).set(i,val);
	}

	/** Add a new state to the trace */
	public void add(double... state) {
		//System.out.println(this);
		if (state.length != coords_nb) {
			//throw new Exception();
			System.out.println("coord_nb : " + coords_nb);
			System.err.println("Non matching dimensions : state["
					+ state.length + "], trace[" + coords_nb + "]" + Arrays.toString(Thread.currentThread().getStackTrace()));
			System.err.println(state.length + " : " +Arrays.toString(state));
			System.err.println(coords.size() + " : " + coords);
			return;
		}
		// Add a sample for each coordinate trace
		for (int i=0; i<coords_nb; i++)
			coords.get(i).add(state[i]);
	}

	/** Check if the coherence of the traces */
	public boolean isCoherent() {
		// Test the number of coordinates
		if (coords_nb!=coords.size())
			return false;

		// Test the length of each trace
		if (coords.size()>0) {
			int len = coords.get(0).size();
			for (int i=0; i<coords.size(); i++) {
				if (coords.get(i).size()!=len)
					return false;
			}
		}            

		// Everything seems fine
		return true;
	}

	/** Clear the whole set of traces
     Reset the wtrace**/
	public void clear() {
		for(Trace t : coords){
			t.clear();
		}
		//coords.clear();
		//coords_nb = 0;
		removedTrace.clear();
	}

	public String toString()
	{
		String ret = new String();
		// Write the variable names
		if (coords.size()>0)
			ret += coords.get(0).getName();
		for (int c=1; c<coords.size(); c++)
			ret +="," + coords.get(c).getName();
		ret += "\n";
		// Write the matrix
		for (int i=0; i<coords.get(0).size(); i++) {
			// First str
			ret += Double.toString(coords.get(0).get(i));
			for (int v=1; v<coords_nb; v++) {
				ret += "," + coords.get(v).get(i);
			}
			ret += "\n";
		}
		return ret;
	}

	/** Save trace as a CSV file 
	 * @throws IOException */
	public void save(String file) throws IOException {
		// Platform independent new line
		String newline = System.getProperty("line.separator");
		// Open the file
		FileWriter out = new FileWriter(new File(file));
		// Write the variable names
		if (coords.size()>0)
			out.write(coords.get(0).getName());
		for (int c=1; c<coords.size(); c++)
			out.write("," + coords.get(c).getName());
		out.write(newline);
		// Write the matrix
		for (int i=0; i<coords.get(0).size(); i++) {
			// First str
			out.write(Double.toString(coords.get(0).get(i)));
			for (int v=1; v<coords_nb; v++) {
				out.write("," + coords.get(v).get(i));
			}
			out.write(newline);
		}
		// Close the file
		out.close();

	}

	/** Load a trace saved as a CSV file */
	public static WTrace load(String file) {
		WTrace trace = null;
		// Open the file
		try {
			BufferedReader in  = new BufferedReader(new FileReader(file));
			// Read the first line to get the variables information
			String line = in.readLine();
			String[] vars = line.split(",");
			// Create the object
			trace = new WTrace(vars);
			// Read the matrix
			int i=0;
			while((line = in.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line,",");
				for (int v=0; v<trace.coords_nb; v++)
					trace.coords.get(v).add(Double.valueOf(st.nextToken()));
				// New point
				i++;
			}
			// Close the file
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trace;
	}
	/**
	 * Return the length of the trace
	 * @return
	 */
	public int length() {
		int ret;
		if(coords.size() > 0)
			ret = coords.get(0).size();
		else
			ret = -1;

		return ret;
	}







}
