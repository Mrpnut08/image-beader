package com.mrpnut08.imagebeader.gui;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class StatusBar extends JPanel {

	private JLabel message;
	private JProgressBar progressbar;
	
	public StatusBar() {
		super(new FlowLayout(FlowLayout.TRAILING));
		
		message = new JLabel("Welcome");
		this.add(message);
		
		progressbar = new JProgressBar();
		this.add(progressbar);
	}
}
