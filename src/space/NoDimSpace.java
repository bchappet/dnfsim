package space;

public class NoDimSpace extends Space<Integer> {
	
	public NoDimSpace(){
		super(1);
	}
	
	
	@Override
	public int getVolume(){
		return 1;
	}

}
