package main.resources.utils;

import java.io.Serializable;

public class Triplet implements Serializable{
	int popIndex;
	int indivNum;
	double fitness;

	public Triplet(int popIndex,int indivNum,double fitness){
		this.popIndex = popIndex;
		this.indivNum = indivNum;
		this.fitness = fitness;
	}
	
	public String toString(){
		return "#" + indivNum + " => " + fitness;
	}

	public int getPopIndex() {
		return popIndex;
	}

	public int getIndivNum() {
		return indivNum;
	}

	public double getFitness() {
		return fitness;
	}


}
