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
			World mainWorld = new World(3,200);
			Entity fall = new Entity(mainWorld,new SettableVector(0.0,-105.0,0.0),new SettableVector(0.0,0.0,0.0),20.0);
			mainWorld.appendContent(fall);
			ForceVector spring = new SpringForce(3,1,2.0);
			ForceVector gravity = new ConstantAcceleration(3,1,-10.0);
			fall.addForces(spring,gravity);
			int stable = 0;
			while(stable < 500)
			{
				mainWorld.step();
				if(Math.abs(fall.getVelocityVector().getSingleValue(1))<0.1)
				{
					stable++;
				}
				else
				{
					stable = 0;
				}
				System.out.println("pos="+Arrays.toString(fall.getPositionVector().getValues())
									+",vel="+Arrays.toString(fall.getVelocityVector().getValues())
									+"; t="+mainWorld.getTimePassed());
			}
		} catch (UnequalDimensionsException ex) {
			Logger.getLogger(PhysicsSimulation.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
}
