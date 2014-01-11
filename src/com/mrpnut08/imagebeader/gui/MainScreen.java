package com.mrpnut08.imagebeader.gui;

import java.awt.Dimension;
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
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mrpnut08.imagebeader.imaging.UnbeadedImage;

public class MainScreen extends JFrame implements ActionListener{

	private JLabel unbeadedimg_holder;
	private UnbeadedImage unbeadedimg;

	private JLabel source_imgpath;
	
	public MainScreen() {
		super("Image Beader");
		this.setSize(640, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		
		JSplitPane root_content_pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
													  this.generateOptionPanel(), 
													  this.generateUnbeadedPreviewPanel());
		this.add(root_content_pane);
	}
	
	private JLabel generateUnbeadedPreviewPanel() {
		
		this.unbeadedimg = new UnbeadedImage();
		this.unbeadedimg_holder = new JLabel();
		this.unbeadedimg_holder.setMinimumSize(new Dimension(440, 480));
		this.unbeadedimg_holder.setHorizontalAlignment(SwingConstants.CENTER);
		this.unbeadedimg_holder.setVerticalAlignment(SwingConstants.CENTER);
		return (this.unbeadedimg_holder);
		
		
	}
	
	private JPanel generateOptionPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setMinimumSize(new Dimension(200, 480));
		
		JButton button = new JButton("Open File");
		button.setActionCommand("FileOpen");
		button.addActionListener(this);
		
		this.source_imgpath = new JLabel("No file selected");
		
		
		panel.add(this.source_imgpath);
		panel.add(button);
		return(panel);
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
