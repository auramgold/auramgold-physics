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
		this.cycles++;
	}

}
