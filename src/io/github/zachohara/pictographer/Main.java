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

import io.github.zachohara.pictographer.gui.GraphWindow;
import io.github.zachohara.pictographer.gui.InputWindow;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) {
		FileSelector dialog = new FileSelector();
		File chosenFile = dialog.getFile();
		BufferedImage image = null;
		try {
			image = ImageIO.read(chosenFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		OptionState options = new OptionState();
		GraphWindow graphWindow = new GraphWindow(image, options);
		InputWindow inputWindow = new InputWindow(graphWindow, options);
		graphWindow.setVisible(true);
		inputWindow.setVisible(true);
	}

}
