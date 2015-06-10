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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputWindow extends JFrame implements KeyListener {
	
	private OptionState options;
	
	private JPanel windowSettings;
	private JTextField xMinField;
	private JTextField xMaxField;
	private JTextField yMinField;
	private JTextField yMaxField;
	private JTextField functionField;
	
	private static final int[] WINDOW_SIZE = {400, 120};
	private static final String WINDOW_TITLE = "Pictographer Settings";
	
	private static final int FUNCTION_FIELD_WIDTH = 28;
	private static final int WINDOW_FIELD_WIDTH = 5;

	private static final long serialVersionUID = 1L;
	
	GraphWindow mainWindow;
	
	public InputWindow(GraphWindow win, OptionState options) {
		super();
		this.options = options;
		this.mainWindow = win;
		this.initializeWindow();
		this.initializeFunctionField();
		this.initializeWindowPanel();
		this.initializeWindowSettings();
	}
	
	private void initializeWindow() {
		this.setTitle(WINDOW_TITLE);
		this.setLayout(new BorderLayout());
		this.setSize(WINDOW_SIZE[0], WINDOW_SIZE[1]);
		this.setLocation(- WINDOW_SIZE[0], 0);
		this.setLocationRelativeTo(this.mainWindow);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(this);
	}
	
	private void initializeFunctionField() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(new JLabel("f(x) = "));
		this.functionField = new JTextField(FUNCTION_FIELD_WIDTH);
		this.functionField.addKeyListener(this);
		bottomPanel.add(this.functionField);
		this.add("South", bottomPanel);
	}
	
	private void initializeWindowPanel() {
		this.windowSettings = new JPanel();
		this.windowSettings.setLayout(new GridLayout(2, 2));
		this.add("Center", this.windowSettings);
	}
	
	private void initializeWindowSettings() {
		this.xMinField = this.initializeSetting("Min. X ");
		this.xMaxField = this.initializeSetting("Max X ");
		this.yMinField = this.initializeSetting("Min. Y ");
		this.yMaxField = this.initializeSetting("Max Y ");
	}
	
	private JTextField initializeSetting(String labelText) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JLabel label = new JLabel(labelText);
		JTextField field = new JTextField(WINDOW_FIELD_WIDTH);
		field.addKeyListener(this);
		panel.add(label);
		panel.add(field);
		this.windowSettings.add(panel);
		return field;
	}
	
	public void updateSettings() {
		this.options.setxMin(Double.parseDouble(this.xMinField.getText()));
		this.options.setxMax(Double.parseDouble(this.xMaxField.getText()));
		this.options.setyMin(Double.parseDouble(this.yMinField.getText()));
		this.options.setyMax(Double.parseDouble(this.yMaxField.getText()));
		this.options.setFunctionString(this.functionField.getText());
		
		this.mainWindow.update();
	}

	public static void main(String[] args) {
		JFrame win = new InputWindow(null, new OptionState());
		win.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			this.updateSettings();
	}

	// do nothing for these
	@Override public void keyReleased(KeyEvent e) {}
	@Override public void keyTyped(KeyEvent e) {}

}
