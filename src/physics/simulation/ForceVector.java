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
	public int dimensionCount;
	public abstract Vector getForceVector(Entity what) throws UnequalDimensionsException;
}
