package io.github.zachohara.pictographer.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Graph extends JPanel {
	
	private List<Point> pointList;
	
	private static final int LINE_THICKNESS = 5;
	private static final Color LINE_COLOR = Color.BLUE;

	private static final long serialVersionUID = 1L;

	public Graph() {
		super();
		this.pointList = new ArrayList<Point>();
		this.setOpaque(false);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(LINE_COLOR);
		for (Point p : this.pointList) {
			g.fillOval(p.x + (LINE_THICKNESS / 2), p.y + (LINE_THICKNESS / 2),
					LINE_THICKNESS, LINE_THICKNESS);
		}
	}
	
	public void addPoint(int x, int y) {
		this.pointList.add(new Point(x, y));
		this.repaint();
	}
	
	public void clear() {
		this.pointList.clear();
	}
	
//	public static void main(String[] args) {
//		Graph g = new Graph();
//		JFrame jf = new JFrame();
//		jf.setLayout(null);
//		jf.setSize(500, 500);
//		g.setSize(jf.getSize());
//		g.setLocation(0, 0);
//		g.pointList.add(new Point(10, 10));
//		jf.add(g);
//		jf.setVisible(true);
//	}

}
