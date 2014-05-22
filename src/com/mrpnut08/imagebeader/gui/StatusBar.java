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

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;

public class StatusBar extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
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
	
	public void updateStatus(boolean active, String text) {
		this.progressbar.setIndeterminate(active);
		this.text.setText(text);
	}
	
	
}
