package main.java.plot;

/** Any kind of main.java.plot to display
 * 
 * @author Jean-Charles Quinton
 * @version 21/02/09
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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import java.util.EventObject;

/** Subplot class to superimpose curves and information */
public abstract class Plot {
    /** Transform to save for other plots */
    protected AffineTransform trans = null;

    /** String size (pixels) */
    public static final int FONT_SIZE = 12;
    /** Size of the marks put near text or information */
    public static final double MARK_SIZE = 4;

    /** Number format when displayed as a string */
    public static final DecimalFormat dformat = new DecimalFormat("#0.00");
    /** Font for displaying info on the graph */
    public static final Font font = new Font("",Font.PLAIN,(int)FONT_SIZE);
    
    /** Curve border (pourcentage) */
    protected static final double CURVE_BORDER = 0.05;

    /** Get the current transform */
    public AffineTransform getTransform() {
        return trans;
    }
    
    /** Set the current transform */
    public void setTransform(AffineTransform t) {
        trans = t;
    }
    
    /** React to interaction events */
    public void interact(EventObject e) {
        // Empty by default
    };
    
    /** React to interaction events */
    public void interactRelease(EventObject e) {
        // Empty by default
    };
    
    /** Return the displayed window
     *  (the order is : x_min, y_min, x_max, y_max) */
    public double[] getLimits() {
        // No limits defined by default
        return null;
    }
    
    /** Render the curve on a given graphics environment
     * (the affine transform will be null when the drawing starts from scratch) */
    abstract public void render(Graphics2D g, int w, int h);
        
    /** Draw a string on the graph */
    protected void drawText(Graphics2D g, String s, double x, double y, int offsetx, int offsety, boolean mark) {
        s = " " + s + " ";
        // Mark
        if (mark)
            g.fill(new Rectangle.Double(x-MARK_SIZE/2,y-MARK_SIZE/2,MARK_SIZE,MARK_SIZE));
        // Offsets for displaying on sides
        FontMetrics fm = g.getFontMetrics();
        offsetx *= fm.stringWidth(s); // tb should be equal to -1,0 or 1 (no offset if tb=0)
        offsety *= fm.getHeight(); // offsety should be equal to -1,0 or 1 (no offset if offsety=0)
        // Actual text
        g.drawString(s,(int)x+offsetx,(int)y+offsety);
    }
    
    /** Draw a string on the graph */
    protected void drawText(Graphics2D g, String s, double x, double y, int offsetx, int offsety) {
        drawText(g,s,x,y,offsetx,offsety,true);
    }
    
    /** Compute the barycenter of 2 colors */
    public static Color meanColor(Color c1, Color c2, double t) {
        //t = max(0.0,min(1.0,t));
        return new Color((int)(t*c2.getRed()+(1-t)*c1.getRed()),
                         (int)(t*c2.getGreen()+(1-t)*c1.getGreen()),
                         (int)(t*c2.getBlue()+(1-t)*c1.getBlue()));
    }
}