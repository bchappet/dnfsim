/*
 * This is a Dynamic Neural Field simulator which is extended to
 *     several other neural networks and extended to hardware simulation.
 *
 *     Copyright (C) 2014  Benoît Chappet de Vangel
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package main.java.reservoirComputing;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;
import main.java.unitModel.UnitModel;

/**
 * Classic tanh reservoir neuron
 * X k+1 = f(W*xk + Win * u k+1)
 *
 * if leak = 1 no memory 0 = static neuron
 * @author bchappet
 *
 */
public class LeakyTanHReservoirNeuronUM extends UnitModel<Double> {
	
	public static final int LEAK = 0;
    public static  final int NOISE = 1;
    public static final int reservoir = 2;
	public static final int conv_WRR_R = 3;
	public static final int conv_WIR_I = 4;

	

	public LeakyTanHReservoirNeuronUM(Double initActivity) {
		super(initActivity);
	}



	@Override
	protected Double compute(BigDecimal time, int index,List<Parameter> params) {
		double leak = (double) params.get(LEAK).getIndex(index);
		double x_1 = (double) params.get(reservoir).getIndex(index);
		double input = (double) params.get(conv_WIR_I).getIndex(index);
		double lateral = (double) params.get(conv_WRR_R).getIndex(index);
        double noise  = (double) params.get(NOISE).getIndex(index);
		//double alpha = (double) params.get(ALPHA).getIndex(index);
		
	//	System.out.println("x-1: " + x_1 + " input: " + input + " lateral: " +  lateral);
		
		
		return Math.tanh((1-leak)*x_1 +  leak*( input +  lateral + noise));
	}

}
