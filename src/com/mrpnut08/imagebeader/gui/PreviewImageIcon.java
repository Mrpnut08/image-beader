package com.mrpnut08.imagebeader.gui;

import java.awt.Image;

import javax.swing.ImageIcon;

/** ImageIcon subclass that automatically scales its image to 200 * 200 pixels.
 *  The scaled image maintains it's aspect ratio.
 * 
 * @author Alfredo Giscombe (Mrpnut08)
 */
public class PreviewImageIcon extends ImageIcon {

	private static final long serialVersionUID = 1L;
	
	/** (Setter)
	 * Sets the image scaled down to fit a 200 by 200 pixel square.
	 * the scaled image maintains it's aspect ratio. 
	 */
	@Override
	public void setImage(Image image) {
		
		// Get the scaling proportion by dividing the 200 by the image's biggest dimension.
		double scale = 200d / (double) Math.max(image.getHeight(null), image.getWidth(null));
		
		//Set the scaled down version of the image as the Icon.
		super.setImage(image.getScaledInstance(
				(int)Math.round(image.getWidth(null) * scale), 
				(int)Math.round(image.getHeight(null) * scale),
				Image.SCALE_FAST)	//scaling set to fast since no image processing will be done with this object. 
				);
	}
}
