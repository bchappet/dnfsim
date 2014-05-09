/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.lucasKanade;

import Jama.Matrix;
import main.java.coordinates.Space;
import main.java.maps.Map;
import main.java.maps.Parameter;

/**
 *
 * @author ncarrara
 */
public class FiltreLucasKanade {
    
    /*public static final int DISCRETIZATION_BEFORE = 0;
    public static final int DISCRETIZATION_AFTER = 1;*/
    
    private static final int IxIx =0;
    private static final int IxIy =1;
    private static final int IyIy =2;
    private static final int IxIt =3;
    private static final int IyIt =4;
    
    private static final boolean A = true;
    private static final boolean b = false;
    
    private int x;
    private int y;
    private int voisinage;
    private boolean isWeighted;
    private boolean isWrapped;
    private int discretizationType;
    private int resolution;
    private double intensity;
    private double width;
    private Map map;
    
    private Space space;
    
    int test = 0; 
    
    /**
     * Créer une filtre de type lucas kanade
     * @precond veuillez choisir un voisinage assez petit pour ne pas "deborder". Un vosinage d'epaisseur 1 (ie une couche de 8 pixels ) sur une map d'un pixel, c'est pas bon, par exemple.
     * @param voisinage épaisseur du voisinage (une fenêtre carrée), ie son "rayon", qui contient tout les points adjacents à p  
     * @param isWeighted indique si on veut donner des poids de type gaussien aux points adjacent à p
     * @param isWrapped indique si les images current et nearest sont wrapped
     */
    public FiltreLucasKanade(Space space,int voisinage,boolean isWeighted, double intensity, double width, boolean isWrapped/*, int discretizationType*/){
        this.voisinage = voisinage;
        this.isWeighted = isWeighted;
        this.isWrapped = isWrapped;
        /* this.discretizationType = discretizationType;*/
        this.intensity = intensity;
        this.width = width;
        this.space = space;
    }
    
    /**
     * récupère le vecteur vitesse au point p : (x,y)
     * @Precond la map sur laquel on travail a une mémoire
     * @param x abscisse
     * @param y ordonnée
     * @param map 
     * @return un vecteur vitesse V : (Vx,Vy)
     */
    public Matrix getLocalImageFlowVector(int x, int y, Parameter map){
        this.x = x;
        this.y = y;
        
        
        this.map = (Map) map;
        
        this.resolution = (int)Math.sqrt(map.getValues().length);


        //pour les tests :
//        this.x = 39;
//        this.y = 39;
        
        // AV = Mb
        Matrix MA = new Matrix(2,2);
        Matrix Mb = new Matrix(2,1);
   
        //calcul de MA
        double A_00 = this.sommeSurVoisinageLocal(FiltreLucasKanade.A, FiltreLucasKanade.IxIx);
        double A_01_10 = sommeSurVoisinageLocal(FiltreLucasKanade.A, FiltreLucasKanade.IxIy);
        double A_11 = sommeSurVoisinageLocal(FiltreLucasKanade.A, FiltreLucasKanade.IyIy);
        MA.set(0, 0, A_00);
        MA.set(0, 1, A_01_10);
        MA.set(1, 0, A_01_10);
        MA.set(1, 1, A_11);
        
        //calcul de Mb
        double b_00 = sommeSurVoisinageLocal(FiltreLucasKanade.b, FiltreLucasKanade.IxIt);
        double b_10 = sommeSurVoisinageLocal(FiltreLucasKanade.b, FiltreLucasKanade.IyIt);
        Mb.set(0, 0, b_00);
        Mb.set(1, 0, b_10);
//        System.out.println("nombre de point au voisinage : "+test);
        Matrix V;
        // on résout le système linéaire : calcul de V
       try{
            V  = MA.solve(Mb);
       }catch(java.lang.RuntimeException e){
           // la matrice n'est pas inversible
           //System.out.println("non inversible");
            V = new Matrix(2,1);
       }
//        Matrix V = new Matrix(2,1);
        return V;
    }
    
    
    
