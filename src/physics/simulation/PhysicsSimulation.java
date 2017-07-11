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

	static double GravityConstant = 6.67408E-11;
	static int dimensionCount = 3;
	static boolean useGravity = true;
	@SuppressWarnings("StaticNonFinalUsedInInitialization")
	static GravityForce gravity = new GravityForce(PhysicsSimulation.dimensionCount);
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			World mainWorld = new World(PhysicsSimulation.dimensionCount,200);
			double orbitVelocity = Math.sqrt(GravityConstant*1E17);
			Entity something = new Entity(mainWorld,10.0,new Vector(0.0,1000.0,0.0),new Vector(orbitVelocity,0.0,0.0));
			Entity anotherThing = new Entity(mainWorld,10.0,new Vector(0.0,-1000.0,0.0),new Vector(-orbitVelocity,0.0,0.0));
			Entity somethingElse = new Entity(mainWorld,1.0E20,new Vector(3),new Vector(3));
			mainWorld.appendContent(something);
			mainWorld.appendContent(anotherThing);
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
