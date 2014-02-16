package com.mrpnut08.imagebeader.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mrpnut08.imagebeader.listener.OnImageLoadListener;

/**
 * Creates a simple panel where
 * 
 * @author Alfredo Giscombe (Mrpnut08)
 * 
 */
public class ImageLoadingPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JLabel image_path;
	private OnImageLoadListener listener;

	/**
	 * Creates the Panel and its content.
	 */
	public ImageLoadingPanel(OnImageLoadListener listener) {
		//Instantiate the JPanel super-class.
		super();
		
		//Set OnImageLoadListener
		this.listener = listener;
		
		//Create the file path label
		this.image_path = new JLabel ("No File Selected");
		this.add(this.image_path, BorderLayout.LINE_START);
		
		//Create the open file button.
		JButton open_file_button = new JButton("Open File");
		open_file_button.setActionCommand("FileOpen");
		open_file_button.addActionListener(this);
		this.add(open_file_button,BorderLayout.LINE_END);
	 }

	/**
	 * Action for open_file_button. Displays the file chooser for the user to
	 * select a file and notifies that a new image has been loaded.
	 * 
	 * @param action action when multiple object use the listener (not used)
	 */
	@Override
	public void actionPerformed(ActionEvent action) {
		//Create file chooser window.
		JFileChooser filebrowser = new JFileChooser();
		
		//Set file filter so that the file chooser only sees compatible files.
		filebrowser.setFileFilter(new FileNameExtensionFilter("Image Files",
				ImageIO.getReaderFormatNames()));
		
		//Show the file chooser to the user and store the result.
		int returnVal = filebrowser.showOpenDialog(this);

		//If the user chose a file. Display the file path and call the listener.
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.image_path.setText(filebrowser.getSelectedFile().getPath());
			this.listener.onImageLoad(filebrowser.getSelectedFile().getPath());
		}
	}
}
