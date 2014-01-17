package com.mrpnut08.imagebeader.beads;

import java.util.Set;
import com.mrpnut08.imagebeader.beads.BeadColor;

public class BeadPallete {
	
	private String name;
	private Set<BeadColor> pallete_colors;
	
	public BeadPallete (String name) {
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
}
