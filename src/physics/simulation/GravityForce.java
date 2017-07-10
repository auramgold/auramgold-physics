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
	public final int dimensionCount;
	
	public GravityForce(int dims)
	{
		this.dimensionCount = dims;
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
