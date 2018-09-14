package com.ethanlo1.evosim.game_model;

public class Food {
	
	private double x;
	private double y;
	
	private int reproCounter = 0;

	
	
	public Food(double xx, double yy){
		x = xx;
		y = yy;
		
	}
	
	
	////////////////////Getters///////////////////////////
	public double getX(){
		return x;
	
	}

	public double getY(){
		return y;
		
	}
	
	public void reproCounterPlus(){
		reproCounter++;
	}
	
	public void resetReproCounter(){
		reproCounter = 0;
	}


	public int getReproCounter() {
		return reproCounter;
	}


	public void setReproCounter(int reproCounter) {
		this.reproCounter = reproCounter;
	}
	

}
