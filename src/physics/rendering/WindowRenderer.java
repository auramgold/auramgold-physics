/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.rendering;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Lauren Smith
 */

public class WindowRenderer implements Runnable
{

	/**
	 * How many frames per second it renders.
	 */
	public static final int framerate = 100;

	/**
	 * The window that it renders to.
	 */
	public static Window wind;

	/**
	 * The swing timer that activates the frame rendering
	 */
	public static Timer frameTimer;

	/**
	 * How many milliseconds per frame
	 */
	public static int frameTime = (int)Math.floor(1000/framerate);

	/**
	 * How many frames have passed
	 */
	public static int frames = 0;
	
	/**
	 * Main method to run the rendering
	 */
	@Override
	public void run()
	{
		wind = new Window("Physics Simulation");
		if(framerate > 1000)
		{
			System.out.println("Warning: Framerates greater than 1000 are not supported");
		}
		wind.setVisible(true);
		ActionListener frameUpdate = new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				wind.updateFrame();
			}
		};
		frameTimer = new Timer(frameTime,frameUpdate);
		frameTimer.setRepeats(true);
		frameTimer.start();
	}
	
	/**
	 * A method to check if the window is open that is null pointer exception safe
	 * @return true if the window is open, false if not.
	 */
	public static boolean checkWindowEnabled()
	{
		boolean ret;
		try
		{
			ret = wind.isEnabled();
		}
		catch (NullPointerException ex)
		{
			ret = true;
		}
		return ret;
	}
	
}
