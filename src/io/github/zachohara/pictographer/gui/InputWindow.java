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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * An {@code InputWindow} is a {@code JFrame} that handles input from the user for the
 * window settings of the graph.
 * 
 * @author Zach Ohara
 */
public class InputWindow extends JFrame implements KeyListener {

	/**
	 * The {@code OptionState} that all settings should be saved to.
	 */
	private OptionState options;


	/**
	 * The {@code JPanel} that holds all elements for text input.
	 */
	private JPanel windowSettings;

	/**
	 * The field for entering the minimum x-boundary. 
	 */
	private JTextField xMinField;

	/**
	 * The field for entering the maximum x-boundary. 
	 */
	private JTextField xMaxField;

	/**
	 * The field for entering the minimum y-boundary. 
	 */
	private JTextField yMinField;

	/**
	 * The field for entering the maximum y-boundary. 
	 */
	private JTextField yMaxField;

	/**
	 * The field for entering the function.
	 */
	private JTextField functionField;


	/**
	 * The default size of the settings window, in a {width, height} format.
	 */
	private static final int[] WINDOW_SIZE = {400, 120};

	/**
	 * The default title of the settings window.
	 */
	private static final String WINDOW_TITLE = "Pictographer Settings";


	/**
	 * The default width of the field used for entering the function.
	 */
	private static final int FUNCTION_FIELD_WIDTH = 28;

	/**
	 * The default width of the fields used for entering x or y boundaries.
	 */
	private static final int WINDOW_FIELD_WIDTH = 5;

	private static final long serialVersionUID = 1L;

	/**
	 * The {@code GraphWindow} that 'owns' this window.
	 */
	GraphWindow mainWindow;

	/**
	 * Constructs a new {@code InputWindow} with the given owner window and option state.
	 * 
	 * @param win the {@code GraphWindow} that 'owns' this window, and should be notified
	 * about any/all setting changes.
	 * @param options the {@code OptionState} object that all setting should be stored in.
	 */
	public InputWindow(GraphWindow win, OptionState options) {
		super();
		this.options = options;
		this.mainWindow = win;
		this.initializeWindow();
		this.initializeFunctionField();
		this.initializeWindowPanel();
		this.initializeWindowSettings();
	}

	/**
	 * Initializes the underlying {@code JFrame} for this window.
	 */
	private void initializeWindow() {
		this.setTitle(WINDOW_TITLE);
		this.setLayout(new BorderLayout());
		this.setSize(WINDOW_SIZE[0], WINDOW_SIZE[1]);
		this.setLocation(- (WINDOW_SIZE[0]), 0);
		this.setLocationRelativeTo(this.mainWindow);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(this);
	}

	/**
	 * Initializes the text field used for entering a function.
	 */
	private void initializeFunctionField() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(new JLabel("f(x) = "));
		this.functionField = new JTextField(FUNCTION_FIELD_WIDTH);
		this.functionField.addKeyListener(this);
		bottomPanel.add(this.functionField);
		this.add("South", bottomPanel);
	}

	/**
	 * Initializes the {@code JPanel} that contains all text input fields.
	 */
	private void initializeWindowPanel() {
		this.windowSettings = new JPanel();
		this.windowSettings.setLayout(new GridLayout(2, 2));
		this.add("Center", this.windowSettings);
	}

	/**
	 * Initializes the four {@code JTextArea} that act as window boundary settings.
	 */
	private void initializeWindowSettings() {
		this.xMinField = this.initializeSetting("Min. X ");
		this.xMaxField = this.initializeSetting("Max X ");
		this.yMinField = this.initializeSetting("Min. Y ");
		this.yMaxField = this.initializeSetting("Max Y ");
		this.loadDefaultSettings();
	}

	/**
	 * Loads the default settings from {@code OptionState}, if there are any, and sets
	 * those numbers to their corresponding fields in this window.
	 */
	private void loadDefaultSettings() {
		if (this.options.getxMin() != 0)
			this.xMinField.setText("" + this.options.getxMin());
		if (this.options.getxMax() != 0)
			this.xMaxField.setText("" + this.options.getxMax());
		if (this.options.getyMin() != 0)
			this.yMinField.setText("" + this.options.getyMin());
		if (this.options.getyMax() != 0)
			this.yMaxField.setText("" + this.options.getyMax());
	}

	/**
	 * Initializes a single setting with the given label text, and return the 
	 * {@code JTextField} for the setting.
	 * 
	 * @param labelText the label for the new setting.
	 * @return the {@code JTextField} for the new setting.
	 */
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

	/**
	 * Updates all of the settings and stores them in the {@code OptionState} object
	 * attached to this window. If any of the numbers in the fields are not valid,
	 * this method only updates from the function field.
	 */
	public void updateSettings() {
		if (this.allAreValid()) {
			this.options.setxMin(Double.parseDouble(this.xMinField.getText()));
			this.options.setxMax(Double.parseDouble(this.xMaxField.getText()));
			this.options.setyMin(Double.parseDouble(this.yMinField.getText()));
			this.options.setyMax(Double.parseDouble(this.yMaxField.getText()));
			this.options.setFunctionString(this.functionField.getText());

			this.mainWindow.update();
		} else {
			this.options.setFunctionString(this.functionField.getText());
			this.mainWindow.update();
		}
	}

	/**
	 * Determines if all of the values in the text fields are valid doubles.
	 * 
	 * @return {@code true} if all of the window settings are valid; {@code false}
	 * otherwise.
	 */
	private boolean allAreValid() {
		try {
			return Double.parseDouble(this.xMinField.getText()) != Double.NaN
					&& Double.parseDouble(this.xMaxField.getText()) != Double.NaN
					&& Double.parseDouble(this.yMinField.getText()) != Double.NaN
					&& Double.parseDouble(this.yMaxField.getText()) != Double.NaN;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Updates the settings when the 'enter' key is pressed.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			this.updateSettings();
	}

	// do nothing for these
	@Override public void keyReleased(KeyEvent e) {}
	@Override public void keyTyped(KeyEvent e) {}

}
