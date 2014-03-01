package com.mrpnut08.imagebeader.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.mrpnut08.imagebeader.beads.BeadPallete;
import com.mrpnut08.imagebeader.imaging.BeadedImage;
import com.mrpnut08.imagebeader.listener.OnImageLoadListener;

public class MainScreen extends JFrame implements ActionListener,
		OnImageLoadListener {

	private static final long serialVersionUID = 1L;

	private BeadPallete pallete;
	
	private JButton beading_button;

	private ImageLoadingPanel image_loader;
	private BeadPatternPanel result_panel;

	public MainScreen() {
		super("Image Beader");
		this.setMinimumSize(new Dimension(640, 480));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);

		try {
			this.pallete = new BeadPallete(
					new File(this.getClass().getClassLoader().getResource("perler_pallete.xml").toURI()));
		} catch (Exception io_error) {
			JOptionPane.showMessageDialog(this, io_error.getMessage());
		} // ~try

		// Generate the GUI interface content.
		this.generateGUIContent();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.image_loader.getFilePath().isEmpty()) {
			JOptionPane.showMessageDialog(this, "No Image has been loaded",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		this.result_panel.generatePattern(this.image_loader.getFilePath(), this.pallete, BeadedImage.TEXT_LARGE);		
	}

	private void generateGUIContent() {
		this.result_panel = new BeadPatternPanel(this);
		
		JSplitPane root_content_pane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, this.generateOptionPanel(),
				this.result_panel);
		this.add(root_content_pane);
	}

	private JPanel generateOptionPanel() {
		// Create main panel.
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setMinimumSize(new Dimension(200, 480));

		// Create ImageLoadingPanel.
		this.image_loader = new ImageLoadingPanel(this);
		panel.add(this.image_loader);

		this.beading_button = new JButton("Generate Bead Pattern");
		this.beading_button.setAlignmentX(CENTER_ALIGNMENT);
		this.beading_button.setActionCommand("BeadImage");
		this.beading_button.addActionListener(this);
		this.beading_button.setEnabled(false);

		panel.add(this.beading_button);
		return (panel);
	}

	@Override
	public void onImageLoad(String filepath) {
			this.beading_button.setEnabled(true);
	}
}
