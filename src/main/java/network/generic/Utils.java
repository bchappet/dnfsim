/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.network.generic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARRARA Nicolas
 */
public class Utils {
    
    public static final String SEP = ",";

    /**
     * Parse un fichier CSV. Si ce n'est pas une matrice (un triangle par
     * exemple) alors les "trous" sont remplacés automatiquement par des zéros.
     *
     * @param file
     * @return
     */
    public final static double[][] parseCSVFile(File file) {
        BufferedReader br = null;
        String line;
        List<String[]> lignes = new ArrayList<>();
        int maxWidth = 0;
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                String[] ligne = line.split(SEP);
                lignes.add(ligne);
                maxWidth = maxWidth < ligne.length ? ligne.length : maxWidth;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
        
        double[][] matrice = new double[lignes.size()][maxWidth];
        for (int l = 0; l < lignes.size(); l++) {
            if (!"".equals(lignes.get(l)[0])) {
                for (int c = 0; c < maxWidth; c++) {
                    if (c < lignes.get(l).length) {
                        matrice[l][c] = Double.parseDouble(lignes.get(l)[c]);
                    }
                }
            }
            
        }
        
        return matrice;
    }

    /**
     * ecrit un fichier csv à partir d'une matrice
     *
     * @param file
     * @param matrice
     */
    public final static void writeCSVFile(File file, double[][] matrice) {
//    	System.out.println("csv file matrix size : "+matrice.length+"x"+matrice[0].length);
//    	System.out.println(Arrays.deepToString(matrice));
//    	System.out.println("file : "+file);
        Writer writer = null;
        int i = 0;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "utf-8"));
//            System.out.println("matrice.length : "+matrice .length);
            for (int l = 0; l < matrice.length; l++) {
//            	System.out.println("matrice["+l+"].length : "+matrice[l].length);
                for (int c = 0; c < matrice[l].length - 1; c++) {
//                	System.out.print(matrice[l][c] + ",");
                    writer.write(matrice[l][c] + SEP);
                    i++;
                    
                }
//                System.out.print(matrice[l][matrice[l].length - 1] + "\n");
                writer.write(matrice[l][matrice[l].length - 1] + "\n");
                i++;
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } 
       
//        System.out.println("\nNombre de case écrite  : "+i);
    }
}
