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
