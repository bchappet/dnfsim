/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelCMSVA;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import maps.Map;
import maps.Parameter;

/**
 *  
 * @author ncarrara
 */
public class ToolCMSVA {
    public static final int LIG = 0;
    public static final int COL = 1;
    public static final int PARCOURUE_VIDE = 3;
    public static final int PARCOURUE_POTENTIEL = 2;
    public static final int VIDE = 0;
    
    private static int resolution;
    
    public static List<Point> getBubbles(Parameter map){
        double[][] tab = getTab(map);
        List<Point> bubbles = new ArrayList<Point>();
        /* pour chaque points du tableau si potentiel>0 
         * alors on cherche tout les potentiels adjacents >0 pour trouver la bulle */
        resolution = tab.length;
        double potentiel = -9999;
        for(int l=0;l<tab.length;l++){
            for(int c=0;c<tab.length;c++){
                potentiel = tab[l][c];
                /* si le point n'a pas encore été parcouru */
                if(potentiel != 2 && potentiel != 3){
                    if(potentiel == 0){
                         //on indique qu'on vient de parcourir cette case qui était vide à la base
                        tab[l][c] = 3;
                    }else{
                        //potentiel > 0
                        //System.out.println("---------------------- NOUVELLE BOULE -------------------------");
                        bubbles.add(getBubble(l, c, tab));
                    }
                   
                    
                }
            }
        }
        
        return bubbles;
    }
    
    public static List<Point> getBubbles(double[][] tab){
        List<Point> bubbles = new ArrayList<Point>();
        /* pour chaque points du tableau si potentiel>0 
         * alors on cherche tout les potentiels adjacents >0 pour trouver la bulle */
        resolution = tab.length;
        double potentiel = -9999;
        for(int l=0;l<tab.length;l++){
            for(int c=0;c<tab.length;c++){
                potentiel = tab[l][c];
                /* si le point n'a pas encore été parcouru */
                if(potentiel != 2 && potentiel != 3){
                    if(potentiel == 0){
                         //on indique qu'on vient de parcourir cette case qui était vide à la base
                        tab[l][c] = 3;
                    }else{
                        //potentiel > 0
                        //System.out.println("---------------------- NOUVELLE BOULE -------------------------");
                        bubbles.add(getBubble(l, c, tab));
                    }
                }
            }
        }
        
        return bubbles;
    }
    
    /**
     * parcours de manière itérative le tableau tant qu'il rencontre des potentiels adjacents > 0
     * positionne à 2 les cases parcourues
     * Considérez position absolue comme "position absolue par rapport au point p
     * @param ligne du point de départ de potentiel >0
     * @param colonne du point de départ de potentiel >0
     * @param tableau de la map
     * @return le centre de tout les potentiels adjacents
     */
    private static Point getBubble(int ligne, int colonne, double[][] tableau){
        Point centre = new Point();
        Point p = new Point(ligne, colonne); 
        int[] positionAbsolue = new int[2]
                ,positionAbsolueNord= new int[2]
                , positionAbsolueSud= new int[2]
                , positionAbsolueEst= new int[2]
                , positionAbsolueOuest = new int[2] 
                , positionAbsolueCentre = new int[2] ;
        
        g(p, positionAbsolueNord, positionAbsolueSud, positionAbsolueEst, positionAbsolueOuest, tableau, positionAbsolue);
        //afficherTableau(tableau);
        //System.out.println("origine : "+p);
        //System.out.println("nord : ["+positionAbsolueNord[LIG]+"]["+positionAbsolueNord[COL]+"]");
        //System.out.println("sud : ["+positionAbsolueSud[LIG]+"]["+positionAbsolueSud[COL]+"]");
        //System.out.println("est : ["+positionAbsolueEst[LIG]+"]["+positionAbsolueEst[COL]+"]");
        //System.out.println("ouest : ["+positionAbsolueOuest[LIG]+"]["+positionAbsolueOuest[COL]+"]");

        positionAbsolueCentre[LIG]= (positionAbsolueEst[LIG]+positionAbsolueOuest[LIG])/2;
        positionAbsolueCentre[COL]= (positionAbsolueNord[COL]+positionAbsolueSud[COL])/2;
        
        //System.out.println("positionAbsolueCentre[LIG] : "+positionAbsolueCentre[LIG]);
        //System.out.println("positionAbsolueCentre[COL] : "+positionAbsolueCentre[COL]);
        centre.x = ligne;
        centre.y = colonne;
        if(positionAbsolueCentre[LIG]>0){
            for(int i = 0 ;i<positionAbsolueCentre[LIG];i++){
                centre.x=incremente(centre.x);
            }
        }
        
        if(positionAbsolueCentre[LIG]<0){
            for(int i = 0 ;i<-positionAbsolueCentre[LIG];i++){
                centre.x=decremente(centre.x);
            }
        }
        
        if(positionAbsolueCentre[COL]>0){
            for(int i = 0 ;i<positionAbsolueCentre[COL];i++){
                centre.y=incremente(centre.y);
            }
        }
        
        if(positionAbsolueCentre[COL]<0){
            for(int i = 0 ;i<-positionAbsolueCentre[COL];i++){
                centre.y=decremente(centre.y);
            }
        }
        
        
        return centre;
    }
    
   
    
