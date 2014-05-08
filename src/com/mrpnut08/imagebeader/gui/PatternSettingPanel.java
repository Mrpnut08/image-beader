package com.mrpnut08.imagebeader.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.mrpnut08.imagebeader.imaging.BeadedImage;

public class PatternSettingPanel extends JPanel implements ActionListener{

	private float textsize;
	private JRadioButton fullsizeradio;
	
	public PatternSettingPanel() {
		super(new GridBagLayout());
		GridBagConstraints constrains = new GridBagConstraints();
		constrains.fill = GridBagConstraints.HORIZONTAL;
		
		textsize = BeadedImage.TEXT_LARGE;
		
		JLabel sizelabel = new JLabel("Size Settings");
		constrains.gridx = 0;
		constrains.gridy = 0;
		constrains.gridwidth = 3;
		this.add(sizelabel,constrains);
		
		ButtonGroup size_group = new ButtonGroup();
		fullsizeradio = new JRadioButton("Use original size",true);
		size_group.add(fullsizeradio);
		constrains.gridy = 1;
		this.add(fullsizeradio,constrains);
		
		// Create Text size Option label.
		JLabel textsizelabel = new JLabel("Text Size");
		constrains.gridx = 0;
		constrains.gridy = 2;
		constrains.gridwidth = 3;
		this.add(textsizelabel,constrains);
		
		// Creating Text Size options	
		constrains.gridy = 3;
		constrains.gridwidth = 1;
		ButtonGroup text_group = new ButtonGroup();
		
		JRadioButton textlarge = new JRadioButton("Large",true);
		textlarge.setActionCommand("TEXT_LARGE");
		textlarge.addActionListener(this);
		text_group.add(textlarge);
		constrains.gridx = 0;
		this.add(textlarge,constrains);
		
		JRadioButton textsmall = new JRadioButton("Small");
		textsmall.setActionCommand("TEXT_SMALL");
		textsmall.addActionListener(this);
		text_group.add(textsmall);
		constrains.gridx = 1;
		this.add(textsmall,constrains);
		
		JRadioButton textnone = new JRadioButton("None");
		textnone.setActionCommand("TEXT_NONE");
		textnone.addActionListener(this);
		text_group.add(textnone);
		constrains.gridx = 2;
		this.add(textnone,constrains);
	}
	
	public float getTextSize() {
		return textsize;
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
		}
		
	}
}
