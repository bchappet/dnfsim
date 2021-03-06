package modelCMSVA;

import console.CNFTCommandLine;
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
import statistics.StatMapCNFT;
import statistics.StatisticsCNFT;

public class FEFStatMap extends StatMapCNFT {

    int test = 0;
    protected double distanceMaximum;
    protected int nbBubble = 0;
    /**
     * Error distance for each bubble*
     */
    protected List</* StatMap */ErrorDistance> bubbleError;
    protected static int MAP = 0;
    private List<Bubble> bubbles;
    private List<Integer> idToRemove;
    private List<Integer> idToAdd;
    private int bubbleIds;
    private static int X = 0;
    private static int Y = 1;
    protected StatisticsCNFT stats;

    public FEFStatMap(String theName, Var dt, Space space, StatisticsCNFT stats,
            List<AbstractMap> tracks, Parameter... maps) {
        super(theName, dt, space, tracks, maps);
        this.distanceMaximum = 1;
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
//        System.out.println("id to add : "+idToAdd);
//        System.out.println("id to remove : "+idToRemove);
        for (Integer id : idToAdd) {

            StatMapCNFT x = new BubbleMap(id, "X", "X_StatMap", dt, space, tracks, params.get(MAP));
            StatMapCNFT y = new BubbleMap(id, "Y", "Y_StatMap", dt, space, tracks, params.get(MAP));

            ErrorDistance error = new ErrorDistance("error_(" + id + ")", dt, space, tracks, x, y);
            stats.addStatisticMap(error);
            bubbleError.add(error);
        }
        List<ErrorDistance> errorToRemove = new ArrayList<ErrorDistance>();
        for (ErrorDistance error : bubbleError) {
            if (idToRemove.contains(((BubbleMap) error.getParam(X)).bubbleId)) {
                errorToRemove.add(error);
            }
        }
        for (ErrorDistance error : errorToRemove) {
            stats.removeStatisticMap(error);
            bubbleError.remove(error);
        }
        return nbBubble;
    }

    /**
     * attribut de nouvelles valeurs aux bubbles déjà présentes. Met à jour le
     * nombre de bulle total et le nombre de bulle à ajouter/supprimer pour la
     * suite
     *
     * @param newCentres
     */
    public void reoganizeBubbles(List<PointContinu> newCentres) {
//        System.out.println("----------------- début reoganize ---------------------------------");
//        System.out.println("newCentres : " + newCentres);
        // les bulles à enlever de l'ensemble de bulles courantes
        ArrayList<Bubble> bubbleToRemove = new ArrayList<Bubble>();
        idToRemove = new ArrayList<Integer>();
        idToAdd = new ArrayList<Integer>();
//        System.out.println("bubbles avant: " + bubbles);
        if (bubbles.size() < newCentres.size()) {
//            System.out.println("flag A");
            ArrayList<PointContinu> centerUsed = new ArrayList<PointContinu>(bubbles.size());
            // on redifinit les centre des bulles déjà présentes
            for (Bubble bubble : bubbles) {
                PointContinu center = centerClosestToBubble(bubble, newCentres);
                if (center != null) {
                    bubble.p = center;
                    centerUsed.add(center);
                } else {
                    bubbleToRemove.add(bubble);
                    idToRemove.add(bubble.id);
                }
            }
            // les nouveaux centres qui ne correspondent à aucun anciens centre sont rajoutés
            for (PointContinu center : newCentres) {
                if (!centerUsed.contains(center)) {
                    bubbleIds++;
                    bubbles.add(new Bubble(center, bubbleIds));
                    idToAdd.add(bubbleIds);
                }
            }
        } else if (bubbles.size() > newCentres.size()) {
            // on a perdu des bulles
//            System.out.println("flag B");

            // les ids des bulles dont on a déjà vérifié la correspondance avec un des centres
            ArrayList<Integer> idModified = new ArrayList<Integer>(newCentres.size());
            // ensemble des centres rajouté
            ArrayList<PointContinu> centerUsed = new ArrayList<PointContinu>();
            // on redifinit les centre des bulles déjà présentes
            for (PointContinu centre : newCentres) {
                Bubble bubble = bubbleClosestToCenter(centre);
                if (bubble != null) {
                    bubble.p = centre;
                    idModified.add(bubble.id);
                } else {
                    // ça veut dire qu'on a une nouvelle bulle
                    bubbleIds++;
                    bubbles.add(new Bubble(centre, bubbleIds));
                    idToAdd.add(bubbleIds);
                    idModified.add(bubbleIds);
                }
            }

            for (Bubble bubble : bubbles) {
                if (!idModified.contains(bubble.id)) {
                    bubbleToRemove.add(bubble);
                    idToRemove.add(bubble.id);
                }
            }
        } else {
            // si il y a autant de bulle que de centres
//            System.out.println("flag c");
            // on redifinit les centre des bulles déjà présentes
            for (Bubble bubble : bubbles) {
                PointContinu centre = centerClosestToBubble(bubble, newCentres);
                if (centre != null) {
                    bubble.p = centre;
                } else {
                    idToRemove.add(bubble.id);
                }
            }
        }
        for (Bubble bubble : bubbleToRemove) {
            bubbles.remove(bubble);
        }
//        System.out.println("bubbles après : " + bubbles);
//        System.out.println("------------------- fin reorganize ----------------------------------");
    }

