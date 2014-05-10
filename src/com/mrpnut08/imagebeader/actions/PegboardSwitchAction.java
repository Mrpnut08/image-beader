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

package com.mrpnut08.imagebeader.actions;

import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import com.mrpnut08.imagebeader.listener.PegboardSwitcherListener;

public class PegboardSwitchAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	
	private Point pegboard_index;
	private PegboardSwitcherListener listener;
	
	public PegboardSwitchAction (PegboardSwitcherListener listener, int x, int y) {
		super("",new ImageIcon(listener.getPegboardThumbnail(x, y).getPath()));
		this.listener = listener;
		this.pegboard_index = new Point(x,y);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		listener.onPegboardSwitch(this.pegboard_index);
	}

}
