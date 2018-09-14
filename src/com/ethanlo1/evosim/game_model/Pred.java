package com.ethanlo1.evosim.game_model;

public class Pred {
	
	
	/**
	 * Method Summary
	 * 
	 * getReproCounter(): returns the int responsible for timing pred reproduction
	 * other getters and setters: set and get the variables
	 * reverseMovingRand(): toggle whether or not the pred is moving randomly or not
	 * setState(string): dictates what state the pred is in
	 * eat(): adds health to pred
	 * hunger():removes health from pred
	 * move(): changes the x and y coordinate
	 */
	
	private int maxHealth;
	private int health; 
	private double speed;
	private int eyesight;
	private int repro;

	
	private double x; //coordinates of predator
	private double y; 

	private double targetX; //where the predator is moving towards
	private double targetY;
	
	private String state = "search"; //how the pred decides how to move
	/*
	 * search - moving around randomly searching for prey
	 * chase - has seen target, and chases it
	 */
	
	private boolean movingRand = false; //tells whether the animal is moving towards a random target; or if it has reached it and needs a new random target.

	private int reproCounter = 1;
	
	public Pred(int maxHealth, int health, double speed, int eyesight, int repro, double x, double y){
		this.maxHealth = maxHealth;
		this.health = health;
		this.speed = speed;
		this.eyesight = eyesight;
		this.repro = repro;
		this.x = x;
		this.y = y;
		targetX = x;
		targetY = y;
		
	}
	

	////////////////////Getters///////////////////////////
	
	public int getReproCounter(){
		return reproCounter;
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	public int getHealth(){
		return health;
	}
	
	public double getBaseSpeed(){
		return speed;
	}
	
	public double getSpeed (){
		
		if(state.equals("search")){
			return speed;
		}
		
		if(state.equals("chase")){
			return speed * 3;
		}
		
		return 0;
	}
	
	public int getEyesight(){
		return eyesight;
	}
	
	public int getRepro(){
		return repro;
	}
	
	public double getX(){
		return x;
	
	}

	public double getY(){
		return y;
		
	}
	
	public double getTargetX(){
		return targetX;
	}
	
	public double getTargetY(){
		return targetY;
	}
	
	public String getState(){
		return state;
	}
	
	public boolean getMovingRand(){
		return movingRand;
	}

	
	//////////////////////setters////////////////////////
	
	public void reproCounterAdd1(){
		reproCounter++;
	}
	
	public void setX(double d){
		x = d;
	}
	
	public void setY(double d){
		y = d;
	}
	
	public void setTargetX(double x){
		targetX = x;
	}
	
	public void setTargetY(double y){
		targetY = y;
	}
	
	public void reverseMovingRand(){
		movingRand = !movingRand;
	}
	
	public void setState(String a){
		state = a;
		
		//System.out.println("Pred is " + a); //Test Case 2
	}
	
	public void eat(){ //adding health points when eating
		health = health + 200;
		
		if(health > maxHealth){ //dont want to go over max health
			health = maxHealth;
		}
		
	//	System.out.println("pred has eaten: health is " + health);
			
	}
	
	public void hunger(){
		if(state.equals("search")){
			health = health - 1;
		}
		if(state.equals("chase")){
			health = health - 4;
		} 
	}
	
	public void move(double dx, double dy){
		x = x + dx;
		y = y + dy;
	}

	
}

