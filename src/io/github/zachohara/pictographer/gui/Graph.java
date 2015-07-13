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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * A {@code Graph} object is a {@code JPanel} that will draw a given set of points on to
 * the screen.
 * 
 * @author Zach Ohara
 */
public class Graph extends JPanel {

	/**
	 * The list of points that should be drawn on in graph.
	 */
	private List<Point> pointList;


	/**
	 * The thickness of the points that should be drawn.
	 */
	private static final int LINE_THICKNESS = 5;

	/**
	 * The color of the points.
	 */
	private static final Color LINE_COLOR = Color.BLUE;

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code Graph} with no points.
	 */
	public Graph() {
		super();
		this.pointList = new ArrayList<Point>();
		this.setOpaque(false);
	}

	/**
	 * Draws the points on to the screen.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(LINE_COLOR);
		for (Point p : this.pointList) {
			g.fillOval(p.x + (LINE_THICKNESS / 2), p.y + (LINE_THICKNESS / 2),
					LINE_THICKNESS, LINE_THICKNESS);
		}
	}

	/**
	 * Adds a point with the given coordinates to this graph.
	 * 
	 * @param x the x-coordinate of the new point.
	 * @param y the y-coordinate of the new point.
	 */
	public void addPoint(int x, int y) {
		this.pointList.add(new Point(x, y));
		this.repaint();
	}

	/**
	 * Clears all the points from this graph.
	 */
	public void clear() {
		this.pointList.clear();
	}

}
