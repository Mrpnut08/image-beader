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

package com.mrpnut08.imagebeader.imaging;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.mrpnut08.imagebeader.beads.BeadColor;
import com.mrpnut08.imagebeader.beads.BeadPallete;

/**
 * Creates and handles the files and parts of the image
 * corresponding to the bead pattern.
 * 
 * @author Alfredo Giscombe (Mrpnut08)
 */
public class BeadedImage {
	
	
	public final static float TEXT_SMALL = 2.5f;
	public final static float TEXT_LARGE = 2;
	public final static float TEXT_NONE = 0;
	
	private final int SQUARE_SIZE = 24;
	
	private final String TEMP_PREFFIX = "ib-";
	private final String TEMP_SUFFIX = "png";
	
	private File thumbnail,
				 fullpattern;
	
	private ArrayList<File> board_pattern,
							pegboard_thumbnail;
	
	private Dimension pattern_size;
	private HashSet<String> colors_used;
	
	private float text_size;
	private BufferedImage image;
	private BeadPallete pallete;
	
	/** Instantiate the file variables. and the colors_used set.
	 */
	public BeadedImage() {
		
		// Set thumbnail and full image pattern to null, 
		// so that they can be initialized when needed
		this.thumbnail = null;
		this.fullpattern = null;
		
		//Initialize the pegboards Arraylist.
		this.board_pattern = new ArrayList<File>();
		this.pegboard_thumbnail = new ArrayList<File>();
		
		// Initialize the color_used HashSet.
		this.colors_used = new HashSet<String>();
	}
	
	public void generatePattern(BufferedImage source, BeadPallete pallete, float text_size) throws IOException {
		this.image = source;
		this.pallete = pallete;
		this.text_size = text_size;
		
		//Calculate the pegboard size based in the image size.
		this.pattern_size = new Dimension();
		this.pattern_size.setSize(Math.ceil(source.getWidth()/29d),
								  Math.ceil(source.getHeight()/29d));
		
		this.createThumbnail(source, pallete);
		this.createThumbnailPegboards(source, pallete, text_size);
	}
	
	public void generateBeadSet(BufferedImage source, BeadPallete pallete, float text_size) throws IOException {
		this.colors_used.clear();
		
		this.pattern_size = new Dimension();
		this.pattern_size.setSize(Math.ceil(source.getWidth()/29d),
								  Math.ceil(source.getHeight()/29d));
		
		this.createThumbnail(source, pallete);
		//this.createFullPattern(source, pallete, text_size);
		this.createPegboards(source, pallete, text_size);
	}
	
	/** (Getter)
	 * Gets the full pattern image as an ImageIcon.
	 * 
	 * @return ImageIcon of the full pattern.
	 */
	public ImageIcon getFullPattern() {
		return (new ImageIcon(this.fullpattern.getPath()));
	}
	
	public File getPatternThumbnail() {
		return this.thumbnail;
	}
	
	public File getPegboardThumbnail(int x, int y) {
		return this.pegboard_thumbnail.get(this.getCoordinate(x, y));
	}
	
	/** (Getter)
	 * Gets the list of bead colors used for this pattern.
	 * 
	 * @return Array of the code and name of the colors used in this pattern.
	 */
	public String[] getUsedColorList() {
		String list = "";
		Iterator<String> iterator = this.colors_used.iterator();
		while (iterator.hasNext()){
			list += iterator.next() + "\n";
		}
		return list.split("\n");
	}
	
	public ImageIcon getPegboard (int x, int y) throws IOException{
		if (this.board_pattern.get(this.getCoordinate(x, y)) == null){
			
			this.board_pattern.set(this.getCoordinate(x, y), (File.createTempFile(this.TEMP_PREFFIX+"pegboard-"+this.getCoordinate(x, y)+"-",
					   this.TEMP_SUFFIX)));
			this.board_pattern.get(this.getCoordinate(x, y)).deleteOnExit();
			
			Rectangle rectangle = new Rectangle(x*29,y*29,
					 (x*29 +28>= this.image.getWidth())? this.image.getWidth()-x*29 : 29,
					 (y*29 +28>= this.image.getHeight())? this.image.getHeight()-y*29 : 29);
			
			ImageIO.write(this.generateImage(this.image, pallete, new Dimension(29,29), rectangle, false, text_size),
				      this.TEMP_SUFFIX, 
				      this.board_pattern.get(this.getCoordinate(x, y)));
		}
		
		return new ImageIcon(this.board_pattern.get(this.getCoordinate(x, y)).getPath());
	}
	
