package main.java.gui;

import java.util.Enumeration;
import java.util.Iterator;

public class EnumChildren implements Enumeration<DisplayNode> {

	protected Iterator<DisplayNode> it;
	public EnumChildren(DisplayNode tree) {
		this.it = tree.getChildren().iterator();
	}

	@Override
	public boolean hasMoreElements() {
		return it.hasNext();
	}

	@Override
	public DisplayNode nextElement() {
		return it.next();
	}

}
