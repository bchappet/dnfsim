package main.java.statistics;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;

import main.java.console.CommandLineFormatException;
import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.SingleValueParam;
import main.java.maps.Var;
import main.java.model.Model;
import main.java.space.Coord;
import main.java.space.Coord2D;
import main.java.space.NoDimSpace;
import main.java.space.Space;
import main.java.space.Space2D;

public class StatCNFT {

	public static final int X = 0;
	public static final int Y = 1;

	protected Var<BigDecimal> dt;
	protected Space<Integer> noDimSpace;
	protected Model model;
	protected List<AbstractMap> tracks;

	public StatCNFT( Var<BigDecimal> dt,Model model, Parameter... maps) {
		//		System.out.println("MEM:"+"construct:"+this.getClass());
		//		System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
		this.model = model;
		this.dt = dt;
		noDimSpace = new NoDimSpace(); //Space with "0" dimension for single value map
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
	 * Compute the statistic function of the main.java.model
	 * @param leaf : target map of the computations 
	 * @param tracks : list of trackable object
	 * @return the list of statistic function
	 * @throws CommandLineFormatException 
	 */
	public List<StatMapCNFT> getDefaultStatistics(Parameter p,
			List<AbstractMap> tracks) throws CommandLineFormatException
			{
		this.tracks = tracks;
		List<StatMapCNFT> list = new LinkedList<StatMapCNFT>();

		StatMapCNFT wsum = getWsum(p);
		StatMapCNFT coorBubble = getCoorBubble(p,wsum);
//		List<StatMapCNFT> coorBubble2 = getCoorBubble2(p,wsum);
//		StatMapCNFT closestTrack = getClosestTrack(coorBubble);
//		List<StatMapCNFT> coorTrack = getCoorTrack(closestTrack);
//
//		StatMapCNFT error = getError(coorBubble,coorTrack);
//
//		StatMapCNFT sizeBubbleH = getSizeBubbleHeight(p,wsum,getCommand(CNFTCommandLine.ACT_THRESHOLD));
//		StatMapCNFT sizeBubbleW = getSizeBubbleWidth(p,wsum,getCommand(CNFTCommandLine.ACT_THRESHOLD));
//
//		StatMapCNFT convergence = getGoodTracking(p, closestTrack, sizeBubbleW, sizeBubbleH,error);
//
//		StatMapCNFT trueError = getTrueError(p,convergence,closestTrack,coorBubble);


		list.add(wsum);
		list.add(coorBubble);
//		list.addAll(coorBubble2);
//		list.add(closestTrack);
//		list.addAll(coorTrack);
//		list.add(error);
//		list.add(sizeBubbleW);
//		list.add(sizeBubbleH);
//		list.add(convergence);
//		list.add(trueError);
//		list.add(getAccError(p, convergence, trueError));





		return list;
			}


//	public StatMapCNFT getLyapunov(Parameter potential,Parameter cnft,Parameter input){
//		StatMapCNFT lyapunov = new StatMapCNFT(StatisticsCNFT.LYAPUNOV,dt,noDimSpace,tracks,potential,cnft,input){
//			public static final int POTENTIAL = 0;
//			public static final int CNFT = 1;
//			public static final int INPUT = 2;
//
//			@Override
//			public double computeStatistic() {
//				double epsilon  = 0;
//				Parameter potential = getParam(POTENTIAL);
//				Parameter input = getParam(INPUT);
//				Parameter cnft = getParam(CNFT);
//
//				double sum0 = 0,sum1 = 0, sum2 = 0;
//
//				for(int i = 0 ; i < potential.getSpace().getDiscreteVolume() ; i++){
//					double pot = potential.getIndex(i);
//					double in = input.getIndex(i);
//					double cft = cnft.getIndex(i);
//
//					sum0 += pot * cft;
//					sum1 += pot * in;
//					//System.out.println("pot : " + pot);
//					sum2 += 0.5 * pot*pot;
//				}
//				//				System.out.println("epsilon = -0.5 * "+ sum0 +  " - " + sum1 +  " + " +  sum2);
//				epsilon = -0.5 * sum0 - sum1 + sum2;
//				//				System.out.println(epsilon);
//
//				return epsilon;
//			}
//
//		};
//
//		return lyapunov;
//	}
//
//
//	/**
//	 * For now it is just sum leaf
//	 * @param leaf
//	 * @return
//	 */
//	public StatMapCNFT getTestConvergence(Parameter param) {
//		StatMapCNFT wsum = new StatMapCNFT(StatisticsCNFT.TEST_CONV,dt,noDimSpace,tracks,leaf){
//
//			@Override
//			public double computeStatistic() {
//				Parameter map =   this.getParam(0);
//				map.constructMemory();
//				int dx = map.getSpace().getDiscreteSize()[Space.X];
//				int dy = map.getSpace().getDiscreteSize()[Space.Y];
//				double sum = 0;
//				for(int i = 0 ; i< dx ; i++)
//				{
//					for(int j = 0 ; j < dy ; j++)
//					{
//						sum += map.getIndex(i + j * dx) ;
//						//if (map.get(i + j * dx) > 0.001)
//						//System.out.println(map.get(i + j * dx));
//					}
//				}
//				//System.out.println("Test conv : " + sum + "(time: " + this.time.val + ")" );//+ Arrays.toString(Thread.currentThread().getStackTrace()));
//				return sum;
//
//			}
//		};
//
//		return wsum;
//	}
//
//
//	public StatMapCNFT getMax(Parameter param) {
//		StatMapCNFT max = new StatMapCNFT(StatisticsCNFT.MAX,dt,noDimSpace,tracks,leaf){
//
//			@Override
//			public double computeStatistic() {
//				Map param = (Map) ((Leaf)getParam(0)).getMap();
//				double max = Double.MIN_VALUE;
//				for(int i = 0 ; i < param.getSpace().getDiscreteVolume() ;i ++){
//					double current = param.getIndex(i);
//					if(current > max)
//						max = current;
//
//				}
//				return max;
//			}
//
//		};
//		return max;
//	}
//
//	private StatMapCNFT getTrueError(Parameter param, StatMapCNFT conv,
//			StatMapCNFT closestTrack, List<StatMapCNFT> coorBubble) {
//
//
//		Parameter[] params = new Parameter[5];
//		System.arraycopy(new Parameter[]{leaf,conv,closestTrack,coorBubble.get(0),coorBubble.get(1)}, 0, params, 0, 5);
//
//		StatMapCNFT trueError = new StatMapCNFT(StatisticsCNFT.TRUE_ERROR,dt,noDimSpace,tracks,params){
//
//
//			AbstractMap tracked =null;
//
//			@Override
//			public void reset(){
//				super.reset();
//				tracked =null;
//			}
//
//
//			@Override
//			public double computeStatistic() {
//				double conv = getParam(1).get();
//				int closestTrack =  (int) getParam(2).get();
//				double bubbleX =  getParam(3).get();
//				double bubbleY = getParam(4).get();
//
//
//				double ret = StatisticsCNFT.ERROR;
//
//				if(bubbleX != StatisticsCNFT.ERROR && conv != StatisticsCNFT.ERROR){
//					//We get the tracked stimulis after the convergence
//					if(tracked == null){
//						for(int i = 0; i < tracks.size()  ; i++)
//						{
//							AbstractMap p = (AbstractMap) tracks.get(i);
//							//System.out.println("Param : " + p + " @" + p.hashCode());
//							if(((double)p.hashCode()) == closestTrack){
//								tracked = (AbstractMap) p;
//								break;
//							}
//						}
//					}
//
//
//
//
//					Double[] center = {bubbleX,bubbleY};
//					Double[] centerTrack = ((Track) ((UnitMap)tracked).getUnitModel()).getCenter();
//					//System.out.println("center " + center);
//					//					System.out.println("track " + centerTrack);
//					ret = Space.distance(center,centerTrack);
//
//
//				}
//				return ret;
//
//			}
//		};
//		return trueError;
//
//	}
//
//	protected StatMapCNFT getAccError(Parameter param,StatMapCNFT conv,StatMapCNFT error){
//		StatMapCNFT accError = new StatMapCNFT(StatisticsCNFT.ACC_ERROR,dt,noDimSpace,tracks,leaf,conv,error){
//
//
//
//			@Override
//			public double computeStatistic() {
//				double conv = getParam(1).get();
//				double errorDist = getParam(2).get();
//
//				double ret = StatisticsCNFT.ERROR;
//				double acceptableError = getCommand(CNFTCommandLine.ACCERROR).get();
//				if(conv != StatisticsCNFT.ERROR){
//					if(errorDist <= acceptableError){
//						ret = 1.0;
//					}
//					else{
//						ret = 0.0;
//						//System.out.println("ret = 0");
//					}
//				}
//				return ret;
//
//			}};
//			return accError;
//	}
//
//	//	protected StatMap getGoodFocus(Leaf leaf,StatMap conv,StatMap width,StatMap height,StatMap error){
//	//		StatMap goodFocus = new StatMap(Statistics.FOCUS,dt,noDimSpace,tracks,leaf,conv,width,height,error){
//	//			@Override
//	//			public double computeStatistic() {
//	//				double conv = getParam(1).get();
//	//				double bubbleWidth =  getParam(2).get();
//	//				double bubbleHeight =  getParam(3).get();
//	//
//	//				double ret = Statistics.ERROR;
//	//
//	//				if(conv != Statistics.ERROR){
//	//					if(bubbleHeight == 0 && bubbleWidth == 0){
//	//						ret = 0.0;
//	//					}
//	//					else{
//	//						ret = 1.0;
//	//					}
//	//				}
//	//				return ret;
//	//
//	//			}};
//	//			return goodFocus;
//	//	}
//
//	/**
//	 * @Update 23/01/2014 : remove the focus parameter, as we can track a target without focus
//	 * @param leaf
//	 * @param closestTrack
//	 * @param width
//	 * @param height
//	 * @param error
//	 * @return
//	 */
//	protected StatMapCNFT getGoodTracking(Parameter param,StatMapCNFT closestTrack,StatMapCNFT width,StatMapCNFT height,StatMapCNFT error){
//		StatMapCNFT goodTracking = new StatMapCNFT(StatisticsCNFT.CONVERGENCE,dt,noDimSpace,tracks,leaf,closestTrack,width,height,error){
//
//			int trackedStimulis = StatisticsCNFT.ERROR;
//			boolean goodTracking = false; //True if tracked and curent are the same or there is no trackedStimulis
//			double stab = 0; //Time with stab
//			double convergence = StatisticsCNFT.ERROR;
//			double preconvergence = StatisticsCNFT.ERROR;
//
//			@Override
//			public void reset(){
//				super.reset();
//				trackedStimulis = StatisticsCNFT.ERROR;
//				goodTracking = false; //True if tracked and curent are the same or there is no trackedStimulis
//				stab = 0; //Time with stab
//				convergence = StatisticsCNFT.ERROR;
//				preconvergence = StatisticsCNFT.ERROR;
//			}
//
//			@Override
//			public double computeStatistic() {
//				//System.out.println("Compute statsControler" + Arrays.toString(Thread.currentThread().getStackTrace()));
//				if(convergence == StatisticsCNFT.ERROR){
//
//					int currentStimulis = (int)( ((Parameter) getParam(1)).get());
//					double bubbleWidth =  getParam(2).get();
//					double bubbleHeight =  getParam(3).get();
//					double errorDist = getParam(4).get();
//
//					double shapeFactor = getCommand(CNFTCommandLine.SHAPE_FACTOR).get();
//					double acceptableError = getCommand(CNFTCommandLine.ACCERROR).get();
//					double stabTime = getCommand(CNFTCommandLine.STAB_TIME).get();//in second
//					
//
//
//
//
//					if(currentStimulis == StatisticsCNFT.ERROR) //We cannot compute the closest track
//					{
//						//we dont change the good tracking
//						
//						if(goodTracking){
//							stab = stab + dt.get();
//							//System.out.println("stab : " + stab);
//							if(stab > stabTime && preconvergence == StatisticsCNFT.ERROR){
//								preconvergence = time.get() - stabTime;
//								//System.out.println("Preconvergence = " + preconvergence);
//							}
//						}
//						
//					}else{
//
//						if(trackedStimulis == StatisticsCNFT.ERROR) 
//							//We initiate the closest track at the beginin or after a failed convergence
//						{
//							trackedStimulis = currentStimulis;
//							//System.out.println("Tracked Stimulis : " + trackedStimulis);
//							goodTracking = true;
//						}
//						else
//						{
//							goodTracking = (trackedStimulis == currentStimulis);
//						}
//
//						if(goodTracking)
//						{
//
//							AbstractMap tracked = ((ModelCNFT)model).getTracked(trackedStimulis);
//							Double[] center = ((Track) ((UnitMap) tracked).getUnitModel()).getCenter();
//
//
//							double width = ((Track) ((UnitMap) tracked).getUnitModel()).getDimension()[Space.X];
//							double height = ((Track) ((UnitMap) tracked).getUnitModel()).getDimension()[Space.Y];
//							if(bubbleWidth != StatisticsCNFT.ERROR && bubbleWidth <= shapeFactor*width 
//									&& bubbleHeight <= shapeFactor*height && bubbleWidth > 0 && bubbleHeight > 0)
//							{
//								//							System.out.println("i:" + i +" :: " +errorDist +"<=?"+ acceptableError);
//								if(errorDist != StatisticsCNFT.ERROR && errorDist <= acceptableError)
//								{
////									System.out.println(" t = " + time.get() + " stab:" + stab +" :: " +errorDist +"<="+ acceptableError);
//									stab = stab + dt.get();
//								}
//								else
//								{
//									//Reinitiate convergence
//									stab = 0;
//									trackedStimulis = StatisticsCNFT.ERROR;
////									System.out.println("Reset " + " error dist " + errorDist);
//								}
//							}
//							else
//							{
//								//Reinitiate convergence
//								stab = 0;
//								trackedStimulis = StatisticsCNFT.ERROR;
////								System.out.println("Reset" + " bubbleWidth : " + bubbleWidth + " bubble height " + bubbleHeight);
//							}
//						}
//						else
//						{
//							//Reinitiate convergence
//							stab = 0;
//							trackedStimulis = StatisticsCNFT.ERROR;
//						}
//
//
//
//						if(stab > stabTime){
//							if(preconvergence == StatisticsCNFT.ERROR){
//								convergence = time.get() - stabTime;
//								//System.out.println("convergence = " + convergence);
//							}else{
//								convergence = preconvergence;
//							}
//						}
//						else
//							convergence = StatisticsCNFT.ERROR;
//					}
//				}
//
//				//System.out.println("Convergence : " +convergence);// + Arrays.toString(Thread.currentThread().getStackTrace()));
//				//System.out.println("Good tracking : " + goodTracking);
//				return convergence;
//			}
//		};
//
//		return goodTracking;
//	}
//
//
//	public StatMapCNFT getSizeBubbleHeight(Parameter param, StatMapCNFT wsum,Var threshold) {
//
//		StatMapCNFT height = new StatMapCNFT(StatisticsCNFT.HEIGHT,dt,noDimSpace,tracks,leaf,wsum,threshold) {
//
//			@Override
//			public double computeStatistic()  {
//				Parameter target = (Parameter) getParam(0);
//				Parameter wsum = (Parameter) getParam(1);
//				Parameter threshold = (Parameter) getParam(2);
//				//System.out.println("Threshold : " + threshold.get());
//
//				double res = StatisticsCNFT.ERROR;
//				if(wsum.get() != StatisticsCNFT.ERROR)
//				{
//
//					target.constructMemory();
//					int minY = Integer.MAX_VALUE;
//					int maxY = Integer.MIN_VALUE;
//					int dx = target.getSpace().getDiscreteSize()[Space.X];
//					int dy = target.getSpace().getDiscreteSize()[Space.Y];
//					int nb = 0;
//					for(int i = 0 ; i< dx ; i++)
//					{
//						for(int j = 0 ; j < dy ; j++)
//						{
//							if(target.getIndex(i + j * dx) >= threshold.get())
//							{
//								//System.out.println("at "+i+","+j+" act= "+target.get(i, j));
//								if(j < minY)
//									minY = j;
//								if(j > maxY)
//									maxY = j;
//								nb++;
//							}
//						}
//					}
//
//					if(nb > 0)
//					{
//						int height = maxY - minY + 1;
//						//Normalize the size :
//						res = target.getSpace().distContinuousProj(height, Space.Y);
//					}
//
//
//
//
//				}
//				return res;
//
//			}
//		};
//
//
//		return height;
//	}
//	public StatMapCNFT getSizeBubbleWidth(Parameter param, StatMapCNFT wsum,Var threshold) {
//		StatMapCNFT width = new StatMapCNFT(StatisticsCNFT.WIDTH,dt,noDimSpace,tracks,leaf,wsum,threshold) {
//
//			@Override
//			public double computeStatistic()  {
//				Parameter target =  getParam(0);
//				Parameter wsum =  getParam(1);
//				Parameter threshold =  getParam(2);
//				double res = StatisticsCNFT.ERROR;
//
//				if(wsum.get() != StatisticsCNFT.ERROR)
//				{
//					target.constructMemory();
//					int minX = Integer.MAX_VALUE;
//					int maxX = Integer.MIN_VALUE;
//					int dx = target.getSpace().getDiscreteSize()[Space.X];
//					int dy = target.getSpace().getDiscreteSize()[Space.Y];
//					int nb = 0;
//					for(int i = 0 ; i< dx ; i++)
//					{
//						for(int j = 0 ; j < dy ; j++)
//						{
//							if(target.getIndex(i + j * dx) >= threshold.get())
//							{
//								if(i < minX)
//									minX = i;
//								if(i > maxX)
//									maxX = i;
//								nb++;
//							}
//						}
//					}
//
//					if(nb > 0)
//					{
//						int width = maxX - minX + 1;
//						//System.out.println("width = " + width);
//						//Normalize the size :
//						res = target.getSpace().distContinuousProj(width, Space.X);
//					}
//
//
//				}
//				return res;
//
//			}
//		};
//
//		return width;
//	}
//
//	/**
//	 * wITH THE COORDINATE OF THE BUBBLE AND THE CLOSEST TRACK RETURN THE ERROR
//	 * @param coorBubble
//	 * @param coorTrack
//	 * @return
//	 */
//	protected StatMapCNFT getError(List<StatMapCNFT> coorBubble,
//			List<StatMapCNFT> coorTrack) {
//		AbstractMap[] params = new AbstractMap[4];
//		params[0] = coorBubble.get(X);
//		params[1] = coorBubble.get(Y);
//		params[2] = coorTrack.get(X);
//		params[3] = coorTrack.get(Y);
//
//
//		StatMapCNFT errorDistance = new StatMapCNFT(StatisticsCNFT.ERROR_DIST
//				,dt,noDimSpace,tracks,params)
//		{
//
//			@Override
//			public double computeStatistic()  {
//				AbstractMap centerX = (AbstractMap) this.getParam(0);
//				AbstractMap centerY = (AbstractMap) this.getParam(1);
//				AbstractMap trackX = (AbstractMap) this.getParam(2);
//				AbstractMap trackY = (AbstractMap) this.getParam(3);
//				double ret = StatisticsCNFT.ERROR;
//
//				if(trackX.get() !=  StatisticsCNFT.ERROR && centerX.get() != StatisticsCNFT.ERROR)
//				{
//
//					Double[] center = {centerX.get(),centerY.get()};
//					Double[] centerTrack = {trackX.get(),trackY.get()};
//					//System.out.println("center " + center);
//					//					System.out.println("track " + centerTrack);
//					ret = Space.distance(center,centerTrack);
//					//					System.out.println("Dist = " + ret);
//				}
//
//				return ret;
//			}
//		};
//		return errorDistance;
//	}
//
//	/**
//	 * Given the closestTrack hash code function, return the main.java.coordinates of the closest tRACK
//	 * @param closestTrack
//	 * @param tracks
//	 * @return
//	 */
//	private List<StatMapCNFT> getCoorTrack(StatMapCNFT closestTrack) {
//		List<StatMapCNFT> ret = new ArrayList<StatMapCNFT>(2);
//
//		AbstractMap[] params = new Map[1];
//		params[0] = closestTrack;
//
//
//
//		StatMapCNFT trackX = new StatMapCNFT(StatisticsCNFT.TRACK_X,dt,noDimSpace,tracks,params)
//		{
//			@Override
//			public double computeStatistic()  {
//				double ret =  StatisticsCNFT.ERROR;
//				AbstractMap closestTrack = (AbstractMap) this.getParam(0);
//				if(closestTrack.get() !=  StatisticsCNFT.ERROR)
//				{
//					AbstractMap closest = null;
//					//System.out.println("Closest : " + closestTrack.get());
//					for(int i = 0 ; i < tracks.size()  ; i++)
//					{
//						AbstractMap p = (AbstractMap) tracks.get(i);
//						//System.out.println("Param : " + p + " @" + p.hashCode());
//						if(((double)p.hashCode()) == closestTrack.get())
//							closest = (AbstractMap) p;
//					}
//
//
//					ret =	((Track) ((UnitMap)closest).getUnitModel()).getCenter()[Space.X];
//				}
//				//System.out.println("TrackX = " + ret);
//				return ret;
//			}
//		};
//
//		StatMapCNFT trackY = new StatMapCNFT(StatisticsCNFT.TRACK_Y,dt,noDimSpace,tracks,params)
//		{
//			@Override
//			public double computeStatistic()  
//			{
//				double ret =  StatisticsCNFT.ERROR;
//				AbstractMap closestTrack = (AbstractMap) this.getParam(0);
//				if(closestTrack.get() !=  StatisticsCNFT.ERROR)
//				{
//					AbstractMap  closest = null;
//					for(int i = 0 ; i < tracks.size() ; i++)
//					{
//
//						AbstractMap p = (AbstractMap) tracks.get(i);
//						if(((double)p.hashCode()) == closestTrack.get())
//							closest = (AbstractMap) p;
//					}
//					ret =	((Track) ((UnitMap)closest).getUnitModel()).getCenter()[Space.Y];
//				}
//				return ret;
//			}
//		};
//
//		ret.add(trackX);
//		ret.add(trackY);
//
//		return ret;
//	}
//
//	/**
//	 * Return a function which return the hashcode of the closest track
//	 * @param coorBubble
//	 * @param tracks
//	 * @return
//	 */
//	protected StatMapCNFT<Track> getClosestTrack(StatMapCNFT<Coord<Double>> coorBubble) {
//		
//		
//		StatMapCNFT<Track> closestTrack = new StatMapCNFT<Track>(StatisticsCNFT.CLOSEST_TRACK, dt, null, tracks,coorBubble) {
//			@Override
//			public Track computeStatistic(BigDecimal time, int index,List<Parameter> params) {
//		
//				Coord<Double> bubble = (Coord<Double>) params.get(0).getIndex(0);
//				
//				if(!isError(center)){
//		
//					if(!(center[Space2D.X].isNaN() || center[Space2D.X].isInfinite() ))
//					{
//						for(int i = 0 ; i < tracks.size() ; i++)
//						{
//							UnitMap p = (UnitMap) tracks.get(i);
//							//System.out.println("Track : " + p.getName() + " @"+ p.hashCode());
//							double d = Space.distance(
//									((Track) ((UnitMap)p).getUnitModel()).getCenter(),
//									center);
//							//System.out.println("focus : " + Arrays.toString(center));
//							//System.out.println("Distance : " +Arrays.toString(((Track) ((AbstractUnitMap)p).getUnitModel()).getCenter()) + " and " + Arrays.toString(center) );
//							//System.out.println("d:"+d + ", minD : " + minD);
//							if(d < minD)
//							{
//								minD = d;
//								closest = p;
//							}
//						}
//						ret = closest.hashCode();
//					}
//		
//				}
//			}
//		};
//		
//		return null;
//		
//	}
//		StatMapCNFT centerX = coorBubble.get(Space.X);
//		StatMapCNFT centerY = coorBubble.get(Space.Y);
//		AbstractMap[] params = new Map[2];
//		params[Space.X] = centerX;
//		params[Space.Y] = centerY;
//
//		/**
//		 * Params : centerX,centerY, tracks...
//		 */
//		StatMapCNFT closestTrack = new StatMapCNFT(StatisticsCNFT.CLOSEST_TRACK,
//				dt,noDimSpace,tracks,params) {
//
//			@Override
//			public double computeStatistic(BigDecimal time, int index, List params) {
//			{
//				double ret =  StatisticsCNFT.ERROR;
//				UnitMap closest = null;
//				double minD = Double.MAX_VALUE;
//				Map centerX = (Map) this.getParam(0);
//				Map centerY = (Map) this.getParam(1);
//				//System.out.println("CenterX: " + centerX.get());
//				//System.out.println("CenterY: " + centerY.get());
//				Double[] center = new Double[]{(Double) centerX.getIndex(0),(Double) centerY.getIndex(0)};
//
//				if(!isError(center)){
//
//					if(!(center[Space2D.X].isNaN() || center[Space2D.X].isInfinite() ))
//					{
//						for(int i = 0 ; i < tracks.size() ; i++)
//						{
//							UnitMap p = (UnitMap) tracks.get(i);
//							//System.out.println("Track : " + p.getName() + " @"+ p.hashCode());
//							double d = Space.distance(
//									((Track) ((UnitMap)p).getUnitModel()).getCenter(),
//									center);
//							//System.out.println("focus : " + Arrays.toString(center));
//							//System.out.println("Distance : " +Arrays.toString(((Track) ((AbstractUnitMap)p).getUnitModel()).getCenter()) + " and " + Arrays.toString(center) );
//							//System.out.println("d:"+d + ", minD : " + minD);
//							if(d < minD)
//							{
//								minD = d;
//								closest = p;
//							}
//						}
//						ret = closest.hashCode();
//					}
//
//				}
//				//System.out.println("error : " + ret);
//				return ret;
//			}
//		};
//		return closestTrack;
	

	public static  boolean isError(Double... vect) {
		boolean ret = true;
		for(Double v : vect){
			ret &= v.equals(new Double(StatisticsCNFT.ERROR));
		}
		return ret;
	}

	/**
	 * Return the main.java.coordinates StatMap for the target. It correspond to the barycenter of
	 * main.java.coordinates normalized with wsum and scaled with the refSpace
	 * 
	 * @since version 0.6, framed main.java.space compliant for no wrap computations
	 * 	
	 * @param leaf
	 * @param wsum
	 * @return
	 */
	private StatMapCNFT<Coord<Double>> getCoorBubble(Parameter p,StatMapCNFT wsum) {
		StatMapCNFT<Coord<Double>> coorBubble = new StatMapCNFT<Coord<Double>>(StatisticsCNFT.COORD_BUBBLE, dt, null, tracks,p,wsum) {
			@Override
			public Coord<Double> computeStatistic(BigDecimal time, int index,
					List params) {
				Map pot = (Map) this.getParam(0);
//				System.out.println("Name   " + pot.getName());
				int wsum = ((Double) ((SingleValueParam)this.getParam(1)).get()).intValue();
				if(wsum != 0){
					double sumX = 0;
					double sumY = 0;
					int dx = pot.getSpace().getIndex(Space2D.X);
					int dy = pot.getSpace().getIndex(Space2D.Y);
					for(int i = 0 ; i< dx ; i++)
					{
						for(int j = 0 ; j < dy ; j++)
						{
							double val = (Double)pot.getIndex(i + j * dx);
							sumX += val * i;
							sumY += val * j;
//							if(val > 0.1 ){
//							System.out.println("val: " + val);
//								System.out.println("i: " + i + " sumX " + sumX);
//								System.out.println("j: " + j + " sumY " + sumY);
//							}
						}
//						System.out.println("end : "  );
//						System.out.println(" sumX " + sumX);
//						System.out.println(" sumY " + sumY);
					}
//					System.out.println("very end : "  );
//					System.out.println(" sumX " + sumX);
//					System.out.println(" sumY " + sumY);
					
					
					// Normalize the center main.java.coordinates (div by wsum)
					sumX = sumX/(double)wsum;
					//project to have the good continuous main.java.coordinates
					sumX = (double) pot.getSpace().typeAxisProj((int) sumX, Space2D.X);
					sumY = sumY/(double)wsum;
					sumY = (double) pot.getSpace().typeAxisProj((int) sumY, Space2D.Y);
//					System.out.println("SumX : " + sumX + " " + "SumY : " + sumY );
					return new Coord2D<Double>(sumX,sumY);
				}else{
					return new Coord2D<Double>((double) StatisticsCNFT.ERROR,(double) StatisticsCNFT.ERROR);
				}
			}
			
		};
		
		return coorBubble;
		
	}
	
//	private List<StatMapCNFT> getCoorBubble2(Parameter p,StatMapCNFT wsum) {
//		List<StatMapCNFT> ret = new ArrayList<StatMapCNFT>(2);
//		//TODO Warning : if the focus bubble is wrapped it does not work! 
//		StatMapCNFT<Double> centerX = new StatMapCNFT(StatisticsCNFT.CENTER_X,dt,0d,tracks,p,wsum) {
//
//			@Override
//			public Double computeStatistic(BigDecimal time, int index, List params) {
//				Map pot = (Map) this.getParam(0);
//				int wsum = ((Double) ((SingleValueParam)this.getParam(1)).get()).intValue();
//				if(wsum != 0){
//					double sumX = 0;
//					int dx = pot.getSpace().getIndex(Space2D.X);
//					int dy = pot.getSpace().getIndex(Space2D.Y);
//					for(int i = 0 ; i< dx ; i++)
//					{
//						for(int j = 0 ; j < dy ; j++)
//						{
//							sumX += (Double)pot.getIndex(i + j * dx) * i;
//						}
//					}
//					System.out.println(" sumX2 " + sumX);
//					// Normalize the center main.java.coordinates (div by wsum)
//					sumX = sumX/(Double)((StatMapCNFT) this.getParam(1)).get();
//					//project to have the good continuous main.java.coordinates
//					sumX = (double) pot.getSpace().typeAxisProj((int) sumX, Space2D.X);
//					return sumX;
//				}else{
//					return (double) StatisticsCNFT.ERROR;
//				}
//
//			}
//		};
//
//		StatMapCNFT<Double> centerY = new StatMapCNFT(StatisticsCNFT.CENTER_Y,dt,0d,tracks,p,wsum) {
//
//			@Override
//			public Double computeStatistic(BigDecimal time, int index, List params) {
//				Map pot =   (Map) this.getParam(0);
//				int wsum = ((Double) ((SingleValueParam)this.getParam(1)).get()).intValue();
//				if(wsum != 0){
//					double sumY = 0;
//					int dx = pot.getSpace().getIndex(Space2D.X);
//					int dy = pot.getSpace().getIndex(Space2D.Y);
//					for(int i = 0 ; i< dx ; i++)
//					{
//						for(int j = 0 ; j < dy ; j++)
//						{
//							sumY += (Double)pot.getIndex(i + j * dx) * j;
//						}
//					}
//					System.out.println(" sumY2 " + sumY);
//					// Normalize the center main.java.coordinates
//					sumY = sumY/(Double)((StatMapCNFT) this.getParam(1)).get();
//					sumY = (double) pot.getSpace().typeAxisProj((int) sumY, Space2D.Y);
//					return sumY;
//				}else{
//					return (double) StatisticsCNFT.ERROR;
//				}
//			}
//		};
//
//		ret.add(centerX);
//		ret.add(centerY);
//
//		return ret;
//	}
//	

	/**
	 * Return the function with the sum  of activity of the target memory
	 * @param leaf
	 * @return
	 */
	public StatMapCNFT getWsum(Parameter p) {
		StatMapCNFT<Double> wsum = new StatMapCNFT(StatisticsCNFT.ACT_SUM,dt,0d,tracks,p){

			@Override
			public Double computeStatistic(BigDecimal time, int index, List params) {
				Map map =   (Map) this.getParam(0);
				int dx = map.getSpace().getIndex(Space2D.X);
				int dy = map.getSpace().getIndex(Space2D.Y);
				double sum = 0;
				for(int i = 0 ; i< dx ; i++)
				{
					for(int j = 0 ; j < dy ; j++)
					{
						sum += ((Number)map.getIndex(i + j * dx)).doubleValue() ;
					}
				}
				return sum;
			}
		};
		return wsum;
	}

//	/**
//	 * Return the mean square error :
//	 * 	Dist between neuron 
//	 * @param leaf
//	 * @return
//	 */
//	public StatMapCNFT getMeanSquareSOM(Parameter p) {
//
//		Map map = (Map) p;
//
//		StatMapCNFT wsum = new StatMapCNFT(StatisticsCNFT.MSE_SOM,dt,noDimSpace,tracks,map){
//
//			@Override
//			public Double computeStatistic(BigDecimal time, int index, List params) {
//				NeighborhoodMap map =   (NeighborhoodMap) this.getParam(0);
//				Space spaceMap = map.getSpace();
//				Neighborhood neigh = (Neighborhood) map.getNeighborhoods().get(0);
//
//				double add = 0;
//				double nb = 0;
//
//				double scale = 1.0;
//
//				for(int i = 0 ; i  < spaceMap.getFramedSpace().getDiscreteVolume() ; i++){
//					int fullIndex = FramedSpaceIterator.framedToFullIndex(i, spaceMap);
//
//
//					double[] currentC = ((SomUM) map.getUnit(fullIndex).getUnitModel()).getWeights();
//					int[] neighboors = neigh.getNeighborhood(fullIndex);
//
//
//					for(int n : neighboors){
//
//						double[] neighC = ((SomUM) map.getUnit(n).getUnitModel()).getWeights();
//						double distance = Math.sqrt(Math.pow(currentC[Space.X] - neighC[Space.X], 2) + Math.pow(currentC[Space.Y] - neighC[Space.Y], 2))/scale;
//						add += distance;
//						nb ++;
//					}
//				}
//
//				//System.out.println("add " + add + " nb " + nb);
//				return add / nb;
//
//			}
//		};
//
//		return wsum;
//
//	}



}
