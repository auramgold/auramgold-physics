/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import physics.simulation.PhysicsSimulation;

/**
 *
 * @author Lauren Smith
 */
public class Window extends JFrame
{

	/**
	 * The surface to which rendering happens.
	 */
	public Surface surf = new Surface();

	/**
	 * The menu at the top of the screen
	 */
	public JMenuBar menuBar = new JMenuBar();
	
	/**
	 * Constructs a new window with the given title.
	 * @param title the title to be assigned to the window.
	 */
	public Window(String title)
	{
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		add(surf);
		this.constructMenu();
		this.setJMenuBar(menuBar);
		setLocationRelativeTo(null);
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		System.out.println(this.isDoubleBuffered()+":"+surf.isDoubleBuffered());
	}
	
	/**
	 * Renders the next frame
	 */
	public void updateFrame()
	{
		Graphics2D surfGraph = (Graphics2D)surf.getGraphics();
		RenderInfo[] renderable = new RenderInfo[]{};
		boolean rendered = false;
		while(!rendered)
		{
			try
			{
				renderable = PhysicsSimulation.mainWorld.render();
				rendered = true;
			}
			finally{}
		}
		ArrayList<Shape> velocities = new ArrayList<>();
		ArrayList<Shape> forces = new ArrayList<>();
		surfGraph.setColor(Color.black);
		surfGraph.drawString("t="+PhysicsSimulation.mainWorld.getTimePassed(),10,this.getHeight()-100);
		for(RenderInfo thing: renderable)
		{
//			if(!checkInWindow(thing)){continue;}
			for(Shape shap: thing.addRepresentation())
			{
				surfGraph.fill(shap);
			}
			velocities.add(thing.velocity);
			forces.addAll(thing.forceRenders);
		}
		surfGraph.setColor(Color.blue);
		for(Shape vel: velocities)
		{
			surfGraph.draw(vel);
		}
		surfGraph.setColor(Color.red);
		for(Shape force: forces)
		{
			surfGraph.draw(force);
		}
		
		this.repaint();
	}
	
	/**
	 * Checks whether a given object is in the window
	 * @param what a RenderInfo to check the location of
	 * @return true if it is in the window, false if not.
	 */
	public boolean checkInWindow(RenderInfo what)
	{
		double[] pos = what.positionVector.getComponents();
		double radius = what.size/2;
		return !(pos[0]<(-radius)||pos[1]<(-radius)
				||pos[0]>this.getWidth()+radius||pos[1]>this.getHeight()+radius);
		
	}
	
	private void constructMenu()
	{
		ArrayList<JMenu> heads = new ArrayList<>();
		JMenu file = new JMenu("File");
		file.add(new JMenuItem("New"));
		file.add(new JMenuItem("Save"));
		file.add(new JMenuItem("Load"));
		heads.add(file);
		for(JMenu top : heads)
		{
			menuBar.add(top);
		}
	}
}
