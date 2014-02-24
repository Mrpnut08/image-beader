package com.mrpnut08.imagebeader.imaging;

import java.awt.Color;
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
import com.mrpnut08.imagebeader.gui.PreviewImageIcon;

/**
 * Creates and handles the files and parts of the image
 * corresponding to the bead pattern.
 * 
 * @author Alfredo Giscombe (Mrpnut08)
 */
public class BeadedImage {
	
	
	public final static int TEXT_SMALL = 3;
	public final static int TEXT_LARGE = 2;
	
	private final int SQUARE_SIZE = 28;
	
	private final String TEMP_PREFFIX = "ib-";
	private final String TEMP_SUFFIX = "png";
	
	private File thumbnail,
				 fullpattern;
	
	private ArrayList<File> board_pattern;
	
	private HashSet<String> colors_used;
	
	
	/** Instantiate the file variables. and the colors_used set.
	 */
	public BeadedImage() {
		
		// Set thumbnail and full image pattern to null, 
		// so that they can be initialized when needed
		this.thumbnail = null;
		this.fullpattern = null;
		
		//Initialize the pegboards Arraylist.
		this.board_pattern = new ArrayList<File>();
		
		// Initialize the color_used HashSet.
		this.colors_used = new HashSet<String>();
	}
	
	
	public void generateBeadSet(String filepath, BeadPallete pallete, int text_size) throws IOException {
		BufferedImage source = ImageIO.read(new File(filepath));
		
		this.createThumbnail(source, pallete);
		this.createFullPattern(source, pallete, text_size);
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
	
	public PreviewImageIcon getThumbnail() {
		return (new PreviewImageIcon(this.thumbnail.getPath()));
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
	
	private BufferedImage generateImage (BufferedImage source, BeadPallete pallete, Rectangle dimensions, boolean thumbnail, int text_size) {
		Graphics2D canvas;
		BeadColor bead_color;
		
		int square_size = (thumbnail)? 1 : this.SQUARE_SIZE;
		
		int tx, ty;
		BufferedImage image = new BufferedImage( (int)dimensions.getWidth()*square_size,
										(int)dimensions.getHeight()*square_size,
										BufferedImage.TYPE_INT_ARGB);
		
		canvas = image.createGraphics();
		
		if(!thumbnail) {
			Font font = new Font(Font.MONOSPACED,
								 Font.PLAIN,
								 this.SQUARE_SIZE / text_size);
			canvas.setFont(font);
		}
		
		
		for (int y = dimensions.y; y < dimensions.getHeight(); y++) {
			for(int x = dimensions.x; x < dimensions.getWidth(); x++) {
				tx = x * square_size;
				ty = y * square_size;
				
				bead_color = pallete.findColorEquivalent(new Color(source.getRGB(x, y),true));
				canvas.setColor(bead_color.getColor());
				canvas.fillRect(tx, ty, square_size, square_size);
				if (!thumbnail) {
					canvas.setColor(Color.LIGHT_GRAY);
					canvas.drawRect(tx, ty, square_size, square_size);
					canvas.setColor(bead_color.getContrastingColor());
					canvas.drawString(bead_color.getId(), tx, (y+1)* square_size);
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
								   new Rectangle(0,0,source.getWidth(),source.getHeight()),
								   true,
								   1),
				this.TEMP_SUFFIX,
				this.thumbnail);
	}
	
	private void createPegboards(BufferedImage source, BeadPallete pallete, int text_size) throws IOException {
		
		// Delete already existing temp from the pegcoards ArrayList.
		for(File board: this.board_pattern){
			board.delete();
		}
		
		// Clear the ArrayList. 
		this.board_pattern.clear();
		
		
	}
	
	private void createFullPattern(BufferedImage source, BeadPallete pallete, int text_size) throws IOException {
		
		this.fullpattern = this.recreateTempFile(this.fullpattern, "fullpattern");
		
		ImageIO.write(
				this.generateImage(source, 
								   pallete,
								   new Rectangle(0,0,source.getWidth(),source.getHeight()),
								   false,
								   text_size),
				this.TEMP_SUFFIX,
				this.fullpattern);
	}
}
