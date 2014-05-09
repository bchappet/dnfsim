/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.resources.tool;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import main.java.maps.Parameter;

/**
 * Ensemble d'outil necessaire à l'études des main.java.maps.
 * @author ncarrara
 */
public class ToolsMAP {

    public static final int LIG = 0;
    public static final int COL = 1;
    public static final int PARCOURUE_VIDE = 3;
    public static final int PARCOURUE_POTENTIEL = 2;
    public static final int VIDE = 0;
    public static final int DIFFUSION_RECURRENCE_NON_TERMINALE = 0;
    public static final int DIFFUSION_WHILE_SEMI_OPTIMISEE = 1;
    public static final int NORD = 0;
    public static final int SUD = 1;
    public static final int OUEST = 2;
    public static final int EST = 3;
    private static int resolution ;

    /**
     * Cherche dans la map toutes les bulles puis renvoit leur centre.
     * 
     * DIFFUSION_RECURRENCE_NON_TERMINALE : cherche les bulles selon un algorithme à goutte d'huile
     * ne fonctionne pas sur les main.java.maps plus grandes que 43*43 (a peu près) à cause de la pile trop petite
     * Les centres renvoyés sont dans un ensemble discret (coordonée continu mais leur placement correspond à des cases de tableaux).
     * DIFFUSION_WHILE_SEMI_OPTIMISEE : cherche les bulles selon un algorithme à fusion. Ie , on parcoure ligne pas ligne
     * , à chaque ligne on determine une tranche de bulles. On fusionne les tranches d'une ligne à l'autre. Pas de limite de résolution
     * en théorie. Les centres renvoyés sont dans un ensemble continu.
     * 
     * Cette fonction est destinée aux main.java.maps wrapped
     * 
     * @param map la map sur laquel on cherche les centres
     * @param diffusion le type de recherche
     * @return l'ensemble des centres des bulles de la map
     */
    public static List<PointContinu> getCenterBubbles(Parameter map, int diffusion) {
        double[][] tab = getTab(map);
        resolution = tab.length;
        List<PointContinu> bubblesCenter = new ArrayList<PointContinu>();
        switch (diffusion) {
            case DIFFUSION_RECURRENCE_NON_TERMINALE:
                /* pour chaque points du tableau si potentiel>0 
                 * alors on cherche tout les potentiels adjacents >0 pour trouver la bulle */
                double potentiel = -9999;
                for (int l = 0; l < tab.length; l++) {
                    for (int c = 0; c < tab.length; c++) {
                        potentiel = tab[l][c];
                        /* si le point n'a pas encore été parcouru */
                        if (potentiel != 2 && potentiel != 3) {
                            if (potentiel == 0) {
                                //on indique qu'on vient de parcourir cette case qui était vide à la base
                                tab[l][c] = 3;
                            } else {
                                //potentiel > 0
                                bubblesCenter.add(new PointContinu(getBubble(l, c, tab)));
                            }
                        }
                    }
                }
                break;
            case DIFFUSION_WHILE_SEMI_OPTIMISEE:
                ArrayList<Bubble> bubbles = getBubbles(tab);
                for (Bubble bubble : bubbles) {
                    bubble.caculerCentreBullePrecision();
                    bubblesCenter.add(bubble.centreDouble);
                }
                break;

        }
        return bubblesCenter;
    }

