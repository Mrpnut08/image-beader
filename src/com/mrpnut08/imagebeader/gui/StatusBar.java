package com.mrpnut08.imagebeader.gui;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;

public class StatusBar extends JPanel {

	private JLabel text;
	private JProgressBar progressbar;
	
	public StatusBar() {
		super(new FlowLayout(FlowLayout.TRAILING));
		
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		text = new JLabel("Welcome");
		this.add(text);
		
		progressbar = new JProgressBar();
		this.add(progressbar);
	}
	
	public void setText(String text) {
		this.text.setText(text);
	}
	
}
