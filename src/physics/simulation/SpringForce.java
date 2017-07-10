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

	/**
	 * The number of components of the vector.
	 */
	public final int dimensionCount;

	/**
	 * The spring constant of the applied force, in units of mass/time<sup>2</sup>
	 */
	public final double springConstant;

	/**
	 * The point the distance is calculated from for the applied force.
	 */
	public final Vector centerPoint;
	
	/**
	 * Constructs a spring force vector.
	 * 
	 * @param dims The number of components of the vector.
	 * @param k	The spring constant, in units of mass/time<sup>2</sup>
	 * @param center The <code>Vector</code> containing the location the distance is calculated from.
	 * @throws UnequalDimensionsException
	 */
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
	public int getDimensionCount()
	{
		return this.dimensionCount;
	}
	
	@Override
	public Vector getForceVector(Entity what) throws UnequalDimensionsException
	{
		Vector inter = what.getPositionVector().getIntermediateVector(this.centerPoint);
		return inter.getUnitVector().multiply(inter.getLength()*this.springConstant);
	}
}
