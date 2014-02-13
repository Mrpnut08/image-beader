package com.mrpnut08.imagebeader.imaging;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import com.mrpnut08.imagebeader.beads.BeadPallete;

public class BeadedImage {
	private BufferedImage image;
	
	public BeadedImage (Image source, BeadPallete pallete) {
		Graphics2D canvas;
		this.image = new BufferedImage( source.getWidth(null)*20,
										source.getHeight(null)*20,
										BufferedImage.TYPE_INT_ARGB);
		canvas = this.image.createGraphics();
	}
}
