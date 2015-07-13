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

import java.awt.FileDialog;
import java.io.File;
import java.util.concurrent.CancellationException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * A {@code FileSelector} is essentially a file selection window. It creates a window,
 * displays that window, and then returns file that the user selects.
 * 
 * @author Zach Ohara
 */
public class FileSelector {

	/**
	 * The window used for the filedialog.
	 */
	private JFrame window;

	/**
	 * The {@code FileDialog} that is displayed in the window.
	 */
	private FileDialog dialog;

	/**
	 * The title for the window.
	 */
	public static final String WINDOW_TITLE = "Choose a background image";

	/**
	 * Constructs a new {@code FileSelector}, but does not display any windows.
	 */
	public FileSelector() {
		this.window = new JFrame(WINDOW_TITLE);
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.dialog = new FileDialog(this.window, WINDOW_TITLE);
		this.dialog.setMode(FileDialog.LOAD);
		this.dialog.setMultipleMode(false);
	}

	/**
	 * Prompts the user to select a file to use as the backdrop, and returns that file.
	 * 
	 * @return the file selected by the user.
	 * @throws CancellationException if the user closed the selection dialog.
	 */
	public File getFile() throws CancellationException {
		this.dialog.setVisible(true);
		File[] selected = this.dialog.getFiles();
		if (selected.length > 0)
			return selected[0];
		throw new CancellationException();
	}

	/**
	 * Warns the user that a selected file could not be loaded for an unknown reason.
	 * 
	 * @param filename the name of the unloadable file.
	 */
	public static void warnLoadError(String filename) {
		JOptionPane.showMessageDialog(null, "An unknown error occured while trying to open "
				+ filename + ".", "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Warns the user that a selected file is not a valid image file.
	 * 
	 * @param filename the name of the invalid file.
	 */
	public static void warnInvalidType(String filename) {
		JOptionPane.showMessageDialog(null, filename + " is not an image, and cannot be loaded",
				"Warning", JOptionPane.WARNING_MESSAGE);
	}

}
