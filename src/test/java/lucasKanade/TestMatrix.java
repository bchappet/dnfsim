/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java.lucasKanade;

import main.resources.tool.ToolsMAP;
import Jama.Matrix;

/**
 *
 * @author ncarrara
 */
public class TestMatrix {
    public static void main(String[] args){
        Matrix x;
        double[][] tabb = {{1},{1}};
        Matrix b = new Matrix(tabb);
        double[][] taba = {{2,1},{8,0}};
        Matrix A = new Matrix(taba);
        x = A.solve(b);
        ToolsMAP.afficherTableau(x.getArray());
    }
}
