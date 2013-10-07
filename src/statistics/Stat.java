package statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import maps.AbstractMap;
import maps.AbstractUnitMap;
import maps.FramedSpaceIterator;
import maps.Leaf;
import maps.Map;
import maps.NeighborhoodMap;
import maps.Parameter;
import maps.Track;
import maps.Var;
import model.Model;
import neigborhood.Neighborhood;
import unitModel.SomUM;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.Space;

public class Stat {

	public static final int X = 0;
	public static final int Y = 1;

	protected Var dt;
	protected Space space2d;
	protected Space noDimSpace;
	protected Model model;
	protected List<AbstractMap> tracks;

	public Stat( Var dt, Space space2d,Model model, Parameter... maps) {
		//		System.out.println("MEM:"+"construct:"+this.getClass());
		//		System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
		this.model = model;
		this.dt = dt;
		this.space2d = space2d;
		noDimSpace = space2d.clone(); //Space with "0" dimension for single value map
		noDimSpace.setDimension(new int[]{0,0});
	}

	protected Var getCommand(String param)
	{
		try {
			return model.getCommandLine().get(param);
		} catch (CommandLineFormatException e) {
			e.printStackTrace();
			System.exit(-1);
			//Programation error
		}
		return null;
	}

	/**
	 * Compute the statistic function of the model
	 * @param leaf : target map of the computations 
	 * @param tracks : list of trackable object
	 * @return the list of statistic function
	 * @throws CommandLineFormatException 
	 */
	public List<StatMap> getDefaultStatistics(Leaf leaf,
			List<AbstractMap> tracks) throws CommandLineFormatException
			{
		this.tracks = tracks;
		List<StatMap> list = new LinkedList<StatMap>();

		StatMap wsum = getWsum(leaf);
		List<StatMap> coorBubble = getCoorBubble(leaf,wsum);
		StatMap closestTrack = getClosestTrack(coorBubble);
		List<StatMap> coorTrack = getCoorTrack(closestTrack);

		StatMap error = getError(coorBubble,coorTrack);

		StatMap sizeBubbleH = getSizeBubbleHeight(leaf,wsum,getCommand(CNFTCommandLine.ACT_THRESHOLD));
		StatMap sizeBubbleW = getSizeBubbleWidth(leaf,wsum,getCommand(CNFTCommandLine.ACT_THRESHOLD));

		StatMap convergence = getGoodTracking(leaf, closestTrack, sizeBubbleW, sizeBubbleH,error);

		StatMap trueError = getTrueError(leaf,convergence,closestTrack,coorBubble);


		list.add(wsum);
		list.addAll(coorBubble);
		list.add(closestTrack);
		list.addAll(coorTrack);
		list.add(error);
		list.add(sizeBubbleW);
		list.add(sizeBubbleH);
		list.add(convergence);
		list.add(getGoodFocus(leaf, convergence, sizeBubbleW, sizeBubbleH,error));
		list.add(trueError);
		list.add(getAccError(leaf, convergence, trueError));





		return list;
			}


	public StatMap getLyapunov(Parameter potential,Parameter cnft,Parameter input){
		StatMap lyapunov = new StatMap(Statistics.LYAPUNOV,dt,noDimSpace,tracks,potential,cnft,input){
			public static final int POTENTIAL = 0;
			public static final int CNFT = 1;
			public static final int INPUT = 2;

			@Override
			public double computeStatistic() {
				double epsilon  = 0;
				Parameter potential = getParam(POTENTIAL);
				Parameter input = getParam(INPUT);
				Parameter cnft = getParam(CNFT);

				double sum0 = 0,sum1 = 0, sum2 = 0;

				for(int i = 0 ; i < space2d.getDiscreteVolume() ; i++){
					double pot = potential.get(i);
					double in = input.get(i);
					double cft = cnft.get(i);

					sum0 += pot * cft;
					sum1 += pot * in;
					//System.out.println("pot : " + pot);
					sum2 += 0.5 * pot*pot;
				}
				//				System.out.println("epsilon = -0.5 * "+ sum0 +  " - " + sum1 +  " + " +  sum2);
				epsilon = -0.5 * sum0 - sum1 + sum2;
				//				System.out.println(epsilon);

				return epsilon;
			}

		};

		return lyapunov;
	}


