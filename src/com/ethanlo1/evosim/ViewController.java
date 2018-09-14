package com.ethanlo1.evosim;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.ethanlo1.evosim.data.Graph;
import com.ethanlo1.evosim.game_model.Crit;
import com.ethanlo1.evosim.game_model.Food;
import com.ethanlo1.evosim.game_model.Pred;

/** 
 * Method Summary
 * 
 * noTimer(): runs several thousand cycles of the simulation and displays the graphs of the info
 * reproduce(): checks if the animals are due to reproduce
 * movement(): calls the individual animals' movement methods
 * decision(): tells each animal where to move towards
 * checkCollForEat(): checks to see if the predator and crit or crit and food are close enough to eat
 * checkBounds(): makes sure animals do not leave the screen
 * hunger(): continuously removes health from the animals
 * checkIfAlive(): checks to see if each animal has health above 0, if not removes it
 * makeFood(): creates a new instance of Food
 * critAnalysis(): enters in data about the animals' attributes and stores them to a ArrayLists
 * stopSimRetData(): pauses the simulation and displays graphs showing how the animal's attributes have changed
 * over time
 */



public class ViewController extends JPanel implements ActionListener, MouseListener, MouseMotionListener{

	ArrayList<Crit> crit = new ArrayList<Crit>(0);
	ArrayList<Food> food = new ArrayList<Food>(0);
	ArrayList<Pred> pred = new ArrayList<Pred>(0);

	ArrayList<Double> foodPop = new ArrayList<Double>(); //stores number of foods

	ArrayList<Double> maxHealth = new ArrayList<Double>(); //stores average max health of the critter population
	ArrayList<Double> health = new ArrayList<Double>(); //stores average health of the critter population
	ArrayList<Double> speed = new ArrayList<Double>(); //stores average speed of the critter population
	ArrayList<Double> guts = new ArrayList<Double>(); //stores average guts of the critter population
	ArrayList<Double> eyesight = new ArrayList<Double>(); //stores average eyesight of the critter population
	ArrayList<Double> repro = new ArrayList<Double>(); //stores average reproduction time of the critter population
	ArrayList<Double> pop = new ArrayList<Double>(); //stores population of critters

	ArrayList<Double> predMaxHealth = new ArrayList<Double>(); //stores avg max health of preds
	ArrayList<Double> predHealth = new ArrayList<Double>(); //stores avg health of preds
	ArrayList<Double> predSpeed = new ArrayList<Double>(); //stores avg speed of preds
	ArrayList<Double> predEyesight = new ArrayList<Double>(); //stores avg eyesight of preds
	ArrayList<Double> predRepro = new ArrayList<Double>(); //stores avg repro time of preds
	ArrayList<Double> predPop = new ArrayList<Double>(); //stores pop size of preds


	//timer and timer counters
	Timer timer;
	int ttt = 0; //timer count (right now its for food)
	int tt = 0;//timer count( for random movement of crit when stuck on wall)
	int t = 1; //for  and hunger
	int time = 0;

	//TODO
	int noTimerCount = 100000; // how many cycles to run when the noTimer method is called..
	// if noTimerCount is too high, program crashes ;(




	//setting the conditions of the simulation//
	int timerSpeed = 5;

	int foodRate = 70;//determines how long until another food is made
	// final int INITFOOD = 1;//how many foods are on the map to start. only start with one

	int initCritPop = 25;//the initial number of critters(plant eaters)
	int initCritMaxHealth = 1000;
	int initCritHealth = 1000;
	double initCritGuts = .7;
	double initCritSpeed = 1;
	int initCritEyesight = 30;
	int initCritRepro = 600;
	double initCritX = 100;
	double initCritY = 350;



	int initPredPop = 3;//the initial number of predators
	int initPredMaxHealth = 3000;
	int initPredHealth = 3000;
	double initPredSpeed = 3.5;
	int initPredEyesight = 120;
	int initPredrepro = 500;
	double initPredX = 700;
	double initPredY = 700;


