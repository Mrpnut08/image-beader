package com.mrpnut08.imagebeader.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.mrpnut08.imagebeader.actions.PegboardSwitchAction;
import com.mrpnut08.imagebeader.listener.PegboardSwitcherListener;

public class SwitchPegoardDialog extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;

	public SwitchPegoardDialog(PegboardSwitcherListener listener, Frame parent, Dimension size, Point index) {
		super(parent, "Switch Pegboard");
		this.setResizable(true);
		this.setMinimumSize(new Dimension(58*(size.width+2), 
										  58*(size.height+2)));
		
		this.setLayout(new BorderLayout());

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraint = new GridBagConstraints();
		
		// Create content Panel.
		JPanel panel = new JPanel(layout);
		
		ButtonGroup group = new ButtonGroup();
		
		for (int y = 0; y < size.height; y++) {
			for (int x = 0; x < size.width; x++) {
				JToggleButton button = new JToggleButton();
				
				button.setSelected((x == index.x && y == index.y));
				button.setPreferredSize(new Dimension(58,58));
				button.setAction(new PegboardSwitchAction(listener, x, y));
				
				constraint.gridx = x;
				constraint.gridy = y;
				
				group.add(button);
				panel.add(button, constraint);
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