	/**
	 * For now it is just sum leaf
	 * @param leaf
	 * @return
	 */
	public StatMap getTestConvergence(Leaf leaf) {
		StatMap wsum = new StatMap(Statistics.TEST_CONV,dt,noDimSpace,tracks,leaf){

			@Override
			public double computeStatistic() {
				Parameter map =   this.getParam(0);
				map.constructMemory();
				int dx = map.getSpace().getDiscreteSize()[Space.X];
				int dy = map.getSpace().getDiscreteSize()[Space.Y];
				double sum = 0;
				for(int i = 0 ; i< dx ; i++)
				{
					for(int j = 0 ; j < dy ; j++)
					{
						sum += map.get(i + j * dx) ;
					}
				}
				return sum;

			}
		};

		return wsum;
	}


	public StatMap getMax(Leaf leaf) {
		StatMap max = new StatMap(Statistics.MAX,dt,noDimSpace,tracks,leaf){

			@Override
			public double computeStatistic() {
				Map param = (Map) ((Leaf)getParam(0)).getMap();

				double max = Double.MIN_VALUE;
				for(int i = 0 ; i < param.getSpace().getDiscreteVolume() ;i ++){
					double current = param.get(i);
					if(current > max)
						max = current;

				}
				return max;
			}

		};
		return max;
	}

	private StatMap getTrueError(Leaf leaf, StatMap conv,
			StatMap closestTrack, List<StatMap> coorBubble) {


		Parameter[] params = new Parameter[5];
		System.arraycopy(new Parameter[]{leaf,conv,closestTrack,coorBubble.get(0),coorBubble.get(1)}, 0, params, 0, 5);

		StatMap trueError = new StatMap(Statistics.TRUE_ERROR,dt,noDimSpace,tracks,params){


			AbstractMap tracked =null;

			@Override
			public void reset(){
				super.reset();
				tracked =null;
			}


			@Override
			public double computeStatistic() {
				double conv = getParam(1).get();
				int closestTrack =  (int) getParam(2).get();
				double bubbleX =  getParam(3).get();
				double bubbleY = getParam(4).get();


				double ret = Statistics.ERROR;

				if(bubbleX != Statistics.ERROR && conv != Statistics.ERROR){
					//We get the tracked stimulis after the convergence
					if(tracked == null){
						for(int i = 0; i < tracks.size()  ; i++)
						{
							AbstractMap p = (AbstractMap) tracks.get(i);
							//System.out.println("Param : " + p + " @" + p.hashCode());
							if(((double)p.hashCode()) == closestTrack){
								tracked = (AbstractMap) p;
								break;
							}
						}
					}




					Double[] center = {bubbleX,bubbleY};
					Double[] centerTrack = ((Track) ((AbstractUnitMap)tracked).getUnitModel()).getCenter();
					//System.out.println("center " + center);
					//					System.out.println("track " + centerTrack);
					ret = Space.distance(center,centerTrack);


				}
				return ret;

			}
		};
		return trueError;

	}

	protected StatMap getAccError(Leaf leaf,StatMap conv,StatMap error){
		StatMap accError = new StatMap(Statistics.ACC_ERROR,dt,noDimSpace,tracks,leaf,conv,error){



			@Override
			public double computeStatistic() {
				double conv = getParam(1).get();
				double errorDist = getParam(2).get();

				double ret = Statistics.ERROR;
				double acceptableError = getCommand(CNFTCommandLine.ACCERROR).get();
				if(conv != Statistics.ERROR){
					if(errorDist <= acceptableError){
						ret = 1.0;
					}
					else{
						ret = 0.0;
					}
				}
				return ret;

			}};
			return accError;
	}

	protected StatMap getGoodFocus(Leaf leaf,StatMap conv,StatMap width,StatMap height,StatMap error){
		StatMap goodFocus = new StatMap(Statistics.FOCUS,dt,noDimSpace,tracks,leaf,conv,width,height,error){
			@Override
			public double computeStatistic() {
				double conv = getParam(1).get();
				double bubbleWidth =  getParam(2).get();
				double bubbleHeight =  getParam(3).get();

				double ret = Statistics.ERROR;

				if(conv != Statistics.ERROR){
					if(bubbleHeight == 0 && bubbleWidth == 0){
						ret = 0.0;
					}
					else{
						ret = 1.0;
					}
				}
				return ret;

			}};
			return goodFocus;
	}