	public ViewController(){

		timer = new Timer(timerSpeed,this);
		addMouseListener(this);
		addMouseMotionListener(this);


		for(int i = 0; i < initCritPop; i++){
			crit.add(new Crit(initCritMaxHealth,initCritHealth,initCritGuts,initCritSpeed,initCritEyesight,initCritRepro,initCritX,initCritY)); //int maxHealth, double guts, double speed, int eyesight, int repro, double x, double y
		}


		for(int i = 0; i < initPredPop; i++){
			pred.add(new Pred(initPredMaxHealth,initPredHealth, initPredSpeed, initPredEyesight, initPredrepro, initPredX, initPredY)); //int maxHealth, double speed, int eyesight, int repro, double x, double y
		}


		food.add(new Food(400., 400.));



		//TODO choose run method
		timer.start();
		//noTimer(); //runs a certain number of cycles. spits out the graphs


	}

	////////////////Run Sim Without Timer///////////////
	public void noTimer(){
		for(int r = 0; r < noTimerCount; r++){
			//Increasing the timer counters
			ttt++;
			tt++;
			t++;
			time++;
			if(tt == 800){
				tt = 0;
			}
			for(int i = 0; i < crit.size(); i++){
				crit.get(i).reproCounterAdd1();
			}
			for(int i = 0; i < pred.size(); i++){
				pred.get(i).reproCounterAdd1();
			}
			for(int i = 0; i<food.size(); i++){
				food.get(i).reproCounterPlus();
			}


			decision();//like the ''brain'' of the animals: how the animals decide where to move and what "state" they are in. "state" determines the speed and how fast the animal loses health.
			hunger();//removes some health to simulate hunger
			checkIfAlive();//checks to see if the animal is alive-if not then removes it
			checkCollForEat();//if pred touches crit, crit gets eaten; if crit touches plant, plant gets eaten
			movement();
			makeFood(); //makes a piece of food for critters to eat
			checkBounds();//make sure animals dont run off the screen
			reproduce();
			critAnalysis();//analyzizes the data, and saves it to a data structure so one can see the changes over time
			//setVisible(false);

			System.out.println("Cycle: " + r); 
		}
		stopSimRetData();
	}


	////////////////////////////paint////////////////////////
	public void paint(Graphics g){
		super.paint(g);

		//Menu Bar//

		g.setColor(Color.white);
		g.fillRect(700, 0, 400, 700);
		g.setColor(Color.black);
		g.drawString("MENU!!", 770, 50);

		g.drawString("pause", 750, 200);
		g.drawString("play", 800, 200);

		g.setColor(Color.red);
		g.fillRect(710, 80, 220, 30);

		g.setColor(Color.black);
		g.drawRect(720, 85, 205, 20);//stop sim and spit out data
		g.drawString("stop sim and show data graphs", 725, 100);

		g.drawRect(745, 185, 40, 20);//pause

		g.drawRect(795, 185, 40, 20);//play

		g.drawString("Time: " + time + "units", 720, 640);

		//	g.drawString("click and drag to draw PREDATORS" , 700, 400);
		
		//Explination of Sim//
		g.setColor(Color.black);
		g.drawString("Brief Explanation------------------oo", 725, 230);
		g.drawString("-This is a simulation of an ecosystem", 735, 260);
		g.drawString("-The black circles: Predators", 735, 280);
		g.drawString("-The blue circles: Plant-eaters", 735, 300);
		g.drawString("-The green sqruares: Plants", 735, 320);
		g.drawString("-Predators eat plant eaters, plant ", 735, 340);
		g.drawString("eaters eat plants", 735, 360);
		g.drawString("-All 3 organisms go through asexual", 735, 380);
		g.drawString("reproduction", 735, 400);
		g.drawString("-The Classes representing each of the", 735, 420);
		g.drawString("3 organisms have fields that represent", 735, 440);
		g.drawString("genetic traits such as speed, speed of ", 735, 460);
		g.drawString("reproduction, and eyesight. ", 735, 480);
		g.drawString("-For predators and plant eaters, their ", 735, 500);
		g.drawString("offspring inherit a slightly different  ", 735, 520);
		g.drawString("version of their parent's traits ", 735, 540);
		g.drawString("-Goal of the simulation is to see if", 735, 560);
		g.drawString("over time, the traits of a species change ", 735, 580);
		g.drawString("to enhance survival ", 735, 600);
		g.drawString(" ", 735, 620);
		g.drawString(" ", 735, 640);




		//SafeZone//
		g.setColor(Color.orange);
		g.fillRect(0,0,120,700);


		//Predators//
		for (int i = 0; i<pred.size(); i++){
			g.setColor(Color.black);
			g.fillOval((int)(pred.get(i)).getX() - 30, (int)(pred.get(i)).getY() - 30, 60, 60);
			g.setColor(Color.white);
			String health = "" + pred.get(i).getHealth();
			g.drawString(health, (int)(pred.get(i)).getX() - 15, (int)(pred.get(i)).getY() + 5);
		}

		//Critters//
		for (int i = 0; i<crit.size(); i++){
			g.setColor(Color.blue);
			g.fillOval((int)(crit.get(i)).getX() - 20, (int)(crit.get(i)).getY() - 20, 40, 40);
			String health = "" + crit.get(i).getHealth();
			g.setColor(Color.white);
			g.drawString(health, (int)(crit.get(i)).getX() - 12, (int)(crit.get(i)).getY() + 5);
		}

		//Food//
		for (int i = 0; i < food.size(); i++){
			g.setColor(Color.GREEN);
			g.fillRect((int)(food.get(i).getX() - 7.5), (int)(food.get(i).getY() - 7.5), 15, 15);
		}
	}