    /**
     * Cherche dans la map toutes les bulles puis renvoit leur centre.
     * 
     * DIFFUSION_RECURRENCE_NON_TERMINALE : cherche les bulles selon un algorithme à goutte d'huile
     * ne fonctionne pas sur les main.java.maps plus grandes que 43*43 (a peu près) à cause de la pile trop petite
     * Les centres renvoyés sont dans un ensemble discret (coordonée continu mais leur placement correspond à des cases de tableaux).
     * DIFFUSION_WHILE_SEMI_OPTIMISEE : cherche les bulles selon un algorithme à fusion. Ie , on parcoure ligne pas ligne
     * , à chaque ligne on determine une tranche de bulles. On fusionne les tranches d'une ligne à l'autre. Pas de limite de résolution
     * en théorie. Les centres renvoyés sont dans un ensemble continu.
     * 
     * Cette fonction est destinée aux main.java.maps wrapped
     * 
     * @param tab le tableau sur lequel on cherche les centres
     * @param diffusion le type de recherche
     * @return l'ensemble des centres des bulles de la map
     */
    public static List<PointContinu> getCenterBubbles(double[][] tab, int diffusion) {
        List<PointContinu> bubblesCenter = new ArrayList<PointContinu>();
        resolution = tab.length;
        switch (diffusion) {
            case DIFFUSION_RECURRENCE_NON_TERMINALE:
                /* pour chaque points du tableau si potentiel>0 
                 * alors on cherche tout les potentiels adjacents >0 pour trouver la bulle */
                double potentiel = -9999;
                for (int l = 0; l < tab.length; l++) {
                    for (int c = 0; c < tab.length; c++) {
                        potentiel = tab[l][c];
                        /* si le point n'a pas encore été parcouru */
                        if (potentiel != 2 && potentiel != 3) {
                            if (potentiel == 0) {
                                //on indique qu'on vient de parcourir cette case qui était vide à la base
                                tab[l][c] = 3;
                            } else {
                                //potentiel > 0
                                bubblesCenter.add(new PointContinu(getBubble(l, c, tab)));
                            }
                        }
                    }
                }
                break;
            case DIFFUSION_WHILE_SEMI_OPTIMISEE:
                ArrayList<Bubble> bubbles = getBubbles(tab);
                for (Bubble bubble : bubbles) {
                    bubble.caculerCentreBullePrecision();
                    bubblesCenter.add(bubble.centreDouble);
                }

                break;

        }
        return bubblesCenter;
    }
    
    /**
     * incrémente la valeur de la projection, si on est sur le bord droit, alors
     * la projection passe à zéro (à cause du wrapping)
     * Cette fonction n'est correcte que si la map est wrapped
     * @param projection la projection sur un des deux axes
     * @return la projection sur un dex deux axes de la valeur suivante
     */
    public static int incrementeW(int projection) {
        int newprojection = projection + 1;
        if (projection == resolution - 1) {
            newprojection = 0;
        }
        return newprojection;
    }

    /**
     * décrémente la valeur de la projection, si on est sur le bord gauche ,
     * alors la projection passe à résolution-1
     * Cette fonction n'est correcte que si la map est wrapped
     * @param projection la projection sur un des deux axes
     * @return la projection sur un dex deux axes de la valeur précedente
     */
    public static int decrementeW(int projection) {
        int newprojection = projection - 1;
        if (projection == 0) {
            newprojection = resolution - 1;
        }
        return newprojection;
    }

    /**
     * Récupérer les valeurs de la mp, les transformes en tableau 2D
     * @param map
     * @return 
     */
    public static double[][] getTab(Parameter map) {
        double[] tab1D = map.getValues();
        int resoltution2D = (int) map.getSpace().getResolution();
        double[][] tab2D = new double[resoltution2D][resoltution2D];
        int x = 0;
        int y = 0;
        for (int i = 0; i < tab1D.length; i++) {
            y = i % resoltution2D;
            x = (i - y) / resoltution2D;
            tab2D[x][y] = tab1D[i];
        }
        int z = 0;
        for (int k = 0; k < resoltution2D; k++) {
            for (int j = 0; j < resoltution2D; j++) {
                z++;
            }
        }
        return tab2D;
    }

    /**
     * transforme un tableau 1D en 2D
     * @param tab
     * @return 
     */
    public static double[][] getTab(double[] tab) {
        double[] tab1D = tab;
        int resolution2D = (int) Math.sqrt(tab.length);
        double[][] tab2D = new double[resolution2D][resolution2D];
        int x = 0;
        int y = 0;
        for (int i = 0; i < tab1D.length; i++) {
            y = i % resolution2D;
            x = (i - y) / resolution2D;
            tab2D[x][y] = tab1D[i];
        }
        int z = 0;
        for (int k = 0; k < resolution2D; k++) {
            for (int j = 0; j < resolution2D; j++) {
                z++;
            }
        }
        return tab2D;
    }

