/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.simulation;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lauren Smith
 */
public abstract class ForceVector{

	/**
	 * The number of components of the vector.
	 */
	public int dimensionCount;

	/**
	 * Gets the force vector applied to a given entity in a given frame.
	 * 
	 * @param what An <code>Entity</code> to apply the force to.
	 * @return A <code>Vector</code> containing the force applied to the entity.
	 * @throws UnequalDimensionsException
	 */
	public abstract Vector getForceVector(Entity what) throws UnequalDimensionsException;
}
