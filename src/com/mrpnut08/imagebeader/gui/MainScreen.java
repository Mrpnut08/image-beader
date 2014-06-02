/*
 * Copyright (C) 2014 Alfredo Giscombe (mrpnut08) <mrpnut08@gmail.com>
 *
 * This file is part of image-beader.
 *
 * image-beader is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * image-beader is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with image-beader.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mrpnut08.imagebeader.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.mrpnut08.imagebeader.beads.BeadPallete;
import com.mrpnut08.imagebeader.listener.OnImageLoadListener;

/** Corresponds to the Main Screen of the user interface where the user interacts 
  * with the program. This is handled using the Java Swing library.
  *  
  * @author Alfredo Giscombe (mrpnut08)
  */
public class MainScreen extends JFrame implements ActionListener,
												  OnImageLoadListener {
	
	private static final long serialVersionUID = 1L;
 
	private BeadPallete pallete;
	
	private JButton beading_button;

	private ImageLoadingPanel image_loader;
	private BeadPatternPanel result_panel;
	private PatternSettingPanel settings_panel;
	private StatusBar statusbar;
	
	public MainScreen() {
		super("Image Beader");
		this.setMinimumSize(new Dimension(640, 480));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);

		//Attempt to load the perler pallete file.
		try {
			this.pallete = new BeadPallete(
					new File(this.getClass().getClassLoader().getResource("./perler_pallete.xml").toURI()));
		} catch (Exception io_error) {
			JOptionPane.showMessageDialog(this, "Could not find the bead pallete file (perler_pallete.xml)", "Error", JOptionPane.ERROR_MESSAGE);
		} // ~try

		// Generate the GUI interface content.
		this.generateGUIContent();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.image_loader.getFilePath().isEmpty()) {
			JOptionPane.showMessageDialog(this, "No Image has been loaded",
					"Error", JOptionPane.ERROR_MESSAGE);
		
		} else {
			BufferedImage image = (this.settings_panel.needResizing())? this.image_loader.getScaledImage(this.settings_panel.getResizeSize()) :
																	this.image_loader.getImage();
		
			this.result_panel.generatePattern(image,
										  	  this.pallete, 
										  	  this.settings_panel.getTextSize());
		}
	}

	private void generateGUIContent() {
		
		this.statusbar = new StatusBar();
		this.add(statusbar,BorderLayout.SOUTH);
		
		this.result_panel = new BeadPatternPanel(this,this.statusbar);
		
		JSplitPane root_content_pane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, this.generateOptionPanel(),
				this.result_panel);
		this.add(root_content_pane);
		
		this.generateMenu();
	}

	
	private void generateMenu(){
		
		JMenuBar menubar = new JMenuBar();
		
		this.setJMenuBar(menubar);
	}
	
	/** Creates the pattern option panel which holds the pattern generation options.
	  * 
	  * @return the left panel of the split view.
	  */
	private JPanel generateOptionPanel() {
		// Create main panel.
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setMinimumSize(new Dimension(225, 480));

		// Create ImageLoadingPanel.
		this.image_loader = new ImageLoadingPanel(this, this.statusbar);
		panel.add(this.image_loader);
	
		// Create PatternSettingPanel which handles the settings the user 
		// inputs in order to generate the pattern.
		this.settings_panel = new PatternSettingPanel();
		panel.add(this.settings_panel);
		
		// Create the "Generate Bead Pattern" button.
		this.beading_button = new JButton("Generate Bead Pattern");
		
		Font font = this.beading_button.getFont().deriveFont(this.beading_button.getFont().getSize()*1.5f);
		this.beading_button.setFont(font);
		
		this.beading_button.setAlignmentX(CENTER_ALIGNMENT);
		this.beading_button.setActionCommand("BeadImage");
		this.beading_button.addActionListener(this);
		this.beading_button.setEnabled(false);
		panel.add(this.beading_button);
		
		// Return the finished panel.
		return (panel);
	}

	@Override
	public void onImageLoad(String filepath) {
			this.beading_button.setEnabled(true);
			this.settings_panel.SetImageSize(this.image_loader.getImageDimensions());
			this.statusbar.updateStatus(false, "Image Loaded");
	}

	public void enableBeading(boolean bool) {
		
		this.beading_button.setEnabled(bool);
	}
}