    /**
     * calcul de la suite Ui avec i voisinage de p (x,y)
     * @param Ui
     * @return 
     */
    private double sommeSurVoisinageLocal(boolean matrice, int deriveesPartiels){
        
        int facteur = 1;
        double facteurWeight = 1;
        double facteurDeriveesPartiels = 1 ;
        if(!matrice)
            facteur = -1;
        int qx;
        int qy;
        Integer[] coord;
        double somme = 0;    
        // on parcours chaque couche, on commence par la ligne nord de la couche, puis sud etc ...
        for (int couche = 1; couche <= voisinage; couche++) {
            double gaussian =  gaussian(couche);
//            System.out.println("gaussian : "+gaussian);
//            System.out.println("couche : "+couche);
//            System.out.println("début points nord");
            //somme points nord
            for (int c = -couche+x; c <= couche+x; c++) {
                qx = c;
                qy = y - couche ;
                coord = correctCoord(qx, qy);
                if(coord[0]==null || coord[1]==null){
                    // on additionne pas car ça signifie que le point du voisinage n'existe pas
                }else{
                    if(isWeighted);
                        facteurWeight = gaussian;
//                    System.out.println("(qx,qy) : "+"("+coord[0]+","+coord[1]+")");
                    switch(deriveesPartiels){
                        case(IxIx) : facteurDeriveesPartiels = deriveePartielX(coord[0], coord[1]) * deriveePartielX(coord[0], coord[1]); break;
                        case(IxIy) : facteurDeriveesPartiels = deriveePartielX(coord[0], coord[1]) * deriveePartielY(coord[0], coord[1]); break;
                        case(IyIy) : facteurDeriveesPartiels = deriveePartielY(coord[0], coord[1]) * deriveePartielY(coord[0], coord[1]); break;
                        case(IxIt) : facteurDeriveesPartiels = deriveePartielX(coord[0], coord[1]) * deriveePartielT(coord[0], coord[1]); break;
                        case(IyIt) : facteurDeriveesPartiels = deriveePartielY(coord[0], coord[1]) * deriveePartielT(coord[0], coord[1]); break;
                    }    
                    test++;
                    somme += facteur * facteurWeight * facteurDeriveesPartiels;
                }
            }
//            System.out.println("début points sud");
            //somme points sud
            for (int c = -couche+x; c <= couche+x; c++) {
                // position qi : (x + couche, y + i)
                qx = c ;
                qy = y + couche;
                coord = correctCoord(qx, qy);
                if(coord[0]==null || coord[1]==null){
                    // on additionne pas car ça signifie que le point du voisinage n'existe pas
                }else{
                    if(isWeighted);
                        facteurWeight = gaussian;
//                    System.out.println("(qx,qy) : "+"("+coord[0]+","+coord[1]+")");
                    switch(deriveesPartiels){
                        case(IxIx) : facteurDeriveesPartiels = deriveePartielX(coord[0], coord[1]) * deriveePartielX(coord[0], coord[1]); break;
                        case(IxIy) : facteurDeriveesPartiels = deriveePartielX(coord[0], coord[1]) * deriveePartielY(coord[0], coord[1]); break;
                        case(IyIy) : facteurDeriveesPartiels = deriveePartielY(coord[0], coord[1]) * deriveePartielY(coord[0], coord[1]); break;
                        case(IxIt) : facteurDeriveesPartiels = deriveePartielX(coord[0], coord[1]) * deriveePartielT(coord[0], coord[1]); break;
                        case(IyIt) : facteurDeriveesPartiels = deriveePartielY(coord[0], coord[1]) * deriveePartielT(coord[0], coord[1]); break;
                    }  
                     test++;
                    somme += facteur * facteurWeight * facteurDeriveesPartiels;
                }
            }
//            System.out.println("début point ouest");
            //somme point ouest
            for (int l = y-(couche - 1); l <= y+(couche - 1); l++) {
                // position qi : (x + i, y - couche)
                qx = x + -couche;
                qy = l;
                coord = correctCoord(qx, qy);
                if(coord[0]==null || coord[1]==null){
                    // on additionne pas car ça signifie que le point du voisinage n'existe pas
                }else{
                    if(isWeighted);
                        facteurWeight = gaussian;
//                    System.out.println("(qx,qy) : "+"("+coord[0]+","+coord[1]+")");
                    switch(deriveesPartiels){
                        case(IxIx) : facteurDeriveesPartiels = deriveePartielX(coord[0], coord[1]) * deriveePartielX(coord[0], coord[1]); break;
                        case(IxIy) : facteurDeriveesPartiels = deriveePartielX(coord[0], coord[1]) * deriveePartielY(coord[0], coord[1]); break;
                        case(IyIy) : facteurDeriveesPartiels = deriveePartielY(coord[0], coord[1]) * deriveePartielY(coord[0], coord[1]); break;
                        case(IxIt) : facteurDeriveesPartiels = deriveePartielX(coord[0], coord[1]) * deriveePartielT(coord[0], coord[1]); break;
                        case(IyIt) : facteurDeriveesPartiels = deriveePartielY(coord[0], coord[1]) * deriveePartielT(coord[0], coord[1]); break;
                    }    
                     test++;
                    somme += facteur * facteurWeight * facteurDeriveesPartiels;
                }
            }
//            System.out.println("début point est");
            //somme point est
            for (int l = y-(couche - 1); l <= y+(couche - 1); l++) {
                // position qi : (x + i, y + couche)
                qx = x + couche;
                qy = l;
                coord = correctCoord(qx, qy);
                if(coord[0]==null || coord[1]==null){
                    // on additionne pas car ça signifie que le point du voisinage n'existe pas
                }else{
                    if(isWeighted);
                        facteurWeight = gaussian;
//                    System.out.println("(qx,qy) : "+"("+coord[0]+","+coord[1]+")");
                    switch(deriveesPartiels){
                        case(IxIx) : facteurDeriveesPartiels = deriveePartielX(coord[0], coord[1]) * deriveePartielX(coord[0], coord[1]); break;
                        case(IxIy) : facteurDeriveesPartiels = deriveePartielX(coord[0], coord[1]) * deriveePartielY(coord[0], coord[1]); break;
                        case(IyIy) : facteurDeriveesPartiels = deriveePartielY(coord[0], coord[1]) * deriveePartielY(coord[0], coord[1]); break;
                        case(IxIt) : facteurDeriveesPartiels = deriveePartielX(coord[0], coord[1]) * deriveePartielT(coord[0], coord[1]); break;
                        case(IyIt) : facteurDeriveesPartiels = deriveePartielY(coord[0], coord[1]) * deriveePartielT(coord[0], coord[1]); break;
                    }     
                     test++;
                    somme += facteur * facteurWeight * facteurDeriveesPartiels;
                }
            }
        }
        return somme;
    }
    
