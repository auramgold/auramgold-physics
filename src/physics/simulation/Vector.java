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
	public final int dimensionCount;
	protected double[] components;
	
	public Vector(int dims)
	{
		this.dimensionCount = dims;
		this.components = new double[dims];//sets all components to 0
	}
	
	public Vector(double... components)
	{
		this.dimensionCount = components.length;
		this.components = components;
	}
	
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
	
	public Vector multiply(double scalar)
	{
		double[] resultComponents = new double[this.dimensionCount];
		for (int i = 0; i < this.dimensionCount; i++)
		{
			resultComponents[i] = this.components[i] * scalar;
		}
		return new Vector(resultComponents);
	}
	
	public Vector negate()
	{
		return this.multiply(-1.0);
	}
	
	public Vector getIntermediateVector(Vector other) throws UnequalDimensionsException
	{
		return other.add(this.negate());
	}
	
	public Vector getUnitVector()
	{
		return this.multiply(1.0/this.getLength());
	}
	
	public double getLengthSquared()
	{
		double result = 0;
		for (int i = 0; i < this.dimensionCount; i++)
		{
			result = result + this.components[i] * this.components[i];
		}
		return result;
	}
	
	public double getLength()
	{
		return Math.sqrt(this.getLengthSquared());
	}
	
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