    /**
     * l'idée c'est d'apeller g sur chaque case adjacent de p. Si on est sur une bordure alors on a 
     * potentiellement un sud et/ou un est et/ou un ouest
     * la récurrence s'arrête si on ne peux plus parcourir les points adjacents ie : =2 ou =0
     * 
     * configuration des points adjacents : 
     * -------
     * |A B COL|
     * |H p D|
     * |G F E|
     * -------
     * 
     * @param p
     * @param sud
     * @param est
     * @param ouest
     * @param tableau 
     */
    private static void g(
            Point p, 
            int[] positionAbsolueNord,
            int[] positionAbsolueSud,
            int[] positionAbsolueEst,
            int[] positionAbsolueOuest,
            double[][] tableau,
            int[] positionAbsolue)
    {
        //System.out.println("------------------- g ------------------------------");
        //System.out.println("p : "+p);
        //afficherTableau(tableau);
        tableau[p.x][p.y] = PARCOURUE_POTENTIEL;
        //afficherTableau(tableau);
        Point A,B,C,D,E,F,G,H; // les 8 cases adjacentes à p
        double pA,pB,pC,pD,pE,pF,pG,pH;
        A = coordonnee("A",p); pA = tableau[A.x][A.y];
        B = coordonnee("B",p); pB = tableau[B.x][B.y];
        C = coordonnee("C",p); pC = tableau[C.x][C.y];
        D = coordonnee("D",p); pD = tableau[D.x][D.y];
        E = coordonnee("E",p); pE = tableau[E.x][E.y];
        F = coordonnee("F",p); pF = tableau[F.x][F.y];
        G = coordonnee("G",p); pG = tableau[G.x][G.y];
        H = coordonnee("H",p); pH = tableau[H.x][H.y];
        //System.out.println("p : "+p);
        //System.out.println("A : "+A+" B : "+B+" C : "+C+" D : "+D+" E : "+E+" F : "+F+" G : "+G+" H : "+H );
        //System.out.println("pA : "+pA+" pB : "+pB+" pC : "+pC+" pD : "+pD+" pE : "+pE+" pF : "+pF+" pG : "+pG+" pH : "+pH );

        /* si on est sur une des limites Nord */
        if((pA== VIDE || pA==PARCOURUE_VIDE) && (pB== VIDE || pB==PARCOURUE_VIDE) && (pC== VIDE || pC==PARCOURUE_VIDE)){
            //System.out.println("FLAG NORD potentiel");
            if(positionAbsolueNord[LIG]>positionAbsolue[LIG]){
                //System.out.println("FLAG NORD changé");
                //System.out.println("ancien nord : ("+positionAbsolueNord[LIG]+","+positionAbsolueNord[COL]+")");
                System.arraycopy(positionAbsolue, 0, positionAbsolueNord, 0, 2);
                //System.out.println("nouveau nord : ("+positionAbsolueNord[LIG]+","+positionAbsolueNord[COL]+")");
            }
        }
        /* si on est sur une des limites Sud */
        if((pG== VIDE || pG==PARCOURUE_VIDE) && (pF== VIDE || pF==PARCOURUE_VIDE) && (pE== VIDE || pE==PARCOURUE_VIDE)){
            //System.out.println("FLAG SUD potentiel");
             if(positionAbsolueSud[LIG]<positionAbsolue[LIG]){
                 //System.out.println("FLAG SUD changé");
                 //System.out.println("ancien sud : ("+positionAbsolueSud[LIG]+","+positionAbsolueSud[COL]+")");
                 System.arraycopy(positionAbsolue, 0, positionAbsolueSud, 0, 2);
                 //System.out.println("nouveau sud : ("+positionAbsolueSud[LIG]+","+positionAbsolueSud[COL]+")");
            }
        }
        /* si on est sur une des limites Est */
        if((pC== VIDE || pC==PARCOURUE_VIDE) && (pD== VIDE || pD==PARCOURUE_VIDE) && (pE== VIDE || pE==PARCOURUE_VIDE)){
            //System.out.println("FLAG EST potentiel");
             if(positionAbsolueEst[COL]<positionAbsolue[COL]){
                 //System.out.println("FLAG EST changé");
                 //System.out.println("ancien est : ("+positionAbsolueEst[LIG]+","+positionAbsolueEst[COL]+")");
                 System.arraycopy(positionAbsolue, 0, positionAbsolueEst, 0, 2);
                 //System.out.println("nouvel est : ("+positionAbsolueEst[LIG]+","+positionAbsolueEst[COL]+")");
            }
        }
        /* si on est sur une des limites Ouest */
        if((pA== VIDE || pA==PARCOURUE_VIDE) && (pH== VIDE || pH==PARCOURUE_VIDE) && (pG== VIDE || pG==PARCOURUE_VIDE)){
            //System.out.println("FLAG OUEST potentiel");
             if(positionAbsolueOuest[COL]>positionAbsolue[COL]){
                //System.out.println("FLAG OUEST changé");
                //System.out.println("ancien ouest : ("+positionAbsolueOuest[LIG]+","+positionAbsolueOuest[COL]+")");
                System.arraycopy(positionAbsolue, 0, positionAbsolueOuest, 0, 2);
                //System.out.println("nouveau ouest : ("+positionAbsolueOuest[LIG]+","+positionAbsolueOuest[COL]+")");
            }
        }
        
        if(pA>0 && pA<=1){
            positionAbsolue[LIG]--;
            positionAbsolue[COL]--;
            g(A, positionAbsolueNord, positionAbsolueSud, positionAbsolueEst, positionAbsolueOuest, tableau, positionAbsolue);
        }
        pB = tableau[B.x][B.y];
        if(pB>0 && pB<=1){
            positionAbsolue[LIG]--;
            g(B, positionAbsolueNord, positionAbsolueSud, positionAbsolueEst, positionAbsolueOuest, tableau, positionAbsolue);
        }
        pC = tableau[C.x][C.y];
        if(pC>0 && pC<=1){
            positionAbsolue[LIG]--;
            positionAbsolue[COL]++;
            g(C, positionAbsolueNord, positionAbsolueSud, positionAbsolueEst, positionAbsolueOuest, tableau, positionAbsolue);
        }
        pD = tableau[D.x][D.y];
        if(pD>0 && pD<=1){
            positionAbsolue[COL]++;
            g(D, positionAbsolueNord, positionAbsolueSud, positionAbsolueEst, positionAbsolueOuest, tableau, positionAbsolue);
        }
        pE = tableau[E.x][E.y];
        if(pE>0 && pE<=1){
            positionAbsolue[COL]++;
            positionAbsolue[LIG]++;
            g(E, positionAbsolueNord, positionAbsolueSud, positionAbsolueEst, positionAbsolueOuest, tableau, positionAbsolue);
        }
        pF = tableau[F.x][F.y];
        if(pF>0 && pF<=1){
            positionAbsolue[LIG]++;
            g(F, positionAbsolueNord, positionAbsolueSud, positionAbsolueEst, positionAbsolueOuest, tableau, positionAbsolue);
        }
        pG = tableau[G.x][G.y];
        if(pG>0 && pG<=1){
            positionAbsolue[LIG]++;
            positionAbsolue[COL]--;
            g(G, positionAbsolueNord, positionAbsolueSud, positionAbsolueEst, positionAbsolueOuest, tableau, positionAbsolue);
        }
        pH = tableau[H.x][H.y];
        if(pH>0 && pH<=1){
            positionAbsolue[COL]--;
            g(H, positionAbsolueNord, positionAbsolueSud, positionAbsolueEst, positionAbsolueOuest, tableau, positionAbsolue);
        }
        //System.out.println("--------------------------------------------------");
    }
    