	////////////////////Timer//////////////////////////
	public void actionPerformed(ActionEvent e) {

		//Increasing the timer counters
		ttt++;
		tt++;
		t++;
		time++;
		if(tt == 800){
			tt = 0;
		}
		for(int i = 0; i < crit.size(); i++){
			crit.get(i).reproCounterAdd1();
		}
		for(int i = 0; i < pred.size(); i++){
			pred.get(i).reproCounterAdd1();
		}
		for(int i = 0; i<food.size(); i++){
			food.get(i).reproCounterPlus();
		}


		//play around with crit pop
		//turn on and off methods--- see if certian methods make the program slower

		decision();//like the ''brain'' of the animals: how the animals decide where to move and what "state" they are in. "state" determines the speed and how fast the animal loses health.
		hunger();//removes some health to simulate hunger
		checkIfAlive();//checks to see if the animal is alive-if not then removes it
		checkCollForEat();//if pred touches crit, crit gets eaten; if crit touches plant, plant gets eaten
		movement();
		makeFood(); //makes a piece of food for critters to eat
		checkBounds();//make sure animals dont run off the screen
		reproduce();
		critAnalysis();//analyzizes the data, and saves it to a data structure so one can see the changes over time
		repaint();

		//for(int i = 0; i < crit.size(); i++){
		//			System.out.println(crit.get(i).getBaseSpeed());
		//	}

		//System.out.println(crit.size());

	}

	public void reproduce(){
		int critSize = crit.size();
		for(int i = 0; i < critSize; i++){
			if(crit.get(i).getReproCounter()%crit.get(i).getRepro() ==0 ){
				//System.out.println("right time");
				if(((double)crit.get(i).getHealth())/((double)crit.get(i).getMaxHealth()) > .5){
					//System.out.println("right health");
					//System.out.println(crit.size());	
					int newMaxHealth = (int) (crit.get(i).getMaxHealth() - 50 + (Math.random()*100));
					double newGuts = crit.get(i).getGuts() - .1 + (Math.random()*.2);
					double newSpeed = crit.get(i).getBaseSpeed() - .2 + (Math.random()*.4);
					int newEyesight = (int) (crit.get(i).getEyesight() - 10 + (Math.random()*20));
					int newRepro = (int) (crit.get(i).getRepro() - 50 + (Math.random()*100));
					if(newRepro < 100){
						newRepro = 100;
					}
					double newX = crit.get(i).getX();
					double newY = crit.get(i).getY();
					//int maxHealth, double guts, double speed, int eyesight, int repro, double x, double y
					crit.add(new Crit(newMaxHealth, (int)(crit.get(i).getHealth()*.7), newGuts, newSpeed, newEyesight, newRepro, newX, newY));		
				}
			}
		}

		//System.out.println((pred.get(0).getReproCounter());

		int predSize = pred.size();
		for(int i = 0; i < predSize; i++){
			if(pred.get(i).getReproCounter()%pred.get(i).getRepro() ==0 ){
				//System.out.println("right time");
				if(((double)pred.get(i).getHealth())/((double)pred.get(i).getMaxHealth()) > .5){
					//System.out.println("right health");
					//System.out.println(crit.size());	
					int newMaxHealth = (int) (pred.get(i).getMaxHealth() - 50 + (Math.random()*100));

					double newSpeed = pred.get(i).getBaseSpeed() - .2 + (Math.random()*.4);
					int newEyesight = (int) (pred.get(i).getEyesight() - 10 + (Math.random()*20));
					int newRepro = (int) (pred.get(i).getRepro() - 50 + (Math.random()*100));
					if(newRepro < 100){
						newRepro = 100;
					}
					double newX = pred.get(i).getX();
					double newY = pred.get(i).getY();
					//int maxHealth, double speed, int eyesight, int repro, double x, double y
					pred.add(new Pred(newMaxHealth, (int)(pred.get(i).getHealth()*.7), newSpeed, newEyesight, newRepro, newX, newY));		

					//	pred.add(new Pred(initPredMaxHealth, (int)(pred.get(i).getHealth()*.7), initPredSpeed, initPredEyesight, initPredrepro, newX, newY));		

				}
			}
		}



	}

