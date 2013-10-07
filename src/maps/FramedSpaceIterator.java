package maps;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import coordinates.Space;

public class FramedSpaceIterator implements Iterator<Unit> {

	protected Space space;
	protected List<Unit> units;
	protected int realIndex;
	protected int length;

	public FramedSpaceIterator(Space space,List<Unit> units) {
		this.space = space;
		this.units = units;
		length = units.size();

		realIndex = 0;
		while(realIndex < length &&  !inside(realIndex,space)){
			realIndex ++;
		}if(realIndex >= length){
			realIndex = -1;
		}
	}



	/**
	 * Return the next index inside the frame or -1 if we are outside the space
	 * @return
	 */
	protected void nextIndex(){
		do{
			realIndex ++;
		}while(realIndex < length &&  !inside(realIndex,space));
		//real index >= length or real index inside
		if(realIndex >= length){
			realIndex = -1;
		}else{
			//nothing
		}

	}
	/**
	 * Return true if the index is inside the frame
	 * @param realIndex2
	 * @return
	 */
	protected static boolean inside(int realIndex2,Space space) {

		Space frameSpace = space.getFramedSpace();
		int resFrameSpace = (int) frameSpace.getResolution();
		int resSpace = (int) space.getResolution();

		if(resFrameSpace != resSpace){

			double ratio = resSpace/(double)resFrameSpace;
			int offSet =(resSpace - resFrameSpace)/2;


			int a = realIndex2 % resSpace; //test on y axis

			if(a >= 0 + offSet && a < resSpace - offSet){
				int b = realIndex2 / resSpace; //test on x axis
				if(b >= 0 + offSet && b < resSpace - offSet){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return true;
		}
	}

	@Override
	public boolean hasNext() {
		//System.out.println("HasNext : " + realIndex + " @" + hashCode());
		return realIndex != -1;
	}

	@Override
	public Unit next() {
		//System.out.println("Next : " + realIndex+ " @" + hashCode());
		Unit ret =  units.get(realIndex);
		nextIndex();
		return ret;
	}

	@Override
	public void remove() {
		System.err.println("Not implemented" + Arrays.toString(Thread.currentThread().getStackTrace()));
		System.exit(-1);

	}

	

	/**
	 * Shift the index as if the array is of framedSpace resolution
	 * @param index
	 * @return
	 */
	public static int fullToFramedIndex(int index,Space space) {

		Space frameSpace = space.getFramedSpace();
		int resFrameSpace = (int) frameSpace.getResolution();
		int resSpace = (int) space.getResolution();
		if(resFrameSpace != resSpace){
			int offset =(resSpace - resFrameSpace)/2;

			int x = index % resSpace;
			int y = index / resSpace;

			x -= offset;
			y -= offset;

			int newIndex = y * resFrameSpace + x;



			//		System.out.println("Index : "+ index);
			//		System.out.println("return : " + newIndex);


			return newIndex;
		}else{
			return index;
		}
	}
	
	/**
	 * Shift the index of framed to the full space resolution
	 * @param index
	 * @return
	 */
	public static int framedToFullIndex(int index,Space space) {

		Space frameSpace = space.getFramedSpace();
		int resFrameSpace = (int) frameSpace.getResolution();
		int resSpace = (int) space.getResolution();
		if(resFrameSpace != resSpace){
			int offset =(resSpace - resFrameSpace)/2;

			int x = index % resFrameSpace;
			int y = index / resFrameSpace;

			x += offset;
			y += offset;

			int newIndex = y * resSpace + x;



			//		System.out.println("Index : "+ index);
			//		System.out.println("return : " + newIndex);


			return newIndex;
		}else{
			return index;
		}
	}


}
