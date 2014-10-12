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

package main.java.reservoirComputing;

import main.java.maps.*;
import main.java.space.*;
import main.java.unitModel.NARMAnthOrderUM;
import main.java.unitModel.RandTrajUnitModel;
import main.java.unitModel.TanhUM;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

/**
 * Interface accessed by script to set ESN parameters
 * Created by bchappet on 24/09/14.
 */
public  class ESNUtils {

    public static final String WEIGHTS_IR = "WeightsIR";
    public static final String WEIGHTS_RR = "WeightsRR";
    public static final String WEIGHTS_RO = "WeightsRO";

    public static final String INPUT = "input";
    public static final String RESERVOIR = "reservoir";
    public static final String OUTPUT = "output";
    public static final String TARGET_OUTPUT = "target_output";

    private static final String SEP = ",";
    private final Var<BigDecimal> dt;
    private final int lengthReservoir;
    private final boolean withInput;
    private final boolean squared;


    public ESNUtils(int lengthReservoir, Var<BigDecimal> dt,boolean withInput,boolean squared) {
        this.lengthReservoir = lengthReservoir;
        this.dt = dt;
        this.withInput = withInput;
        this.squared = squared;
    }

    public  String generateRandomWeights(double spectralRadius,double scale){
        String s = null;
        File workingDirectory = new File("src/main/java/reservoirComputing/scripts/rayonSpectral/");
        String file = "weights"+lengthReservoir+"_"+spectralRadius;
        String globalPath = "src/main/java/reservoirComputing/weights/";
        String returnedFile = globalPath + file ;
        String outFile = "../../weights/" + file +"_0.csv";
        try {
            Process p = Runtime.getRuntime().exec(
                    "./generateRandomScaledWeigth.sh " + lengthReservoir + " " + spectralRadius + " " + scale + " "  + outFile,
                    null,workingDirectory);
            BufferedReader bre = new BufferedReader
                    (new InputStreamReader(p.getErrorStream()));
            String line;
            while((line = bre.readLine()) != null){
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-2);
        }
        return returnedFile;
    }

