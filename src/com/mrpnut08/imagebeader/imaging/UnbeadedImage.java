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

package com.mrpnut08.imagebeader.imaging;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UnbeadedImage {
	
	private BufferedImage image;
	private String filepath;
	
	private static final int SCALE_WIDTH = 0;
	private static final int SCALE_HEIGHT = 1;
	
	public UnbeadedImage () {
		this.image = null;
		this.filepath = "";
	}
	
	public void loadImage(String filepath) throws IOException {
		this.filepath = filepath;
		this.image = ImageIO.read(new File(filepath));
	}
	
	public void loadImage(File file) throws IOException {
		this.filepath = file.getAbsolutePath();
		this.image = ImageIO.read(file);
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	public BufferedImage getResizedImage (Dimension pegboard_size){
		
		Dimension new_dim;
		
		// If image width is greater than height then scale based by width.
		if (this.image.getWidth() > this.image.getHeight()) {
			new_dim = this.getScaleDim(this.image.getWidth(), pegboard_size.width*29, this.image.getHeight(), SCALE_WIDTH);
			
			// Check if image height fits the pattern configuration and resized it by height if it doesn't
			if (new_dim.height > pegboard_size.height*29) {
				new_dim = this.getScaleDim(new_dim.height, pegboard_size.height*29, new_dim.width, SCALE_HEIGHT);		
			}
		
		// Otherwise, scale it by width.
		}else {
			new_dim = this.getScaleDim(this.image.getHeight(), pegboard_size.height*29, this.image.getWidth(), SCALE_HEIGHT);
			
			// Checking if the width fits and rescales accordingly.
			if (new_dim.width > pegboard_size.width*29) {
				new_dim = this.getScaleDim(new_dim.width, pegboard_size.width*29, new_dim.height, SCALE_WIDTH);		
			}
		}
		
		BufferedImage scaled_image = new BufferedImage(new_dim.width, new_dim.height, BufferedImage.TYPE_INT_ARGB);
		scaled_image.getGraphics().drawImage(this.image.getScaledInstance(new_dim.width, new_dim.height, Image.SCALE_REPLICATE),0,0,null);
		
		return scaled_image;
	}
	
	public String getFilePath() {
		return this.filepath;
	}

	public int getWidth() {
		return this.image.getWidth();
	}
	
	public int getHeight() {
		return this.image.getHeight();
	}
	
	private Dimension getScaleDim (int unscaled_size, int scaled_size, int opposite_size, int scale_side) {
		
		double scale_factor = ((double)scaled_size/unscaled_size);
		int new_size = (int)Math.ceil(opposite_size*scale_factor);
		
		if(scale_side == UnbeadedImage.SCALE_WIDTH) {
			return new Dimension(scaled_size, new_size);
		} else
			return new Dimension(new_size, scaled_size);
	}
}
