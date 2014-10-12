package main.java.space;

import main.resources.utils.FluxUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SpaceFactory {

	/**
	 * Construct 1D space from signal file
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static Space1D getSpace1D(String file) throws IOException{
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		String[] data = br.readLine().split(FluxUtils.SEP);
		int res = data.length;
		br.close();
		
		return new Space1D(res);
		
		
	}
	

	/**
	 * Return the space resulting of an horizontal concatenation of the two spaces given in arguments 2DDimention max
	 * @precond a.getDimY = b.getDimY
	 * @param as
	 * @param bs
	 * @return space with  a.getDimX + b.getDimX, a.getDimY
	 */
	public static Space horizontalConcatenationSpace(Space as, Space bs) {

		int aDimX = -1,aDimY = -1;
		int bDimX = -1;
		if(as instanceof NoDimSpace){
			aDimX = 1;
			aDimY = 1;
		}else if(as instanceof Space1D){ //assume line vector
			aDimX = ((Space1D) as).getDimX();
			aDimY = 1;
		}else if(as instanceof Space2D){
			aDimX = ((Space2D) as).getDimX();
			aDimY = ((Space2D) as).getDimY();
		}
		
		if(bs instanceof NoDimSpace){
			bDimX = 1;
		}else if(bs instanceof Space1D){ //assume line vector
			bDimX = ((Space1D) bs).getDimX();
		}else if(bs instanceof Space2D){
			bDimX = ((Space2D) bs).getDimX();
		}
		
		return new Space2D(aDimX + bDimX , aDimY);
		
	}

    /**
     * Return a space with A(na,ma) * B(nb,mb) = AB(na,mb) AB(xa,yb)
     * @param as
     * @param bs
     * @return
     */
    public static Space<Integer> matrixProductSpace(Space as, Space bs) {
        int aDimX = -1,aDimY = -1;
        int bDimX = -1,bDimY = -1;
        if(as instanceof NoDimSpace){
            aDimX = 1;
            aDimY = 1;
        }else if(as instanceof Space1D){ //assume line vector
            aDimX = ((Space1D) as).getDimX();
            aDimY = 1;
        }else if(as instanceof Space2D){
            aDimX = ((Space2D) as).getDimX();
            aDimY = ((Space2D) as).getDimY();
        }

        if(bs instanceof NoDimSpace){
            bDimX = 1;
            bDimY = 1;
        }else if(bs instanceof Space1D){ //assume line vector
            bDimX = ((Space1D) bs).getDimX();
            bDimY = 1;
        }else if(bs instanceof Space2D){
            bDimX = ((Space2D) bs).getDimX();
            bDimY = ((Space2D) bs).getDimY();
        }

        return new Space2D(aDimX , bDimY);

    }

    public static Space<Integer> matrixInvSpace(Space as) {
        int aDimX = -1,aDimY = -1;
        int bDimX = -1,bDimY = -1;
        if(as instanceof NoDimSpace){
            aDimX = 1;
            aDimY = 1;
        }else if(as instanceof Space1D){ //assume line vector
            aDimX = ((Space1D) as).getDimX();
            aDimY = 1;
        }else if(as instanceof Space2D){
            aDimX = ((Space2D) as).getDimX();
            aDimY = ((Space2D) as).getDimY();
        }

        return new Space2D(aDimY,aDimX);
    }
}
