/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.simulation;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import physics.rendering.RenderInfo;
import physics.rendering.WindowRenderer;

/**
 *
 * @author Lauren Smith
 */
public class World
{

	/**
	 * Number of spatial dimensions in the world.
	 */
	protected int dimensionCount;

	/**
	 * Number of physics steps per second.
	 */
	protected double timeScale;

	/**
	 * Number of world cycles passed.
	 */
	protected int cycles = 0;
	
	/**
	 * The <code>Thing</code>s in the world.
	 */
	protected List<Thing> contents = new ArrayList<>();
	
	public boolean paused = true;
	
	/**
	 * A lock to prevent rendering and processing going on at the same time
	 */
	public static ReentrantLock lock = new ReentrantLock();

	/**
	 * Constructs a world object.
	 * 
	 * @param dimensions - The integer number of spatial dimensions in the world
	 * @param scale - A double of how many steps per second to iterate on the world
	 */
	public World(int dimensions, double scale)
	{
		this.dimensionCount = dimensions;
		this.timeScale = scale;
	}
	
	public World(String represent) throws UnequalDimensionsException
	{
		String[] entArr = represent.replace("\r", "").split("\n");
		String[] worldInfo = entArr[0].split(";");
		this.timeScale = Double.parseDouble(worldInfo[0]);
		this.dimensionCount = Integer.parseInt(worldInfo[1]);
		this.cycles = Integer.parseInt(worldInfo[2]);
		if(entArr.length > 1)
		{
			for(int i = 1; i < entArr.length; i++)
			{
				String[] entRep = entArr[i].split(";");
				this.appendContent
				(
					new Entity
					(
						this,
						Double.parseDouble(entRep[0]),
						Vector.parseVector(entRep[1]),
						Vector.parseVector(entRep[2]),
						new Color(Integer.parseInt(entRep[3]))
					)
				);
			}
		}
	}
	
	/**
	 * Get how much simulated time has occurred
	 * 
	 * @return The double number of cycles divided by the time scale.
	 */
	public double getTimePassed()
	{
		return (double)(this.cycles/this.timeScale);
	}
	
	/**
	 * Gets the number of spatial dimensions in the world.
	 * 
	 * @return
	 */
	public int getDimensionCount()
	{
		return this.dimensionCount;
	}
	
	/**
	 * Gets the time scale of the world.
	 * 
	 * @return 
	 */
	public double getTimeScale()
	{
		return this.timeScale;
	}
	
	/**
	 * Returns the list of things in the world.
	 * 
	 * @return An <code>ArrayList&lt;Thing&gt;</code> of the world contents.
	 */
	public List<Thing> getWorldContents()
	{
		return this.contents;
	}
	
	/**
	 * Returns a list of all items in the world except for a specified one
	 * 
	 * @param what A <code>Thing</code> to not return
	 * @return An <code>ArrayList&lt;Thing&gt;</code> of the world contents that are not <code>what</code>
	 */
	public List<Thing> getAllContentsBut(Thing what)
	{
		List<Thing> retList = new ArrayList<>();
		int amount = this.contents.size();
		int i;
		for(i=0;i<amount;i++)
		{
			if(this.contents.get(i)!=what)
			{
				retList.add(this.contents.get(i));
			}
		}
		return retList;
	}
	
	/**
	 * Adds a <code>Thing</code> to the world.
	 * 
	 * @param what The <code>Thing</code> to add.
	 * @throws UnequalDimensionsException
	 */
	public final void appendContent(Thing what) throws UnequalDimensionsException
	{
		if(what instanceof Entity)
		{
			if(this.dimensionCount == ((Entity) what).getDimensionCount())
			{
				if(((Entity) what).mass!=0.0 && PhysicsSimulation.useGravity)
				{
					((Entity) what).addForce(PhysicsSimulation.gravity);
				}
				this.contents.add(what);
			}
			else
			{
				throw new UnequalDimensionsException();
			}
		}
	}
	
	/**
	 * Steps through a world cycle.
	 */
	public void step()
	{
		lock.lock();
		try
		{
			int amount = this.contents.size();
			for(int i=0;i<amount;i++)
			{
				this.contents.get(i).step();
			}
			for(int i=0;i<amount;i++)
			{
				this.contents.get(i).completeStep();
			}
			this.cycles++;
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * Gets the render info for all Things in the world
	 * @return an array of RenderInfo
	 */
	public RenderInfo[] render()
	{
		lock.lock();
		int amount = this.contents.size();
		RenderInfo[] ret = new RenderInfo[amount];
		try
		{
			for(int i=0;i<amount;i++)
			{
				try
				{
					ret[i] = this.contents.get(i).render();
				}
				catch (UnequalDimensionsException ex)
				{
					Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		} 
		finally
		{
			lock.unlock();
		}
		return ret;
	}
	
	public Shape[] getBarycenter()
	{
		Vector sum = new Vector(dimensionCount);
		double crossSize = 5;
		double totalMass = 0.0;
		Shape[] ret = new Shape[2];
		for(Thing obj: contents)
		{
			if(obj instanceof Entity)
			{
				Entity ent = (Entity)obj;
				try
				{
					sum = sum.add(ent.getPositionVector().multiply(ent.getMass()));
					totalMass += ent.getMass();
				}
				catch (UnequalDimensionsException ex)
				{
					Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
				}
				
			}
		}
		Vector avg = sum.divide(totalMass);
		double[] coords = WindowRenderer.wind.surf.simuToScreenCoords(avg);
		ret[0] = new Line2D.Double(coords[0]-crossSize, coords[1], coords[0]+crossSize, coords[1]);
		ret[1] = new Line2D.Double(coords[0], coords[1]-crossSize, coords[0], coords[1]+crossSize);
		return ret;
	}
	
	public void pause()
	{
		this.paused = true;
	}
	
	public void unpause()
	{
		this.paused = false;
	}
	
	public void setPause(boolean state)
	{
		this.paused = state;
	}
	
	public String getRepresent()
	{
		String ret = timeScale+";"+dimensionCount+";"+cycles;
		for(Thing ent:contents)
		{
			ret += "\n"+ent.getRepresent();
		}
		return ret;
	}
	
	/**
	 * Prints all contents of the world and their forces, if applicable.
	 * 
	 * @return A formatted string.
	 */
	public String print()
	{
		String ret = "\nAt t="+this.getTimePassed()+":";
		int count = this.contents.size();
		for(int i = 0; i < count; i++)
		{
			Thing item = this.contents.get(i);
			ret = ret+"\n	"+i+": "+item.print();
			if(item instanceof Entity)
			{
				ret = ret+"\n"+((Entity) item).printForces();
			}
		}
		return ret;
	}
}