    /* VERIFICATION : ## */
    /**
     * renvoi les coordonnee d'un point adjacent à p
     * @param pointAdjacent
     * @param p
     * @return 
     */
    private static Point coordonnee(String pointAdjacent, Point p){
        Point adjacent = new Point();
        
            // on donne A B COL et cie en fonction de la position de p (si sur le bord ou non)
          if(pointAdjacent.equals("A")){
                adjacent.x= decremente(p.x);
                adjacent.y= decremente(p.y);
          }else if(pointAdjacent.equals("B")){
                adjacent.y= p.y;
                adjacent.x= decremente(p.x);
          }else if(pointAdjacent.equals("C")){
                adjacent.y = incremente(p.y);
                adjacent.x = decremente(p.x);
          }else if(pointAdjacent.equals("D")){
                adjacent.y = incremente(p.y);
                adjacent.x = p.x;
          }else if(pointAdjacent.equals("E")){
                adjacent.y = incremente(p.y);
                adjacent.x = incremente(p.x);
          }else if(pointAdjacent.equals("F")){
                adjacent.y = p.y;
                adjacent.x = incremente(p.x);
          }else if(pointAdjacent.equals("G")){
                adjacent.y = decremente(p.y);
                adjacent.x = incremente(p.x);
          }else if(pointAdjacent.equals("H")){
                adjacent.y = decremente(p.y);
                adjacent.x = p.x;
          }
               
        return adjacent;
    }
    
