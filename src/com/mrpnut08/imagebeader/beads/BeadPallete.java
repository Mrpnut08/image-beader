package com.mrpnut08.imagebeader.beads;

import java.io.File;
import java.util.HashSet;

import com.mrpnut08.imagebeader.xml.PalleteHandler;

public class BeadPallete {
	
	private String name;
	private HashSet<BeadColor> pallete_colors;
	
	public BeadPallete (String name) {
		this.name = name;
	}
	
	public BeadPallete (File file) throws Exception{
		PalleteHandler xmlreader = new PalleteHandler(file);
		this.name = xmlreader.getPalleteName();
		this.pallete_colors = xmlreader.getBeadColorSet();
	}
	
	public String getName(){
		return this.name;
	}
}
