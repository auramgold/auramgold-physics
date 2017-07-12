/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.logging.Level;
import java.util.logging.Logger;
import physics.simulation.PhysicsSimulation;

/**
 *
 * @author Lauren Smith
 */

public class WindowRenderer implements Runnable{
	public static final int framerate = 60;
	public static final Window wind = new Window("Physics Simulation");
	public static long frameTime = (long)Math.floor(1E9/framerate);
	public static int frames = 0;
	
	@Override
	public void run()
	{
		wind.setVisible(true);
		Graphics2D graphicsReference = (Graphics2D) wind.surf.getGraphics();
		while(wind.isVisible())
		{
			long startTime = System.nanoTime();
			boolean passed = false;
			RenderInfo[] renderable = null;
			while(!passed)
			{
				try
				{
					renderable = PhysicsSimulation.mainWorld.render();
					passed = true;
				}
				catch(NullPointerException ex)
				{
					passed = false;
				}
			}
			for(RenderInfo thing: renderable)
			{
				graphicsReference.setColor(Color.black);
				for(Shape shap: thing.getRepresentation())
				{
					graphicsReference.draw(shap);
					graphicsReference.fill(shap);
				}
				graphicsReference.setColor(Color.blue);
				graphicsReference.draw(thing.velocity);
				graphicsReference.setColor(Color.red);
				for(Shape vectorDisp: thing.forceRenders)
				{
					graphicsReference.draw(vectorDisp);
				}
			}
			long endTime = System.nanoTime();
			long procTime = endTime-startTime;
			if(procTime < frameTime)
			{
				try
				{
					Thread.sleep((long)Math.floor((frameTime - procTime)/1E6));
				}
				catch (InterruptedException ex)
				{
					Logger.getLogger(WindowRenderer.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			frames++;
			wind.surf.paintComponent(graphicsReference);
		}
		wind.setVisible(false);
	}
	
	public static void main()
	{
		(new Thread(new WindowRenderer())).start();
	}
	
}