    /**
     * @param centre
     * @return la bulle dont le point est le plus proche du centre par rapport
     * au autre bulle de bubbles return null si il n'existe pas de bulle dans un
     * périmètre bien défini
     */
    private Bubble bubbleClosestToCenter(PointContinu centre) {
        Bubble closestBubble = null;
        double minimum = 9999.0;
        for (Bubble bubble : bubbles) {
            double temp = (Math.abs(bubble.p.x - centre.x) + Math.abs(bubble.p.y - centre.y));
            if (temp < minimum) {
                closestBubble = bubble;
                minimum = temp;
            }
        }
        if (minimum > distanceMaximum) {
//            System.out.println("Ce centre est tout nouveau : "+centre);
            closestBubble = null;
        }
        return closestBubble;
    }

    /**
     * @param bubble
     * @param newCentres
     * @return le centre le plus proche du point de bubble par rapport au autres
     * points de newCentres.
     */
    private PointContinu centerClosestToBubble(Bubble bubble, List<PointContinu> newCentres) {
        PointContinu closestCenter = null;
        double minimum = 9999.0;
        for (PointContinu centre : newCentres) {
            double temp = (Math.abs(bubble.p.x - centre.x) + Math.abs(bubble.p.y - centre.y));
            if (temp < minimum) {
                closestCenter = centre;
                minimum = temp;
            }
        }
        if (minimum > distanceMaximum) {
//            System.out.println("Cette bulle n'existe plus : "+bubble);
            closestCenter = null;
        }
        return closestCenter;
    }

    class Bubble {

        int id;
        PointContinu p;

        public Bubble(PointContinu p, int id) {
            this.p = p;
            this.id = id;
        }

        @Override
        public String toString() {
            return "{ p : " + p + ", id : " + id + "}";
        }
    }

    class BubbleMap extends StatMapCNFT {

        private String axe;
        private int bubbleId;

        public BubbleMap(int bubbleId, String axe, String theName, Parameter dt, Space space, List<AbstractMap> tracks, Parameter... maps) {
            super(theName, dt, space, tracks, maps);
            this.axe = axe;
            this.bubbleId = bubbleId;
        }

        @Override
        public double computeStatistic() {
            double value = 0;
            PointContinu centre = findBubble();
            if (axe.equals("X")) {
                value = centre.y;
                value = space.continuousProj(value, Space.X);
            } else if (axe.equals("Y")) {
                value = centre.x;
                value = space.continuousProj(value, Space.Y);

            }
            return value;
        }

        private PointContinu findBubble() {
            PointContinu p = null;
            boolean stop = false;
            int i = 0;
            Bubble bubble;
//                System.out.println("bubbles : "+bubbles);
            while (!stop && i < bubbles.size()) {
                bubble = bubbles.get(i);
//                    System.out.println("bubble.id : "+bubble.id);
//                    System.out.println("bubbleId cherché : "+bubbleId);
                if (bubble.id == bubbleId) {
                    stop = true;
                    p = bubble.p;
                }
                i++;
            }
//                if(p==null){
//                    test++;
//                    p=new PointContinu();
//                    System.out.println("p est null !! : "+test);
//                }
            return p;

        }
    }

    /**
     * Compute the error distance of a bubble (its cenrer X and Y are given)
     * with the closest track of the input
     *
     * @author bchappet
     *
     */
    protected class ErrorDistance extends StatMapCNFT {

        public ErrorDistance(String theName, Parameter dt, Space space, List<AbstractMap> tracks,
                Parameter centerX, Parameter centerY) {
            super(theName, dt, space, tracks, centerX, centerY);
        }

        @Override
        public double computeStatistic() {
            double centerX = this.getParam(0).get();
            double centerY = this.getParam(1).get();
            double ret = StatisticsCNFT.ERROR;

            if (centerX != StatisticsCNFT.ERROR && centerY != StatisticsCNFT.ERROR) {

                Double[] center = {centerX, centerY};
                ret = getClosestTrack(tracks, center);
            }

            return ret;
        }

        /**
         * Return the distance from the closest track
         *
         * @param tracks
         * @param center
         * @return
         */
        protected double getClosestTrack(List<AbstractMap> tracks, Double[] center) {
            double minD = Double.MAX_VALUE;
            for (AbstractMap tck : tracks) {
                double d = Space.distance(
                        ((Track) ((AbstractUnitMap) tck).getUnitModel()).getCenter(),
                        center);
                if (d < minD) {
                    minD = d;
                }
            }
            return minD;
        }
    }
}
