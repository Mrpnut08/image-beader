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

public class SwitchPegboardDialog extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;

	public SwitchPegboardDialog(PegboardSwitcherListener listener, Frame parent, Dimension size, Point index) {
		super(parent, "Switch Pegboard");
		this.setResizable(false);
		this.setMinimumSize(new Dimension(87*(size.width+2), 
										  87*(size.height+2)));
		
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
				button.setPreferredSize(new Dimension(87,87));
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
