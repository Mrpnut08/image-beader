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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.mrpnut08.imagebeader.imaging.BeadedImage;

public class PatternSettingPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private float textsize;
	private JRadioButton fullsizeradio,
						 resizedradio;
	
	private JSpinner widthfield,
					 heightfield;
	
	private SpinnerModel widthmodel,
					 	 heightmodel;
	
	public PatternSettingPanel() {
		super();
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		textsize = BeadedImage.TEXT_LARGE;
		
		this.add(createSizePanel());
		
		// Create Text size Option label.
		this.add(createTextSizePanel());
	}
	
	private JPanel createSizePanel(){
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints constrains = new GridBagConstraints();
		constrains.fill = GridBagConstraints.HORIZONTAL;
		
		JLabel sizelabel = new JLabel("Size Settings");
		sizelabel.setFont(sizelabel.getFont().deriveFont(Font.BOLD));
		constrains.gridx = 0;
		constrains.gridy = 0;
		constrains.gridwidth = 5;
		pane.add(sizelabel,constrains);
		
		ButtonGroup size_group = new ButtonGroup();
		
		fullsizeradio = new JRadioButton("Use original size",true);
		fullsizeradio.setActionCommand("FULL_SIZE");
		fullsizeradio.addActionListener(this);
		size_group.add(fullsizeradio);
		constrains.gridy = 1;
		pane.add(fullsizeradio,constrains);
		
		resizedradio = new JRadioButton("Resize to fit pegboard size: ");
		resizedradio.setActionCommand("RESIZE");
		resizedradio.addActionListener(this);
		size_group.add(resizedradio);
		constrains.gridy = 2;
		pane.add(resizedradio,constrains);
		
		constrains.gridy=3;
		constrains.gridwidth=1;
		widthmodel = new SpinnerNumberModel(0, 0, 10, 1);
		heightmodel = new SpinnerNumberModel(0,0,10,1);
		
		JLabel widthlabel = new JLabel("Width:");
		widthlabel.setAlignmentX(CENTER_ALIGNMENT);
		constrains.gridx = 0;
		pane.add(widthlabel,constrains);
		
		widthfield = new JSpinner(widthmodel);
		widthfield.setEnabled(false);
		constrains.gridx = 1;
		pane.add(widthfield,constrains);
		
		JLabel divider = new JLabel(" x ");
		divider.setAlignmentX(CENTER_ALIGNMENT);
		constrains.gridx = 2;
		pane.add(divider,constrains);
		
		JLabel heightlabel = new JLabel("Height:");
		heightlabel.setAlignmentX(CENTER_ALIGNMENT);
		constrains.gridx = 3;
		pane.add(heightlabel,constrains);
		
		heightfield = new JSpinner(heightmodel);
		heightfield.setEnabled(false);
		constrains.gridx = 4;
		pane.add(heightfield,constrains);
		
		return(pane);
	}
	
	private JPanel createTextSizePanel(){
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints constrains = new GridBagConstraints();
		constrains.fill = GridBagConstraints.HORIZONTAL;
		
		JLabel textsizelabel = new JLabel("Text Size");
		textsizelabel.setFont(textsizelabel.getFont().deriveFont(Font.BOLD));
		constrains.gridx = 0;
		constrains.gridy = 0;
		constrains.gridwidth = 3;
		pane.add(textsizelabel,constrains);
		
		// Creating Text Size options	
		constrains.gridy = 1;
		constrains.gridwidth = 1;
		ButtonGroup text_group = new ButtonGroup();
		
		JRadioButton textlarge = new JRadioButton("Large",true);
		textlarge.setActionCommand("TEXT_LARGE");
		textlarge.addActionListener(this);
		text_group.add(textlarge);
		constrains.gridx = 0;
		pane.add(textlarge,constrains);
		
		JRadioButton textsmall = new JRadioButton("Small");
		textsmall.setActionCommand("TEXT_SMALL");
		textsmall.addActionListener(this);
		text_group.add(textsmall);
		constrains.gridx = 1;
		pane.add(textsmall,constrains);
		
		JRadioButton textnone = new JRadioButton("None");
		textnone.setActionCommand("TEXT_NONE");
		textnone.addActionListener(this);
		text_group.add(textnone);
		constrains.gridx = 2;
		pane.add(textnone,constrains);
		
		return (pane);
	}
	
	public float getTextSize() {
		return textsize;
	}
	
	public boolean needResizing() {
		return (this.resizedradio.isSelected() && ((int)widthmodel.getValue() > 0 && (int)heightmodel.getValue() > 0));
	}
	
	public Dimension getResizeSize(){
		return new Dimension((int)widthmodel.getValue(), (int)heightmodel.getValue());
	}
	
	public void SetImageSize (Dimension imagesize){
		fullsizeradio.setText("Use original size (" + 
							  (int)Math.ceil(imagesize.getWidth()/29f) +
							  " x " +
							  (int)Math.ceil(imagesize.getHeight()/29f) +
							  ")");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "TEXT_LARGE":
				textsize = BeadedImage.TEXT_LARGE;
				return;
			case "TEXT_SMALL":
				textsize = BeadedImage.TEXT_SMALL;
				return;
			case "TEXT_NONE":
				textsize = BeadedImage.TEXT_NONE;
				return;
			case "FULL_SIZE":
				widthfield.setEnabled(false);
				heightfield.setEnabled(false);
				return;
			case "RESIZE":
				widthfield.setEnabled(true);
				heightfield.setEnabled(true);
				return;
		}
	}
}
