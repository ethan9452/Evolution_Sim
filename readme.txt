/* Ethan Lo
 * 
 * Evolution Simulator
 * 
 * OBJECTIVE:
 * The objective of this project is to simulate an ecosystem in which its organisms's 
 * offspring inherit traits from their parents.The traits are similar, but have slight
 * random variation.
 * 
 * The goal is to see if the species living in the ecosystem evolve over time.
 * 
 * h
 * 
 * SUMMARY OF THE ECOSYSTEM:
 * There are 3 types of organisms in the ecosystem: predators, plant eaters, and plants.
 * Predators eat the plant eaters and plant eaters eat the plants. A plant eater/predator
 * has a constantly diminishing stored-calorie-level(health), and if it does not eat for too 
 * long, it dies. 
 * 
 * On the left side of the screen there is a safe-zone where the predators cannot eat the 
 * plant eaters. Predators are constantly trying to catch the plant eaters. However, plant 
 * eaters are not always trying to eat plants, they only leave the safety of the safe-zone 
 * when their health dips below a certain level. 
 * 
 * Also, each type of organism undergoes asexual reproduction, meaning that when the time comes, the 
 * organism will split into two organisms. 
 * 
 * For the predators and plant eaters, when they reproduce, their offspring inherit 
 * slightly modified version of their parents traits. For example, if the parent was 
 * a fast runner, the first child might be slightly faster, while the second slightly slower. 
 * In nature, the offspring that inherit traits that are more suitable for survival have a 
 * higher chance to survive and reproduce. Over time, we can see very large changes in species.
 * 
 * 
 * 
 * IMPLEMENTATION:
 * The 3 organisms are represented by 3 classes. Each class has numbers(doubles) that represent 
 * specific traits of the organism. For example, for one instance of a plant eater class, we 
 * might have that speed = 3 and eyesight = 100, and health = 200. 
 * 
 * To simulate reproduction, a new instance of the class is created. For the predators and plant
 * eaters, I simulated the variation in inherited traits by adding or subtracting a random decimal
 * from the parent's trait and inputting that number into the child's constructor. For example:
 * 
 * 	class Animal{
 * 		double health;
 * 		double speed;
 * 		...
 * 		constructor
 * 		...
 * }
 * ...
 * ...
 * 	parent = new Animal(1,1,1)
 * 	
 * 	child = new Animal(parent's health + random decimal, parent's speed + random decimal);
 * 
 * To simulate eating, when predator overlaps with a plant eater (when the 2 circles intersect), 
 * the instance of that plant eater is deleted, and the predator's health is raised. A similar 
 * process goes on to simulate plant eaters eating plants.
 * 
 * 
 * 
 * I also implemented code that stores data on the current state of each species. There are lists
 * that store the average value of each species's traits over time. If desired, the user can press 
 * a button to display the graphs that represent this stored data.
 * 
 * 
 * 
 * INTERESTING OBSERVATIONS:
 * -When no plant eaters are present, the plant population seems to follow a logistic curve
 * 
 * -The population graphs for the predators and plant eaters look like they are phase-shifted:
 * 	right after the plant eater population rises, the predator population rises, then when
 *  predator population rises to a certain level, the plant eater population drops, then 
 *  there is a corresponding drop in predator population level. 
 *  
 * -Unexpectedly, many traits did not act the way I expected. For example, I expected 
 * the number representing eyesight to keep going up over time, however in most simulations
 * it oscillated between 2 points. 
 * 
 */