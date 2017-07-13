/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.rendering;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Lauren Smith
 */
public class Window extends JFrame
{
	public Surface surf = new Surface();
	public JMenuBar menuBar = new JMenuBar();
	
	public Window(String title)
	{
		setSize(1000, 1000);
		add(surf);
		this.constructMenu();
		this.setJMenuBar(menuBar);
		//setLocationRelativeTo(null);
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
