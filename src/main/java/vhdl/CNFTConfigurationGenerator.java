package main.java.vhdl;

import main.resources.utils.Hardware;

public class CNFTConfigurationGenerator extends VHDLGenerator2 {
	
	protected int width_id;

	public CNFTConfigurationGenerator(int res2) {
		super(res2);
		
		width_id = Hardware.necessaryNbBit(this.res)*2;
	}
	
	public String getWidth_id(){
		return indent + width_id;
	}
	
	public String getRes(){
		return indent + this.res;
	}

}
