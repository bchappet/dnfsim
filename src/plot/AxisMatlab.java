package plot;

/**
 * Axis to display on a graph in a Matlab fashion
 *
 * @author Jean-Charles Quinton
 * @version 28/10/10
 * creation 28/10/10
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class AxisMatlab extends Axis {
    @Override
    public void render(Graphics2D g, int w, int h) {        
        // Transform coordinates
        double[] tl = new double[4];
        trans.transform(limits,0,tl,0,limits.length/2);
        // Clear for full repaint
        g.setColor(Color.white);
        g.fill(new Rectangle2D.Double(0,0,w,h));
        // Display additional information
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(LIMIT_WIDTH));
        g.draw(new Rectangle2D.Double(
            Math.min(tl[0],tl[2]),Math.min(tl[1],tl[3]),
            Math.abs(tl[2]-tl[0]),Math.abs(tl[3]-tl[1])
        ));
        // Ticks with text
        double[] tt = new double[2];
        g.setFont(font);
        double tick_length = 10;
        double xtick = tickspace(limits[0],limits[2],10);
        double ytick = tickspace(limits[1],limits[3],10);
        // System.out.println(xtick + " " + ytick);
        DecimalFormat dformat = tickformat(xtick);
        for (double x=Math.ceil(limits[0]/xtick)*xtick; x<limits[2]; x+=xtick) {
            trans.transform(new double[]{x,tl[1]},0,tt,0,1);
            g.draw(new Line2D.Double(tt[0],tl[1],tt[0],tl[1]-tick_length));
            g.draw(new Line2D.Double(tt[0],tl[3],tt[0],tl[3]+tick_length));
            drawText(g,dformat.format(x),tt[0],tl[1],0,1,false);
        }
        dformat = tickformat(ytick);
        for (double y=Math.ceil(limits[1]/ytick)*ytick; y<limits[3]; y+=ytick) {
            trans.transform(new double[]{tl[0],y},0,tt,0,1);
            g.draw(new Line2D.Double(tl[0],tt[1],tl[0]+tick_length,tt[1]));
            g.draw(new Line2D.Double(tl[2],tt[1],tl[2]-tick_length,tt[1]));
            drawText(g,dformat.format(y),tl[0],tt[1],-1,0,false);
        }
    }
    
    /** Obtain the spacing between ticks */
    public double tickspace(double min, double max, int tick_nb) {
        double delta = max-min;
        int shifts = 0;
        // Make sure there are 2 figures before the decimals
        while (Math.abs(delta)<=10) {
            shifts++;
            delta *= 10;
        }
        // Divide the refSpace in the correct number of elements
        delta = Math.ceil(delta/tick_nb);
        // Go back to the original scale
        while (shifts>0) {
            shifts--;
            delta /= 10;
        }
        return delta;
    }
    
    /** Get the text format for the ticks */
    public DecimalFormat tickformat(double tick) {
        String tformat = "#" + tick;
        while(tformat.endsWith("0")) tformat = tformat.substring(0,tformat.length()-1);
        if(tformat.endsWith(".")) tformat = tformat.substring(0,tformat.length()-1);
        tformat = tformat.replaceAll("\\d","0");
        DecimalFormat dformat = new DecimalFormat(tformat);
        DecimalFormatSymbols dsymbols = new DecimalFormatSymbols();
        dsymbols.setDecimalSeparator('.');
        dformat.setDecimalFormatSymbols(dsymbols);
        return dformat;
    }
}