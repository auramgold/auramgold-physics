package physics.simulation;

import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lauren Smith
 */
public class GravityForce extends ForceVector{

	/**
	 * The number of components of the vector.
	 */
	public final int dimensionCount;
	
	/**
	 * The number of components the gravity force has.
	 * 
	 * @param dims An integer number of dimensions.
	 */
	public GravityForce(int dims)
	{
		this.dimensionCount = dims;
	}
	
	@Override
	public int getDimensionCount()
	{
		return this.dimensionCount;
	}
	
	@Override
	public Vector getForceVector(Entity what) throws UnequalDimensionsException
	{
		World world = what.getContainingWorld();
		Vector ret = new Vector(this.dimensionCount);
		List<Thing> stuff = world.getAllContentsBut(what);
		int count = stuff.size();
		for(int i = 0;i<count;i++)
		{
			Thing current = stuff.get(i);
			if(current instanceof Entity)
			{
				if(((Entity) current).getMass() > 0.0)
				{
					Entity curr = (Entity) current;
					Vector inter = what.getPositionVector().getIntermediateVector(curr.getPositionVector());
					Vector gravity = inter.getUnitVector()
									.multiply(PhysicsSimulation.GravityConstant*what.getMass()*curr.getMass()
												/inter.getLengthSquared());
					ret = ret.add(gravity);
					System.out.println("Radius: "+inter.getLength());
				}
			}
		}
		return ret;
	}
}
