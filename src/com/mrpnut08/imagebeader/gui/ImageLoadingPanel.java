package com.mrpnut08.imagebeader.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mrpnut08.imagebeader.imaging.UnbeadedImage;
import com.mrpnut08.imagebeader.listener.OnImageLoadListener;

/**
 * Creates the panel the user loads the image.
 * 
 * @author Alfredo Giscombe (Mrpnut08)
 * 
 */
public class ImageLoadingPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JLabel image;
	private OnImageLoadListener listener;
	private UnbeadedImage source;

	/**
	 * Creates the Panel and its content.
	 */
	public ImageLoadingPanel(OnImageLoadListener listener) {
		// Instantiate the JPanel super-class.
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Set OnImageLoadListener
		this.listener = listener;

		// Create the file path label
		this.image = new JLabel("No File Selected");
		this.image.setAlignmentX(CENTER_ALIGNMENT);
		this.image.setVerticalTextPosition(JLabel.BOTTOM);
		this.image.setHorizontalTextPosition(JLabel.CENTER);
		this.add(this.image);

		// Create the open file button.
		JButton open_file_button = new JButton("Open File");
		open_file_button.setAlignmentX(CENTER_ALIGNMENT);
		open_file_button.setActionCommand("FileOpen");
		open_file_button.addActionListener(this);
		this.add(open_file_button);
		
		//
		this.source = new UnbeadedImage();
	}

	/**
	 * Action for open_file_button. Displays the file chooser for the user to
	 * select a file and notifies that a new image has been loaded.
	 * 
	 * @param action not used.
	 */
	@Override
	public void actionPerformed(ActionEvent action) {
		// Create file chooser window.
		JFileChooser filebrowser = new JFileChooser();

		// Set file filter so that the file chooser only sees compatible files.
		filebrowser.setFileFilter(new FileNameExtensionFilter("Image Files",
				ImageIO.getReaderFormatNames()));

		// Show the file chooser to the user and store the result.
		int returnVal = filebrowser.showOpenDialog(this);

		// If the user chose a file. Display the file path and call the
		// listener.
		try {
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.source.loadImage(filebrowser.getSelectedFile());
			this.image.setText(filebrowser.getSelectedFile().getName());
			this.loadPreviewImage();
			this.listener.onImageLoad(this.source.getFilePath());
		}
		} catch (IOException io_error) {
			JOptionPane.showMessageDialog(this, io_error.getMessage());
		}
	}
	
	public String getFilePath() {
		return this.source.getFilePath();
	}
	
	public Dimension getImageDimensions() {
		return new Dimension(source.getWidth(), source.getHeight());
	}

	private void loadPreviewImage() throws IOException {
		PreviewImageIcon image = new PreviewImageIcon();
		image.setImage(this.source.getImage());
		this.image.setIcon(image);
	}
}
