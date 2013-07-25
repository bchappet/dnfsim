package modelCMSVA;

import tool.PointContinu;
import tool.ToolsMAP;
import java.util.List;

import maps.AbstractMap;
import maps.AbstractUnitMap;
import maps.Parameter;
import maps.Track;
import maps.Var;
import coordinates.Space;
import java.util.ArrayList;
import statistics.StatMap;
import statistics.Statistics;

public class FEFStatMap extends StatMap {

	protected int nbBubble = 0;
	/**Error distance for each bubble**/
	protected List</* StatMap */ErrorDistance> bubbleError;
        protected static int MAP = 0;
        private List<Bubble> bubbles ;
        private List<Integer> idToRemove;
        private List<Integer> idToAdd;
        private int bubbleIds;
        private static int X = 0;
        private static int Y = 1;

	protected Statistics stats;

	public FEFStatMap(String theName, Var dt, Space space,Statistics stats,
			List<AbstractMap> tracks, Parameter... maps) {
		super(theName, dt, space,tracks, maps);
		this.stats = stats;
                bubbles = new ArrayList<Bubble>();
                bubbleIds = 0;
                idToRemove = new ArrayList<Integer>();
                idToAdd = new ArrayList<Integer>();
                bubbleError = new ArrayList<ErrorDistance>();
	}

	@Override
	public double computeStatistic() {
                List<PointContinu> centres = ToolsMAP.getCenterBubbles(params.get(MAP), ToolsMAP.DIFFUSION_WHILE_SEMI_OPTIMISEE/* ToolsMAP.DIFFUSION_RECURRENCE_NON_TERMINALE */);
                reoganizeBubbles(centres);
//                System.out.println("Centres : "+centres);
//                System.out.println("bubbles : "+ bubbles);
                if(nbBubble < centres.size()){
                    nbBubble = centres.size();
                    for(Integer id:idToAdd){
                        
                        StatMap x = new BubbleMap(id, "X","X_StatMap", dt, space, tracks, params.get(MAP));
                        StatMap y = new BubbleMap(id, "Y","Y_StatMap", dt, space, tracks, params.get(MAP));

                        ErrorDistance error = new ErrorDistance("error_("+id+")", dt, space, tracks,x, y);
                        stats.addStatisticMap(error);
                        bubbleError.add(error);
                    }
                }else if(nbBubble > centres.size()){
                    nbBubble = centres.size();
                    List<ErrorDistance> errorToRemove = new ArrayList<ErrorDistance>();
                    for(ErrorDistance error: bubbleError){
                       if(idToRemove.contains(((BubbleMap)error.getParam(X)).bubbleId)){
                           errorToRemove.add(error);
                       }
                    }
                    for(ErrorDistance error : errorToRemove){
                        stats.removeStatisticMap(error);
                        bubbleError.remove(error);
                    }
                }else{
                    // nothing to do
                }
                //System.out.println("error map : "+this.bubbleError);
		return nbBubble;
	}
        
        
        /**
         * attribut de nouvelles valeurs aux bubbles déjà présentes. Met à jour le nombre de bulle total et le nombre de bulle à
         * ajouter/supprimer pour la suite
         * @param newCentres 
         */
        public void reoganizeBubbles(List<PointContinu> newCentres){
            idToRemove = new ArrayList<Integer>();
            idToAdd = new ArrayList<Integer>();
            if(this.bubbles.size()<newCentres.size()){
                ArrayList<PointContinu> centerUsed= new ArrayList<PointContinu>(bubbles.size());
                for(Bubble bubble: bubbles){
                    PointContinu center = centerClosestToBubble(bubble, newCentres);
                    bubble.p = center;
                    centerUsed.add(center);
                }
                
                for(PointContinu center : newCentres){
                    if(!centerUsed.contains(center)){
                        bubbleIds++;
                        bubbles.add(new Bubble(center,bubbleIds));
                        idToAdd.add(bubbleIds);
                    }
                }
            }else if(this.bubbles.size()>newCentres.size()){
                ArrayList<Bubble> bubbleToRemove = new ArrayList<Bubble>(bubbles.size()-newCentres.size());
                ArrayList<Integer> idModified = new ArrayList<Integer>(newCentres.size());
                for(PointContinu centre: newCentres){
                    Bubble bubble = bubbleClosestToCenter(centre);
                    bubble.p = centre;
                    idModified.add(bubble.id);
                }
                
                for(Bubble bubble:bubbles){
                    if(!idModified.contains(bubble.id)){
                        bubbleToRemove.add(bubble);
                        idToRemove.add(bubble.id);
                    }
                }
                
                for(Bubble bubble : bubbleToRemove){
                    bubbles.remove(bubble);
                }
            }else{ // si il y a autant de bulle que de centres
                for(Bubble bubble: bubbles){
                    PointContinu centre = centerClosestToBubble(bubble, newCentres);
                    bubble.p = centre;
                }
            }
        }
        
      
       /**
        * 
        * @param centre
        * @return la bulle dont le point est le plus proche du centre par rapport au autre bulle de bubbles
        */
       private Bubble bubbleClosestToCenter(PointContinu centre){
            Bubble closestBubble = null;
            double minimum = 9999.0;
            for(Bubble bubble:bubbles){
                double temp =  (Math.abs(bubble.p.x-centre.x)+Math.abs(bubble.p.y-centre.y));
                if(temp< minimum){
                    closestBubble = bubble;
                    minimum = temp;
                }
            }
            return closestBubble;
        }
        
