/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics.simulation;

/**
 *
 * @author Lauren Smith
 */
public class ConstantAcceleration extends ForceVector {
	public final int dimensionCount;
	public final Vector accelerationVector;
	
	public ConstantAcceleration(Vector accelerationVector)
	{
		this.dimensionCount = accelerationVector.dimensionCount;
		this.accelerationVector = accelerationVector;
	}
	
	@Override
	public Vector getForceVector(Entity what)
	{
		return this.accelerationVector.multiply(what.getMass());
	}
}
