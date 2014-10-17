/*
 * This is a Dynamic Neural Field simulator which is extended to
 *     several other neural networks and extended to hardware simulation.
 *
 *     Copyright (C) 2014  Benoît Chappet de Vangel
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package main;

import main.java.controler.Printer;

import java.io.IOException;

/**
 * Created by bchappet on 10/16/14.
 */
public class Run {




    public static final void main(String[] args) throws IOException {


        int iteration = 1;
        int[] ress = {999};


        String context =
                "resolution=9;" +
                        "n=15;"    +
                        "dt=bd0.1;" +
                        "ia=2.33;"  +
                        "ib=-1.59;" +
                        "wa=0.0043;" +
                        "wb=0.17;"  +
                        "tau=0.64;" +
                        "threshold=0.75;"+
                        "alpha=10.00;"+
                        "wrap=F;";




        for(int res : ress){


            String patSave = "save/focus"+res;
            Runtime.getRuntime().exec("mkdir "+patSave);
            String argss = "model=NSpike show=true context="+context+"resolution="+res+";" +
                    " savemap=Focus savepath=save/focus"+res+"/ scenario=load=competition.dnfs;"+
                    " it="+iteration+" cores=7";
            String[] args2 = argss.split(" ");
            Printer.runDNFSim(args2);
        }



    }


}