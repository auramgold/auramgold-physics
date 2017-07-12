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
	public static double timeScale = 800.0;
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
			double orbitVelocity = Math.sqrt(GravityForce.GravityConstant*1E17/200);
			Entity something = new Entity(mainWorld,10000.0,new Vector(500.0,300.0),new Vector(orbitVelocity,0));
			//Entity anotherThing = new Entity(mainWorld,100.0,new Vector(250.0,550.0),new Vector(2.0,100.0));
			Entity starA = new Entity(
										mainWorld,
										1E17,
										new Vector(500.0,500.0),
										new Vector(
													0,
													//258.31373366509183*Math.pow(0.5,0.5)
													0
													)
										);
			Entity starB = new Entity(
										mainWorld,
										0.5E17,
										new Vector(525.0,500.0),
										new Vector(0,-258.31373366509183*Math.pow(0.5,0.5))
										);
			mainWorld.appendContent(something);
			//mainWorld.appendContent(anotherThing);
			mainWorld.appendContent(starA);
			//mainWorld.appendContent(starB);
			mainWorld.appendContent(new Entity(mainWorld,1000.0,new Vector(700.0,480.0),new Vector(0,140)));
			//mainWorld.appendContent(new Entity(mainWorld,1E14,new Vector(50,500),new Vector(0,101.31898219902405)));
			mainWorld.appendContent(new Entity(mainWorld,1E3,new Vector(100,525),new Vector(-18.26553927482022,101.31898219902405)));
			mainWorld.appendContent(new Entity(mainWorld,1E9,new Vector(600,400),new Vector(10,250)));
			//mainWorld.appendContent(central);
			(new Thread(new PhysicsSimulation())).start();
			(new Thread(new WindowRenderer())).start();
		} 
		catch (UnequalDimensionsException ex) 
		{
			Logger.getLogger(PhysicsSimulation.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
}
