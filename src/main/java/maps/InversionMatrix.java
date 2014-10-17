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

package main.java.maps;

import main.java.space.SpaceFactory;

/**
 * Created by bchappet on 30/09/14.
 */
public class InversionMatrix extends MatrixDouble2D {
    private static final int LINKED = 0;
    public InversionMatrix(MatrixDouble2D mat) {
        super(mat.getName()+"_inv",mat.getDt(), SpaceFactory.matrixInvSpace(mat.getSpace()),mat);
    }

    public void compute(){
        this.setMat(((MatrixDouble2D) getParam(LINKED)).getMat().inverse());
    }
}