       /**
        * 
        * @param bubble
        * @param newCentres
        * @return le centre le plus proche du point de bubble par rapport au autres points de newCentres.
        */
        private PointContinu centerClosestToBubble(Bubble bubble, List<PointContinu> newCentres){
            PointContinu closestCenter = null;
            double minimum = 9999.0;
            for(PointContinu centre :  newCentres){
                double temp =  (Math.abs(bubble.p.x-centre.x)+Math.abs(bubble.p.y-centre.y));
                if(temp< minimum){
                    closestCenter = centre;
                    minimum = temp;
                }
            }
            return closestCenter;
        }
        
        class Bubble{
            int id;
            PointContinu p;
            public Bubble(PointContinu p, int id){
                this.p = p;
                this.id = id;
            }
            @Override
            public String toString(){
                return "{ p : "+p+", id : "+id+"}";
            }
        }
        
        class BubbleMap extends StatMap{
            private String axe;
            private int bubbleId; 
            
            public BubbleMap(int bubbleId, String axe, String theName,Parameter dt, Space space,List<AbstractMap> tracks, Parameter... maps){
                super(theName, dt, space, tracks, maps);
                this.axe = axe;
                this.bubbleId = bubbleId;
            }
            
            @Override
            public double computeStatistic()  {
                double value = 0;
                PointContinu centre = findBubble();
                if(axe.equals("X")){
                        value = centre.y;
                        value = space.continuousProj(value, Space.X);
                } else if (axe.equals("Y")){
                        value = centre.x;
                        value = space.continuousProj(value, Space.Y);
                   
                }
                return value;
            }
            
            private PointContinu findBubble(){
                PointContinu p = null ;
                boolean stop = false;
                int i=0;
                Bubble bubble;
                while(!stop && i<bubbles.size()){
                    bubble = bubbles.get(i);
                    if(bubble.id==bubbleId){
                        stop = true;
                        p = bubble.p;
                    }
                    i++;
                }
                return p;
                        
            }
            
        }

	/**
	 * Compute the error distance of a bubble (its cenrer X and Y are given) 
	 * with the closest track of the input
	 * @author bchappet
	 *
	 */
	protected class ErrorDistance extends StatMap
	{
		
		public ErrorDistance(String theName, Parameter dt, Space space,List<AbstractMap> tracks,
				Parameter centerX,Parameter centerY) {
			super(theName, dt, space, tracks,centerX,centerY);
		}

		@Override
		public double computeStatistic()  {
			double centerX = this.getParam(0).get();
			double centerY =  this.getParam(1).get();
			double ret = Statistics.ERROR;

			if(centerX !=  Statistics.ERROR && centerY != Statistics.ERROR)
			{

				Double[] center = {centerX,centerY};
				ret = getClosestTrack(tracks,center);
			}

			return ret;
		}
		
		/**
		 * Return the distance from the closest track
		 * @param tracks
		 * @param center
		 * @return
		 */
		protected double getClosestTrack(List<AbstractMap> tracks,Double[] center)
		{
			double minD = Double.MAX_VALUE;
			for(AbstractMap tck : tracks)
			{
				double d = Space.distance(
						((Track) ((AbstractUnitMap)tck).getUnitModel()).getCenter(),
						center);
				if(d < minD)
					minD = d;
			}
			return minD;
		}


	}
}

