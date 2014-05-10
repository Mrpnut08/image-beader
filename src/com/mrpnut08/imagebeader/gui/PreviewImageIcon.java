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

package com.mrpnut08.imagebeader.gui;

import java.awt.Image;

import javax.swing.ImageIcon;

/** ImageIcon subclass that automatically scales its image to 200 * 200 pixels.
 *  The scaled image maintains it's aspect ratio.
 * 
 * @author Alfredo Giscombe (Mrpnut08)
 */
public class PreviewImageIcon extends ImageIcon {

	private static final long serialVersionUID = 1L;
	
	public PreviewImageIcon() {
		super();
	}
	
	/** (Setter)
	 * Sets the image scaled down to fit a 200 by 200 pixel square.
	 * the scaled image maintains it's aspect ratio. 
	 */
	@Override
	public void setImage(Image image) {
		
		// Get the scaling proportion by dividing the 200 by the image's biggest dimension.
		double scale = 200d / (double) Math.max(image.getHeight(null), image.getWidth(null));
		
		//Set the scaled down version of the image as the Icon.
		super.setImage(image.getScaledInstance(
				(int)Math.round(image.getWidth(null) * scale), 
				(int)Math.round(image.getHeight(null) * scale),
				Image.SCALE_FAST)	//scaling set to fast since no image processing will be done with this object. 
				);
	}
}