    /* VERIFICATION : ## */
    /**
     * incrémente la valeur de la projection, si on est sur le bord droit, alors la projection passe à zéro (à cause du wrapping)
     * @param projection
     * @return 
     */
    private static int incremente(int projection){
        int newprojection = projection+1;
        if(projection==resolution-1)
            newprojection = 0;
        return newprojection;
    }
    
    /* VERIFICATION : ## */
    /**
     * décrémente la valeur de la projection, si on est sur le bord gauche , alors la projection passe à résolution-1 (à cause du wrapping)
     * @param projection
     * @return 
     */
    private static int decremente(int projection){
        int newprojection = projection-1;
        if(projection==0)
            newprojection = resolution-1;
        return newprojection;
    }
   
    /* VERIFICATION : Fonctionnel */
    private static double[][] getTab(Parameter map){
        double[] tab1D = map.getValues();
        int resolution = (int) map.getSpace().getResolution();
        //System.out.println("Resolution(int) : "+resolution);
        double[][] tab2D = new double[resolution][resolution];
        int x=0;
        int y=0;
        for(int i=0;i<tab1D.length;i++){
            y = i % resolution;
            x = (i - y) /resolution;
            tab2D[x][y]=tab1D[i];
        }
        int z = 0 ;
        for(int k = 0;k<resolution;k++){
            for(int j =0;j<resolution;j++){
                z++;
                //System.out.println("tab2D["+k+"]["+j+"] : "+tab2D[k][j]);
                //System.out.println("tab1D["+z+"] : "+tab1D[z]);
            }
        }
        return tab2D;
    }
    
