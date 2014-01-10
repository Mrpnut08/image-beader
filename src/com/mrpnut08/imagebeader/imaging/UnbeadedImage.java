package com.mrpnut08.imagebeader.imaging;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class UnbeadedImage {
	
	private BufferedImage image;
	private String filepath;
	
	public UnbeadedImage(String filepath) throws IOException {
		this.filepath = filepath;
		this.loadImage();
	}
	
	private void loadImage() throws IOException {
		File source = new File(this.filepath);
		
		this.image = ImageIO.read(source);
	}
	
	public ImageIcon getImageIcon() {
		return (new ImageIcon(this.image));
	}
}
