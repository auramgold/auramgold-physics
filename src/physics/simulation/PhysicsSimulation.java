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

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			World mainWorld = new World(3,60);
			Entity something = new Entity(mainWorld,10.0,new Vector(0.0,5.0,0.0),new Vector(2.0,0.0,-1.0));
			mainWorld.appendContent(something);
			ConstantAcceleration grav = new ConstantAcceleration(new Vector(0,-10,0));
			something.addForces(grav);
			while(mainWorld.getTimePassed()<=1.0)
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
