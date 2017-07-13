/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.rendering;

import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.util.Objects.isNull;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Lauren Smith
 */
public class Surface extends JPanel 
{
	Graphics gOld = null;
	
	@Override
	public void paintComponent(Graphics g)
	{
		if(!isNull(gOld))
		{
			super.paintComponent(gOld);
			try {
				Thread.sleep(1, 500000);
			} catch (InterruptedException ex) {
				Logger.getLogger(Surface.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		gOld = g.create();
		super.paintComponent(g);
	}
}
