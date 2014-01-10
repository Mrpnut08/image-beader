package com.mrpnut08.imagebeader.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mrpnut08.imagebeader.imaging.UnbeadedImage;

public class MainScreen extends JFrame implements ActionListener{
	
	private JLabel unbeadedimg_holder;
	private UnbeadedImage unbeadedimg;
	
	private JLabel source_imgpath;
	
	public MainScreen() {
		super("Image Beader");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setResizable(true);
		
		this.generateOptionPanel();
		this.generateUnbeadedPreviewPanel();
	}
	
	private void generateUnbeadedPreviewPanel() {
		this.unbeadedimg = new UnbeadedImage();
		this.unbeadedimg_holder = new JLabel();
		this.add(this.unbeadedimg_holder);
		
	}
	
	private void generateOptionPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		
		JButton button = new JButton("Open File");
		button.setActionCommand("FileOpen");
		button.addActionListener(this);
		
		this.source_imgpath = new JLabel("No file selected");
		
		
		panel.add(this.source_imgpath);
		panel.add(button);
		this.add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()) {
			
			case "FileOpen":
				try {
					
					JFileChooser filebrowser = new JFileChooser();
					filebrowser.setFileFilter(new FileNameExtensionFilter("Image Files",
																		  ImageIO.getReaderFormatNames()));
					int returnVal = filebrowser.showOpenDialog(this);
					
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						this.unbeadedimg.loadImage(filebrowser.getSelectedFile());
						this.unbeadedimg_holder.setIcon(this.unbeadedimg.getImageIcon());
						this.source_imgpath.setText(unbeadedimg.getFilePath());
					}// if(returnVal == JFileChooser.APPROVE_OPTION)
					
				} catch(IOException io_error){
					JOptionPane.showMessageDialog(this, io_error.getMessage());
				} //try
			break;
			
		} //switch(e.getActionCommand())
		
	}
}
