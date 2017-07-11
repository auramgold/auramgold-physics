/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.rendering;

import java.awt.Graphics;
import javax.swing.JFrame;

/**
 *
 * @author Lauren Smith
 */
public class Window extends JFrame
{
	public Surface surf = new Surface();
	
	public Window(String title)
	{
		add(surf);
		setSize(1000, 1000);
		setLocationRelativeTo(null);
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void paint(Graphics g)
	{
		surf.paintComponent(g);
		//g.drawString("hello",40,30);
	}
}