    /**
     * INUTILE, retourne tout les cardinaux potentiels de type N/S/E ou O.
     * Ne fonctionne que si la map est wrapped
     * @param cardinal le cardinal: NORD EST OUEST ou SUD
     * @param bubble un ensemble de bulle dont on extrait les cardinaux potentiel
     * @param tab le tableau dans lequel se trouve les bulles
     * @return l'ensemble des cardinaux potentiels
     */
    public static ArrayList<Point> getCardinauxPotentiels(int cardinal, ArrayList<Point> bubble, double[][] tab) {
        ArrayList<Point> points = new ArrayList<Point>();
        Point A, B, C, D, E, F, G, H;
        double pA, pB, pC, pD, pE, pF, pG, pH;
        for (Point p : bubble) {
            switch (cardinal) {
                case NORD:
                    A = coordonneeW("A", p);
                    pA = tab[A.x][A.y];
                    B = coordonneeW("B", p);
                    pB = tab[B.x][B.y];
                    C = coordonneeW("C", p);
                    pC = tab[C.x][C.y];
                    if (pA == 0 && pB == 0 && pC == 0) {
                        points.add(p);
                    }
                    break;
                case SUD:
                    G = coordonneeW("G", p);
                    pG = tab[G.x][G.y];
                    F = coordonneeW("F", p);
                    pF = tab[F.x][F.y];
                    E = coordonneeW("E", p);
                    pE = tab[E.x][E.y];
                    if (pG == 0 && pF == 0 && pE == 0) {
                        points.add(p);
                    }
                    break;
                case EST:
                    C = coordonneeW("C", p);
                    pC = tab[C.x][C.y];
                    D = coordonneeW("D", p);
                    pD = tab[D.x][D.y];
                    E = coordonneeW("E", p);
                    pE = tab[E.x][E.y];
                    if (pC == 0 && pD == 0 && pE == 0) {
                        points.add(p);
                    }
                    break;
                case OUEST:
                    A = coordonneeW("A", p);
                    pA = tab[A.x][A.y];
                    H = coordonneeW("H", p);
                    pH = tab[H.x][H.y];
                    G = coordonneeW("G", p);
                    pG = tab[G.x][G.y];
                    if (pA == 0 && pH == 0 && pG == 0) {
                        points.add(p);
                    }
                    break;
                default:
                    //error
                    break;
            }
        }
        return points;
    }

    /**
     * affiche un tableau 2D sur la sortie standard
     * @param tab 
     */
    public static void afficherTableau(double[][] tab) {
        String res = "tab : " + tab + "\n";
        for (int l = 0; l < tab.length; l++) {
            for (int c = 0; c < tab[0].length; c++) {
                res = res + " " + tab[l][c];
            }
            res = res + "\n";
        }
        System.out.println("" + res);
    }
    
    /**
     * renvoi les coordonnees  d'un point adjacent à p appartenant à A B C D E F G H.
     * configuration des points adjacents : 
     * ------- 
     * |A B C| 
     * |H p D| 
     * |G F E|
     * -------
     * Cette fonction n'est correct que si la map est wrapped
     * @param pointAdjacent
     * @param p
     * @return la position du point adjacent sur le tableau
     */
    public static Point coordonneeW(String pointAdjacent, Point p) {
        Point adjacent = new Point();

        // on donne A B COL et cie en fonction de la position de p (si sur le bord ou non)
        if (pointAdjacent.equals("A")) {
            adjacent.x = decrementeW(p.x);
            adjacent.y = decrementeW(p.y);
        } else if (pointAdjacent.equals("B")) {
            adjacent.y = p.y;
            adjacent.x = decrementeW(p.x);
        } else if (pointAdjacent.equals("C")) {
            adjacent.y = incrementeW(p.y);
            adjacent.x = decrementeW(p.x);
        } else if (pointAdjacent.equals("D")) {
            adjacent.y = incrementeW(p.y);
            adjacent.x = p.x;
        } else if (pointAdjacent.equals("E")) {
            adjacent.y = incrementeW(p.y);
            adjacent.x = incrementeW(p.x);
        } else if (pointAdjacent.equals("F")) {
            adjacent.y = p.y;
            adjacent.x = incrementeW(p.x);
        } else if (pointAdjacent.equals("G")) {
            adjacent.y = decrementeW(p.y);
            adjacent.x = incrementeW(p.x);
        } else if (pointAdjacent.equals("H")) {
            adjacent.y = decrementeW(p.y);
            adjacent.x = p.x;
        }

        return adjacent;
    }
    
    
    /* ------------------------------ ENSEMBLE DES FONCTIONS PRIVEE NECESSAIRE AUX FONCTIONS CI DESSUS. ----------------------------- */

    
    
