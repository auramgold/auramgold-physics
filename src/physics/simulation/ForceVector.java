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
public abstract class ForceVector extends CoordinateVector {
	
	
	public double[] getValues(Entity what)
	{
		int i;
		double[] retarr = new double[this.dimensionCount];
		for(i=0;i<this.dimensionCount;i++)
		{
			try {
				retarr[i] = getSingleValue(i,what);
			} catch (UnequalDimensionsException ex) {//this shouldn't happen
				Logger.getLogger(ForceVector.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return retarr;
	}
	
	public abstract double getSingleValue(int dim,Entity what) throws UnequalDimensionsException;
}
