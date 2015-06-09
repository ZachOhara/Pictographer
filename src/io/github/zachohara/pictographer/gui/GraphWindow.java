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

package io.github.zachohara.pictographer.gui;

import io.github.zachohara.pictographer.functioncore.Polynomial;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphWindow extends JFrame {

	private int xMin;
	private int xMax;
	private int yMin;
	private int yMax;
	
	private BufferedImage image;
	private JPanel graph;
	
	
	private static final long serialVersionUID = 1L;

	public GraphWindow() {
		super();
		this.initializeWindow();
		//TODO stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private void initializeWindow() {
		//TODO stub
	}
	
	public void drawGraph(Polynomial p) {
		this.graph.removeAll();
		
		//TODO
	}
	
	/**
	 * Scales a number within some range to a corresponding position relative
	 * to another range. If the absolute value of the number is below the
	 * input range, 0 will be returned. If the absolute value of the number is
	 * above the input range, the maximum value of the output range, but with
	 * the same polarity as the number, will be returned. 
	 * @param power the number to be scaled
	 * @param inputRange a two-element array representing the lowest and
	 * and highest values, respectively, of the range to be scaled from 
	 * @param outputRange a two-element array representing the lowest and
	 * and highest values, respectively, of the range to be scaled to
	 * @return the number, scaled to the same relative position within the
	 * output range
	 */
	private static double scaleToRange(double power, int[] inputRange, int[] outputRange) {
		double signum = Math.signum(power);
		power = Math.abs(power);
		if (power < inputRange[0])
			return 0;
		if (power > inputRange[1])
			return outputRange[1] * signum;
		int inputDifference = inputRange[1] - inputRange[0];
		int outputDifference = outputRange[1] - outputRange[0];
		double posInRange = (power - inputRange[0]) / inputDifference;
		double posOutRange = outputRange[0] + (posInRange * outputDifference);
		return posOutRange * signum;
	}

}
