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
public abstract class CoordinateVector {
	
	protected int dimensionCount;
	
	public abstract double[] getValues();
	public abstract double getSingleValue(int dim) throws UnequalDimensionsException;
	
	public int getDimensionCount()
	{
		return this.dimensionCount;
	}
}