	public void movement(){ //method for the animals to move
		for (int i = 0; i<crit.size(); i++){
			double dx;
			double dy;
			double distance = Math.sqrt((crit.get(i).getTargetX()-crit.get(i).getX())*(crit.get(i).getTargetX()-crit.get(i).getX()) + (crit.get(i).getTargetY()-crit.get(i).getY())*(crit.get(i).getTargetY()-crit.get(i).getY()));

			dx = computeCritDX (crit, distance, i);
			dy = computeCritDY (crit, distance, i);

			crit.get(i).move(dx, dy);

		}
		for (int i = 0; i<pred.size(); i++){
			double dx;
			double dy;

			double distance = Math.sqrt((pred.get(i).getTargetX()-pred.get(i).getX())*(pred.get(i).getTargetX()-pred.get(i).getX()) + (pred.get(i).getTargetY()-pred.get(i).getY())*(pred.get(i).getTargetY()-pred.get(i).getY()));

			dx = computePredDX(pred, distance, i);
			dy = computePredDY(pred, distance, i);
			pred.get(i).move(dx, dy);

		}
	}


	public void decision(){ //decides what the animals do
		for (int i = 0; i<crit.size(); i++){
			// variables: health, guts, 
			double hp = ((double)crit.get(i).getHealth()/(double)crit.get(i).getMaxHealth()); // a proportion

			if(hp < crit.get(i).getGuts()){ //search mode
				crit.get(i).setState("search");

				if(crit.get(i).getMovingRand() == false){ //not moving randomly right now
					crit.get(i).reverseMovingRand();
					crit.get(i).setTargetX(120 + 500*Math.random());
					crit.get(i).setTargetY(25 + 655*Math.random());	
				}

				if(Math.abs(crit.get(i).getTargetX() - crit.get(i).getX()) <= 5 &&Math.abs(crit.get(i).getTargetY() - crit.get(i).getY()) <= 5){
					crit.get(i).reverseMovingRand();
				}
			}

			if(hp > crit.get(i).getGuts()){ // the crit is full
				crit.get(i).setState("idle");
				crit.get(i).setTargetX(50);

			}

			if(crit.get(i).getState().equals("search") == true){ //when to get food
				int r = -234; //index of closest plant
				double d = 100000000; //smallest distance
				for(int j = 0; j<food.size(); j++){
					double distance = Math.sqrt((crit.get(i).getX()-food.get(j).getX())*(crit.get(i).getX()-food.get(j).getX())+(crit.get(i).getY()-food.get(j).getY())*(crit.get(i).getY()-food.get(j).getY()));
					if(distance <= d){
						r = j;
						d = distance;
					}
				}

				if(d <= crit.get(i).getEyesight()){
					crit.get(i).setTargetX(food.get(r).getX());
					crit.get(i).setTargetY(food.get(r).getY());
				}

			}

			double dP = 123123; // distnace from predator
			int p = -234; //index of closest predator
			for(int j = 0; j<pred.size(); j++){
				double distance = Math.sqrt((crit.get(i).getX()-pred.get(j).getX())*(crit.get(i).getX()-pred.get(j).getX())+(crit.get(i).getY()-pred.get(j).getY())*(crit.get(i).getY()-pred.get(j).getY()));
				if(dP > distance){
					dP = distance;
					p = j;
				}
			}

			if(dP <= crit.get(i).getEyesight()){
				crit.get(i).setState("run");
				crit.get(i).setTargetX(crit.get(i).getX() - (pred.get(p).getX() - crit.get(i).getX()));
				crit.get(i).setTargetY(crit.get(i).getY() - (pred.get(p).getY() - crit.get(i).getY()));

				if(crit.get(i).getY() > 645){
					crit.get(i).setTargetY(645);
					crit.get(i).setTargetY(0);
				}

				if(crit.get(i).getY() < 25){
					crit.get(i).setTargetY(25);
					crit.get(i).setTargetY(0);
				}

				if(crit.get(i).getX() > 675){
					crit.get(i).setTargetX(675);
					crit.get(i).setTargetY(400 - tt);
				}

				if(crit.get(i).getX() < 150){
					crit.get(i).setTargetY(400 - tt);
				}

			}
		}


		for (int i = 0; i<pred.size(); i++){

			int r = -234; //index of closest crit
			double d = 100000000; //smallest distance
			for(int j = 0; j<crit.size(); j++){
				double distance = Math.sqrt((pred.get(i).getX()-crit.get(j).getX())*(pred.get(i).getX()-crit.get(j).getX())+(pred.get(i).getY()-crit.get(j).getY())*(pred.get(i).getY()-crit.get(j).getY()));
				if(distance <= d){
					r = j;
					d = distance;
				}
			}

			if(d <= pred.get(i).getEyesight()){ //pred can see crit
				pred.get(i).setTargetX(crit.get(r).getX());
				pred.get(i).setTargetY(crit.get(r).getY());
			}

			else if(pred.get(i).getMovingRand() == false){ //can't see crit. has to choose new random target
				pred.get(i).reverseMovingRand();
				pred.get(i).setTargetX(100 + 600*Math.random());
				pred.get(i).setTargetY(700*Math.random());	
			}

			if(Math.abs(pred.get(i).getTargetX() - pred.get(i).getX()) <= 5 &&Math.abs(pred.get(i).getTargetY() - pred.get(i).getY()) <= 5){ //pred has reached randomly assigned target
				pred.get(i).reverseMovingRand();
			}
		}
	}