    /**
     *
     * @param tableau
     * @return une liste de bulles (une bulle est une liste de point)
     */
    private static ArrayList<Bubble> getBubbles(double[][] tableau) {
        ArrayList<Bubble> bubbles = new ArrayList<Bubble>();
        ArrayList<Bubble> bubblesCurrentLine = new ArrayList<Bubble>(); // l'ensemble des tranches de bulles sur cette ligne
        for (int l = 0; l < tableau.length; l++) {
            for (int c = 0; c < tableau[0].length; c++) {
                // on cherche toutes les "tranches" de point sur la lignes
                double potentielCourant = tableau[l][c];
                if (potentielCourant > 0 && potentielCourant <= 1) {
                    bubblesCurrentLine.add(getTrancheBubble(l, c, tableau));
                }
            }
            fusion(bubbles, bubblesCurrentLine, l, tableau.length - 1);
            bubblesCurrentLine.clear();
        }
        bubbles = fusionHorizontaleFinale(bubbles);
        return bubbles;
    }
    
    
    /**
     *
     * @param c
     * @param l
     * @return la tranche de la bulle , ie l'ensemble des points adjacent deux à
     * deux sur cette ligne.
     */
    private static Bubble getTrancheBubble(int l, int c, double[][] tableau) {
        Bubble tranche = new Bubble(resolution);
        Point depart = new Point(l, c);
        tranche.points.add(depart);
        tranche.nord = new Point(depart);
        tranche.sud = new Point(depart);
        tranche.ouest = new Point(depart);
        tableau[l][c] = ToolsMAP.PARCOURUE_POTENTIEL;
        int colonne = c;
        colonne ++;
        double potentielDroit = 2;
        if(colonne<resolution){
            potentielDroit = tableau[l][colonne];
        }
        boolean stop = potentielDroit <= 0 || potentielDroit > 1 ;
        while (!stop) {
            tableau[l][colonne] = ToolsMAP.PARCOURUE_POTENTIEL;
            tranche.points.add(new Point(l, colonne));
            colonne++;
            if(colonne < resolution){
                potentielDroit = tableau[l][colonne];
            }
            stop = potentielDroit <= 0 || potentielDroit > 1 || colonne >= resolution;
        }
        
        tranche.est = new Point(l, colonne - 1);
        return tranche;
    }

