/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.simulation;

import physics.rendering.RenderInfo;

/**
 * Something in a World that can be stepped and rendered.
 * @author Lauren Smith
 */
public interface Thing
{
	
	/**
	 * The method run every <code>World</code> cycle.
	 */
	public void step();

	/**
	 * The method run at the end of every <code>World</code> cycle.
	 */
	public void completeStep();

	/**
	 * Returns a simple textual representation of the thing.
	 * 
	 * @return A String representation of <code>this</code>.
	 */
	public String print();
	
	public String getRepresent();
	
	/**
	 * Gets a RenderInfo representing this Thing
	 * @return
	 * @throws UnequalDimensionsException
	 */
	public RenderInfo render() throws UnequalDimensionsException;
}
