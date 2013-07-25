/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Un bulle dans une map (ensemble de points adjacents)
 * @author ncarrara
 */
public class Bubble {

    ArrayList<Point> points;
    Point centre;
    tool.PointContinu centreDouble;
    Point nord;
    Point sud;
    Point est;
    Point ouest;
    boolean wrappedHorizontale;
    boolean wrappedVertical;
    int resolution;

    public Bubble(int resolutionMap) {
        resolution = resolutionMap;
        centreDouble = new tool.PointContinu();
        centre = new Point();
        nord = new Point();
        sud = new Point();
        est = new Point();
        ouest = new Point();
        points = new ArrayList<Point>();
        wrappedHorizontale = false;
        wrappedVertical = false;
    }

    @Override
    public String toString() {
        return "Cint(" + centre.x + "," + centre.y + ") "
                + "Cdouble(" + centreDouble.x + "," + centreDouble.y + ") "
                + "N(" + nord.x + "," + nord.y + ") "
                + "S(" + sud.x + "," + sud.y + ") "
                + "E(" + est.x + "," + est.y + ") "
                + "O(" + ouest.x + "," + ouest.y + ")  "
                + "H(" + wrappedHorizontale + ") "
                + "V(" + wrappedVertical + ") "
                + " ||| Points : " + points;
    }

    /**
     * calcul le centre de cette bulle
     */
    public void calculerCentreBulle() {
        // ligne
        if (!wrappedVertical) {
            //configuration standard
            centre.x = (nord.x + sud.x) / 2;
        } else {
            //wrapped
            int diffLastLine = resolution - 1 - nord.x + 1;
            int diffZero = sud.x + 1;
            int distance = diffLastLine + diffZero;
            int distanceFrom = (distance - distance % 2) / 2;

            centre.x = 0;
            if (diffLastLine > diffZero) {
                centre.x = nord.x + distanceFrom;
            }
            if (diffLastLine < diffZero) {
                centre.x = sud.x - distanceFrom;
            }
        }
        // colonne
        if (!wrappedHorizontale) {
            //configuration standard
            centre.y = (ouest.y + est.y) / 2;
        } else {
            //wrapped
            int diffDroite = resolution - 1 - ouest.y + 1;
            int diffGauche = est.y + 1;
            int distance = diffDroite + diffGauche;
            int distanceFrom = (distance - distance % 2) / 2;
            centre.y = 0;
            if (diffDroite > diffGauche) {
                centre.y = ouest.y + distanceFrom;
            }
            if (diffDroite < diffGauche) {
                centre.y = est.y - distanceFrom;
            }
        }
    }

    /**
     * calcul le centre de cette bulle, en renvoyer des coordonées dans un plan
     * continu.
     */
    public void caculerCentreBullePrecision() {
        tool.PointContinu nordDouble = new tool.PointContinu(nord.x + 0.5, nord.y + 0.5);
        tool.PointContinu sudDouble = new tool.PointContinu(sud.x + 0.5, sud.y + 0.5);
        tool.PointContinu estDouble = new tool.PointContinu(est.x + 0.5, est.y + 0.5);
        tool.PointContinu ouestDouble = new tool.PointContinu(ouest.x + 0.5, ouest.y + 0.5);

        // ligne
        if (!wrappedVertical) {
            //configuration standard
            centreDouble.x = (nordDouble.x + sudDouble.x) / 2;
        } else {
            //wrapped
            double diffLastLine = resolution - nordDouble.x;
            double diffZero = sudDouble.x;
            double distance = diffLastLine + diffZero;
            double distanceFrom = (distance - distance % 2) / 2;

            centreDouble.x = 0;
            if (diffLastLine > diffZero) {
                centreDouble.x = nordDouble.x + distanceFrom;
            }
            if (diffLastLine < diffZero) {
                centreDouble.x = sudDouble.x - distanceFrom;
            }
        }

        // colonne
        if (!wrappedHorizontale) {
            //configuration standard
            centreDouble.y = (ouestDouble.y + estDouble.y) / 2;
        } else {
            //wrapped
            double diffDroite = resolution - ouestDouble.y;
            double diffGauche = estDouble.y;
            double distance = diffDroite + diffGauche;
            double distanceFrom = (distance - distance % 2) / 2;
            centreDouble.y = 0;
            if (diffDroite > diffGauche) {
                centreDouble.y = ouestDouble.y + distanceFrom;
            }
            if (diffDroite < diffGauche) {
                centreDouble.y = estDouble.y - distanceFrom;
            }
        }
    }
}