	public void checkCollForEat(){//checks to see if pred catches and eats crit, and if crit reaches and eats food
		//for loops runs for each crit/pred. for each crit/pred, for loops run for each thing the crit/pred could possibly eat.
		for(int i = 0; i<crit.size(); i++){
			for(int j = 0; j<food.size(); j++){
				if(Math.abs(crit.get(i).getX() - food.get(j).getX()) < 10 && Math.abs(crit.get(i).getY() - food.get(j).getY()) < 10){
					crit.get(i).eat();
					food.remove(j);

				}
			}
		}

		for(int i = 0; i<pred.size(); i++){
			for(int j = 0; j<crit.size(); j++){
				if(Math.abs(pred.get(i).getX() - crit.get(j).getX()) < 10 && Math.abs(pred.get(i).getY() - crit.get(j).getY()) < 10){
					pred.get(i).eat();
					crit.remove(j);

				}
			}
		}
	}


	public void checkBounds(){
		for(int i = 0; i < crit.size(); i++){
			if(crit.get(i).getX() < 20){
				crit.get(i).setX(22);
				crit.get(i).setTargetX(22);
			}

			if(crit.get(i).getX() > 675){
				crit.get(i).setX(673);
				crit.get(i).setTargetX(673);
			}

			if(crit.get(i).getY() > 650){
				crit.get(i).setY(648);
				crit.get(i).setTargetY(648);
			}

			if(crit.get(i).getY() < 20){
				crit.get(i).setY(22);
				crit.get(i).setTargetY(22);
			}
		}


		for(int i = 0; i < pred.size(); i++){
			if(pred.get(i).getX() < 150){
				pred.get(i).setX(152);
				pred.get(i).setTargetX(152);
			}

			if(pred.get(i).getX() > 675){
				pred.get(i).setX(673);
				pred.get(i).setTargetX(673);
			}

			if(pred.get(i).getY() > 650){
				pred.get(i).setY(648);
				pred.get(i).setTargetY(648);
			}

			if(pred.get(i).getY() < 20){
				pred.get(i).setY(22);
				pred.get(i).setTargetY(22);
			}
		}

	}

	public void hunger(){//health was dropping too fast in relation to food appearance, so run hunger half as often
		if(t%2 == 0){
			for (int i = 0; i<crit.size(); i++){
				crit.get(i).hunger();
			}

			for (int i = 0; i<pred.size(); i++){
				pred.get(i).hunger();
			}
		}
	}