    private double gaussian(double distanceFromP){
        return intensity * Math.exp(-Math.pow(distanceFromP,2)/Math.pow(width, 2));
    }
    
    private double deriveePartielX(int qx, int qy){
//        double Ix = 1;
        double Ix;
        if(qx == 0){
            Ix = map.getFast(qx+1, qy) - map.getFast(qx, qy); // current[qx+1][qy] - ...
        }else if(qx == resolution -1){
            Ix = map.getFast(qx, qy) - map.getFast(qx-1 ,qy);
        }else{
            Ix = (map.getFast(qx+1, qy) - map.getFast(qx-1 ,qy))/2;
        }
        return Ix;
    }
    
    private double deriveePartielY(int qx, int qy){
//        double Iy = 1;
        double Iy;
        if(qy == 0){
            Iy = map.getFast(qx, qy+1) - map.getFast(qx ,qy);
        }else if(qy == resolution -1){
            Iy = map.getFast(qx, qy) - map.getFast(qx ,qy-1);
        }else{
            Iy = (map.getFast(qx, qy+1) - map.getFast(qx, qy-1))/2;
        }
        return Iy;
    }
    
    private double deriveePartielT(int qx, int qy){
//        double It = 1;
        double It = 0;
        Double[] coord = {new Double(qx),new Double(qy)};
        Double[] tab = space.continuousProj(coord);
        It = map.getFast(qx, qy) - map.getDelay(1, tab[Space.X], tab[Space.Y]);
        /*switch(discretizationType){
            case(DISCRETIZATION_BEFORE): 
                It = - nearestImage[qx][qy] + currentImage[qx][qy];
                break;
            case(DISCRETIZATION_AFTER): 
                It = nearestImage[qx][qy] - currentImage[qx][qy];
                break;
        }*/
        return It;
    }
    
    /**
     * renvoi les coordonées correct du point (qx,qy). Ie on test si wrappé et cie.
     * Si le point ne doit pas exister (ie non wrappé et hors map) alors on retourne {null,null}
     * @param qx
     * @param qy
     * @return 
     */
    private Integer[] correctCoord(Integer qx, Integer qy){
        Integer[] nouvellesCoord = {qx,qy};
        
        if(qx < 0){
            if(isWrapped){
                nouvellesCoord[0] = qx + resolution ;
            }else{
                nouvellesCoord[0] = null;
            }
        }
        if(qx > resolution -1 ){
            if(isWrapped){
                nouvellesCoord[0] = qx - resolution ;
            }else{
                nouvellesCoord[0] = null;
            }
        }
        
        if(qy < 0){
            if(isWrapped){
                nouvellesCoord[1] = qy + resolution ;
            }else{
                nouvellesCoord[1] = null;
            }
        }
        if(qy > resolution -1 ){
            if(isWrapped){
                nouvellesCoord[1] = qy - resolution ;
            }else{
                nouvellesCoord[1] = null;
            }
        }
        
        return nouvellesCoord;
    }
       
}
