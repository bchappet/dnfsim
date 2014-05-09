package main.java.plot;

/**
 * Double buffered JPanel with
 * volatile images for performance
 * and quality improvement
 * 
 * @author Jean-Charles Quinton
 * @version 11/03/2008
 * creation 23/03/2007
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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.VolatileImage;
import java.util.EventObject;

import javax.swing.JPanel;

import main.java.view.ParameterView;

abstract public class JPanelDB extends ParameterView {

	/** Double charge on VRAM handling */
	VolatileImage backBuffer = null;

	/** Constructor */
	public JPanelDB() {
		super();
		// Interactions
		MouseAdapter adapter = new MouseInteract();
		addMouseMotionListener(adapter);
		addMouseListener(adapter);
		addMouseWheelListener(adapter);
		addKeyListener(new KeyInteract());
	}

	/** Key and mouse listeners and interactions */
	class MouseInteract extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			int button = e.getButton();
			if(button == MouseEvent.BUTTON3)//Left click
				interact(e);
		}

		@Override
		public void mouseReleased(MouseEvent e){
				interactRelease(e);
		}
	}

	class KeyInteract extends KeyAdapter {            
		public void keyPressed(KeyEvent e) {

			interact(e);
		}
	}

	/** Interactions with the interface */
	abstract public void interact(EventObject event);

	/** Interactions with the interface */
	abstract public void interactRelease(EventObject event);

	/** Creation of a back charge if not existing */
	void createBackBuffer() {
		if (backBuffer != null) {
			backBuffer.flush();
			backBuffer = null;
		}
		backBuffer = createVolatileImage(getWidth(), getHeight());
	}

	public void paint(Graphics g) {
		if (backBuffer == null || backBuffer.getWidth()!=getWidth() || backBuffer.getHeight()!=getHeight()) {
			createBackBuffer();
		}
		do {
			int valCode = backBuffer.validate(getGraphicsConfiguration());
			if (valCode == VolatileImage.IMAGE_INCOMPATIBLE) {
				createBackBuffer();
			}
			Graphics gBB = backBuffer.getGraphics();
			Graphics2D g2d = (Graphics2D)gBB;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			try
			{
				render(g2d);
			}catch (Exception e) {
			}
			g.drawImage(backBuffer, 0, 0, this);
		} while (backBuffer.contentsLost());
	}

	/** True repaint function where commands
	 *  to draw elements are to be put
	 *  (this function should not be called directly)
	 *  @original  g   graphics environment given by the paint function */
	abstract public void render(Graphics2D g);
}