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
public class Entity implements Thing {
	
	protected World containingWorld;
	protected int dimensionCount;
	protected SettableVector position;
	protected SettableVector velocity;
	protected double mass = 0;
	
	public Entity(World container)
	{
		this.containingWorld = container;
		this.dimensionCount = containingWorld.getDimensionCount();
		this.position = new SettableVector(new double[this.dimensionCount]);
		this.velocity = new SettableVector(new double[this.dimensionCount]);
	}
	
	public Entity(World container,SettableVector pos,SettableVector vel) throws UnequalDimensionsException
	{
		this.containingWorld = container;
		this.dimensionCount = this.containingWorld.getDimensionCount();
		if(pos.getDimensionCount()!=this.dimensionCount)
		{
			throw new UnequalDimensionsException("Position vector has different dimension count to world.");
		}
		else if(vel.getDimensionCount()!=this.dimensionCount)
		{
			throw new UnequalDimensionsException("Velocity vector has different dimension count to world.");
		}
		else
		{
			this.position = pos;
			this.velocity = vel;
		}
	}
	
	public Entity(World container,SettableVector pos,SettableVector vel, double massAmount) throws UnequalDimensionsException
	{
		this.containingWorld = container;
		this.dimensionCount = this.containingWorld.getDimensionCount();
		if(pos.getDimensionCount()!=this.dimensionCount)
		{
			throw new UnequalDimensionsException("Position vector has different dimension count to world.");
		}
		else if(vel.getDimensionCount()!=this.dimensionCount)
		{
			throw new UnequalDimensionsException("Velocity vector has different dimension count to world.");
		}
		else
		{
			this.position = pos;
			this.velocity = vel;
			this.mass = massAmount;
		}
	}
	
	public SettableVector getPositionVector()
	{
		return this.position;
	}
	
	public SettableVector getVelocityVector()
	{
		return this.velocity;
	}
	
	public double distanceTo(Entity other) throws UnequalDimensionsException
	{
		if(this.dimensionCount==other.getDimensionCount())
		{
			double[] thisVals = this.getPositionVector().getValues();
			double[] otherVals = other.getPositionVector().getValues();
			int i;
			double distSquared = 0;
			for(i=0;i<this.dimensionCount;i++)
			{
				distSquared = distSquared + Math.pow(otherVals[i] - thisVals[i],2);
			}
			return Math.sqrt(distSquared);
		}
		else
		{
			throw new UnequalDimensionsException();
		}
	}
	
	public int getDimensionCount()
	{
		return this.dimensionCount;
	}
	
	@Override
	public void step()
	{
		
	}
}
