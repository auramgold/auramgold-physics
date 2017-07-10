/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.simulation;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lauren Smith
 */
public class World {

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
	public void appendContent(Thing what) throws UnequalDimensionsException
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
		int amount = this.contents.size();
		int i;
		for(i=0;i<amount;i++)
		{
			this.contents.get(i).step();
		}
		for(i=0;i<amount;i++)
		{
			this.contents.get(i).completeStep();
		}
		this.cycles++;
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
			ret = ret+"\n    "+i+": "+item.print();
			if(item instanceof Entity)
			{
				ret = ret+"\n"+((Entity) item).printForces();
			}
		}
		return ret;
	}
}
