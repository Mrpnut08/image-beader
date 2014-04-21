package com.mrpnut08.imagebeader.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.mrpnut08.imagebeader.beads.BeadPallete;
import com.mrpnut08.imagebeader.imaging.BeadedImage;

public class BeadPatternPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JLabel image_view; 
	
	private JButton pattern_details,
					pegboard_switcher;
	
	private BeadedImage pattern;
	
	private Frame parent;
	
	private Point pegboard_index;
	
	public BeadPatternPanel (Frame parent){
		
		// Call super class constructor to initialize JPanel.
		super();
		this.setMinimumSize(new Dimension(440,480));
		this.setLayout(new BorderLayout());

		// Store parent Frame used for calling the dialogs.
		this.parent = parent;
		
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
	
	public void generatePattern (String filepath, BeadPallete pallete, int text_size) {
		
		// Attempt to generate the bead pattern 
		try {
			
			this.pattern.generateBeadSet(filepath, pallete, text_size);
		
			// Enable Buttons.
			this.pattern_details.setEnabled(true);
			this.pegboard_switcher.setEnabled(true);
			
			//load upper-left pegboard (0,0).
			this.image_view.setIcon(this.pattern.getPegboard(0, 0));
			
			//save pegboard index.
			this.pegboard_index = new Point(0,0);
			
		// Show error dialog if anything happens
		} catch (IOException io_err) {
			JOptionPane.showMessageDialog(this, io_err.getMessage());
		}
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		// Check which button was pressed
		switch( e.getActionCommand()){
		
		// Open pattern details window if the pattern datails button was pressed.
		case "PatternDetails":
			new PatternDetailsDialog(this.parent, this.pattern.getThumbnail(), this.pattern.getUsedColorList()).setVisible(true);
		break;
		
		// if the pegboard switcher button was pressed.
		case "SwitchPegboard":
		break;
		}
	}
}
