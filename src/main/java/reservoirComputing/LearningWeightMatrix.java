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

import main.java.console.CommandLineFormatException;
import main.java.maps.MatrixDouble2D;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.space.Space;

import java.math.BigDecimal;

/**
 * Created by bchappet on 24/09/14.
 */
public abstract class LearningWeightMatrix extends MatrixDouble2D{

    /**
     * Do we rise the non linearities by using an horizontal concatenation x1,x2...xn,x1²,x2²...xn²
     */
    protected boolean square = false;


    public LearningWeightMatrix(String name, Var<BigDecimal> dt,
                                               Space space, Parameter... params) {
        super(name,dt,space,params);
    }

    public void setSquare(boolean square) {
        this.square = square;
    }

    public boolean isSquare() {
        return square;
    }

    public abstract void resetStates();

    public abstract void computeOutputWeights() throws CommandLineFormatException, DeterminantErrror, ComputationOutOfMemoryError;
}
