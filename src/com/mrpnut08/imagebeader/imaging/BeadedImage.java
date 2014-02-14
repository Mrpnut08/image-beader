package com.mrpnut08.imagebeader.imaging;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.ImageIcon;

import com.mrpnut08.imagebeader.beads.BeadColor;
import com.mrpnut08.imagebeader.beads.BeadPallete;

public class BeadedImage {
	
	private final int SQUARE_SIZE = 24;
	
	private BufferedImage image;
	private HashSet<String> colors_used;
	
	public BeadedImage() {
		this.colors_used = new HashSet<String>();
	}
	
	public void generateBeadImage (UnbeadedImage source, BeadPallete pallete) {
		Graphics2D canvas;
		BeadColor bead_color;
		Font font = new Font(Font.MONOSPACED,Font.PLAIN,this.SQUARE_SIZE/2);
		
		int tx, ty;
		this.image = new BufferedImage( source.getWidth()*this.SQUARE_SIZE,
										source.getHeight()*this.SQUARE_SIZE,
										BufferedImage.TYPE_INT_ARGB);
		canvas = this.image.createGraphics();
		canvas.setFont(font);
		
		for (int y = 0; y < source.getHeight(); y++) {
			for(int x = 0; x < source.getWidth(); x++) {
				tx = x * this.SQUARE_SIZE;
				ty = y * this.SQUARE_SIZE;
				
				bead_color = pallete.findColorEquivalent(source.getPixel(x, y));
				canvas.setColor(bead_color.getColor());
				canvas.fillRect(tx, ty, this.SQUARE_SIZE, this.SQUARE_SIZE);
				canvas.setColor(Color.WHITE);
				canvas.drawRect(tx, ty, this.SQUARE_SIZE, this.SQUARE_SIZE);
				canvas.drawString(bead_color.getId(), tx, (y+1)*this.SQUARE_SIZE);
				
				if (!bead_color.getId().isEmpty()) {
				this.colors_used.add(bead_color.getId() +" - "+ bead_color.getName());
				}
			}
		}
		this.image.flush();
	}
	
	public ImageIcon getImageIcon() {
		return (new ImageIcon(this.image));
	}
	
	public String[] getUsedColorList() {
		String list = "";
		Iterator<String> iterator = this.colors_used.iterator();
		while (iterator.hasNext()){
			list += iterator.next() + "\n";
		}
		return list.split("\n");
	}
}