	public void checkIfAlive(){
		for (int i = 0; i<crit.size(); i++){
			if(crit.get(i).getHealth() < 0){
				crit.remove(i); 
				//System.out.println("crit has died");
			}
		}

		for (int j = 0; j<pred.size(); j++){
			if(pred.get(j).getHealth() < 0){
				pred.remove(j); 
			}
		}
	}

	public void makeFood(){ //TODO



		for(int i = 0; i < food.size(); i++){
			boolean occupied = false;
			if(food.get(i).getReproCounter() >= foodRate){
				if(Math.random() < .25){ // new food to right
					for(int j = 0; j < food.size(); j++){
						if(food.get(j).getX() == food.get(i).getX() + 20  && food.get(j).getY() == food.get(i).getY()){
							occupied = true;
						}
					}
					if(!occupied && food.get(i).getX() + 20 < 650){
						food.add(new Food(food.get(i).getX() + 20, food.get(i).getY()));
					}


				}
				else if(Math.random() < .5){ // new food to left 
					for(int j = 0; j < food.size(); j++){
						if(food.get(j).getX() == food.get(i).getX() -20  && food.get(j).getY() == food.get(i).getY()){
							occupied = true;
						}
					}
					if(!occupied && food.get(i).getX() - 20 > 130){
						food.add(new Food(food.get(i).getX() - 20, food.get(i).getY()));
					}
				}
				else if(Math.random() < .75){ // new food placed below
					for(int j = 0; j < food.size(); j++){
						if(food.get(j).getX() == food.get(i).getX()  && food.get(j).getY() == food.get(i).getY() + 20){
							occupied = true;
						}
					}
					if(!occupied && food.get(i).getY() + 20 < 650){
						food.add(new Food(food.get(i).getX(), food.get(i).getY() + 20));
					}
				}
				else{  //new food placed above
					for(int j = 0; j < food.size(); j++){
						if(food.get(j).getX() == food.get(i).getX() && food.get(j).getY() == food.get(i).getY() - 20){
							occupied = true;
						}
					}
					if(!occupied && food.get(i).getY() - 20 > 30){
						food.add(new Food(food.get(i).getX(), food.get(i).getY() - 20));
					}
				}

				if(!occupied){
					food.get(i).resetReproCounter();
					occupied = false;
				}
			}
		}


	}

	public double computeCritDX (ArrayList<Crit> crit, double distance, int i){
		if(distance <= 5){
			return 0;
		}

		if(distance < crit.get(i).getSpeed()){//if the crit will overshoot target
			return crit.get(i).getTargetX()-crit.get(i).getX();
		}

		return ((crit.get(i).getTargetX()-crit.get(i).getX()) / distance)*crit.get(i).getSpeed();
	}

	public double computeCritDY (ArrayList<Crit> crit, double distance, int i){
		if(distance <= 5){
			return 0;
		}

		if(distance < crit.get(i).getSpeed()){
			return crit.get(i).getTargetY()-crit.get(i).getY();
		}

		return ((crit.get(i).getTargetY()-crit.get(i).getY()) / distance)*crit.get(i).getSpeed();
	}

	public double computePredDX (ArrayList<Pred> pred, double distance, int i){
		if(distance <= 5){
			return 0;
		}

		return ((pred.get(i).getTargetX()-pred.get(i).getX()) / distance)*pred.get(i).getSpeed();
	}

	public double computePredDY (ArrayList<Pred> pred, double distance, int i){
		if(distance <= 5){
			return 0;
		}

		return ((pred.get(i).getTargetY()-pred.get(i).getY()) / distance)*pred.get(i).getSpeed();
	}


