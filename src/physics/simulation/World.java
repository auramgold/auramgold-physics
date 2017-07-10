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
	
	protected List<Thing> contents = new ArrayList<>();

	/**
	 * 
	 * @param dimensions
	 * @param scale
	 */
	public World(int dimensions, double scale)
	{
		this.dimensionCount = dimensions;
		this.timeScale = scale;
	}
	
	public double getTimePassed()
	{
		return (double)(this.cycles/this.timeScale);
	}
	
	public int getDimensionCount()
	{
		return this.dimensionCount;
	}
	
	public double getTimeScale()
	{
		return this.timeScale;
	}
	
	public List<Thing> getWorldContents()
	{
		return this.contents;
	}
	
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
	
	public void appendContent(Thing what) throws UnequalDimensionsException
	{
		if(what instanceof Entity)
		{
			if(this.dimensionCount == ((Entity) what).dimensionCount)
			{
				this.contents.add(what);
			}
			else
			{
				throw new UnequalDimensionsException();
			}
		}
	}
	
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
	
	public String print()
	{
		String ret = "\nAt t="+this.getTimePassed()+":";
		int count = this.contents.size();
		for(int i = 0; i < count; i++)
		{
			ret = ret+"\n    "+i+": "+this.contents.get(i).print();
		}
		return ret;
	}
}
