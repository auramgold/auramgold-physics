/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.rendering;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Lauren Smith
 */
public class Surface extends JPanel 
{
	Graphics gOld = null;
	
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
}
