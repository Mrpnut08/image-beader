package com.mrpnut08.imagebeader.imaging;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UnbeadedImage {
	
	private BufferedImage image;
	private String filepath;
	
	
	public UnbeadedImage () {
		this.image = null;
		this.filepath = "";
	}
	
	public void loadImage(String filepath) throws IOException {
		this.filepath = filepath;
		this.image = ImageIO.read(new File(filepath));
	}
	
	public void loadImage(File file) throws IOException {
		this.filepath = file.getAbsolutePath();
		this.image = ImageIO.read(file);
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public Image getResizedImage (Dimension pegboard_size){
		Image scaled_image;
		
		
		return null;
	}
	
	public String getFilePath() {
		return this.filepath;
	}

	public int getWidth() {
		return this.image.getWidth();
	}
	
	public int getHeight() {
		return this.image.getHeight();
	}
}
