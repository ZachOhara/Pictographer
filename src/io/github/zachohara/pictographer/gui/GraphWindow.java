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

import io.github.zachohara.pictographer.OptionState;
import io.github.zachohara.pictographer.functioncore.Polynomial;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class GraphWindow extends JFrame {
	
	private OptionState options;
	
	private JLayeredPane layerManager;
	private BufferedImage image;
	private Graph graph;
	
	private static final String WINDOW_TITLE = "Pictographer";
	private static final int[] WINDOW_BOUNDARY_CORRECTION = {0, 30};
	
	private static final long serialVersionUID = 1L;

	public GraphWindow(BufferedImage bImg, OptionState optionState) {
		super();
		this.image = bImg;
		this.options = optionState;
		this.initializeLayeredPane();
		this.initializeGraph();
		this.setImage(bImg);
		this.initializeWindow();
	}
	
	public void update() {
		this.drawGraph(this.options.getFunction());
	}
	
	public void drawGraph(Polynomial function) {
//		System.out.println(function);
		this.graph.clear();
		double[] imageWidth = {0, imageWidth()};
		double[] imageHeight = {0, this.imageHeight()};
		for (int xPix = 0; xPix < imageWidth(); xPix++) {
			 double x = scaleToRange(xPix, imageWidth, this.options.getxRange());
			 double y = function.valueAt(x);
			 int yPix = (int)(scaleToRange(y, this.options.getyRange(), imageHeight));
			 this.plotGraphPoint(xPix, yPix);
		}
	}
	
	public int imageWidth() {
		return this.image.getWidth() - WINDOW_BOUNDARY_CORRECTION[0];
	}
	
	public int imageHeight() {
		return this.image.getHeight() - WINDOW_BOUNDARY_CORRECTION[1];
	}
	
	private void plotGraphPoint(int x, int y) {
//		System.out.println("plot " + x + ", " + y);
		y = this.imageHeight() - y;
		this.graph.addPoint(x, y);
	}
	
	public void setImage(BufferedImage i) {
		this.image = i;
		this.setSize(this.image.getWidth(), this.image.getHeight());
		this.setLocationRelativeTo(null);
		JComponent compImage = new JLabel(new ImageIcon(i));
		compImage.setSize(this.image.getWidth(), this.image.getHeight());
		compImage.setLocation(0, 0);
		this.layerManager.add(compImage, 2);
	}
	
	@Override
	public int getWidth() {
		return super.getWidth();
	}
	
	@Override
	public int getHeight() {
		return super.getHeight();
	}
	
	private void initializeWindow() {
		this.setTitle(WINDOW_TITLE);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initializeLayeredPane() {
		this.layerManager = new JLayeredPane();
		this.layerManager.setSize(this.image.getWidth(), this.image.getHeight());
		this.layerManager.setLocation(0,0);
		this.add(this.layerManager);
	}
	
	private void initializeGraph() {
		this.graph = new Graph();
		this.graph.setLocation(0, 0);
		this.graph.setSize(this.image.getWidth(), this.image.getHeight());
		this.layerManager.add(this.graph, 1);
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
	private static double scaleToRange(double power, double[] inputRange, double[] outputRange) {
		double inputDifference = inputRange[1] - inputRange[0];
		double outputDifference = outputRange[1] - outputRange[0];
		double posInRange = (power - inputRange[0]) / inputDifference;
		double posOutRange = outputRange[0] + (posInRange * outputDifference);
		return posOutRange;
	}

}
