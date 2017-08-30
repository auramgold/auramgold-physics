/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.rendering;

import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import physics.simulation.UnequalDimensionsException;
import physics.simulation.Vector;

/**
 *
 * @author Lauren Smith
 */
public class Surface extends JPanel 
{
	protected double scale = 1.0;
	protected Vector offset = new Vector(0.0,0.0);
	
	/**
	 *
	 * @param g
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		//horrible hacky hack that shouldn't work but does. prevents animation from flashing
		for(int i = 0; i<32;i++)
		{
			super.paintComponent(g);
		}
	}
	
	public double getScale()
	{
		return scale;
	}
	
	public Vector getOffset()
	{
		return offset;
	}
	
	public void shiftScale(double clicks)
	{
		this.scale *= Math.pow(1.1,clicks);
	}
	
	public void shiftOffset(int xShift, int yShift)
	{
		try
		{
			offset = offset.add(new Vector((double)xShift,(double)yShift).multiply(1/scale));
		}
		catch (UnequalDimensionsException ex)
		{
			Logger.getLogger(Surface.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
