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

package com.mrpnut08.imagebeader.beads;

import java.awt.Color;

public class BeadColor {

	public static final BeadColor BLANK = new BeadColor("", "Blank", 0, 0, 0, 0);

	private String name, id;
	private Color color;

	public BeadColor(String id, String name, int red, int green, int blue,
			int alpha) {
		this.id = id;
		this.name = name;

		this.color = new Color(red, green, blue, alpha);
	}

	public Color getColor() {
		return this.color;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Color getContrastingColor() {
			float average = this.color.getRed() + this.getColor().getGreen()
					+ this.color.getBlue();
			average /= 3;
			return (average < 192) ? Color.WHITE : Color.BLACK;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	
}
