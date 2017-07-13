/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.rendering;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import physics.simulation.Entity;
import physics.simulation.ForceVector;
import physics.simulation.RenderableForce;
import physics.simulation.UnequalDimensionsException;
import physics.simulation.Vector;

/**
 *
 * @author Lauren Smith
 */
public class RenderInfo {
	public final Vector positionVector;
	public final double size;
	public final int dimensionCount;
	public final Line2D velocity;
	public final Entity what;
	ArrayList<Shape> forceRenders = new ArrayList<>();
	ArrayList<Shape> otherShapes = new ArrayList<>();
	
	public RenderInfo(Vector position,double size,Entity what) throws UnequalDimensionsException
	{
		this.positionVector = position;
		this.dimensionCount = this.positionVector.dimensionCount;
		this.size = Math.log10(Math.abs(size));
		this.what = what;
		velocity = (Line2D)what.getVelocityVector().renderVectorLine(false,what.getPositionVector().getComponents());
	}
	
	public RenderInfo(Entity what,ArrayList<ForceVector> forces) throws UnequalDimensionsException
	{
		this(what.getPositionVector(),what.getMass(),what);
		for (ForceVector force : forces)
		{
			if(force instanceof RenderableForce)
			{
				otherShapes.add(((RenderableForce) force).getRepresentation(what));
			}
		}
		ArrayList<Vector> forcedisps = what.getAppliedForces();
		if(forcedisps.size() > 0)
		{
			Vector[] forced = forcedisps.toArray(new Vector[1]);
			for(Vector force : forced)
			{
				Vector modForce = force.multiply(1/what.getMass());
				if(modForce.getLength() > 1.0)
				{
					this.forceRenders.addAll(Arrays.asList(modForce.renderVector(false,what.getPositionVector().getComponents())));
				}
			}
		}
	}
	
	public ArrayList<Shape> getRepresentation()
	{
		double[] coords = positionVector.getComponents();
		double radius = size;
		double x = coords[0] - radius;
		double y = coords[1] - radius;
		Ellipse2D eli = new Ellipse2D.Double(x, y, radius * 2, radius * 2);
		//System.out.println(x+","+y+","+radius);
		ArrayList<Shape> ret = otherShapes;
		ret.add(eli);
		return ret;
	}
}