    /* VERIFICATION : Fonctionnel */
    private static double[][] getTab(double[] tab){
        double[] tab1D = tab;
        int resolution = (int) Math.sqrt(tab.length);
        //System.out.println("Resolution(int) : "+resolution);
        double[][] tab2D = new double[resolution][resolution];
        int x=0;
        int y=0;
        for(int i=0;i<tab1D.length;i++){
            y = i % resolution;
            x = (i - y) /resolution;
            tab2D[x][y]=tab1D[i];
        }
        int z = 0 ;
        for(int k = 0;k<resolution;k++){
            for(int j =0;j<resolution;j++){
                
                //System.out.println("tab2D["+k+"]["+j+"] : "+tab2D[k][j]);
                //System.out.println("tab1D["+z+"] : "+tab1D[z]);
                z++;
            }
        }
        return tab2D;
    }
    
    public static void main(String[] args){
        /*double[] test = {8,9,8,5
                        ,6,4,12,87
                        ,98,226,45,12
                        ,15,99,13,14};
        double[][] res = getTab(test);*/
        
        /*double[][] tab = {
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
        };
        ==> test réussi
        
        double[][] tab = {
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
        };*/
        // ==> test réussi
        
        /*double[][] tab = {
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,0,0,0},
            {0,0,0,1,1,1,0,0,0,0},
            {0,0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
        };*/
        // ==> test reussi
        
        /*double[][] tab = {
            {0,0,0,0,0,0,0,1,0,0},
            {0,0,0,0,0,0,1,1,1,0},
            {0,0,0,0,1,0,0,1,0,0},
            {0,0,0,1,1,1,0,0,0,0},
            {0,0,1,1,1,1,1,0,0,0},
            {0,0,0,1,1,1,0,0,0,0},
            {0,0,0,0,1,0,0,1,0,0},
            {0,1,0,0,0,0,1,1,1,0},
            {1,1,1,0,0,0,0,1,0,0},
            {0,1,0,0,0,0,0,0,0,0},
        };*/
        // ==> test reussi
        
        /*double[][] tab = {
            {0,0,0,1,1,1,0,0,0,0},
            {0,0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,0,0,0},
        };*/
        // ==> test reussi
        
        
        
        /*double[][] tab = {
            {0,1,1,1,0,1,1,1,0,0},
            {0,0,1,0,0,0,1,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,1,0,0,0},
        };*/
        // ==> test reussi
        
        /*double[][] tab = {
            {0,0,1,0,1,1,1,0,0,0},
            {0,0,0,0,0,1,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,0,0,0},
            {0,0,0,1,1,1,0,1,0,0},
            {0,0,0,0,1,0,1,1,1,0},
            {0,0,0,0,0,0,0,1,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0,0},
            {0,1,1,1,0,1,0,0,0,0},
        };*/
        // ==> a voir mais respecte l'effet voulu
        
        
        /*double[][] tab = {
            {0,0,0,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0,0,0},
            {1,1,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,1,1},
            {0,0,0,0,0,0,0,0,0,1},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
        };*/
        //=> SUCCES
        
        /*double[][] tab = {
            {0,0,0,0,0,0,0,0,0,1},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,1,1},
        };*/
        //=> SUCCES
        
        /*double[][] tab = {
            {1,0,0,0,0,0,0,0,1,1},
            {0,0,0,0,0,0,0,0,0,1},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,1},
        };*/
        //SUCCES
        /*double[][] tab = {
            {1,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0,0,0},
            {1,1,0,0,0,0,0,0,0,1},
        };*/
        // SUCCES
        
        /*double[][] tab = {
            {1,1,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0,0,0},
        };*/
        //SUCCES
        double[][] tab = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1}
        };
        //SUCCES
        System.out.println("getBubbles(tab): " + getBubbles(tab));
        
        /*int[] test =  {5,8,9,33,18};
        int[] clone ={}; 
        System.out.println("clone avant : "+clone);
        clone = test.clone();
        System.out.println("test : "+test);
        System.out.println("clone : "+clone);
        System.out.println("test[0] : "+test[0]);
        System.out.println("clone[0] : "+clone[0]);*/
        
    }
    
    public static void afficherTableau(double[][] tab){
        String res = "tab : "+tab+ "\n";
        for(int l=0;l<tab.length;l++){
            for(int c=0;c<tab[0].length;c++){
                res = res + " " +tab[l][c];
            }
            res = res + "\n";
        }
        System.out.println(""+res);
    }
    
}
