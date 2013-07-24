package plot;

/**
 * Render elements in a vector file
 * in FIG format
 *
 * @author Jean-Charles Quinton
 * @version 14/01/10
 * creation 14/01/10
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
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.FileWriter;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

// Might be needed when called from the component paint method
// (to avoid painting on a backbuffer which is not a FigGraphics2D,
//  and only then copying the raster to the FigGraphics2D... not vectorized)
//     RepaintManager.currentManager(frame).setDoubleBufferingEnabled(false);
//     frame.paint(new FigGraphics());
//     RepaintManager.currentManager(frame).setDoubleBufferingEnabled(true);
 
public class FigGraphics extends Graphics2D {
    /** Reference Graphics2D object
     *  (to not reimplement some functions for
     *   manipulating string and fonts for instance) */
    Graphics2D reference;

    /** Header */
    String header =
         "#FIG 3.2\n"
        +"Portrait\n"
        +"Center\n"
        +"Metric\n"
        +"A4\n"
        +"100.0\n"
        +"Single\n"
        +"-2\n"
        +"1200 2\n";

    /** None str in the FIG format (color...) */    
    public static final int NONE = -1;
    
    /** Colors */
    ArrayList<Color> colors = new ArrayList(
        Arrays.asList(new Color[] {
            Color.black,    // 0
            Color.blue,     // 1
            Color.green,    // 2
            Color.cyan,     // 3
            Color.red,      // 4
            Color.magenta,  // 5
            Color.yellow,   // 6
            Color.white,    // 7
        })
    );
    
    /** FIG shapes */
    String shapes;
    
    /** Current font */
    Font font;
    
    /** Current color */
    int color = 0; // black by default
    
    /** Current depth */
    int depth = 999;
    
    /** Pen tickness */
    int thickness = 1;
    
    /** Scale of the whole figure */
    double scale=5;
    
    /** Constructor
     * @original ref   reference Graphics2D object
     * (can be null if its functionalities are not needed) */
    public FigGraphics(Graphics2D ref) {
        // Set the reference Graphics object
        reference = ref;
        // Set the default font
        font = reference.getFont();
        // Add other shades of colors to the list of known colors
        for (int c=colors.size(); c<=31; c++) {
            colors.add(null);
        }
        // Initialize the shapes
        shapes = "";
    }
    
    public void addRenderingHints(Map<?,?> hints) {}
    public void clip(Shape s) {}
    
    public void draw(Shape s) {
        drawFill(s,color,NONE);
    }
    
    public void fill(Shape s) {
        drawFill(s,NONE,color);
    }
    
    protected void drawFill(Shape s, int pen_color, int fill_color) {
        // Fill or not?
        int area_fill = NONE;
        if (fill_color!=NONE)
            area_fill = 20;
        // Border or not?
        int pen_thickness = thickness;
        if (pen_color==NONE)
            pen_thickness = 0;
        // Line
        if (s instanceof Line2D.Double) {
            Line2D.Double line = (Line2D.Double)s;
            int x1 = scale(line.getX1());
            int y1 = scale(line.getY1());
            int x2 = scale(line.getX2());
            int y2 = scale(line.getY2());
            shapes += "2 1 0 " + pen_thickness + " " + pen_color + " " + fill_color
                   + " " + depth + " 1 " + area_fill + " 0 0 0 12 0 0 2"
                   + "\n\t"
                   + " " + x1 + " " + y1
                   + " " + x2 + " " + y2
                   + "\n";
        }
        // Path (curve)
        if (s instanceof Path2D.Double) {
            // Segment information
            float[] segment = new float[6];
            // Go over the whole path
            String segments = "";
            int segments_nb = 0;
            PathIterator path = ((Path2D.Double)s).getPathIterator(null);
            while(!path.isDone()) {
                // Should test if type is SEG_MOVETO or SEG_LINETO
                int type = path.currentSegment(segment);
                // We do not model Quads and Cubics => only the first point is used
                int x = scale(segment[0]);
                int y = scale(segment[1]);
                segments += x + " " + y + " ";
                // Go to the next segment
                segments_nb++;
                path.next();
            }
            // Generate the full FIG element   
            shapes += "2 1 0 " + pen_thickness + " " + pen_color + " " + fill_color
                   + " " + depth + " 1 " + area_fill + " 0 0 0 12 0 0 " + segments_nb
                   + "\n\t" + segments + "\n";
        }
        // Rectangle
        else if (s instanceof Rectangle2D.Double) {
            Rectangle2D.Double rect = (Rectangle2D.Double)s;
            int x1 = scale(rect.getX());
            int y1 = scale(rect.getY());
            int x2 = scale(rect.getX()+rect.getWidth());
            int y2 = scale(rect.getY()+rect.getHeight());
            shapes += "2 2 0 " + pen_thickness + " " + pen_color + " " + fill_color
                   + " " + depth + " 1 " + area_fill + " 0 0 0 0 0 0 5"
                   + "\n\t"
                   + " " + x1 + " " + y1
                   + " " + x1 + " " + y2
                   + " " + x2 + " " + y2
                   + " " + x2 + " " + y1
                   + " " + x1 + " " + y1
                   + "\n";
        }
        // Ellipse
        else if (s instanceof Ellipse2D.Double) {
            Ellipse2D.Double ellipse = (Ellipse2D.Double)s;
            int x1 = scale(ellipse.getX());
            int y1 = scale(ellipse.getY());
            int w = scale(ellipse.getWidth());
            int h = scale(ellipse.getHeight());
            shapes += "1 1 0 " + pen_thickness + " " + pen_color + " " + fill_color
                   + " " + depth + " 1 " + area_fill + " 0 1 0.0"
                   + " " + x1+w/2 + " " + y1+h/2
                   + " " + w/2 + " " + h/2
                   + " " + x1 + " " + y1
                   + " " + x1+w + " " + y1+h
                   + "\n";
        }
            
        // Change the depth for each element plotted
        // (as long as there are less than 1000 elements)
        if (depth>0)
            depth--;
    }
    
    protected int scale(double coord) {
        return (int)(coord*scale);
    }
        
    public void drawGlyphVector(GlyphVector g, float x, float y) {}
    public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {}
    public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
        return false;
    }
    public void drawRenderableImage(RenderableImage img, AffineTransform xform) {}
    public void drawRenderedImage(RenderedImage img, AffineTransform xform) {}
    public void drawString(AttributedCharacterIterator iterator, float x, float y) {}
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {}
    
    public void drawString(String str, float x, float y) {
        int tx = scale(x);
        int ty = scale(y);
        // Compute the width and height using the font chosen
        FontMetrics fm = getFontMetrics(font);
        int w = scale(getTransform().getScaleX()*fm.stringWidth(str));
        int h = scale(getTransform().getScaleY()*fm.getHeight());
        // Font size to use (fixed str for now)
        float font_size = 10; // (float)(font.getSize()/scale)
        // But use LaTeX default font in the FIG file
        shapes += "4 0 " + color + " " + depth + " 1 0 " + font_size
                 + " 0.0 6 " + h + " " + w + " " + tx + " " + ty + " " + str + "\\001" + "\n";
    }
    
    public void drawString(String str, int x, int y) {
        drawString(str,(float)x,(float)y);
    }
        
    public Color getBackground() {
        return null;
    }
    public Composite getComposite() {
        return null;
    }
    public GraphicsConfiguration getDeviceConfiguration() {
        return reference.getDeviceConfiguration();
    }
    public FontRenderContext getFontRenderContext() {
        return reference.getFontRenderContext();
    }
    public Paint getPaint() {
        return reference.getPaint();
    }
    public Object getRenderingHint(RenderingHints.Key hintKey) {
        return reference.getRenderingHint(hintKey);
    }
    public RenderingHints getRenderingHints() {
        return reference.getRenderingHints();
    }
    public Stroke getStroke() {
        return reference.getStroke();
    }
    public AffineTransform getTransform() {
        return reference.getTransform();
    }
    public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
        return false;
    }
    public void rotate(double theta) {}
    public void rotate(double theta, double x, double y) {}
    public void scale(double sx, double sy) {}
    public void setBackground(Color color) {}
    public void setComposite(Composite comp) {}
    public void setPaint(Paint paint) {}
    public void setRenderingHint(RenderingHints.Key hintKey, Object hintValue) {}
    public void setRenderingHints(Map<?,?> hints) {}
    
    public void setStroke(Stroke s) {
        if (s instanceof BasicStroke) {
            thickness = (int)((BasicStroke)s).getLineWidth(); // to scale ?
        }
    }
       
    public void setTransform(AffineTransform Tx) {}
    public void shear(double shx, double shy) {}
    public void transform(AffineTransform Tx) {}
    public void translate(double tx, double ty) {}
    public void translate(int x, int y) {}
    
    public void clearRect(int x, int y, int width, int height) {}{}
    public void clipRect(int x, int y, int width, int height) {}
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {}
    public Graphics create() {
        return new FigGraphics(reference);
    }
    public void dispose() {}
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {}
    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return false;
    }
    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        return false;
    }
    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        return false;
    }
    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
        return false;
    }
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
        return false;
    }
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
        return false;
    }
    public void drawLine(int x1, int y1, int x2, int y2) {
        draw(new Line2D.Double(x1,y1,x2,y2));
    }
    public void drawOval(int x, int y, int width, int height) {
        draw(new Ellipse2D.Double(x,y,width,height));
    }
    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {}
    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {}
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {}
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {}
    public void fillOval(int x, int y, int width, int height) {
        draw(new Ellipse2D.Double(x,y,width,height));
    }
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {}
    public void fillRect(int x, int y, int width, int height) {
        draw(new Rectangle2D.Double(x,y,width,height));
    }
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {}
    public Shape getClip() {
        return reference.getClip();
    }
    public Rectangle getClipBounds() {
        return reference.getClipBounds();
    }
    public Color getColor() {
        return colors.get(color);
    }
    public Font getFont() {
        return font;
    }
    public FontMetrics getFontMetrics(Font f) {
        return reference.getFontMetrics(f);
    }
    public void setClip(int x, int y, int width, int height) {}
    public void setClip(Shape clip) {}
    
    public void setColor(Color c) {
        // Try to find the color in the list of used colors
        color = colors.indexOf(c);
        // If not found, add it
        if (color<0) {
            colors.add(c);
            color = colors.size()-1;
            assert(colors.get(color)==c);
        }
    }
    
    public void setFont(Font font) {
        this.font = font;
    }
    
    public void setPaintMode() {}
    public void setXORMode(Color c1) {}
    
    protected String colorToString(int c) {
        String s = "0 " + c + " #";
        Color col = colors.get(c);
        s += String.format("%02x%02x%02x",
            col.getRed(),col.getGreen(),col.getBlue());
        return s+"\n";
    }
    
    public String toString() {
        String s = header;
        // Output the colors
        for (int c=32; c<colors.size(); c++)
            s += colorToString(c);
        // Output the shapes
        s += shapes;
        return s;
    }
     
    /** Save the graphics as a FIG file */
    public void save(File file) {
        // Platform independent new line
        String newline = System.getProperty("line.separator");
        // Open the file
        try {
            FileWriter out = new FileWriter(file);
            out.write(this.toString());
            // Close the file
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}