	//////////////Analysis//////////////
	public void critAnalysis(){
		//analysis for food//

		foodPop.add((double)food.size());

		//Analysis for critter//
		pop.add((double)crit.size());

		double avgSpeed = 0;
		for(int i = 0; i<crit.size(); i++){
			avgSpeed = avgSpeed + crit.get(i).getBaseSpeed();
		}
		avgSpeed = avgSpeed/crit.size();
		speed.add(avgSpeed);
		if(t%100 == 0){
			//	System.out.println(avgSpeed);
		}

		double avgHealth = 0;
		for(int i = 0; i<crit.size(); i++){
			avgHealth = avgHealth + crit.get(i).getHealth();
		}
		avgHealth = avgHealth/crit.size(); 
		health.add(avgHealth);
		if(t%100 == 0){
			//System.out.println(avgHealth);
		}

		double avgMaxHealth = 0;
		for(int i = 0; i<crit.size(); i++){
			avgMaxHealth = avgMaxHealth + crit.get(i).getMaxHealth();
		}
		avgMaxHealth = avgMaxHealth/crit.size();
		maxHealth.add(avgMaxHealth);
		if(t%100 == 0){
			//System.out.println(maxHealth);
		}

		double avgGuts = 0;
		for(int i = 0; i<crit.size(); i++){
			avgGuts = avgGuts + crit.get(i).getGuts();
		}
		avgGuts = avgGuts/crit.size();
		guts.add(avgGuts);
		if(t%100 == 0){
			//System.out.println(avgGuts);
		}

		double avgEyesight = 0;
		for(int i = 0; i<crit.size(); i++){
			avgEyesight = avgEyesight + crit.get(i).getEyesight();
		}
		avgEyesight = avgEyesight/crit.size();
		eyesight.add(avgEyesight);
		if(t%100 == 0){
			//System.out.println(avgEyesight);
		}

		double avgRepro = 0;
		for(int i = 0; i<crit.size(); i++){
			avgRepro = avgRepro + crit.get(i).getRepro();
		}
		avgRepro = avgRepro/crit.size();
		repro.add(avgRepro);
		if(t%100 == 0){
			//System.out.println(avgRepro);
		}

		//pred anal//
		double c = 0;
		for(int i = 0; i < pred.size(); i++){
			c = c + pred.get(i).getMaxHealth();
		}
		c = c/pred.size();
		predMaxHealth.add(c);

		c = 0;
		for(int i = 0; i < pred.size(); i++){
			c = c + pred.get(i).getHealth();
		}
		c = c/pred.size();
		predHealth.add(c);

		c = 0;
		for(int i = 0; i < pred.size(); i++){
			c = c + pred.get(i).getSpeed();
		}
		c = c/pred.size();
		predSpeed.add(c);

		c = 0;
		for(int i = 0; i < pred.size(); i++){
			c = c + pred.get(i).getEyesight();
		}
		c = c/pred.size();
		predEyesight.add(c);

		c = 0;
		for(int i = 0; i < pred.size(); i++){
			c = c + pred.get(i).getRepro();
		}
		c = c/pred.size();
		predRepro.add(c);

		predPop.add((double)pred.size());

	}




	public void stopSimRetData(){//stops the simulation and spits out the data
		timer.stop();

		/*
		 * 
		 * input the graph constructor takes: String title, ArrayList data
		 */


		new Graph("Average Max Health of Critters Over Time" , maxHealth);
		new Graph("Average Health of Critters Over Time" , health);
		new Graph("Average Speed of Critters Over Time" , speed);
		new Graph("Average Guts of Critters Over Time" , guts);
		new Graph("Average Eyesight of Critters Over Time" , eyesight);
		new Graph("Average Reproduction Time of Critters Over Time" , repro);
		new Graph("Population Size of Critters Over Time" , pop);

		new Graph("Average Max Health of Predetors Over Time", predMaxHealth);
		new Graph("Average Health of Predators Over Time", predHealth);
		new Graph("Average Speed of Predators Over Time", predSpeed);
		new Graph("Average Eyesight of Predators Over Time", predEyesight);
		new Graph("Average Reproduction Time of Predatprs Over Time", predRepro);
		new Graph("Population Size of Predators Over Time", predPop);

		new Graph("Number of Foods Over Time", foodPop);

	}


	public void mouseClicked(MouseEvent e) {


	}


	public void mouseEntered(MouseEvent arg0) {


	}


	public void mouseExited(MouseEvent arg0) {


	}


	public void mousePressed(MouseEvent e) {
		Rectangle stopSim = new Rectangle(720, 85, 150, 20);
		Rectangle pause = new Rectangle(745, 185, 40, 20);
		Rectangle play = new Rectangle(795, 185, 40, 20);

		if(stopSim.contains(e.getPoint())){
			stopSimRetData();
		}		

		if(pause.contains(e.getPoint())){
			timer.stop();
		}

		if(play.contains(e.getPoint())){
			timer.start();
		}


	}


	public void mouseReleased(MouseEvent e) {
		//pred.add(new Pred(1000, 1000, 0, 30, 200, e.getX(), e.getY()));


	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//pred.add(new Pred(1000, 1000, 0, 30, 200, e.getX(), e.getY()));

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}

}
