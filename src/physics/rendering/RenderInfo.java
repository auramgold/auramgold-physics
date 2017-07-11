/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.rendering;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import physics.simulation.Entity;
import physics.simulation.ForceVector;
import physics.simulation.RenderableForce;
import physics.simulation.Vector;

/**
 *
 * @author Lauren Smith
 */
public class RenderInfo {
	public final Vector positionVector;
	public final double size;
	public final int dimensionCount;
	ArrayList<Shape> otherShapes = new ArrayList<>();
	
	public RenderInfo(Vector position,double size)
	{
		this.positionVector = position;
		this.dimensionCount = this.positionVector.dimensionCount;
		this.size = Math.log10(Math.abs(size));
	}
	
	public RenderInfo(Entity what,ArrayList<ForceVector> forces)
	{
		this(what.getPositionVector(),what.getMass());
		for (ForceVector force : forces) {
			if(force instanceof RenderableForce)
			{
				otherShapes.add(((RenderableForce) force).getRepresentation(what));
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
