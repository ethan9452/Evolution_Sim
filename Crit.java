
public class Crit {
	
	/**
	 * Method Summary
	 * 
	 * getReproCounter(): returns the int responsible for timing critter reproduction
	 * other getters and setters: set and get the variables
	 * reverseMovingRand(): toggle whether or not the crit is moving randomly or not
	 * setState(string): dictates what state the critter is in
	 * eat(): adds health to critter
	 * hunger():removes health from critter
	 * move(): changes the x and y coordinate
	 */

	private int maxHealth;//critter's maximum health
	private int health;//current health
	private double guts; //whether critter will go for food or not when hungry: between 0-1, 1 is gung ho, 0 is weenie
	private double speed;
	private int eyesight;//how far away plants can be for the crit/planteater to detect it (and move towards it)
	private int repro;// speed of reproduction


	private double x; //coordinates of critter
	private double y; 

	private double targetX; //where the critter is moving towards
	private double targetY;

	private String state = "idle";//"idle" , "search" , "run"
	/* the "state" represents what the animal's brain is telling it to do:
		-idle - in cave and not hungry -or- just eaten and returning home
		-search: hungry - searching for food- walks around randomly until it finds food
		-run: see's predator: running away from pred
	 */
	private boolean movingRand = false; //tells whether the animal is moving towards a random target; or if it has reached it and needs a new random target.
	private int directionPref; //this is the natural direction the crit turns when it is stuck in a corner
	
	private int reproCounter = 0;
	
	Crit(int maxHealth, int health, double guts, double speed, int eyesight, int repro, double x, double y){

		this.maxHealth = maxHealth;
		this.health = health;
		this.guts = guts;
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

	public double getGuts(){
		return guts;
	}
	
	public double getBaseSpeed(){
		return speed;
	}

	public double getSpeed(){
		if(state.equals("idle")){
			return speed;
		}
		
		if(state.equals("search")){
			return speed * 2;
		}
		
		if(state.equals("run")){
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
	
	public int getDirectionPref(){
		return directionPref;
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
		
		//System.out.println("Critter is " + a); 
	}

	public void eat(){ //adding health points when eating
		health = health + 200;

		if(health > maxHealth){ //dont want to go over max health
			health = maxHealth;
		}
		
	//	System.out.print("Critter has eaten. Health: ");
	//	System.out.println(health);
	}

	public void hunger(){	
		if(state.equals("idle")){
			health = health - 1;
		}
		
		if(state.equals("search")){
			health = health - 2;
		}
		
		if(state.equals("run")){
			health = health - 4;
		}
		
	}
	
	public void move(double dx, double dy){
		x = x + dx;
		y = y + dy;
	}


}
