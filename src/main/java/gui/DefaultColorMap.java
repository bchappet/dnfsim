package main.java.gui;

import java.awt.Color;

public class DefaultColorMap extends AdaptiveAndEquilibratedColorMap {

	public DefaultColorMap() {
		super(new Color[]{Color.BLUE,Color.WHITE,Color.RED});
	}

	public DefaultColorMap(AdaptiveAndEquilibratedColorMap map) {
		super(map);
	}

}
