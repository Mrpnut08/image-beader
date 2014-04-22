package com.mrpnut08.imagebeader.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class SwitchPegoardDialog extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;

	public SwitchPegoardDialog(Frame parent, Dimension size, Point index) {
		super(parent, "Pattern Details");
		this.setResizable(true);
		this.setMinimumSize(new Dimension(400, 280));
		this.setLayout(new BorderLayout());

		// Create content Panel.
		JPanel panel = new JPanel(new GridLayout(size.height, size.width));
		
		ButtonGroup group = new ButtonGroup();
		
		for (int x = 0; x < size.width; x++) {
			for (int y = 0; y < size.height; y++) {
				JToggleButton button = new JToggleButton(y +", "+ x, (x == index.x && y == index.y));
				group.add(button);
				panel.add(button);
			}
		}
		
		this.add(panel,BorderLayout.CENTER);
		
		
		// Create Dialog Close Button
		JButton close_button = new JButton("Close");
		close_button.addActionListener(this);
		this.add(close_button, BorderLayout.PAGE_END);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.dispose();	
	}
}