    /**
     * fusionne les bulles déjà trouvées avec les bulles de la tranches.
     * Si on arrive au bout du tableau (verticalement) on fusionne les bubbles collé aux bords nord et sud.
     * @param bubblesCenter
     * @param bubblesCurrentLine
     */
    private static void fusion(ArrayList<Bubble> bubbles, ArrayList<Bubble> tranches, int currentLine, int lastLine) {
        for (Bubble tranche : tranches) {
            ArrayList<Integer> indexBubbleToFusion = new ArrayList<Integer>();
            Bubble currentBubble;
            for (int index = 0; index < bubbles.size(); index++) {
                currentBubble = bubbles.get(index);
                if (adjacenceVerticale(tranche, currentBubble)) {
                    indexBubbleToFusion.add(index);
                }
            }
            int removed = 0;
            Bubble bubbleToFusion;
            for (Integer index : indexBubbleToFusion) {
                bubbleToFusion = bubbles.get(index - removed);
                if (currentLine == lastLine) {
                    fusionVerticaleBubbleToBubbleFinale(bubbleToFusion, tranche);
                }else{
                    if (tranche.nord.x > bubbleToFusion.nord.x) {
                            tranche.nord = new Point(bubbleToFusion.nord);
                    }
                }
                if(tranche.ouest.y> bubbleToFusion.ouest.y ){
                    tranche.ouest = new Point(bubbleToFusion.ouest);
                }
                
                if(tranche.est.y  < bubbleToFusion.est.y ){
                    tranche.est = new Point(bubbleToFusion.est);
                }
                
                tranche.points.addAll(bubbleToFusion.points);
                bubbles.remove(bubbleToFusion);
                removed++;
            }
            bubbles.add(tranche);
        }
    }

    
    /**
     * fais la fusion de toutes les bulles collé à l'axe ouest avec celle collées à l'axe est.
     * @param bubbles
     * @return l'ensemble des bulles fusionnées
     */
    private static ArrayList<Bubble> fusionHorizontaleFinale(ArrayList<Bubble>bubbles){
        boolean stop = bubbles.size() < 2;
        ArrayList<Bubble> fusionHorizontale = new ArrayList<Bubble>();
        Bubble currentBubble;
        Bubble bubbleToCompare;
        while(!stop){
            currentBubble = bubbles.get(0);
            int removed = 0;
            for(int i = 1 ; i < bubbles.size(); i++ ){
                bubbleToCompare = bubbles.get(i-removed);
                if(adjacenceHorizontale(currentBubble, bubbleToCompare )){
                    fusionHorizontaleBubbleToBubbleFinale(currentBubble, bubbleToCompare);
                    currentBubble.points.addAll(bubbleToCompare.points);
                    bubbles.remove(bubbleToCompare);
                    removed++;
                }
            }
            fusionHorizontale.add(currentBubble);
            bubbles.remove(0);
            stop = bubbles.size() < 2; 
        }
        // necessaire seulement si il n'y a qu'une bulle.
        for(Bubble bubbleLast : bubbles){
            fusionHorizontale.add(bubbleLast);
        }
        return fusionHorizontale;
    }
    
    /**
     * Fusionne une bulle collée à la ligne ouest avec une bulle de la ligne est
     * Ne fonctionne que si la map est wrapped
     * @param currentBubble
     * @param bubbleToFusion 
     */
    private static void fusionHorizontaleBubbleToBubbleFinale(Bubble currentBubble, Bubble bubbleToFusion) {
        // definition est et ouest
        currentBubble.wrappedHorizontale = true;
        if (currentBubble.ouest.y == 0) {
            currentBubble.ouest = new Point(bubbleToFusion.ouest);
        } else {
            currentBubble.est = new Point(bubbleToFusion.est);
        }
        
        // definition nord et sud
        if ((currentBubble.wrappedVertical && bubbleToFusion.wrappedVertical) || (!currentBubble.wrappedVertical && !bubbleToFusion.wrappedVertical)) {
            if (currentBubble.nord.x > bubbleToFusion.nord.x) {
                currentBubble.nord = new Point(bubbleToFusion.nord);
            }
            if (currentBubble.sud.x < bubbleToFusion.sud.x) {
                currentBubble.sud = new Point(bubbleToFusion.sud);
            }
        } else {
            // une bulle est wrappé et pas l'autre
            if(!currentBubble.wrappedVertical){
                // si c'estla bulle courante est non wrappé
                if(currentBubble.nord.x ==0){
                    // on est collé au nord
                    currentBubble.nord = new Point(bubbleToFusion.nord);
                    if(currentBubble.sud.x < bubbleToFusion.sud.x){
                        currentBubble.sud = new Point(bubbleToFusion.sud);
                    }
                }else{
                    // on est collé au sud
                    currentBubble.sud = new Point(bubbleToFusion.sud);
                    if(currentBubble.nord.x> bubbleToFusion.nord.x){
                        currentBubble.nord = new Point(bubbleToFusion.nord);
                    }
                }
            }else{
                // si c'est la bulle fusion qui est  non wrappé
                if(bubbleToFusion.nord.x ==0){
                    // on est collé au nord
                    // currentBubble garde son nord
                    if(currentBubble.sud.x < bubbleToFusion.sud.x){
                        currentBubble.sud = new Point(bubbleToFusion.sud);
                    }
                }else{
                    // on est collé au sud
                    // currentBubble garde son sud
                    if(currentBubble.nord.x> bubbleToFusion.nord.x){
                        currentBubble.nord = new Point(bubbleToFusion.nord);
                    }
                }
                
            }
            currentBubble.wrappedVertical = true;
        }       
    }

    
    
    
    /**
     * fusionne la bulle collé à la dernière ligne avec la bulle collé à la première ligne (à cause du wrapping)
     * Ne fonctionne que si la map est wrapped
     * @param bubbleToFusion
     * @param tranche 
     */
    private static void fusionVerticaleBubbleToBubbleFinale(Bubble bubbleToFusion, Bubble tranche){
        if (bubbleToFusion.nord.x == 0) {
            // on fait rien vu que c'est la tranche qui l'emporte (a cause du wrapping)
            tranche.wrappedVertical = true;
        } else {
            if (bubbleToFusion.nord.x < tranche.nord.x) {
                tranche.nord = new Point(bubbleToFusion.nord);
            } else {
                // le nord de la tranche est deja le plus haut
            }
        }
        
        if (bubbleToFusion.wrappedVertical) {
            tranche.wrappedVertical = true;
            if (tranche.sud.x == resolution - 1) {
                tranche.sud = new Point(bubbleToFusion.sud);
            } else {
                if (tranche.sud.x < bubbleToFusion.sud.x) {
                    tranche.sud = new Point(bubbleToFusion.sud);
                }
            }
        } else {
            if (bubbleToFusion.nord.x == 0) {
                tranche.sud = new Point(bubbleToFusion.sud);
            }
        }
    }

