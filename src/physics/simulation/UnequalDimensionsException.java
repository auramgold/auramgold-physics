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
public class UnequalDimensionsException extends Exception {

	/**
	 * Creates a new instance of <code>UnequalDimensionsException</code> without
	 * detail message.
	 */
	public UnequalDimensionsException() {
	}

	/**
	 * Constructs an instance of <code>UnequalDimensionsException</code> with
	 * the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public UnequalDimensionsException(String msg) {
		super(msg);
	}
}
