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

package io.github.zachohara.pictographer.gui;

import io.github.zachohara.pictographer.OptionState;
import io.github.zachohara.pictographer.core.Polynomial;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 * A {@code GraphWindow} is a {@code JFrame} window that, in simple terms, contains a
 * background image and a graph of a function that should be drawn on top of the graph.
 * 
 * @author Zach Ohara
 */
public class GraphWindow extends JFrame {

	/**
	 * The current state of the options panel associated with this window.
	 */
	private OptionState options;


	/**
	 * The {@code JLayeredPane} object that manages the overlaying of the graph over the
	 * background image.
	 */
	private JLayeredPane layerManager;

	/**
	 * The background image.
	 */
	private BufferedImage image;

	/**
	 * The graph to draw over the image.
	 */
	private Graph graph;


	/**
	 * The title of the window.
	 */
	private static final String WINDOW_TITLE = "Pictographer";

	/**
	 * The correction factor that is required for the graph to line up properly with the
	 * window. The format is {width correction, height correction}. These numbers are
	 * not scale factors, but pixel offsets.
	 */
	private static final int[] WINDOW_BOUNDARY_CORRECTION = {0, 30};

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code GraphWindow} with the given background image and option
	 * information.
	 * 
	 * @param bImg the background image to create this window with.
	 * @param optionState the option information for the options pane attached to this
	 * window.
	 */
	public GraphWindow(BufferedImage bImg, OptionState optionState) {
		super();
		this.image = bImg;
		this.options = optionState;
		this.initializeLayeredPane();
		this.initializeGraph();
		this.setImage(bImg);
		this.initializeWindow();
	}

	/**
	 * Redraws the graph after a change to the graph settings.
	 */
	public void update() {
		this.drawGraph(this.options.getFunction());
	}

	/**
	 * Draws a graph of the given polynomial function.
	 * 
	 * @param function the polynomial function to graph.
	 */
	public void drawGraph(Polynomial function) {
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

	/**
	 * Gets the (corrected) width of the background image.
	 * 
	 * @return the width of the image.
	 */
	public int imageWidth() {
		return this.image.getWidth() - WINDOW_BOUNDARY_CORRECTION[0];
	}

	/**
	 * Gets the (corrected) height of the background image.
	 * 
	 * @return the height of the image.
	 */
	public int imageHeight() {
		return this.image.getHeight() - WINDOW_BOUNDARY_CORRECTION[1];
	}

	/**
	 * Plots a given point (in pixel coordinates) on the graph.
	 * 
	 * @param x the x-coordinate of the new point.
	 * @param y the y-coordinate of the new point.
	 */
	private void plotGraphPoint(int x, int y) {
		y = this.imageHeight() - y;
		this.graph.addPoint(x, y);
	}

	/**
	 * Sets the background image for this window, and adjusts the size of the window
	 * accordint to the size of the image.
	 * 
	 * @param image the new background image.
	 */
	private void setImage(BufferedImage image) {
		this.image = image;
		this.setSize(this.image.getWidth(), this.image.getHeight());
		this.setLocationRelativeTo(null);
		JComponent compImage = new JLabel(new ImageIcon(image));
		compImage.setSize(this.image.getWidth(), this.image.getHeight());
		compImage.setLocation(0, 0);
		this.layerManager.add(compImage, 2);
	}

	/**
	 * Initializes the underlying {@code JFrame} for this window.
	 */
	private void initializeWindow() {
		this.setTitle(WINDOW_TITLE);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Initializes the {@code JLayeredPane} that facilitates the overlay of the graph over
	 * the background image.
	 */
	private void initializeLayeredPane() {
		this.layerManager = new JLayeredPane();
		this.layerManager.setSize(this.image.getWidth(), this.image.getHeight());
		this.layerManager.setLocation(0,0);
		this.add(this.layerManager);
	}

	/**
	 * Initializes the {@code Graph} for this window.
	 */
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
	 * 
	 * @param num the number to be scaled.
	 * @param inputRange a two-element array representing the lowest and
	 * and highest values, respectively, of the range to be scaled from.
	 * @param outputRange a two-element array representing the lowest and
	 * and highest values, respectively, of the range to be scaled to.
	 * @return the number, scaled to the same relative position within the
	 * output range.
	 */
	private static double scaleToRange(double num, double[] inputRange, double[] outputRange) {
		double inputDifference = inputRange[1] - inputRange[0];
		double outputDifference = outputRange[1] - outputRange[0];
		double posInRange = (num - inputRange[0]) / inputDifference;
		double posOutRange = outputRange[0] + (posInRange * outputDifference);
		return posOutRange;
	}

}
