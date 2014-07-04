package main.java.plot;

/**
 * Swing display class
 * (based on previous curve displayers)
 *
 * @author Jean-Charles Quinton
 * @version 26/11/09
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

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.logging.Logger;

import main.java.view.ParameterView;

public class Plotter extends JPanelDB {
	
	private final transient Logger LOGGER = Logger.getLogger(Plotter.class.getName());

    /** Axis to display */
	private    Axis axis;
    /** Subplots to display */
	private   ArrayList<Plot> plots = new ArrayList<Plot>();  
    
    /** Constructor with axis */
    public Plotter(Plot...plots) {
        super();
        axis = new Axis();
        for (Plot p : plots)
            add(p);
    }
    
    /** Set axis */
    public void setAxis(Axis axis) {
        this.axis = axis;
    }
    
    /** Add a new subplot */
    public void add(Plot p) {
        plots.add(p);
    }
    
    @Override
    public void interact(EventObject event) {
        for (Plot p : plots)
            p.interact(event);
    }
    
    @Override
    public void interactRelease(EventObject event) {
        for (Plot p : plots)
            p.interactRelease(event);
    }
    
    @Override
    public void render(Graphics2D g) {
    	LOGGER.info("Rendering " + this);
        // Compute the limits of the graph
        axis.clearLimits();
        for (Plot p : plots) {
            axis.updateLimits(p.getLimits());
        }
        // Clear and draw the axis
        axis.setTransform(getWidth(),getHeight());
        axis.render(g,getWidth(),getHeight());
        // Draw the other plots using the same coordinate transform
        for (Plot p : plots) {
            p.setTransform(axis.getTransform());
            p.render(g,getWidth(),getHeight());
        }
    }
}