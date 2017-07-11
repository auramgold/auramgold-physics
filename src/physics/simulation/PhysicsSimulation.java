/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.simulation;

import java.util.logging.Level;
import java.util.logging.Logger;
import physics.rendering.WindowRenderer;

/**
 *
 * @author Lauren Smith
 */
public class PhysicsSimulation implements Runnable{
	public static int dimensionCount = 2;
	public static double timeScale = 200.0;
	static boolean useGravity = true;
	@SuppressWarnings("StaticNonFinalUsedInInitialization")
	static GravityForce gravity = new GravityForce(PhysicsSimulation.dimensionCount);
	public static World mainWorld = new World(PhysicsSimulation.dimensionCount,PhysicsSimulation.timeScale);
	@SuppressWarnings("StaticNonFinalUsedInInitialization")
	static long frameTime = (long)Math.floor(1E9/timeScale);
	
	@Override
	public void run()
	{
		while(WindowRenderer.wind.isEnabled()||mainWorld.getTimePassed()<=5.0)//change to another condition later, no user input available yet
		{
			long startTime = System.nanoTime();
			//System.out.println(mainWorld.print());
			mainWorld.step();
			long endTime = System.nanoTime();
			long execTime = endTime - startTime;
			if(execTime<frameTime)
			{
				try
				{
					Thread.sleep((long)Math.floor((frameTime-execTime)/1E6));
				}
				catch (InterruptedException ex)
				{
					Logger.getLogger(PhysicsSimulation.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		System.out.println(mainWorld.print());
	}
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			//double orbitVelocity = Math.sqrt(GravityForce.GravityConstant*1E17)*0;
			Entity something = new Entity(mainWorld,100.0,new Vector(200.0,250.0),new Vector(0,0));
			//Entity anotherThing = new Entity(mainWorld,100.0,new Vector(150.0,100.0),new Vector(1.0,1.0));
			/*Entity somethingElse = new Entity(
										mainWorld,
										1.0E20,
										new Vector(PhysicsSimulation.dimensionCount),
										new Vector(PhysicsSimulation.dimensionCount)
										);*/
			SpringForce spr = new SpringForce(2,50,new Vector(200,200));
			ConstantAcceleration grav = new ConstantAcceleration(new Vector(0,9.81));
			something.addForces(spr,grav);
			mainWorld.appendContent(something);
			//mainWorld.appendContent(anotherThing);
			//mainWorld.appendContent(somethingElse);
			(new Thread(new PhysicsSimulation())).start();
			(new Thread(new WindowRenderer())).start();
		} 
		catch (UnequalDimensionsException ex) 
		{
			Logger.getLogger(PhysicsSimulation.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
}
