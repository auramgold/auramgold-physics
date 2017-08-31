/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.simulation;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import physics.rendering.WindowRenderer;

/**
 *
 * @author Lauren Smith
 */
public class PhysicsSimulation implements Runnable
{

	/**
	 * How many dimensions are in the world, this will be changed to a user
	 * configurable value later.
	 */
	public static final int dimensionCount = 2;

	/**
	 * How many steps per second the world runs at.
	 */
	public static final int timeScale = 1000;//<1000

	/**
	 * Number of milliseconds between frames
	 */
	public static final long waitTime = (long)Math.floor(1000/timeScale);
	static boolean useGravity = true;
	@SuppressWarnings("StaticNonFinalUsedInInitialization")
	static GravityForce gravity = new GravityForce(PhysicsSimulation.dimensionCount);

	/**
	 * The main world that the simulation is run in.
	 */
	public static World mainWorld = new World(PhysicsSimulation.dimensionCount,PhysicsSimulation.timeScale);
	
	/**
	 * Main method to simulate the world.
	 */
	@Override
	public void run()
	{
		Timer timer = new Timer();
		TimerTask runner = new TimerTask()
		{
			@Override
			public void run()
			{
				PhysicsSimulation.mainWorld.step();
				if(!WindowRenderer.checkWindowEnabled())
				{
					timer.purge();
				}
			}
		};
		timer.scheduleAtFixedRate(runner, 0, waitTime);
		
		//System.out.println(mainWorld.print());
	}
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			double orbitVelocity = Math.sqrt(GravityForce.GravityConstant*1E17/200);
			Entity something = new Entity(mainWorld,10000.0,new Vector(500.0,300.0),new Vector(orbitVelocity,0));
			Entity anotherThing = new Entity(mainWorld,100.0,new Vector(250.0,550.0),new Vector(2.0,170.0));
			Entity starA = new Entity(
										mainWorld,
										0.5E17,
										new Vector(475.0,500.0),
										new Vector(
													0,
													258.31373366509183*Math.pow(0.5,0.5)
													)
										);
			Entity starB = new Entity(
										mainWorld,
										0.5E17,
										new Vector(525.0,500.0),
										new Vector(0,-258.31373366509183*Math.pow(0.5,0.5))
										);
			Entity starC = new Entity(
										mainWorld,
										0.5E17,
										new Vector(10475.0,500.0),
										new Vector(
													0,
													258.31373366509183*Math.pow(0.5,0.5)
													)
										);
			Entity starD = new Entity(
										mainWorld,
										0.5E17,
										new Vector(10525.0,500.0),
										new Vector(0,-258.31373366509183*Math.pow(0.5,0.5))
										);
			mainWorld.appendContent(something);
			mainWorld.appendContent(anotherThing);
			mainWorld.appendContent(starA);
			mainWorld.appendContent(starB);
//			mainWorld.appendContent(starC);
//			mainWorld.appendContent(starD);
			mainWorld.appendContent(new Entity(mainWorld,1000.0,new Vector(700.0,480.0),new Vector(0,-200)));
			mainWorld.appendContent(new Entity(mainWorld,1E10,new Vector(950,500),new Vector(5,101.31898219902405)));
			mainWorld.appendContent(new Entity(mainWorld,1E3,new Vector(100,525),new Vector(-18.26553927482022,101.31898219902405)));
			mainWorld.appendContent(new Entity(mainWorld,1E9,new Vector(600,500),new Vector(10,270)));
			mainWorld.appendContent(new Entity(mainWorld,1E6,new Vector(200,500),new Vector(-10,180)));
			mainWorld.appendContent(new Entity(mainWorld,1E15,new Vector(500,10),new Vector(116,0)));
			mainWorld.appendContent(new Entity(mainWorld,1E4,new Vector(500,30), new Vector(116-57.76,0)));
			mainWorld.appendContent(new Entity(mainWorld,1E15,new Vector(500,-2500),new Vector(51.662746,0)));
			mainWorld.appendContent(new Entity(mainWorld,1E5,new Vector(500,-2450),new Vector(51.662746-36.531,0)));
			mainWorld.appendContent(new Entity(mainWorld,1E7,new Vector(500,-5000),new Vector(10,0)));
			(new Thread(new PhysicsSimulation())).start();
			(new Thread(new WindowRenderer())).start();
		} 
		catch (UnequalDimensionsException ex) 
		{
			Logger.getLogger(PhysicsSimulation.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static int randomInt(int max)
	{
		return (int)Math.floor(Math.random()*max);
	}
	
}
