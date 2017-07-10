package physics.simulation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lauren Smith
 */
public class SpringForce extends ForceVector{
	int dimSpring;
	double springConstant;
	
	public SpringForce(int dims,int dimSpring,double k) throws UnequalDimensionsException
	{
		if(dims>dimSpring)
		{
			this.dimensionCount = dims;
			this.dimSpring = dimSpring;
			this.springConstant = k;
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
			if(dim==this.dimSpring)
			{
				return this.springConstant*-what.getPositionVector().getSingleValue(dim);
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
