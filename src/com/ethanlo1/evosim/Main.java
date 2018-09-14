package com.ethanlo1.evosim;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

	public static void main (String[] arsg){
		JFrame topLevelContainer = new JFrame();
		JPanel field = new ViewController();
		
		topLevelContainer.add(field);  //on my MAC, the window only draws stuff if i add the Field at the beginning of the constructor, but not on Windows PC
		topLevelContainer.setSize(1000,700);
		topLevelContainer.setVisible(true);
	//	topLevelContainer.setDefaultCloseOperation(EXIT_ON_CLOSE);
		topLevelContainer.setTitle("Evolution Simulator");
		topLevelContainer.setResizable(false);
	}
	
}