	public Dimension getSize() {
		return this.pattern_size;
	}
	
	private BufferedImage generateImage (BufferedImage source, BeadPallete pallete, Dimension pegboard, Rectangle dimensions, boolean thumbnail, float text_size) {
		Graphics2D canvas;
		BeadColor bead_color;
		
		int square_size = (thumbnail)? 3 : this.SQUARE_SIZE;
		
		int tx, ty;
		BufferedImage image = new BufferedImage( (int)pegboard.width*square_size,
										(int)pegboard.height*square_size,
										BufferedImage.TYPE_INT_ARGB);
		
		canvas = image.createGraphics();
		
		if(!thumbnail && text_size != BeadedImage.TEXT_NONE) {
			Font font = new Font(Font.MONOSPACED,
								 Font.PLAIN,
								 Math.round(this.SQUARE_SIZE / text_size));
			canvas.setFont(font);
		}
		
		
		for (int y = 0; y < pegboard.height; y++) {
			for(int x = 0; x < pegboard.width; x++) {
				tx = x * square_size;
				ty = y * square_size;
				
				if(x < dimensions.getWidth() && y < dimensions.getHeight()){
					bead_color = pallete.findColorEquivalent(new Color(source.getRGB(dimensions.x + x,dimensions.y + y),true));
				}else {
					bead_color = BeadColor.BLANK;
				}
				
				canvas.setColor(bead_color.getColor());
				canvas.fillRect(tx, ty, square_size, square_size);
				if (!thumbnail) {
					canvas.setColor(Color.LIGHT_GRAY);
					canvas.drawRect(tx, ty, square_size, square_size);
					canvas.setColor(bead_color.getContrastingColor());
					if (text_size != BeadedImage.TEXT_NONE){
						canvas.drawString(bead_color.getId(), tx, (y+1)* square_size);
					}
				}
				image.flush();
				
				
				if (thumbnail && !bead_color.getId().isEmpty()) {
				this.colors_used.add(bead_color.getId() +" - "+ bead_color.getName());
				}
			}
		}
		return image;
	}
	
	/** Returns a new usable temporal file. Deletes current one if it exists.
	 * 
	 * @param temp existing File object to check for.
	 * @param filename name for the new temp file.
	 * 
	 * @return a new blank, ready-to-use temporal file.
	 * 
	 * @throws IOException There was a problem deleting or creating the temporal file.
	 */
	private File recreateTempFile(File temp, String filename) throws IOException{
		
		// Delete file it exists.
		if (temp != null && temp.exists()) {
			temp.delete();
		}
		
		// Create temporal file that deletes itself when the program exits.
		temp = File.createTempFile(this.TEMP_PREFFIX + filename, this.TEMP_SUFFIX);
		temp.deleteOnExit();
		
		// Return new file.
		return temp;
	}

	
	
	private void createThumbnail(BufferedImage source, BeadPallete pallete) throws IOException {
		
		this.thumbnail = this.recreateTempFile(this.thumbnail, "thumbnail");
		
		ImageIO.write(
				this.generateImage(source, 
								   pallete,
								   new Dimension(source.getWidth(), source.getHeight()),
								   new Rectangle(0,0,source.getWidth(),source.getHeight()),
								   true,
								   1),
				this.TEMP_SUFFIX,
				this.thumbnail);
	}
	
