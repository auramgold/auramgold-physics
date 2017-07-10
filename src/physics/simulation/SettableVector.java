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
public class SettableVector extends CoordinateVector{
	
	/**
	 * Array of values of vector.
	 */
	protected double[] values;
	
	/**
	 * Creates a vector and sets all values to 0.
	 * 
	 * @param dimensions The number of dimensions in the vector.
	 */
	public SettableVector(int dimensions)
	{
		this.dimensionCount = dimensions;
		this.values = new double[dimensions];
	}
	
	/**
	 * Creates a vector and sets values.
	 * 
	 * @param dimensions The number of dimensions in the vector.
	 * @param vals The values to set the vector to.
	 * @throws UnequalDimensionsException 
	 */
	public SettableVector(int dimensions,double... vals) throws UnequalDimensionsException
	{
		if(dimensions != vals.length)
		{
			throw new UnequalDimensionsException();
		}
		else
		{
			this.dimensionCount = dimensions;
			this.values = new double[dimensions];
			this.values = vals;
		}
	}
	
	/**
	 * Creates a vector and sets values.
	 * 
	 * @param vals The values to set the vector to.
	 */
	public SettableVector(double... vals)
	{
		int dimensions = vals.length;
		this.dimensionCount = dimensions;
		this.values = new double[dimensions];
	}
	
	/**
	 * Sets all values of the vector to a given value.
	 * 
	 * @param value The value to set the vector to.
	 */
	public void setValues(double value)
	{
		int i;
		for(i=0;i<this.dimensionCount;i++)
		{
			this.values[i] = value;
		}
	}
	
	/**
	 * Sets vector values.
	 * 
	 * @param vals The values to set the vector to.
	 * @throws UnequalDimensionsException
	 */
	public void setValues(double... vals) throws UnequalDimensionsException
	{
		if(vals.length != this.dimensionCount)
		{
			throw new UnequalDimensionsException();
		}
		else
		{
			int i;
			for(i=0;i<this.dimensionCount;i++)
			{
				this.values[i] = vals[i];
			}
		}
	}
	
	/**
	 * Sets a single dimension of the vector.
	 * 
	 * @param dim Which dimension to set.
	 * @param val What to set it to.
	 * @throws UnequalDimensionsException
	 */
	public void setSingleValue(int dim,double val) throws UnequalDimensionsException
	{
		if(dim>this.dimensionCount)
		{
			throw new UnequalDimensionsException("Tried to set value of dimension "+dim+" in vector of size "+this.dimensionCount);
		}
		else
		{
			this.values[dim] = val;
		}
	}
	
	/**
	 * Gets the array of vector values.
	 * 
	 * @return The values of the vector.
	 */
	@Override
	public double[] getValues()
	{
		return this.values;
	}
	
	/**
	 * Gets the value of the vector at a given dimension.
	 *
	 * @param dim The position to get the value from.
	 * @return The value of the given dimension.
	 * @throws UnequalDimensionsException
	 */
	@Override
	public double getSingleValue(int dim) throws UnequalDimensionsException
	{
		if(dim>this.dimensionCount)
		{
			throw new UnequalDimensionsException("Tried to get value of dimension "+dim+" in vector of size "+this.dimensionCount);
		}
		else
		{
			return this.values[dim];
		}
	}
	
	/**
	 * Shifts all dimensions of the vector by a given value.
	 *
	 * @param value The value to shift by.
	 */
	public void shiftValues(double value)
	{
		int i;
		for(i=0;i<this.dimensionCount;i++)
		{
			this.values[i] = this.values[i] + value;
		}
	}
	
	/**
	 * Shifts the dimensions of the vector by given values.
	 *
	 * @param vals The values to shift the vector by.
	 * @throws UnequalDimensionsException
	 */
	public void shiftValues(double... vals) throws UnequalDimensionsException
	{
		if(vals.length != this.dimensionCount)
		{
			throw new UnequalDimensionsException();
		}
		else
		{
			int i;
			for(i=0;i<this.dimensionCount;i++)
			{
				this.values[i] = this.values[i] + vals[i];
			}
		}
	}
	
	/**
	 * Shifts a single dimension of the vector by a given value.
	 *
	 * @param dim Which dimension to shift.
	 * @param val The amount to shift the given dimension by.
	 * @throws UnequalDimensionsException
	 */
	public void shiftSingleValue(int dim,double val) throws UnequalDimensionsException
	{
		if(dim>this.dimensionCount)
		{
			throw new UnequalDimensionsException("Tried to shift value of dimension "+dim+" in vector of size "+this.dimensionCount);
		}
		else
		{
			this.values[dim] = this.values[dim] + val;
		}
	}
}
