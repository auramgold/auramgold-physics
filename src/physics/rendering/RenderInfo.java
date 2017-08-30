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
import java.util.logging.Level;
import java.util.logging.Logger;
import physics.simulation.Entity;
import physics.simulation.ForceVector;
import physics.simulation.RenderableForce;
import physics.simulation.UnequalDimensionsException;
import physics.simulation.Vector;

/**
 *
 * @author Lauren Smith
 */
public class RenderInfo
{

	/**
	 * The position vector of the entity
	 */
	public final Vector positionVector;

	/**
	 * The radius of the rendered object
	 */
	public final double size;

	/**
	 * How many dimensions the entity has
	 */
	public final int dimensionCount;

	/**
	 * The line representing the entity's velocity vector.
	 */
	public final Line2D velocity;

	/**
	 * The entity that this has info for.
	 */
	public final Entity what;
	ArrayList<Shape> forceRenders = new ArrayList<>();
	ArrayList<Shape> otherShapes = new ArrayList<>();
	
	/**
	 * Constructs a render info with a given position and size for a given entity.
	 * @param position the position vector of the entity.
	 * @param size the radius of the rendered object
	 * @param what the entity that this describes
	 * @throws UnequalDimensionsException
	 */
	public RenderInfo(Vector position,double size,Entity what) throws UnequalDimensionsException
	{
		this.positionVector = position;
		this.dimensionCount = this.positionVector.dimensionCount;
		this.size = Math.log10(Math.abs(size));
		this.what = what;
		velocity = (Line2D)what.getVelocityVector().renderVector
		(
			WindowRenderer.wind.surf.getScale(),
			WindowRenderer.wind.surf.getOffset(),
			what.getPositionVector().getComponents()
		);
	}
	
	/**
	 * Constructs a render info with the given entity and the forces on it
	 * @param what the entity to describe
	 * @param forces an ArrayList of ForceVectors applies to the object in the given frame
	 * @throws UnequalDimensionsException
	 */
	public RenderInfo(Entity what,ArrayList<ForceVector> forces) throws UnequalDimensionsException
	{
		this(what.getPositionVector(),what.getMass(),what);
//		for (ForceVector force : forces)
//		{
//			if(force instanceof RenderableForce)
//			{
//				otherShapes.add(((RenderableForce) force).getRepresentation(what));
//			}
//		}
		ArrayList<Vector> forcedisps = what.getAppliedForces();
		if(forcedisps.size() > 0)
		{
			Vector[] forced = forcedisps.toArray(new Vector[1]);
			for(Vector force : forced)
			{
				Vector modForce = force.multiply(1/what.getMass());
				if(modForce.getLength() > 1.0)
				{
//					modForce = modForce.getUnitVector().multiply(Math.log(modForce.getLength())*20);
					this.forceRenders.add(modForce.renderVector
					(
						WindowRenderer.wind.surf.getScale(),
						WindowRenderer.wind.surf.getOffset(),
						what.getPositionVector().getComponents())
					);
				}
			}
		}
	}
	
	/**
	 * Adds the circle representing the entity to the shapes.
	 * @return the ArrayList of shapes
	 */
	public ArrayList<Shape> addRepresentation()
	{
		double[] coords = null;
		try
		{
			coords = positionVector.add(WindowRenderer.getSurf().getOffset())
					.multiply(WindowRenderer.getSurf().getScale()).getComponents();
		}
		catch (UnequalDimensionsException ex)
		{
			Logger.getLogger(RenderInfo.class.getName()).log(Level.SEVERE, null, ex);
		}
		double radius = size*WindowRenderer.wind.surf.scale;
		double x = coords[0] - radius;
		double y = coords[1] - radius;
		Ellipse2D eli = new Ellipse2D.Double(x, y, radius * 2, radius * 2);
		//System.out.println(x+","+y+","+radius);
		ArrayList<Shape> ret = otherShapes;
		ret.add(eli);
		return ret;
	}
}
