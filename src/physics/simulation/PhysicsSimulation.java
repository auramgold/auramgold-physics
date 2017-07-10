/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.simulation;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lauren Smith
 */
public class PhysicsSimulation {

	static double GravityConstant = 6.674E-11;
	static int dimensionCount = 3;
	static boolean useGravity = true;
	@SuppressWarnings("StaticNonFinalUsedInInitialization")
	static GravityForce gravity = new GravityForce(dimensionCount);
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			World mainWorld = new World(dimensionCount,200);
			Entity something = new Entity(mainWorld,10.0,new Vector(0.0,1000.0,0.0),new Vector(2583.1373366509183,0.0,0.0));
			Entity somethingElse = new Entity(mainWorld,1.0E20,new Vector(3),new Vector(3));
			mainWorld.appendContent(something);
			mainWorld.appendContent(somethingElse);
			while(mainWorld.getTimePassed()<=5.0)
			{
				System.out.println(mainWorld.print());
				mainWorld.step();
			}
			System.out.println(mainWorld.print());
		} 
		catch (UnequalDimensionsException ex) 
		{
			Logger.getLogger(PhysicsSimulation.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
}