    /**
     * Deux bulles collés l'une sur l'autre ou pas (point commun A B C ou E F G)
     * Ne fonctionne que si la map est wrapped
     * @param tranche
     * @param currentBubble
     * @return vrai si la tranche et la bulle ont des points adjacents verticalement
     */
    private static boolean adjacenceVerticale(Bubble tranche, Bubble currentBubble) {
        boolean adjacent = false;
        Point A, B, C, E, F, G;
        search:
        for (Point pTranche : tranche.points) {
            A = coordonneeW("A", pTranche);
            B = coordonneeW("B", pTranche);
            C = coordonneeW("C", pTranche);
            E = coordonneeW("E", pTranche);
            F = coordonneeW("F", pTranche);
            G = coordonneeW("G", pTranche);
            for (Point pBubble : currentBubble.points) {
                // vérifie que les cases adjacent A C E G ne sont pas de l'autre coté (wrapping) => on evite de fusionner les elements ouest et est pour le moment (on le fait à la fin)
                if(Math.abs(pTranche.y - A.y)==1 && Math.abs(pTranche.y - C.y)==1 && Math.abs(pTranche.y - E.y)==1 && Math.abs(pTranche.y - G.y)==1){
                    adjacent = pBubble.equals(A)
                            || pBubble.equals(B)
                            || pBubble.equals(C)
                            || pBubble.equals(E)
                            || pBubble.equals(F)
                            || pBubble.equals(G);
                }else{
                     adjacent = pBubble.equals(B)
                            || pBubble.equals(F);
                }
                if (adjacent) {
                    break search;
                }
            }
        }
        return adjacent;
    }
    
    /**
     * Deux bulles collés l'une à coté de l'autre ou pas (point commun D ou H ( C E et G déjà traités via l'adjacence verticale ) )
     * @param bubbleToCompare
     * @param currentBubble
     * @return vrai si la tranche et la bulle ont des points adjacents horizontalement
     */
    private static boolean adjacenceHorizontale(Bubble bubbleToCompare, Bubble currentBubble) {
        boolean adjacent = false;
        Point D, H;
        search:
        for (Point pTranche : bubbleToCompare.points) {
            D = coordonneeW("D", pTranche);
            H = coordonneeW("H", pTranche);
            for (Point pBubble : currentBubble.points) {
                adjacent = pBubble.equals(D)
                        || pBubble.equals(H);
                if (adjacent) {
                    break search;
                }
            }
        }
        return adjacent;
    }

