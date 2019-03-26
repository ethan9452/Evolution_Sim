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
import com.ethanlo1.evosim.game_model.GameModel;
import com.ethanlo1.evosim.game_model.Pred;


@SuppressWarnings("serial")
public class ViewController extends JPanel implements ActionListener, MouseListener, MouseMotionListener{


	//timer and timer counters
	Timer timer;
	int ttt = 0; //timer count (right now its for food)
	int tt = 0;//timer count( for random movement of crit when stuck on wall)
	int t = 1; //for  and hunger
	int time = 0;


	GameModel gameModel = new GameModel();



	//setting the conditions of the simulation//
	int timerSpeed = 5;




	public ViewController(){

		timer = new Timer(timerSpeed,this);
		addMouseListener(this);
		addMouseMotionListener(this);


		//TODO choose run method
		timer.start();
		//noTimer(); //runs a certain number of cycles. spits out the graphs


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
		gameModel.clockTick();
		
		repaint();

		//for(int i = 0; i < crit.size(); i++){
		//			System.out.println(crit.get(i).getBaseSpeed());
		//	}

		//System.out.println(crit.size());

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
			gameModel.stopSimRetData();
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
