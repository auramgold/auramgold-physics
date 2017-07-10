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
	int dimAccel;
	double accelRate;
	
	public ConstantAcceleration(int dims, int dimAcc, double rate) throws UnequalDimensionsException
	{
		if(dims>dimAcc)
		{
			this.dimensionCount = dims;
			this.dimAccel = dimAcc;
			this.accelRate = rate;
		}
		else
		{
			throw new UnequalDimensionsException();
		}
	}
	
	@Override
	public double getSingleValue(int dim,Entity what) throws UnequalDimensionsException
	{
		if(dim<this.dimensionCount)
		{
			if(dim==this.dimAccel)
			{
				return this.accelRate*what.getMass();
			}
			else
			{
				return 0.0;
			}
		}
		else
		{
			throw new UnequalDimensionsException();
		}
	}
}
