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

import io.github.zachohara.pictographer.gui.GraphWindow;
import io.github.zachohara.pictographer.gui.SettingsWindow;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CancellationException;

import javax.imageio.ImageIO;

/**
 * The {@code Main} class acts as the entry point for the application.
 * 
 * @author Zach Ohara
 */
public class Main {

	/**
	 * The main procedure for entire application.
	 * 
	 * @param args command-line arguments.
	 */
	public static void main(String[] args) {
		BufferedImage image = loadImage();
		OptionState options = new OptionState();
		GraphWindow graphWindow = new GraphWindow(image, options);
		SettingsWindow inputWindow = new SettingsWindow(graphWindow, options);
		graphWindow.setVisible(true);
		inputWindow.setVisible(true);
	}

	/**
	 * Handles all file loading, from the creation of the file dialog, to the rejection of
	 * invalid files, to handling the user quitting the dialog.
	 * 
	 * @return a final, fully loaded {@code BufferedImage}.
	 */
	private static BufferedImage loadImage() {
		FileSelector dialog = new FileSelector();
		BufferedImage image = null;
		try {
			while (image == null) {
				File chosenFile = dialog.getFile();
				try {
					image = ImageIO.read(chosenFile);
				} catch (IOException e) {
					FileSelector.warnLoadError(chosenFile.getName());
				}
				if (image == null) {
					FileSelector.warnInvalidType(chosenFile.getName());
				}
			}
		} catch (CancellationException e) {
			System.exit(0);
		}
		return image;
	}

}