	protected StatMap getGoodTracking(Leaf leaf,StatMap closestTrack,StatMap width,StatMap height,StatMap error){
		StatMap goodTracking = new StatMap(Statistics.CONVERGENCE,dt,noDimSpace,tracks,leaf,closestTrack,width,height,error){

			int trackedStimulis = Statistics.ERROR;
			boolean goodTracking = false; //True if tracked and curent are the same or there is no trackedStimulis
			int stab = 0; //Nb iteration with stab
			int i = 1; //iteration
			int convergence = Statistics.ERROR;

			@Override
			public void reset(){
				super.reset();
				trackedStimulis = Statistics.ERROR;
				goodTracking = false; //True if tracked and curent are the same or there is no trackedStimulis
				stab = 0; //Nb iteration with stab
				i = 1; //iteration
				convergence = Statistics.ERROR;
			}

			@Override
			public double computeStatistic() {

				//				System.out.println("Compute stats" + Arrays.toString(Thread.currentThread().getStackTrace()));

				if(convergence == Statistics.ERROR){

					int currentStimulis = (int)( ((Parameter) getParam(1)).get());
					double bubbleWidth =  getParam(2).get();
					double bubbleHeight =  getParam(3).get();
					double errorDist = getParam(4).get();

					double shapeFactor = getCommand(CNFTCommandLine.SHAPE_FACTOR).get();
					double acceptableError = getCommand(CNFTCommandLine.ACCERROR).get();
					double stabIt = getCommand(CNFTCommandLine.STABIT).get();




					if(currentStimulis == Statistics.ERROR) //We cannot compute the closest track
					{
						goodTracking = false;
					}
					else if(trackedStimulis == Statistics.ERROR) 
						//We initiate the closest track at the beginin or after a failed convergence
					{
						trackedStimulis = currentStimulis;
						goodTracking = true;
					}
					else
					{
						goodTracking = (trackedStimulis == currentStimulis);
					}

					if(goodTracking)
					{
						AbstractMap tracked = model.getTracked(trackedStimulis);
						Double[] center = ((Track) ((AbstractUnitMap) tracked).getUnitModel()).getCenter();


						//					System.out.println(stats.get(widthId,i) +"<=" +shapeFactor*tracked.getWidth(center) 
						//					+"&&" +stats.get(heightId,i)+ "<="+ shapeFactor*tracked.getHeight(center));
						double width = ((Track) ((AbstractUnitMap) tracked).getUnitModel()).getDimension()[Space.X];
						double height = ((Track) ((AbstractUnitMap) tracked).getUnitModel()).getDimension()[Space.Y];
						if(bubbleWidth != Statistics.ERROR && bubbleWidth <= shapeFactor*width 
								&& bubbleHeight <= shapeFactor*height && bubbleWidth > 0 &&  bubbleHeight > 0)
						{

							//System.out.println("test : " +stats.get(errorId,i) +"<="+ get(ACCERROR));
							if(errorDist != Statistics.ERROR && errorDist <= acceptableError)
							{

								//System.out.println("i:" + i +" :: " +errorDist +"<="+ acceptableError);
								stab ++;
							}
							else
							{
								//Reinitiate convergence
								stab = 0;
								trackedStimulis = Statistics.ERROR;
							}
						}
						else
						{
							//Reinitiate convergence
							stab = 0;
							trackedStimulis = Statistics.ERROR;
						}
					}
					else
					{
						//Reinitiate convergence
						stab = 0;
						trackedStimulis = Statistics.ERROR;
					}



					if(stab == stabIt)
						convergence = (int) (i - (stabIt-1));
					else
						convergence = Statistics.ERROR;

					i++;



				}

				//				System.out.println(convergence + Arrays.toString(Thread.currentThread().getStackTrace()));
				return convergence;
			}
		};

		return goodTracking;
	}


