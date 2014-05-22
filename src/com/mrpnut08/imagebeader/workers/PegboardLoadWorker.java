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

package com.mrpnut08.imagebeader.workers;

import java.awt.Point;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

import com.mrpnut08.imagebeader.imaging.BeadedImage;
import com.mrpnut08.imagebeader.listener.PegboardLoadWorkerListener;

public class PegboardLoadWorker extends SwingWorker<ImageIcon, Void>{

	private BeadedImage image;
	private int x,
				y;
	private PegboardLoadWorkerListener listener;
	
	public PegboardLoadWorker(PegboardLoadWorkerListener listener, BeadedImage img, int x, int y) {
		this.image = img;
		this.x = x;
		this.y = y;
		this.listener = listener;
	}
	
	@Override
	protected ImageIcon doInBackground() throws Exception {
		
		ImageIcon icon = this.image.getPegboard(this.x, this.y);
		
		return icon;
	}
	
	@Override
	protected void done() {
		try {
			listener.onPegboardLoad(this.get(), new Point(x,y));
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
