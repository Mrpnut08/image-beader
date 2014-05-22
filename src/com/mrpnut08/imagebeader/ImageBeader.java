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

package com.mrpnut08.imagebeader;

import javax.swing.UIManager;

import com.mrpnut08.imagebeader.gui.MainScreen;

/** Program entry point. This class holds the main method.
  *  
  * @author Alfredo Giscombe (mrpnut08)
  */
public class ImageBeader {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
			
			new MainScreen().setVisible(true);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
