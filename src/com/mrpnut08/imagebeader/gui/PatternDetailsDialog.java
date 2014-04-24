package com.mrpnut08.imagebeader.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PatternDetailsDialog extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;

	public PatternDetailsDialog(Frame parent, PreviewImageIcon thumbnail_image, String[] colors) {
		super(parent, "Pattern Details");
		this.setResizable(true);
		this.setMinimumSize(new Dimension(400, 480));
		this.setLayout(new BorderLayout());

		// Create content Panel.
		JPanel panel = new JPanel(new FlowLayout());
		this.add(panel,BorderLayout.CENTER);
		
		// Create preview thumbnail JLabel.
		JLabel thumbnail = new JLabel("Preview");
		thumbnail.setVerticalTextPosition(JLabel.TOP);
		thumbnail.setHorizontalTextPosition(JLabel.CENTER);
		thumbnail.setAlignmentY(TOP_ALIGNMENT);
		thumbnail.setMinimumSize(new Dimension(200,200));
		panel.add(thumbnail);
		
		//Load the preview image.
		thumbnail.setIcon(thumbnail_image);
		
		//Create the panel for the colors used in the pattern.
		JPanel color_panel = new JPanel();
		color_panel.setMinimumSize(new Dimension(250,280));
		color_panel.setLayout(new BoxLayout(color_panel, BoxLayout.Y_AXIS));
		
		
		JLabel bead;
		for (String color : colors) {
			bead = new JLabel(color);
			bead.setBorder(BorderFactory.createEmptyBorder(3, 1, 3, 1));
			color_panel.add(bead);
		}
		
		JScrollPane scrollpanel = new JScrollPane(color_panel);
		scrollpanel.setMinimumSize(new Dimension(250,280));
		scrollpanel.setMaximumSize(new Dimension(250,480));
		scrollpanel.setBorder(BorderFactory.createTitledBorder("Beads used"));
		
		panel.add(scrollpanel);
		
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