    /**
     * parcours de manière itérative le tableau tant qu'il rencontre des
     * potentiels adjacents > 0 positionne à 2 les cases parcourues Considérez
     * position absolue comme "position absolue par rapport au point p
     * Attention, le centre retourné sur trouve dans un ensemble discret
     * Cette fonction n'est correct que si la map est wrapped
     * @param ligne du point de départ de potentiel >0
     * @param colonne du point de départ de potentiel >0
     * @param tableau de la map
     * @return le centre de tout les potentiels adjacents
     */
    private static Point getBubble(int ligne, int colonne, double[][] tableau) {
        Point centre = new Point();
        Point p = new Point(ligne, colonne);
        int[] positionAbsolue = new int[2], positionAbsolueNord = new int[2], positionAbsolueSud = new int[2], positionAbsolueEst = new int[2], positionAbsolueOuest = new int[2], positionAbsolueCentre = new int[2];

        g(p, positionAbsolueNord, positionAbsolueSud, positionAbsolueEst, positionAbsolueOuest, tableau, positionAbsolue);

        positionAbsolueCentre[LIG] = (positionAbsolueEst[LIG] + positionAbsolueOuest[LIG]) / 2;
        positionAbsolueCentre[COL] = (positionAbsolueNord[COL] + positionAbsolueSud[COL]) / 2;

        centre.x = ligne;
        centre.y = colonne;
        if (positionAbsolueCentre[LIG] > 0) {
            for (int i = 0; i < positionAbsolueCentre[LIG]; i++) {
                centre.x = incrementeW(centre.x);
            }
        }

        if (positionAbsolueCentre[LIG] < 0) {
            for (int i = 0; i < -positionAbsolueCentre[LIG]; i++) {
                centre.x = decrementeW(centre.x);
            }
        }

        if (positionAbsolueCentre[COL] > 0) {
            for (int i = 0; i < positionAbsolueCentre[COL]; i++) {
                centre.y = incrementeW(centre.y);
            }
        }

        if (positionAbsolueCentre[COL] < 0) {
            for (int i = 0; i < -positionAbsolueCentre[COL]; i++) {
                centre.y = decrementeW(centre.y);
            }
        }


        return centre;
    }

    /**
     * Partie réccursive de la recherche de bulle en mode réccursive non terminale.
     * l'idée c'est d'apeller g sur chaque case adjacent de p. Si on est sur une
     * bordure alors on a potentielement un sud et/ou un est et/ou un ouest la
     * récurrence s'arrête si on ne peux plus parcourir les points adjacents ie
     * : =2 ou =0
     *
     * configuration des points adjacents : 
     * ------- 
     * |A B C| 
     * |H p D| 
     * |G F E|
     * -------
     * Cette fonction n'est correct que si la map est wrapped
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
            int[] positionAbsolue) {
        tableau[p.x][p.y] = PARCOURUE_POTENTIEL;
        Point A, B, C, D, E, F, G, H;
        double pA, pB, pC, pD, pE, pF, pG, pH;
        A = coordonneeW("A", p);
        pA = tableau[A.x][A.y];
        B = coordonneeW("B", p);
        pB = tableau[B.x][B.y];
        C = coordonneeW("C", p);
        pC = tableau[C.x][C.y];
        D = coordonneeW("D", p);
        pD = tableau[D.x][D.y];
        E = coordonneeW("E", p);
        pE = tableau[E.x][E.y];
        F = coordonneeW("F", p);
        pF = tableau[F.x][F.y];
        G = coordonneeW("G", p);
        pG = tableau[G.x][G.y];
        H = coordonneeW("H", p);
        pH = tableau[H.x][H.y];
        /* si on est sur une des limites Nord */
        if ((pA == VIDE || pA == PARCOURUE_VIDE) && (pB == VIDE || pB == PARCOURUE_VIDE) && (pC == VIDE || pC == PARCOURUE_VIDE)) {
            if (positionAbsolueNord[LIG] > positionAbsolue[LIG]) {
                System.arraycopy(positionAbsolue, 0, positionAbsolueNord, 0, 2);
            }
        }
        /* si on est sur une des limites Sud */
        if ((pG == VIDE || pG == PARCOURUE_VIDE) && (pF == VIDE || pF == PARCOURUE_VIDE) && (pE == VIDE || pE == PARCOURUE_VIDE)) {
            if (positionAbsolueSud[LIG] < positionAbsolue[LIG]) {
                System.arraycopy(positionAbsolue, 0, positionAbsolueSud, 0, 2);
            }
        }
        /* si on est sur une des limites Est */
        if ((pC == VIDE || pC == PARCOURUE_VIDE) && (pD == VIDE || pD == PARCOURUE_VIDE) && (pE == VIDE || pE == PARCOURUE_VIDE)) {
            if (positionAbsolueEst[COL] < positionAbsolue[COL]) {
                System.arraycopy(positionAbsolue, 0, positionAbsolueEst, 0, 2);
            }
        }
        /* si on est sur une des limites Ouest */
        if ((pA == VIDE || pA == PARCOURUE_VIDE) && (pH == VIDE || pH == PARCOURUE_VIDE) && (pG == VIDE || pG == PARCOURUE_VIDE)) {
            if (positionAbsolueOuest[COL] > positionAbsolue[COL]) {
                System.arraycopy(positionAbsolue, 0, positionAbsolueOuest, 0, 2);
            }
        }

