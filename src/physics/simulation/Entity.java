/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.simulation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import physics.rendering.RenderInfo;

/**
 *
 * @author Lauren Smith
 */
public class Entity implements Thing {
	
	/**
	 * The <code>World</code> the the Entity is contained by.
	 */
	protected World containingWorld;

	/**
	 * The integer number of spatial dimensions that the entity has
	 */
	public final int dimensionCount;

	/**
	 * A <code>Vector</code> containing the entity's position.
	 */
	protected Vector position;

	/**
	 * A <code>Vector</code> containing the entity's velocity.
	 */
	protected Vector velocity;
	
	private Vector positionChange;
	private Vector velocityChange;
	private ArrayList<Vector> appliedForces = new ArrayList<>();

	/**
	 * The mass of the object
	 */
	protected double mass = 0;

	/**
	 * An <code>ArrayList</code> of the forces applied to the entity.
	 */
	protected ArrayList<ForceVector> forces = new ArrayList<>();
	
	public final Color renderColor;
	
	/**
	 * Constructs an entity with position and velocity vectors being all 0.0.
	 * 
	 * @param container The containing <code>World</code>
	 * @param massAmount The mass of the object.
	 * @throws physics.simulation.UnequalDimensionsException
	 */
	public Entity(World container, double massAmount) throws UnequalDimensionsException
	{
		this(container,massAmount,new Vector(container.dimensionCount),new Vector(container.dimensionCount));
	}
	
	/**
	 * Constructs an entity with the given position and velocity.
	 * 
	 * @param container The containing <code>World</code>
	 * @param massAmount The mass of the object.
	 * @param pos The <code>Vector</code> of the object's position.
	 * @param vel The <code>Vector</code> of the object's velocity.
	 * @throws UnequalDimensionsException
	 */
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
		this.renderColor = new Color
		(
			(int)Math.floor(this.position.getLength())%256,
			(int)Math.floor(this.velocity.getLength())%256,
			(int)Math.floor(this.position.multiply(this.velocity.getLengthSquared()).getLength())%256
		);
	}
	
	/**
	 * Adds a force to the entity.
	 * 
	 * @param what The <code>ForceVector</code> to add.
	 * @throws UnequalDimensionsException
	 */
	public void addForce(ForceVector what) throws UnequalDimensionsException
	{
		if(this.getDimensionCount()==what.getDimensionCount())
		{
			this.forces.add(what);
		}
		else
		{
			throw new UnequalDimensionsException();
		}
	}
	
	/**
	 * Adds a variable number of forces to the entity.
	 * 
	 * @param what Either an array or a comma-separated list of <code>ForceVector</code>s
	 * @throws physics.simulation.UnequalDimensionsException
	 */
	public void addForces(ForceVector... what) throws UnequalDimensionsException
	{
		for (ForceVector what1 : what) {
			this.addForce(what1);
		}
	}
	
	/**
	 * Gets the world the entity is contained in.
	 * 
	 * @return The <code>World</code> that the entity is in.
	 */
	public World getContainingWorld()
	{
		return this.containingWorld;
	}
	
	/**
	 * Gets the entity's position vector.
	 * 
	 * @return A <code>Vector</code> of the entity's position.
	 */
	public Vector getPositionVector()
	{
		return this.position;
	}
	
	/**
	 * Gets the entity's velocity vector.
	 * 
	 * @return A <code>Vector</code> of the entity's velocity.
	 */
	public Vector getVelocityVector()
	{
		return this.velocity;
	}
	
	/**
	 * Gets the distance between this and another entity.
	 * 
	 * @param other Another <code>Entity</code> to get the distance to.
	 * @return A double of the distance between <code>this</code> and <code>other</code>.
	 * @throws UnequalDimensionsException
	 */
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
	
	/**
	 * Gets the number of dimensions of the entity.
	 * 
	 * @return The integer number of dimensions.
	 */
	public int getDimensionCount()
	{
		return this.dimensionCount;
	}
	
	/**
	 * Gets the mass of the entity
	 * 
	 * @return The double of the entity's mass.
	 */
	public double getMass()
	{
		return this.mass;
	}
	
	/**
	 * Gets an ArrayList of the forces currently being applied to the Entity.
	 * @return
	 */
	public ArrayList<Vector> getAppliedForces()
	{
		return this.appliedForces;
	}
	
	/**
	 * Calculates all changes in position and velocity of the entity for the given frame.
	 */
	@Override
	public void step()
	{
		try 
		{
			int forceCount = this.forces.size();
			this.positionChange = new Vector(this.dimensionCount);
			this.velocityChange = new Vector(this.dimensionCount);
			this.appliedForces = new ArrayList<>();
			double timeScale = this.containingWorld.getTimeScale();
			int i;
			for(i=0;i<forceCount;i++)
			{
				ForceVector which = this.forces.get(i);
				for(Vector force : which.getForceVector(this))
				{
					this.appliedForces.add(force);
					this.velocityChange = this.velocityChange.add(force.multiply(1.0/(this.mass*timeScale)));
				}
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
	
	/**
	 * Applies position and velocity changes for the current frame.
	 */
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
	
	/**
	 * Outputs a RenderInfo object representing the entity
	 * @return
	 * @throws UnequalDimensionsException
	 */
	@Override
	public RenderInfo render() throws UnequalDimensionsException
	{
		return new RenderInfo(this,this.forces);
	}
	
	@Override
	public String getRepresent()
	{
		return mass+";"+position.toString()+";"+velocity.toString();
	}
	
	/**
	 *
	 * @return A string containing the position and velocity vectors of the object in the current frame in a human readable form.
	 */
	@Override
	public String print()
	{
		return "Pos: "+this.position.toString()+"; Vel: "+this.velocity.toString();
	}
	
	/**
	 *
	 * @return A string containing the force vectors of the previous frame in a human readable form.
	 */
	public String printForces()
	{
		String ret = "Force Vectors:\n";
		int count = this.appliedForces.size();
		for(int i = 0;i<count;i++)
		{
			ret = ret+"		"+i+": "+this.appliedForces.get(i).toString()+"\n";
		}
		return ret;
	}
}
