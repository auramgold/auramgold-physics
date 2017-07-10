/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.simulation;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lauren Smith
 */
public class Entity implements Thing {
	
	protected World containingWorld;
	public final int dimensionCount;
	protected Vector position;
	protected Vector velocity;
	private Vector positionChange;
	private Vector velocityChange;
	private Vector[] appliedForces = new Vector[0];
	protected double mass = 0;
	protected List<ForceVector> forces = new ArrayList<>();
	
	public Entity(World container, double massAmount)
	{
		this.containingWorld = container;
		this.dimensionCount = containingWorld.getDimensionCount();
		this.position = new Vector(new double[this.dimensionCount]);
		this.velocity = new Vector(new double[this.dimensionCount]);
		this.mass = massAmount;
	}
	
	public Entity(World container, double massAmount,Vector pos,Vector vel) throws UnequalDimensionsException
	{
		this.containingWorld = container;
		this.dimensionCount = this.containingWorld.getDimensionCount();
		if(pos.dimensionCount!=this.dimensionCount)
		{
			throw new UnequalDimensionsException("Position vector has different dimension count to world.");
		}
		else if(vel.dimensionCount!=this.dimensionCount)
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
	
	public World getContainingWorld()
	{
		return this.containingWorld;
	}
	
	public Vector getPositionVector()
	{
		return this.position;
	}
	
	public Vector getVelocityVector()
	{
		return this.velocity;
	}
	
	public double distanceTo(Entity other) throws UnequalDimensionsException
	{
		if(this.dimensionCount==other.dimensionCount)
		{
			return this.position.getIntermediateVector(other.getPositionVector()).getLength();
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
		try 
		{
			int forceCount = this.forces.size();
			this.positionChange = new Vector(this.dimensionCount);
			this.velocityChange = new Vector(this.dimensionCount);
			this.appliedForces = new Vector[forceCount];
			double timeScale = this.containingWorld.getTimeScale();
			int i;
			for(i=0;i<forceCount;i++)
			{
				ForceVector which = this.forces.get(i);
				Vector force = which.getForceVector(this);
				this.appliedForces[i] = force;
				this.velocityChange = this.velocityChange.add(force.multiply(1.0/(this.mass*timeScale)));
			}
			this.positionChange = this.positionChange
									.add(this.velocity.multiply(1/timeScale))
									.add(this.velocityChange.multiply(1/(2.0*timeScale)));
		} 
		catch (UnequalDimensionsException ex) 
		{
			Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	@Override
	public void completeStep()
	{
		try 
		{
			this.velocity = this.velocity.add(this.velocityChange);
			this.position = this.position.add(this.positionChange);
		} 
		catch (UnequalDimensionsException ex) 
		{
			Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	@Override
	public String print()
	{
		return "Pos: "+this.position.toString()+"; Vel: "+this.velocity.toString();
	}
	
	public String printForces()
	{
		String ret = "Force Vectors:\n";
		int count = this.appliedForces.length;
		for(int i = 0;i<count;i++)
		{
			ret = ret+"        "+i+": "+this.appliedForces[i].toString()+"\n";
		}
		return ret;
	}
}
