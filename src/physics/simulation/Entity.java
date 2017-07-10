/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.simulation;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	protected List<ForceVector> forces = new ArrayList<>();
	
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
			System.out.println(Arrays.toString(pos.getValues()));
			this.position = pos;
			this.velocity = vel;
			this.mass = massAmount;
		}
	}
	
	public void addForce(ForceVector what)
	{
		this.forces.add(what);
	}
	
	public void addForces(ForceVector... what)
	{
		int len = what.length;
		int i;
		for(i=0;i<len;i++)
		{
			this.forces.add(what[i]);
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
	
	public double getMass()
	{
		return this.mass;
	}
	
	@Override
	public void step()
	{
		int forceCount = this.forces.size();
		double[] positionShift = new double[dimensionCount];
		double[] velocityShift = new double[dimensionCount];
		double timeScale = this.containingWorld.getTimeScale();
		int i;
		for(i=0;i<forceCount;i++)
		{
			int o;
			ForceVector which = this.forces.get(i);
			double[] vals = which.getValues(this);
			for(o=0;o<dimensionCount;o++)
			{
				double accel = vals[o]/this.mass;
				velocityShift[o] = velocityShift[o] + accel/timeScale;
			}
		}
		int o;
		double[] velocityArr = this.velocity.getValues();
		for(o=0;o<dimensionCount;o++)
		{
			positionShift[o] = velocityArr[o]/timeScale + velocityShift[o]/(2*timeScale);
		}
		
		try {//this should not happen
			this.position.shiftValues(positionShift);
			this.velocity.shiftValues(velocityShift);
		} catch (UnequalDimensionsException ex) {
			Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