	public StatMap getSizeBubbleHeight(Leaf leaf, StatMap wsum,Var threshold) {

		StatMap height = new StatMap(Statistics.HEIGHT,dt,noDimSpace,tracks,leaf,wsum,threshold) {

			@Override
			public double computeStatistic()  {
				Parameter target = (Parameter) getParam(0);
				Parameter wsum = (Parameter) getParam(1);
				Parameter threshold = (Parameter) getParam(2);
				//System.out.println("Threshold : " + threshold.get());

				double res = Statistics.ERROR;
				if(wsum.get() != Statistics.ERROR)
				{

					target.constructMemory();
					int minY = Integer.MAX_VALUE;
					int maxY = Integer.MIN_VALUE;
					int dx = target.getSpace().getDiscreteSize()[Space.X];
					int dy = target.getSpace().getDiscreteSize()[Space.Y];
					int nb = 0;
					for(int i = 0 ; i< dx ; i++)
					{
						for(int j = 0 ; j < dy ; j++)
						{
							if(target.get(i + j * dx) >= threshold.get())
							{
								//System.out.println("at "+i+","+j+" act= "+target.get(i, j));
								if(j < minY)
									minY = j;
								if(j > maxY)
									maxY = j;
								nb++;
							}
						}
					}

					if(nb > 0)
					{
						int height = maxY - minY;
						//System.out.println("height = " + height);
						//Normalize the size :
						res = space.distContinuousProj(height, Space.Y);
					}




				}
				return res;

			}
		};


		return height;
	}
	public StatMap getSizeBubbleWidth(Leaf leaf, StatMap wsum,Var threshold) {
		StatMap width = new StatMap(Statistics.WIDTH,dt,noDimSpace,tracks,leaf,wsum,threshold) {

			@Override
			public double computeStatistic()  {
				Parameter target =  getParam(0);
				Parameter wsum =  getParam(1);
				Parameter threshold =  getParam(2);
				double res = Statistics.ERROR;

				if(wsum.get() != Statistics.ERROR)
				{
					target.constructMemory();
					int minX = Integer.MAX_VALUE;
					int maxX = Integer.MIN_VALUE;
					int dx = target.getSpace().getDiscreteSize()[Space.X];
					int dy = target.getSpace().getDiscreteSize()[Space.Y];
					int nb = 0;
					for(int i = 0 ; i< dx ; i++)
					{
						for(int j = 0 ; j < dy ; j++)
						{
							if(target.get(i + j * dx) >= threshold.get())
							{
								if(i < minX)
									minX = i;
								if(i > maxX)
									maxX = i;
								nb++;
							}
						}
					}

					if(nb > 0)
					{
						int height = maxX - minX;
						//System.out.println("height = " + height);
						//Normalize the size :
						res = space.distContinuousProj(height, Space.X);
					}


				}
				return res;

			}
		};

		return width;
	}

	/**
	 * wITH THE COORDINATE OF THE BUBBLE AND THE CLOSEST TRACK RETURN THE ERROR
	 * @param coorBubble
	 * @param coorTrack
	 * @return
	 */
	protected StatMap getError(List<StatMap> coorBubble,
			List<StatMap> coorTrack) {
		AbstractMap[] params = new AbstractMap[4];
		params[0] = coorBubble.get(X);
		params[1] = coorBubble.get(Y);
		params[2] = coorTrack.get(X);
		params[3] = coorTrack.get(Y);


		StatMap errorDistance = new StatMap(Statistics.ERROR_DIST
				,dt,noDimSpace,tracks,params)
		{

			@Override
			public double computeStatistic()  {
				AbstractMap centerX = (AbstractMap) this.getParam(0);
				AbstractMap centerY = (AbstractMap) this.getParam(1);
				AbstractMap trackX = (AbstractMap) this.getParam(2);
				AbstractMap trackY = (AbstractMap) this.getParam(3);
				double ret = Statistics.ERROR;

				if(trackX.get() !=  Statistics.ERROR && centerX.get() != Statistics.ERROR)
				{

					Double[] center = {centerX.get(),centerY.get()};
					Double[] centerTrack = {trackX.get(),trackY.get()};
					//System.out.println("center " + center);
					//					System.out.println("track " + centerTrack);
					ret = Space.distance(center,centerTrack);
					//					System.out.println("Dist = " + ret);
				}

				return ret;
			}
		};
		return errorDistance;
	}

