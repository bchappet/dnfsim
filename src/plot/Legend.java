package plot;

/**
 * Labels for the legend of a graph/curve
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

import java.awt.Color;
import java.awt.Graphics2D;

public class Legend extends Plot {
    /** Variables */
    private String[] names;
    /** Colors */
    private Color[] colors;

    /** Constructor */
    public Legend(String[] names_, Color[] colors_) {
        names = names_;
        colors = colors_;
    }

    public void render(Graphics2D g, int w, int h) {
        // Check if the variables are not null
        if (names==null || colors==null)
            return;
        
        // Display the labels
        g.setFont(font);
        for (int i=0; i<names.length; i++) {
            if (i<colors.length)
                g.setColor(colors[i]);
            else
                g.setColor(Color.black);
            drawText(g,names[i],w-200,h/10+i*30,0,0);
        }
    }
}