        //---------------------------------------- c'est cette partie là qui rend l'algo NON terminal( vu qu'après l'appel de g, on refait des appels de g, donc on doit garder en mémoire les A B C D ...-----//

        if (pA > 0 && pA <= 1) {
            positionAbsolue[LIG]--;
            positionAbsolue[COL]--;
            g(A, positionAbsolueNord, positionAbsolueSud, positionAbsolueEst, positionAbsolueOuest, tableau, positionAbsolue);
        }
        pB = tableau[B.x][B.y];
        if (pB > 0 && pB <= 1) {
            positionAbsolue[LIG]--;
            g(B, positionAbsolueNord, positionAbsolueSud, positionAbsolueEst, positionAbsolueOuest, tableau, positionAbsolue);
        }
        pC = tableau[C.x][C.y];
        if (pC > 0 && pC <= 1) {
            positionAbsolue[LIG]--;
            positionAbsolue[COL]++;
            g(C, positionAbsolueNord, positionAbsolueSud, positionAbsolueEst, positionAbsolueOuest, tableau, positionAbsolue);
        }
        pD = tableau[D.x][D.y];
        if (pD > 0 && pD <= 1) {
            positionAbsolue[COL]++;
            g(D, positionAbsolueNord, positionAbsolueSud, positionAbsolueEst, positionAbsolueOuest, tableau, positionAbsolue);
        }
        pE = tableau[E.x][E.y];
        if (pE > 0 && pE <= 1) {
            positionAbsolue[COL]++;
            positionAbsolue[LIG]++;
            g(E, positionAbsolueNord, positionAbsolueSud, positionAbsolueEst, positionAbsolueOuest, tableau, positionAbsolue);
        }
        pF = tableau[F.x][F.y];
        if (pF > 0 && pF <= 1) {
            positionAbsolue[LIG]++;
            g(F, positionAbsolueNord, positionAbsolueSud, positionAbsolueEst, positionAbsolueOuest, tableau, positionAbsolue);
        }
        pG = tableau[G.x][G.y];
        if (pG > 0 && pG <= 1) {
            positionAbsolue[LIG]++;
            positionAbsolue[COL]--;
            g(G, positionAbsolueNord, positionAbsolueSud, positionAbsolueEst, positionAbsolueOuest, tableau, positionAbsolue);
        }
        pH = tableau[H.x][H.y];
        if (pH > 0 && pH <= 1) {
            positionAbsolue[COL]--;
            g(H, positionAbsolueNord, positionAbsolueSud, positionAbsolueEst, positionAbsolueOuest, tableau, positionAbsolue);
        }
    }

    

    
}