	/**
	 * Given the closestTrack hash code function, return the coordinates of the closest tRACK
	 * @param closestTrack
	 * @param tracks
	 * @return
	 */
	private List<StatMap> getCoorTrack(StatMap closestTrack) {
		List<StatMap> ret = new ArrayList<StatMap>(2);

		AbstractMap[] params = new Map[1];
		params[0] = closestTrack;



		StatMap trackX = new StatMap(Statistics.TRACK_X,dt,noDimSpace,tracks,params)
		{
			@Override
			public double computeStatistic()  {
				double ret =  Statistics.ERROR;
				AbstractMap closestTrack = (AbstractMap) this.getParam(0);
				if(closestTrack.get() !=  Statistics.ERROR)
				{
					AbstractMap closest = null;
					//System.out.println("Closest : " + closestTrack.get());
					for(int i = 0 ; i < tracks.size()  ; i++)
					{
						AbstractMap p = (AbstractMap) tracks.get(i);
						//System.out.println("Param : " + p + " @" + p.hashCode());
						if(((double)p.hashCode()) == closestTrack.get())
							closest = (AbstractMap) p;
					}


					ret =	((Track) ((AbstractUnitMap)closest).getUnitModel()).getCenter()[Space.X];
				}
				return ret;
			}
		};

		StatMap trackY = new StatMap(Statistics.TRACK_Y,dt,noDimSpace,tracks,params)
		{
			@Override
			public double computeStatistic()  
			{
				double ret =  Statistics.ERROR;
				AbstractMap closestTrack = (AbstractMap) this.getParam(0);
				if(closestTrack.get() !=  Statistics.ERROR)
				{
					AbstractMap  closest = null;
					for(int i = 0 ; i < tracks.size() ; i++)
					{

						AbstractMap p = (AbstractMap) tracks.get(i);
						if(((double)p.hashCode()) == closestTrack.get())
							closest = (AbstractMap) p;
					}
					ret =	((Track) ((AbstractUnitMap)closest).getUnitModel()).getCenter()[Space.Y];
				}
				return ret;
			}
		};

		ret.add(trackX);
		ret.add(trackY);

		return ret;
	}

	/**
	 * Return a function which return the hashcode of the closest track
	 * @param coorBubble
	 * @param tracks
	 * @return
	 */
	protected StatMap getClosestTrack(List<StatMap> coorBubble) {
		StatMap centerX = coorBubble.get(Space.X);
		StatMap centerY = coorBubble.get(Space.Y);
		AbstractMap[] params = new Map[2];
		params[Space.X] = centerX;
		params[Space.Y] = centerY;

		/**
		 * Params : centerX,centerY, tracks...
		 */
		StatMap closestTrack = new StatMap(Statistics.CLOSEST_TRACK,
				dt,noDimSpace,tracks,params) {

			@Override
			public double computeStatistic()  
			{
				double ret =  Statistics.ERROR;
				AbstractMap closest = null;
				double minD = Double.MAX_VALUE;
				AbstractMap centerX = (AbstractMap) this.getParam(0);
				AbstractMap centerY = (AbstractMap) this.getParam(1);
				Double[] center = new Double[]{centerX.get(),centerY.get()};
				if(!(center[Space.X].isNaN() || center[Space.X].isInfinite() ))
				{
					for(int i = 0 ; i < tracks.size() ; i++)
					{
						AbstractMap p = (AbstractMap) tracks.get(i);
						//System.out.println("Track : " + p.getName() + " @"+ p.hashCode());
						double d = Space.distance(
								((Track) ((AbstractUnitMap)p).getUnitModel()).getCenter(),
								center);
						//System.out.println("focus : " + Arrays.toString(center));
						//System.out.println("Distance : " +Arrays.toString(((Track) ((AbstractUnitMap)p).getUnitModel()).getCenter()) + " and " + Arrays.toString(center) );
						//	System.out.println("d:"+d + ", minD : " + minD);
						if(d < minD)
						{
							minD = d;
							closest = p;
						}
					}
					ret = closest.hashCode();
				}
				//System.out.println("error : " + ret);
				return ret;
			}
		};
		return closestTrack;
	}

