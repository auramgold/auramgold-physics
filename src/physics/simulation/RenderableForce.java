/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.simulation;

import java.awt.Shape;

/**
 * This interface represents a force with a special rendering (like a spring)
 * @author Lauren Smith
 */
public interface RenderableForce 
{

	/**
	 * Gets the representation of the force.
	 * @param what The Entity the force is on.
	 * @return
	 */
	public Shape getRepresentation(Entity what);
}
