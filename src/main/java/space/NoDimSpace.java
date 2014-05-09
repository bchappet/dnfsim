package main.java.space;

public class NoDimSpace extends Space1D {
	
	public NoDimSpace(){
		super(1);
	}
	
	
	@Override
	public int getVolume(){
		return 1;
	}

}
