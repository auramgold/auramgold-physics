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
public class ConstantAcceleration extends ForceVector {

	/**
	 * The number of components of the vector.
	 */
	public final int dimensionCount;

	/**
	 * The constant acceleration vector.
	 */
	public final Vector accelerationVector;
	
	/**
	 * Constructs a constant acceleration force with the given vector.
	 * 
	 * @param accelerationVector The constant force <code>Vector</code>.
	 */
	public ConstantAcceleration(Vector accelerationVector)
	{
		this.dimensionCount = accelerationVector.dimensionCount;
		this.accelerationVector = accelerationVector;
	}
	
	/**
	 *
	 * @return
	 */
	@Override
	public int getDimensionCount()
	{
		return this.dimensionCount;
	}

	@Override
	public Vector[] getForceVector(Entity what)
	{
		return new Vector[]{this.accelerationVector.multiply(what.getMass())};
	}
}
