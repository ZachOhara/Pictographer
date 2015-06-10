/*
 *  Copyright (C) 2015 Zach Ohara
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.zachohara.pictographer;

import io.github.zachohara.pictographer.functioncore.FunctionParser;
import io.github.zachohara.pictographer.functioncore.Polynomial;

public class OptionState {

	private double xMin;
	private double xMax;
	private double yMin;
	private double yMax;
	
	private String functionString;
	private Polynomial functiton;
	
	public OptionState() {
		this.xMin = 0;
		this.xMax = 0;
		this.yMin = 0;
		this.yMax = 0;
		this.functionString = "";
		this.functiton = null;
	}

	public double getxMin() {
		return xMin;
	}

	public double getxMax() {
		return xMax;
	}

	public double getyMin() {
		return yMin;
	}

	public double getyMax() {
		return yMax;
	}
	
	public double[] getxRange() {
		double[] xRange = new double[2];
		xRange[0] = this.getxMin();
		xRange[1] = this.getxMax();
		return xRange;
	}
	
	public double[] getyRange() {
		double[] yRange = new double[2];
		yRange[0] = this.getyMin();
		yRange[1] = this.getyMax();
		return yRange;
	}

	public String getFunctionString() {
		return functionString;
	}

	public Polynomial getFunction() {
		return functiton;
	}

	public void setxMin(double xMin) {
		this.xMin = xMin;
	}

	public void setxMax(double xMax) {
		this.xMax = xMax;
	}

	public void setyMin(double yMin) {
		this.yMin = yMin;
	}

	public void setyMax(double yMax) {
		this.yMax = yMax;
	}

	public void setFunctionString(String functionString) {
		this.functionString = functionString;
		this.functiton = FunctionParser.parsePolynomialFunction(functionString);
	}

}
