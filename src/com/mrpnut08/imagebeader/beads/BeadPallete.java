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
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;

import com.mrpnut08.imagebeader.xml.PalleteHandler;

public class BeadPallete {
	
	private String name;
	private HashSet<BeadColor> pallete_colors;
	
	public BeadPallete (String filename) throws Exception {
		this(new File(filename));
	}
	
	public BeadPallete (File file) throws Exception{
		PalleteHandler xmlreader = new PalleteHandler(file);
		this.name = xmlreader.getPalleteName();
		this.pallete_colors = xmlreader.getBeadColorSet();
	}
	
	public String getName(){
		return this.name;
	}
	
	public BeadColor findColorEquivalent(Color sample) {
		double distance;
		double minimal = Double.MAX_VALUE;
		BeadColor color, current;
		Iterator<BeadColor> iterator = this.pallete_colors.iterator();
		
		color = BeadColor.BLANK;
		
		if(sample.getAlpha() < 191) {
			return color;
		}
		
		
		
		while (iterator.hasNext()) {
			current = iterator.next();
			distance = this.findDistance(sample, current.getColor());
			if (minimal > distance) {
			 minimal = distance;
			 color = current;
		 }
		}
	
		return color;
	}
	
	private double findDistance (Color color1, Color color2) {
		double difference;
		difference = Math.pow(color1.getRed() - color2.getRed(), 2)
		 		   + Math.pow(color1.getGreen() - color2.getGreen(), 2)
		 		   + Math.pow(color1.getBlue() - color2.getBlue(), 2);
		difference = Math.sqrt(difference);
		return difference;
	}
	
}