	/**
	 * Return the coordinates StatMap for the target. It correspond to the barycenter of
	 * coordinates normalized with wsum and scaled with the refSpace
	 * 	
	 * @param leaf
	 * @param wsum
	 * @return
	 */
	private List<StatMap> getCoorBubble(Leaf leaf,StatMap wsum) {
		List<StatMap> ret = new ArrayList<StatMap>(2);
		//Warning : if the focus bubble is wrapped it does not work! //TODO
		StatMap centerX = new StatMap(Statistics.CENTER_X,dt,noDimSpace,tracks,leaf,wsum) {

			@Override
			public double computeStatistic()    {
				Parameter pot =  this.getParam(0);
				int wsum = (int) this.getParam(1).get();
				if(wsum != 0){
					pot.constructMemory();
					double sumX = 0;
					int dx = pot.getSpace().getDiscreteSize()[Space.X];
					int dy = pot.getSpace().getDiscreteSize()[Space.Y];
					for(int i = 0 ; i< dx ; i++)
					{
						for(int j = 0 ; j < dy ; j++)
						{
							sumX += pot.get(i + j * dx) * i;
						}
					}
					// Normalize the center coordinates
					sumX = sumX/((StatMap) this.getParam(1)).get();
					sumX = space.continusousProj(sumX, Space.X);
					return sumX;
				}else{
					return Statistics.ERROR;
				}

			}
		};

		StatMap centerY = new StatMap(Statistics.CENTER_Y,dt,noDimSpace,tracks,leaf,wsum) {

			@Override
			public double computeStatistic()  {
				Parameter pot =   this.getParam(0);
				int wsum = (int) this.getParam(1).get();
				if(wsum != 0){
					pot.constructMemory();
					double sumY = 0;
					int dx = pot.getSpace().getDiscreteSize()[Space.X];
					int dy = pot.getSpace().getDiscreteSize()[Space.Y];
					for(int i = 0 ; i< dx ; i++)
					{
						for(int j = 0 ; j < dy ; j++)
						{
							sumY += pot.get(i + j * dx) * j;
						}
					}
					// Normalize the center coordinates
					sumY = sumY/((StatMap) this.getParam(1)).get();
					sumY = space.continusousProj(sumY, Space.Y);
					return sumY;
				}else{
					return Statistics.ERROR;
				}
			}
		};

		ret.add(centerX);
		ret.add(centerY);

		return ret;
	}

	/**
	 * Return the function with the sum  of activity of the target memory
	 * @param leaf
	 * @return
	 */
	public StatMap getWsum(Leaf leaf) {
		StatMap wsum = new StatMap(Statistics.ACT_SUM,dt,noDimSpace,tracks,leaf){

			@Override
			public double computeStatistic() {
				Parameter map =   this.getParam(0);
				map.constructMemory();
				int dx = map.getSpace().getDiscreteSize()[Space.X];
				int dy = map.getSpace().getDiscreteSize()[Space.Y];
				double sum = 0;
				for(int i = 0 ; i< dx ; i++)
				{
					for(int j = 0 ; j < dy ; j++)
					{
						sum += map.get(i + j * dx) ;
					}
				}
				return sum;

			}
		};

		return wsum;
	}

	/**
	 * Return the mean square error :
	 * 	Dist between neuron 
	 * @param leaf
	 * @return
	 */
	public StatMap getMeanSquareSOM(Leaf leaf) {
		
		Map map = (Map) leaf.getMap();
		
		StatMap wsum = new StatMap(Statistics.MSE_SOM,dt,noDimSpace,tracks,map){

			@Override
			public double computeStatistic() {
				NeighborhoodMap map =   (NeighborhoodMap) this.getParam(0);
				Space spaceMap = map.getSpace();
				Neighborhood neigh = map.getNeighborhood().get(0);
				
				double add = 0;
				double nb = 0;
				
				double scale = 1.0;
				
				for(int i = 0 ; i  < spaceMap.getFramedSpace().getDiscreteVolume() ; i++){
					int fullIndex = FramedSpaceIterator.framedToFullIndex(i, spaceMap);
				
					
					double[] currentC = ((SomUM) map.getUnit(fullIndex).getUnitModel()).getWeights();
					int[] neighboors = neigh.getNeighborhood(fullIndex);
					
					
					for(int n : neighboors){
					
						double[] neighC = ((SomUM) map.getUnit(n).getUnitModel()).getWeights();
						double distance = Math.sqrt(Math.pow(currentC[Space.X] - neighC[Space.X], 2) + Math.pow(currentC[Space.Y] - neighC[Space.Y], 2))/scale;
						add += distance;
						nb ++;
					}
				}
				
				//System.out.println("add " + add + " nb " + nb);
				return add / nb;

			}
		};

		return wsum;
		
	}



}
