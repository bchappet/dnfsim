package main.java.plot;

/**
 * Axis to display on a graph
 *
 * @author Jean-Charles Quinton
 * @version 11/12/09
 * creation 21/02/09
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

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

public class Axis extends Plot {
    /** Default limits */
    private double[] default_limits = new double[] {
        Double.MAX_VALUE,Double.MAX_VALUE,-Double.MAX_VALUE,-Double.MAX_VALUE
    };    
    /** Limits to display */
    protected double[] limits = new double[4];

    /** Invert the Y axis or not */
    private int invert = NOTINV;
    public static final int INVERT = 1;
    public static final int NOTINV = -1;
    
    /** Line width for the limits */
    protected static final float LIMIT_WIDTH = 1.0f;
    
    /** Constructor */
    public Axis() {
        clearLimits();
    }
    
    /** Get the default limits */
    public double[] getDefaults() {
        return default_limits;
    }
    
    /** Set the default limits */
    public void setDefaults(double[] def) {
        default_limits = def;
    }
    
    /** Set the Y axis inversion */
    public void setInvert(int inv) {
        invert = inv;
    }
    
    /** Get the limits */
    public double[] getLimits() {
        return limits;
    }
    
    /** Clear the limits */
    public void clearLimits() {
        limits = Arrays.copyOf(default_limits,4);
    }
    
    /** Update the limits */
    public void updateLimits(double[] limits_) {
        // Test the parameter
        if (limits_==null)
            return;
        // Update each limit
        limits[0] = min(limits[0],limits_[0]);
        limits[1] = min(limits[1],limits_[1]);
        limits[2] = max(limits[2],limits_[2]);
        limits[3] = max(limits[3],limits_[3]);
    }

    public void setTransform(int w, int h) {
        // Empty border to add
        double dx = limits[2]-limits[0];
        double dy = limits[3]-limits[1];
        double borderx = dx*CURVE_BORDER;
        double bordery = dy*CURVE_BORDER;
        // Scaling
        double sizex = dx+2*borderx;
        double sizey = dy+2*bordery;
        // Does the transform exist
        if (trans==null) trans = new AffineTransform();
        trans.setToScale(w/sizex,invert*h/sizey);
        // Translation
        if (invert==INVERT)
            trans.translate(-limits[0]+borderx,-limits[1]+bordery);
        else
            trans.translate(-limits[0]+borderx,-limits[3]-bordery);
    }
    
    @Override
    public void render(Graphics2D g, int w, int h) {        
        // Transform main.java.coordinates
        double[] tl = new double[4];
        trans.transform(limits,0,tl,0,limits.length/2);
        // Clear for full repaint
        g.setColor(Color.white);
        g.fill(new Rectangle2D.Double(0,0,w,h));
        // Display additional information
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(LIMIT_WIDTH));
        g.draw(new Line2D.Double(0,tl[1],w,tl[1]));
        g.draw(new Line2D.Double(tl[0],0,tl[0],h));
        // Draw text for the limits
        g.setFont(font);
        drawText(g,dformat.format(limits[1]),tl[0],tl[1],-1,0);
        drawText(g,dformat.format(limits[3]),tl[0],tl[3],0,0);
        drawText(g,dformat.format(limits[0]),tl[0],tl[1],0,1);
        drawText(g,dformat.format(limits[2]),tl[2],tl[1],-1,1);
    }
}