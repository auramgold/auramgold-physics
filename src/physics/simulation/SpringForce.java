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
	@SuppressWarnings("FieldNameHidesFieldInSuperclass")
	public final int dimensionCount;
	public final double springConstant;
	public final Vector centerPoint;
	
	public SpringForce(int dims,double k,Vector center) throws UnequalDimensionsException
	{
		if(center.dimensionCount==dims)
		{
			this.dimensionCount = dims;
			this.springConstant = k;
			this.centerPoint = center;
		}
		else
		{
			throw new UnequalDimensionsException();
		}
	}
	
	@Override
	public Vector getForceVector(Entity what) throws UnequalDimensionsException
	{
		Vector inter = what.getPositionVector().getIntermediateVector(this.centerPoint);
		return inter.getUnitVector().multiply(inter.getLength()*this.springConstant);
	}
}
