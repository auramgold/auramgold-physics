/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.simulation;

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
	
	protected Thing[] contents;

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
	
	public void step()
	{
		int amount = this.contents.length;
		int i;
		for(i=0;i<amount;i++)
		{
			this.contents[i].step();
		}
		this.cycles++;
	}

}