    public  String generateSparseRandomWeights(double spectralRadius,double connectivity,double scale){
        String s = null;
      //  File workingDirectory = new File("src/main/java/reservoirComputing/scripts/rayonSpectral/");
        File workingDirectory = new File("src/main/java/reservoirComputing/scripts/rayonSpectral/");
        String file = "weightsSparse"+lengthReservoir+"_"+spectralRadius;
        String globalPath = "src/main/java/reservoirComputing/weights/";
        String returnedFile = globalPath + file ;
        String outFile = "../../weights/" + file +"_0.csv";
        try {
            Process p = Runtime.getRuntime().exec(
                    "./generateRandomSparseScaledWeigth.sh " + lengthReservoir + " " + spectralRadius + " "+connectivity+" " +scale+" "+ outFile,
                    null,workingDirectory);
            BufferedReader bre = new BufferedReader
                    (new InputStreamReader(p.getErrorStream()));
            String line;
            while((line = bre.readLine()) != null){
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
           // System.exit(-2);
        }
        return returnedFile;
    }

    public  String generateRandomRSDNFWeights(){
        //TODO
        return null;
    }

    public  Map readWeightFromFile(String fileName){
        Space spaceWeightsReservoir = new Space2D(lengthReservoir,lengthReservoir);
       Map weights = new MatrixCSVFileReader(WEIGHTS_RR, new InfiniteDt(),
               spaceWeightsReservoir, new Var<String>("fileWeightsRR",fileName));

        return weights;
    }


    /**
     * Construct input and targetOutput
     * @param order
     * @return an array with input and out
     **/
    public  MatrixDouble2D[] generateNARAMAIO(int order){

        Trajectory inputMem;
        inputMem =new Trajectory<Double>(INPUT, dt,
                new RandTrajUnitModel(0d),new Var<>("center",0.25),new Var<>("radius",0.25) );
        Double[] mem = new Double[order];
        for(int i = 0 ; i < order ; i++){
            mem[i] = Math.random()*0.5;
        }
        inputMem.addMemories(order,mem);

        MatrixDouble2D inputRes = new MatrixDouble2DWrapper(inputMem);

        Trajectory target = new Trajectory<Double>(TARGET_OUTPUT+"_tmp", dt, new NARMAnthOrderUM(0d));
        target.addParameters(inputMem,target,new Var<Integer>("Order",order));
        target.addMemories(order);

        MatrixDouble2D targetRes = new MatrixDouble2DWrapper(TARGET_OUTPUT,target);

        MatrixDouble2D[] IO = new MatrixDouble2D[]{inputRes,targetRes};
        return IO;

    }

    public  MatrixDouble2D[] generateFileIO(String fileIn,String fileOut,double inputScale){


        MatrixDouble2D inputRes  = null;
        try {
            inputRes = new VectorFileReaderMap(
                    ModelESN2.INPUT,dt, SpaceFactory.getSpace1D(fileIn),new Var<String>("fileIn",fileIn));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MatrixDouble2D normalize = new NormalisationMatrix(inputRes,new Var<Double>("inputScale",inputScale));

        MatrixDouble2D tgtOut = null;
        try {
            tgtOut  = new VectorFileReaderMap(
                    TARGET_OUTPUT,dt,SpaceFactory.getSpace1D(fileOut),new Var<String>("fileTargetOut",fileOut));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MatrixDouble2D[] IO = new MatrixDouble2D[]{normalize,tgtOut};
        return IO;
    }

    /**

     * * @postCond addParameters : reservoir, target , input (if needed)
     */
    public  LearningWeightMatrix generateRidgeRegressionLearningMatrix(double regFactor){


        Var<Integer> lengthResVar = new Var<Integer>("lengthRes", lengthReservoir);
        Space2D space = new Space2D(lengthResVar, new Var<Integer>(1));


        LearningWeightMatrix weightsRO = null;
        if(!withInput) {

            weightsRO =
                    new RidgeRegressionLearningWeightMatrix(
                            WEIGHTS_RO, dt, space,
                            new Var<Double>("regulazisationFactor", regFactor)
                    );
        }else {
            throw new Error("Not yet implemented");
        }

        weightsRO.setSquare(squared);

        return weightsRO;

    }

    /**
     * Return the size of output weights : (resSize + inputSize) * 2 (if Squared)
     * @return
     */
    public  int computeLengthWeights(){
        int input = withInput ? 1 : 0;
        int squaredInt = squared ? 2 : 1;

        return (lengthReservoir + input) * squaredInt;
    }

    /**
     *

     * @return
     * @postCond addParameters : reservoir, target , input (if needed)
     */
    public   LearningWeightMatrix generatePseudoInverseLearningMatrix(){
        int lengthWeights = this.computeLengthWeights();
        Var<Integer> lengthResVar = new Var<Integer>("lengthRes", lengthWeights);
        Space2D space =  new Space2D(lengthResVar,new Var<Integer>(1));

        LearningWeightMatrix weightsRO = null;
        if(!withInput) {
            weightsRO =
                    new PseudoInverseLearningWeightMatrix(
                            WEIGHTS_RO, dt, space);
        }else{
            weightsRO =
                    new PseudoInverseLearningWeightMatrixWithInput(
                            WEIGHTS_RO, dt, space);
        }

        weightsRO.setSquare(squared);


        return weightsRO;
    }

    public  Map getWeightsInput(){
        Space1D spaceReservoir = new Space1D(lengthReservoir);
        Map  weightsIR = new TransposedMatrix(new  MatrixDouble2DWrapper(
                new UnitMap(WEIGHTS_IR,new InfiniteDt(),
                        spaceReservoir,new RandTrajUnitModel(0d), new Var<Double>(0d),new Var<Double>(0.1d))));

        return weightsIR;

    }

    /**
     *

     * @param leak leak of the neurons in 0-1, if 1 : no memory if 0 : static neurons
     * @param noise noise (optional set to 0 to remove)
     * @return
     * @postCond add parameters in this order:reservoir,R*Wr, I*Wi
     */
    public  UnitMap getTanhReservoir( double leak, double noise){
        Space1D spaceReservoir = new Space1D(lengthReservoir);

        Parameter noiseP;//noise parameter
        Var<Double> noiseVar = new Var("NoiseAmp",noise);
        if(noise > 0d){
            //Construct noise map
            noiseP = new UnitMap("NoiseMap",dt,spaceReservoir,new RandTrajUnitModel(0.),
                    new Var(0),noiseVar);
        }else{
            noiseP = noiseVar;
        }

        UnitMap reservoir = new UnitMap(RESERVOIR,dt,spaceReservoir,new LeakyTanHReservoirNeuronUM(0d),
                new Var("Leak",leak),noiseP);
        return  reservoir;
    }

    /**
     *@param tanh
     * @param targetOutput
     * @return the presignal of the targetOutput (apply tanh⁻¹ is asked)
     */
    public Map getTargetOutputPresignal(Map targetOutput,boolean tanh){
        Map tartgetOutputPresignal;
        if(tanh) {
            tartgetOutputPresignal = new UnitMap(TARGET_OUTPUT + "_preSignal", dt, new NoDimSpace(), new TanhInvUM(0d),targetOutput);
        }else{
            tartgetOutputPresignal = targetOutput;
        }
        return tartgetOutputPresignal;
    }

    /**
     * Apply tanh to preoutput if asked
     * @param outputPreSignal
     * @param tanh
     * @return
     */
    public Map getOutput(Map outputPreSignal,boolean tanh){
        Map output;
        if(tanh) {
           output =  new UnitMap(OUTPUT,dt,new NoDimSpace(),new TanhUM(0d),outputPreSignal);
        }else{
            output = outputPreSignal;
        }
        return output;
    }



    /**
     * Get X to compute the output
     * //We are using X squares to compute the output: u,x1,x2...u²,x1²,x2²...
     * @return
     */
    public Map getOutputPresignal(Map input, Map matReservoir,Map weightsRO){

        MatrixDouble2D X;
        if(withInput) {
            X = new HorizontalConcatenationMatrix((Map) input, matReservoir);
        }else{
            if(matReservoir instanceof MatrixDouble2D)
                X = (MatrixDouble2D)matReservoir;
            else
                X = new MatrixDouble2DWrapper(matReservoir);
        }

        if(squared) {
            MatrixDouble2D Xsquared = new ToSquareMatrix(X);
            X = new HorizontalConcatenationMatrix(X, Xsquared);

        }

         X = new TransposedMatrix(X);

        MultiplicationMatrix outputPreSignal =
                new MultiplicationMatrix(OUTPUT+"_lin",dt,new NoDimSpace(),weightsRO,X);

        return outputPreSignal;
    }

}