	private void createThumbnailPegboards(BufferedImage source, BeadPallete pallete, float text_size) throws IOException {
		
		// Delete already existing temp from the pegboards ArrayList.
		for(File board: this.board_pattern){
			if (board != null) {
			board.delete();
			}
		}
		
		for(File thumbnail: this.pegboard_thumbnail){
			thumbnail.delete();
		}
		
		// Clear the ArrayLists. 
		this.board_pattern.clear();
		this.pegboard_thumbnail.clear();
		
		// Declare Rectangle used for beading.
		Rectangle rectangle;
		
		for(int y = 0; y < this.pattern_size.getHeight(); y ++) {
			for(int x = 0; x < this.pattern_size.getWidth(); x ++) {
				
				// Create temporal file to store the pattern
				this.board_pattern.add(null);
				
				this.pegboard_thumbnail.add(File.createTempFile(this.TEMP_PREFFIX+"pegboard_thumbnail-"+this.getCoordinate(x, y)+"-",
						   this.TEMP_SUFFIX));
				this.pegboard_thumbnail.get(this.getCoordinate(x, y)).deleteOnExit();
				
				
				rectangle = new Rectangle(x*29,y*29,
										 (x*29 +28>= source.getWidth())? source.getWidth()-x*29 : 29,
										 (y*29 +28>= source.getHeight())? source.getHeight()-y*29 : 29);
				
				ImageIO.write(this.generateImage(source, pallete, new Dimension(29,29), rectangle, true, text_size),
					      this.TEMP_SUFFIX, 
					      this.pegboard_thumbnail.get(this.getCoordinate(x, y)));
			}
		}
	}
	
	
	private void createPegboards(BufferedImage source, BeadPallete pallete, float text_size) throws IOException {
		
		// Delete already existing temp from the pegboards ArrayList.
		for(File board: this.board_pattern){
			board.delete();
		}
		
		for(File thumbnail: this.pegboard_thumbnail){
			thumbnail.delete();
		}
		
		// Clear the ArrayLists. 
		this.board_pattern.clear();
		this.pegboard_thumbnail.clear();
		
		// Calculate the Dimensions of the pattern in pegboards.
		this.pattern_size = new Dimension((int)Math.ceil(source.getWidth()/29f),
										  (int)Math.ceil(source.getHeight()/29f));
		
		
		// Declare Rectangle used for beading.
		Rectangle rectangle;
		
		for(int y = 0; y < this.pattern_size.getHeight(); y ++) {
			for(int x = 0; x < this.pattern_size.getWidth(); x ++) {
				
				// Create temporal file to store the pattern
				this.board_pattern.add(File.createTempFile(this.TEMP_PREFFIX+"pegboard-"+this.getCoordinate(x, y)+"-",
														   this.TEMP_SUFFIX));
				this.board_pattern.get(this.getCoordinate(x, y)).deleteOnExit();
				
				this.pegboard_thumbnail.add(File.createTempFile(this.TEMP_PREFFIX+"pegboard_thumbnail-"+this.getCoordinate(x, y)+"-",
						   this.TEMP_SUFFIX));
				this.pegboard_thumbnail.get(this.getCoordinate(x, y)).deleteOnExit();
				
				
				rectangle = new Rectangle(x*29,y*29,
										 (x*29 +28>= source.getWidth())? source.getWidth()-x*29 : 29,
										 (y*29 +28>= source.getHeight())? source.getHeight()-y*29 : 29);
				
				ImageIO.write(this.generateImage(source, pallete, new Dimension(29,29), rectangle, true, text_size),
					      this.TEMP_SUFFIX, 
					      this.pegboard_thumbnail.get(this.getCoordinate(x, y)));
				
				ImageIO.write(this.generateImage(source, pallete, new Dimension(29,29), rectangle, false, text_size),
						      this.TEMP_SUFFIX, 
						      this.board_pattern.get(this.getCoordinate(x, y)));
			}
		}
	}
	
//	private void createFullPattern(BufferedImage source, BeadPallete pallete, int text_size) throws IOException {
//		
//		this.fullpattern = this.recreateTempFile(this.fullpattern, "fullpattern");
//		
//		ImageIO.write(
//				this.generateImage(source, 
//								   pallete,
//								   new Rectangle(0,0,source.getWidth(),source.getHeight()),
//								   false,
//								   text_size),
//				this.TEMP_SUFFIX,
//				this.fullpattern);
//	}
	
	private int getCoordinate(int x, int y) {
		return (y*this.pattern_size.width + x);
	}
}
