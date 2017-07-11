/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.simulation;

/**
 *
 * @author Solonarv
 */
public class Vector {

	/**
	 * The number of components of the vector.
	 */
	public final int dimensionCount;

	/**
	 * The array of vector components.
	 */
	protected double[] components;
	
	/**
	 * Constructs a vector with all components set to 0.
	 * 
	 * @param dims The number of vector components.
	 */
	public Vector(int dims)
	{
		this.dimensionCount = dims;
		this.components = new double[dims];//sets all components to 0
	}
	
	/**
	 * Constructs a vector with the given components
	 * 
	 * @param components The components of the vector, in either an array or comma separated list.
	 */
	public Vector(double... components)
	{
		this.dimensionCount = components.length;
		this.components = components;
	}
	
	/**
	 * Adds a vector to another vector.
	 * 
	 * @param other The <code>Vector</code> to add to.
	 * @return The <code>Vector</code> equal to the sum of <code>this</code> and <code>other</code>.
	 * @throws UnequalDimensionsException
	 */
	public Vector add(Vector other) throws UnequalDimensionsException 
	{
		if (other.dimensionCount != this.dimensionCount )
		{
			throw new UnequalDimensionsException();
		}
		double[] resultComponents = new double[this.dimensionCount];
		for (int i = 0; i < this.dimensionCount; i++)
		{
			resultComponents[i] = this.components[i] + other.components[i];
		}
		return new Vector(resultComponents);
	}
	
	public Vector subtract(Vector other) throws UnequalDimensionsException
	{
		if (other.dimensionCount != this.dimensionCount )
		{
			throw new UnequalDimensionsException();
		}
		return this.add(other.negate());
	}
	
	/**
	 * Multiplies a vector by a given amount.
	 * 
	 * @param scalar A double to multiply the vector by.
	 * @return The <code>Vector</code> equal to <code>this</code> multiplied by <code>scalar</code>.
	 */
	public Vector multiply(double scalar)
	{
		double[] resultComponents = new double[this.dimensionCount];
		for (int i = 0; i < this.dimensionCount; i++)
		{
			resultComponents[i] = this.components[i] * scalar;
		}
		return new Vector(resultComponents);
	}
	
	/**
	 * Multiplies the vector by -1.0.
	 * 
	 * @return The <code>Vector</code> equal to the negation of <code>this</code>.
	 */
	public Vector negate()
	{
		return this.multiply(-1.0);
	}
	
	/**
	 * Subtracts one vector from the other.
	 * 
	 * @param other The vector that is subtracted from.
	 * @return A <code>Vector</code> equal to <code>other - this</code>.
	 * @throws UnequalDimensionsException
	 */
	public Vector getIntermediateVector(Vector other) throws UnequalDimensionsException
	{
		return other.subtract(this);
	}
	
	/**
	 * Gets the unit vector.
	 * 
	 * @return The normalized vector of <code>this</code>.
	 */
	public Vector getUnitVector()
	{
		return this.multiply(1.0/this.getLength());
	}
	
	/**
	 * Gets the length of the vector squared.
	 * 
	 * @return ||<code>Vector this</code>||**2.
	 */
	public double getLengthSquared()
	{
		double result = 0;
		for (int i = 0; i < this.dimensionCount; i++)
		{
			result = result + this.components[i] * this.components[i];
		}
		return result;
	}
	
	/**
	 * Gets the length of the vector.
	 *
	 * @return ||<code>Vector this</code>||
	 */
	public double getLength()
	{
		return Math.sqrt(this.getLengthSquared());
	}
	
	public double[] getComponents()
	{
		return this.components;
	}
	
	/**
	 * 
	 * @return
	 */
	@Override
	public String toString()
	{
		String ret = "<";
		for(int i = 0;i < this.dimensionCount;i++)
		{
			ret = ret + ((i!=0)?",":"") + this.components[i];
		}
		return ret+">";
	}
}
