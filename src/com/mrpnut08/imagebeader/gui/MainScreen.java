package com.mrpnut08.imagebeader.gui;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.mrpnut08.imagebeader.imaging.UnbeadedImage;

public class MainScreen extends JFrame {
	
	private JLabel unbeadedimg_holder;
	private UnbeadedImage unbeadedimg;
	
	public MainScreen() {
		super("Image Beader");
		this.setSize(800, 600);
		
		this.generateUnbeadedPreviewPanel();
	}
	
	private void generateUnbeadedPreviewPanel() {
		this.unbeadedimg_holder = new JLabel();
		this.add(this.unbeadedimg_holder);
		
		try {
			
		 this.unbeadedimg = new UnbeadedImage("/home/algis-kun/Downloads/s1.png");
		 this.unbeadedimg_holder.setIcon(this.unbeadedimg.getImageIcon());
		
		} catch(IOException io_error){
			
		}
	}
}
