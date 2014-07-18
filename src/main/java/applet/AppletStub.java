/**
 * (c) Copyright 1999, 2003 Uwe Voigt
 * All Rights Reserved.
 * 
 * Modifications
 * @author Jean-Charles Quinton
 * @version 03/03/10
 */
package main.java.applet;

import java.applet.Applet;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;

import javax.swing.JFrame;

public final class AppletStub extends JFrame implements java.applet.AppletStub, Runnable {
    private Applet applet;
    private URL docBase;
    private java.applet.AppletContext context;
    private String title;

    public AppletStub(final Applet applet, String title) {
        super(title);
        this.title = title;
        this.applet = applet;
        try {
            File file = new File(".");
            docBase = new URL("file", "/", file.getAbsolutePath().replace('\\', '/'));
        } catch (Exception e) {
            e.printStackTrace();
        }
        context = new AppletContext(this);
                    
        new Thread(this, "AppletStub").start();
    }
    
   
    
   
    
    /** Convert a parameter str into a boolean */
    public static boolean valueToBoolean(String val) {
        if (val==null) return false;
        return val.equals("true") || val.equals("1") || val.equals("yes");
    }
    
    /** Convert a parameter str into a double */
    public static Double valueToDouble(String val) {
        Double d = null;
        try {
            d = Double.parseDouble(val);
        } catch (Exception e) {
            // This includes the case where "val" is null
        }
        return d;
    }

    @Override
    public void run() {
        applet.init();
        add(applet);
        
        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent event) {
                applet.stop();
                applet.destroy();
                dispose();
                System.exit(0);
            }
        });
        
        pack();
        setVisible(true);
        applet.start();
    }

    @Override
    public void appletResize(int w, int h) {
        applet.resize(w, h);
    }

    @Override
    public java.applet.AppletContext getAppletContext() {
        return context;
    }

    @Override
    public URL getCodeBase() {
        return docBase;
    }

    @Override
    public URL getDocumentBase() {
        return docBase;
    }

    @Override
    public String getParameter(String name) {
        return System.getProperty(name);
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void setSize(int w, int h) {
        super.setSize(w, h);
        Insets i = getInsets();
        appletResize(w - i.left - i.right, h - i.top - i.bottom);
    }
}
