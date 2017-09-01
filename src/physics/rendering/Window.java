/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ItemEvent;
import static java.awt.event.ItemEvent.SELECTED;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import javax.swing.JCheckBoxMenuItem;
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
	
	
	public boolean isBeingDragged = false;
	public int[] coords = {0,0};
	public JCheckBoxMenuItem pauseBox;
	
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
		this.addMouseWheelListener(new MouseWheelListener()
		{
			@Override
			public void mouseWheelMoved(MouseWheelEvent mwe)
			{
				double shift = -mwe.getPreciseWheelRotation();
				surf.shiftScale(shift);
			}
		});
		this.addMouseMotionListener(new MouseMotionListener()
		{
			@Override
			public void mouseDragged(MouseEvent me)
			{
				int[] newCoords = {me.getX(),me.getY()};
				surf.shiftOffset(newCoords[0]-coords[0], newCoords[1]-coords[1]);
				coords = newCoords;
			}

			@Override
			public void mouseMoved(MouseEvent me)
			{
				int[] newCoords = {me.getX(),me.getY()};
				coords = newCoords;
			}
		});
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
		for(RenderInfo thing: renderable)
		{
			surfGraph.setColor(thing.what.renderColor);
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
		surfGraph.setColor(Color.black);
		surfGraph.drawString("t="+PhysicsSimulation.mainWorld.getTimePassed(),10,this.getHeight()-100);
		surfGraph.drawString("Scale: 1 pixel = "+1/surf.getScale()+" meters",10,this.getHeight()-80);
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
		JMenu options = new JMenu("Options");
		pauseBox = new JCheckBoxMenuItem("Pause");
		pauseBox.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				boolean newVal = e.getStateChange()==SELECTED;
				PhysicsSimulation.mainWorld.setPause(newVal);
			}
		});
		options.add(pauseBox);
		heads.add(options);
		for(JMenu top : heads)
		{
			menuBar.add(top);
		}
	}
}
