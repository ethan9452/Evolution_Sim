import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;


public class Graph extends JFrame{
	
	/**
	 * Method Summary
	 * 
	 * organizeData(ArrayList): takes the elements from the ArrayList and transforms them so that they can
	 * be displayed as a graph in a 700x700 window
	 */

	private ArrayList<Double> x = new ArrayList<Double>(); //time
	private ArrayList<Double> y = new ArrayList<Double>(); //values

	private double big;
	private double small;

	Graph(String title, ArrayList<Double> data){
		setSize(700,700);
		setVisible(true);
		setResizable(false);
		setTitle(title);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);

		big = data.get(0);
		small = data.get(0);

		organizeData(data);

		repaint();
	}

	public void organizeData(ArrayList<Double> data){
		//how can i get the data on the various animal attributes and make them into a graph?!?!
		/*
		 * make another arrayList the represents time
		 *	1,2,3,4,5,6,7,8,..........,data.size()
		 *	the time arrayLIst is  the same size as the input arraylist, so that for each data point, there is a time point to plot it with.
		 *
		 *problems 
		 *	will have to scale the points so they fit the window. for example (320234024,242342342342) will NOT fit.
		 */


		for(int i = 1; i < data.size(); i++){//determine biggst and smallest
			if(big < data.get(i)){//the next value is bigger
				big = data.get(i);
			}
			if(small > data.get(i)){//next valueis smaler
				small = data.get(i);
			}
		}


		for(int i = 0; i < data.size(); i++){
			x.add((i * 600/data.size()) + 50.);
		}

		for(int i = 0; i < data.size(); i++){
			//array of values
			//find range, big - small
			//we want data to fit into 600 pixels
			//(value/range) * 600 
			//shift to start at 50
			//(value/range) * 600 - (small/range) * 600 +50
			//origin is actually at top left
			//700 - ((value/range) * 600 - (small/range) * 600 +50)

			//700 - ((data.get(i)/(big-small)) * 600 - (small/(big - small)) * 600 +50)
			if(big == small){
				y.add(300.);
			}
			else{
				y.add(700 - ((data.get(i)/(big-small)) * 600 - (small/(big - small)) * 600 +50));
				
			}
		}
	}

	public void paint(Graphics g){
		super.paint(g);
		for(int i = 0; i < x.size(); i++){
			g.setColor(Color.black);
			g.fillOval((x.get(i).intValue()),y.get(i).intValue(),3,3);
			
			if(i != 0){
				g.drawLine(x.get(i - 1).intValue(),y.get(i - 1).intValue(), x.get(i).intValue(),y.get(i).intValue());
			}
		}
		
		if(big == small){
			g.drawString("max = min: " + big, 10, 300);

		}
		else{
			g.drawString("max: " + big, 20, (int)(700 - ((big/(big-small)) * 600 - (small/(big - small)) * 600 +50)) );
			g.drawString("min: " + small, 20 , (int)(700 - ((small/(big-small)) * 600 - (small/(big - small)) * 600 +50)));
		}
		g.drawString("time: " + x.size(), 600, 690);
	}

}
