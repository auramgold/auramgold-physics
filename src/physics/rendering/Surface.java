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
	private final double scaleExpBase = Math.pow(2.0,0.125);
	
	/**
	 *
	 * @param g
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		//horrible hacky hack that shouldn't work but does. prevents animation from flashing
		int max = (int)Math.ceil(20/Math.pow(this.getHeight()*this.getWidth(),0.01));
		for(int i = 0; i<max;i++)
		{
			super.paintComponent(g);
			try
			{
				Thread.sleep(0, 50);
			}
			catch (InterruptedException ex)
			{
				Logger.getLogger(Surface.class.getName()).log(Level.SEVERE, null, ex);
			}
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
		this.scale *= Math.pow(scaleExpBase,clicks);
		if(Math.abs(1000-(this.scale*1000))<5)
		{
			this.scale = 1.0;
		}
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
