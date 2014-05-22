/*
 * Copyright (C) 2014 Alfredo Giscombe (mrpnut08) <mrpnut08@gmail.com>
 *
 * This file is part of image-beader.
 *
 * image-beader is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * image-beader is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with image-beader.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mrpnut08.imagebeader.workers;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.mrpnut08.imagebeader.beads.BeadPallete;
import com.mrpnut08.imagebeader.imaging.BeadedImage;
import com.mrpnut08.imagebeader.listener.PatternGeneratorListener;

public class PatternGeneratorWorker extends SwingWorker<Void, Void> {

	private BeadedImage pattern;
	
	private PatternGeneratorListener listener;
	
	private boolean error;
	private String errormessage;
	
	private BufferedImage source;
	private BeadPallete pallete;
	private float text_size;
	
	public PatternGeneratorWorker(PatternGeneratorListener listener, BeadedImage pattern, BufferedImage source, BeadPallete pallete, float text_size) {
		this.pattern = pattern;
		this.listener = listener;
		this.source = source;
		this.pallete = pallete;
		this.text_size = text_size;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		
		try{
		
		this.pattern.generatePattern(source, pallete, text_size);
		error = false;
		
		} catch (IOException err) {
			error = true;
			errormessage = err.getMessage();
		}
		
		return null;
	}
	
	@Override
	protected void done() {
		if (error) {
			JOptionPane.showMessageDialog(null, errormessage);
		} else {
			listener.generationResutlts();
		}
	}
}
