package com.mrpnut08.imagebeader.gui;

import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.mrpnut08.imagebeader.imaging.UnbeadedImage;

public class MainScreen extends JFrame {
	
	private JLabel unbeadedimg_holder;
	private UnbeadedImage unbeadedimg;
	
	private JLabel source_imgpath;
	
	public MainScreen() {
		super("Image Beader");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		this.generateOptionPanel();
		this.generateUnbeadedPreviewPanel();
	}
	
	private void generateUnbeadedPreviewPanel() {
		this.unbeadedimg = new UnbeadedImage();
		this.unbeadedimg_holder = new JLabel();
		this.add(this.unbeadedimg_holder);
		
		try {
			
		 this.unbeadedimg.loadImage("/home/algis-kun/Downloads/s1.png");
		 this.unbeadedimg_holder.setIcon(this.unbeadedimg.getImageIcon());
		 this.source_imgpath.setText(unbeadedimg.getFilePath());
		
		} catch(IOException io_error){
			JOptionPane.showMessageDialog(this, io_error.getMessage());
		}
	}
	
	private void generateOptionPanel() {
		this.source_imgpath = new JLabel("No file selected");
		this.add(this.source_imgpath);
	}
}
