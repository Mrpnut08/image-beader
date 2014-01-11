package com.mrpnut08.imagebeader.imaging;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class UnbeadedImage {
	
	private BufferedImage image;
	private String filepath;
	
	
	public void loadImage(String filepath) throws IOException {
		this.filepath = filepath;
		this.image = ImageIO.read(new File(filepath));
	}
	
	public void loadImage(File file) throws IOException {
		this.filepath = file.getAbsolutePath();
		this.image = ImageIO.read(file);
	}
	
	public ImageIcon getImageIcon() {
		return (new ImageIcon(this.image));
	}
	
	public ImageIcon getScaledImageIcon(float percent) {
		int new_width = (int)Math.floor(this.image.getWidth() * percent / 100);
		int	new_height = (int)Math.floor(this.image.getHeight() * percent / 100);
		
		return (new ImageIcon(this.image.getScaledInstance(new_width,new_height, Image.SCALE_DEFAULT)));
	}
	
	public String getFilePath() {
		return this.filepath;
	}
}
