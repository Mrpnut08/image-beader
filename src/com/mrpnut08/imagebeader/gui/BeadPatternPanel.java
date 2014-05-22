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
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.mrpnut08.imagebeader.beads.BeadPallete;
import com.mrpnut08.imagebeader.imaging.BeadedImage;
import com.mrpnut08.imagebeader.listener.PatternGeneratorListener;
import com.mrpnut08.imagebeader.listener.PegboardLoadWorkerListener;
import com.mrpnut08.imagebeader.listener.PegboardSwitcherListener;
import com.mrpnut08.imagebeader.workers.PatternGeneratorWorker;
import com.mrpnut08.imagebeader.workers.PegboardLoadWorker;

public class BeadPatternPanel extends JPanel implements ActionListener, PegboardSwitcherListener, PegboardLoadWorkerListener, PatternGeneratorListener{

	private static final long serialVersionUID = 1L;
	
	private JLabel image_view; 
	
	private JButton pattern_details,
					pegboard_switcher;
	
	private BeadedImage pattern;
	
	private MainScreen parent;
	
	private Point pegboard_index;
	
	private StatusBar statusbar;
	
	public BeadPatternPanel (MainScreen parent, StatusBar statusbar){
		
		// Call super class constructor to initialize JPanel.
		super();
		this.setMinimumSize(new Dimension(415,480));
		this.setLayout(new BorderLayout());

		// Store parent Frame used for calling the dialogs.
		this.parent = parent;
		
		// Store the statusbar to update the status.
		this.statusbar = statusbar;
		
		// Getting ready the BeadedImage class.
		this.pattern = new BeadedImage();
		
		// Create the image view JLabel used to display the images.
		this.image_view = new JLabel();
		this.image_view.setHorizontalAlignment(JLabel.CENTER);
		this.image_view.setAlignmentX(CENTER_ALIGNMENT);
		this.image_view.setAlignmentY(CENTER_ALIGNMENT);
		
		//Create scrollpane for the imageview.
		JScrollPane image_scroll = new JScrollPane(this.image_view);
		this.add(image_scroll, BorderLayout.CENTER);
		
		//Create the Button Panel
		JPanel button_panel = new JPanel(new FlowLayout());
		this.add(button_panel, BorderLayout.PAGE_START);
		
		//Create the Pattern Details Button.
		this.pattern_details = new JButton("Pattern Details");
		this.pattern_details.setActionCommand("PatternDetails");
		this.pattern_details.addActionListener(this);
		this.pattern_details.setEnabled(false);
		button_panel.add(pattern_details);
		
		//Create the pegboard switcher button.
		this.pegboard_switcher = new JButton("Switch Pegboard");
		this.pegboard_switcher.setActionCommand("SwitchPegboard");
		this.pegboard_switcher.addActionListener(this);
		this.pegboard_switcher.setEnabled(false);
		button_panel.add(pegboard_switcher);
	}
	
	public void generatePattern (BufferedImage image, BeadPallete pallete, float text_size) {
		this.parent.enableBeading(false);
		this.statusbar.updateStatus(true, "Generating Pattern");
		
		new PatternGeneratorWorker(this, this.pattern, image, pallete, text_size).execute();
	}
	
	public void actionPerformed(ActionEvent e) {
		
		// Check which button was pressed
		switch( e.getActionCommand()){
		
		// Open pattern details window if the pattern datails button was pressed.
		case "PatternDetails":
			try {
			PreviewImageIcon thumbnail = new PreviewImageIcon();
			thumbnail.setImage(ImageIO.read(this.pattern.getPatternThumbnail()));
			
			new PatternDetailsDialog(this.parent, thumbnail, this.pattern.getUsedColorList()).setVisible(true);
			} catch (IOException io_err) {
				JOptionPane.showMessageDialog(this, io_err.getMessage());
			}
		break;
		
		// if the pegboard switcher button was pressed.
		case "SwitchPegboard":
			new SwitchPegboardDialog(this, this.parent,this.pattern.getSize(),this.pegboard_index).setVisible(true);
		break;
		}
	}

	@Override
	public void onPegboardSwitch(Point index) {
		this.statusbar.updateStatus(true, "Loading pegboard " + (index.x+1) + ", " + (index.y+1));
		new PegboardLoadWorker(this, this.pattern, index.x, index.y).execute();
	}

	@Override
	public File getPegboardThumbnail(int x, int y) {
		return this.pattern.getPegboardThumbnail(x, y);
	}

	@Override
	public void onPegboardLoad(ImageIcon icon, Point index) {
		this.pegboard_index = index;
		this.image_view.setIcon(icon);
		
		this.statusbar.updateStatus(false, "Loaded pegboard at " + (index.x+1) + ", " + (index.y+1));
	}

	@Override
	public void generationResults() {
		
		// Enable Buttons.
		this.pattern_details.setEnabled(true);
		this.pegboard_switcher.setEnabled(true);
					
		this.statusbar.updateStatus(false, "Finished pattern");
		
		//load the pegboard (0,0).
		this.onPegboardSwitch(new Point(0,0));
		
		this.parent.enableBeading(true);
	}

	@Override
	public void generationFailed() {
		this.parent.enableBeading(true);
	}
}
