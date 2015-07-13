/* Copyright (C) 2015 Zach Ohara
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.zachohara.pictographer;

import io.github.zachohara.pictographer.functioncore.FunctionParser;
import io.github.zachohara.pictographer.functioncore.Polynomial;

/**
 * An {@code OptionState} object contains all the information inside any graph option
 * pane.
 * 
 * @author Zach Ohara
 */
public class OptionState {

	/**
	 * The minimum x-boundary.
	 */
	private double xMin;

	/**
	 * The maximum x-boundary.
	 */
	private double xMax;

	/**
	 * The minimum y-boundary.
	 */
	private double yMin;

	/**
	 * The maximum y-boundary.
	 */
	private double yMax;


	/**
	 * The {@code String} that the user entered as a function.
	 */
	private String functionString;

	/**
	 * The {@code Polynomial} that is generated from the string that the user entered.
	 */
	private Polynomial function;

	/**
	 * Constructs a new {@code OptionState}
	 */
	public OptionState() {
		this.xMin = 0;
		this.xMax = 0;
		this.yMin = 0;
		this.yMax = 0;
		this.functionString = "";
		this.function = null;
	}

	/**
	 * Gets the minimum x-boundary.
	 * 
	 * @return the minimum x-boundary.
	 */
	public double getxMin() {
		return xMin;
	}

	/**
	 * Gets the maximum x-boundary.
	 * 
	 * @return the maximum x-boundary.
	 */
	public double getxMax() {
		return xMax;
	}

	/**
	 * Gets the minimum y-boundary.
	 * 
	 * @return the minimum y-boundary.
	 */
	public double getyMin() {
		return yMin;
	}

	/**
	 * Gets the maximum y-boundary.
	 * 
	 * @return the maximum y-boundary.
	 */
	public double getyMax() {
		return yMax;
	}

	/**
	 * Gets the difference between the maximum x-boundary and the minimum x-boundary.
	 * 
	 * @return the difference between the x-boundaries.
	 */
	public double[] getxRange() {
		double[] xRange = new double[2];
		xRange[0] = this.getxMin();
		xRange[1] = this.getxMax();
		return xRange;
	}

	/**
	 * Gets the difference between the maximum y-boundary and the minimum y-boundary.
	 * 
	 * @return the difference between the y-boundaries.
	 */
	public double[] getyRange() {
		double[] yRange = new double[2];
		yRange[0] = this.getyMin();
		yRange[1] = this.getyMax();
		return yRange;
	}

	/**
	 * Gets the string that the user entered as a function.
	 * 
	 * @return the {@code String} of the function.
	 */
	public String getFunctionString() {
		return functionString;
	}

	/**
	 * Gets the {@code Polynomial} that is generated from the string that the user entered.
	 * 
	 * @return the {@code Polynomial} of the function.
	 */
	public Polynomial getFunction() {
		return function;
	}

	/**
	 * Sets the minimum x-boundary.
	 */
	public void setxMin(double xMin) {
		this.xMin = xMin;
	}

	/**
	 * Sets the maximum x-boundary.
	 */
	public void setxMax(double xMax) {
		this.xMax = xMax;
	}

	/**
	 * Sets the minimum y-boundary.
	 */
	public void setyMin(double yMin) {
		this.yMin = yMin;
	}

	/**
	 * Sets the maximum y-boundary.
	 */
	public void setyMax(double yMax) {
		this.yMax = yMax;
	}

	/**
	 * Sets a new {@code String} of the function. The String will be converted into a new
	 * {@code Polynomial} that can be retrieved with the {@link #getFunction()} method.
	 * 
	 * @param functionString the {@code String} of the function.
	 */
	public void setFunctionString(String functionString) {
		this.functionString = functionString;
		try {
			this.function = FunctionParser.parsePolynomialFunction(functionString);
		} catch (NumberFormatException e) {
			this.function = null;
		}
	}

}