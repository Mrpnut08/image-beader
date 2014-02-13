package com.mrpnut08.imagebeader.imaging;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.mrpnut08.imagebeader.beads.BeadPallete;

public class BeadedImage {
	
	private final int SQUARE_SIZE = 32;
	
	private BufferedImage image;
	
	public void generateBeadImage (UnbeadedImage source, BeadPallete pallete) {
		Graphics2D canvas;
		
		this.image = new BufferedImage( source.getWidth()*this.SQUARE_SIZE,
										source.getHeight()*this.SQUARE_SIZE,
										BufferedImage.TYPE_INT_ARGB);
		canvas = this.image.createGraphics();
		for (int y = 0; y < source.getHeight(); y++) {
			for(int x = 0; x < source.getWidth(); x++) {
				canvas.setColor(source.getPixel(x, y));
				canvas.fillRect(x*this.SQUARE_SIZE, y*this.SQUARE_SIZE, this.SQUARE_SIZE, this.SQUARE_SIZE);
				canvas.setColor(Color.WHITE);
				canvas.drawRect(x*this.SQUARE_SIZE, y*this.SQUARE_SIZE, this.SQUARE_SIZE, this.SQUARE_SIZE);
			}
		}
		this.image.flush();
	}
	
	public ImageIcon getImageIcon() {
		return (new ImageIcon(this.image));
	}
}
