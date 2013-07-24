/*
 * (c) Copyright 1999, 2003 Uwe Voigt
 * All Rights Reserved.
 */
package applet;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Canvas;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

public final class AppletContext extends Canvas implements java.applet.AppletContext
{
	private Vector applets = new Vector();
	private AppletStub stub;

	public AppletContext(AppletStub s)
	{
		stub = s;
	}

	public Applet getApplet(String name)
	{
		return null;
	}

	public Enumeration getApplets()
	{
		return applets.elements();
	}

	public AudioClip getAudioClip(URL url)
	{
		return new Audio(url.getFile());
	}

	public Image getImage(URL url)
	{
		return Toolkit.getDefaultToolkit().getImage(url.getFile());
	}

	public void showDocument(URL url)
	{
	}

	public void showDocument(URL url, String target)
	{
	}

	public void showStatus(String status)
	{
		stub.setTitle(status);
	}
/////////////
	
	public InputStream getStream(String key)
	{
		return null;
	}

	public Iterator getStreamKeys()
	{
		return null;
	}

	public void setStream(String key, InputStream stream) throws IOException
	{
	}

} // AppletContext
