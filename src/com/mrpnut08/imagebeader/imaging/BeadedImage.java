package com.mrpnut08.imagebeader.imaging;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.ImageIcon;

import com.mrpnut08.imagebeader.beads.BeadColor;
import com.mrpnut08.imagebeader.beads.BeadPallete;

public class BeadedImage {
	
	private final int SQUARE_SIZE = 32;
	
	private BufferedImage image;
	private HashSet<String> colors_used;
	
	public BeadedImage() {
		this.colors_used = new HashSet<String>();
	}
	
	public void generateBeadImage (UnbeadedImage source, BeadPallete pallete) {
		Graphics2D canvas;
		BeadColor bead_color;
		
		this.image = new BufferedImage( source.getWidth()*this.SQUARE_SIZE,
										source.getHeight()*this.SQUARE_SIZE,
										BufferedImage.TYPE_INT_ARGB);
		canvas = this.image.createGraphics();
		for (int y = 0; y < source.getHeight(); y++) {
			for(int x = 0; x < source.getWidth(); x++) {
				bead_color = pallete.findColorEquivalent(source.getPixel(x, y));
				canvas.setColor(bead_color.getColor());
				canvas.fillRect(x*this.SQUARE_SIZE, y*this.SQUARE_SIZE, this.SQUARE_SIZE, this.SQUARE_SIZE);
				canvas.setColor(Color.WHITE);
				canvas.drawRect(x*this.SQUARE_SIZE, y*this.SQUARE_SIZE, this.SQUARE_SIZE, this.SQUARE_SIZE);
				canvas.drawString(bead_color.getId(), x*this.SQUARE_SIZE, (y+1)*this.SQUARE_SIZE);
				this.colors_used.add(bead_color.getName());
			}
		}
		this.image.flush();
		this.printColorList();
	}
	
	public ImageIcon getImageIcon() {
		return (new ImageIcon(this.image));
	}
	
	private void printColorList() {
		Iterator<String> iterator = this.colors_used.iterator();
		while (iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}
}
