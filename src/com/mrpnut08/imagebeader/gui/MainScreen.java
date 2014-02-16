package com.mrpnut08.imagebeader.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.PanelUI;

import com.mrpnut08.imagebeader.beads.BeadPallete;
import com.mrpnut08.imagebeader.imaging.BeadedImage;
import com.mrpnut08.imagebeader.imaging.UnbeadedImage;

public class MainScreen extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTabbedPane image_tab_holder;
	private JLabel unbeaded_image_holder;
	private UnbeadedImage unbeaded_image;

	private BeadPallete pallete;

	private JLabel beaded_image_holder;
	private BeadedImage beaded_image;

	private JLabel source_imgpath;

	private ImageLoadingPanel image_loader;
	private BeadingResultPane beadingresult;

	public MainScreen() {
		super("Image Beader");
		this.setSize(640, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);

		try {
			this.pallete = new BeadPallete(
					"/home/algis-kun/code/perlercolorsorter/perler_pallete.xml");
		} catch (Exception io_error) {
			JOptionPane.showMessageDialog(this, io_error.getMessage());
		} // ~try

		// Generate the GUI interface content.
		this.generateGUIContent();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if( this.unbeaded_image.getFilePath().isEmpty()) {
			JOptionPane.showMessageDialog(this, "No Image has been loaded","Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (this.image_tab_holder.getTabCount() < 2) {
			this.generateBeadedImageTab();
		}

		this.beaded_image.generateBeadImage(this.unbeaded_image, this.pallete);
		this.beaded_image_holder.setIcon(this.beaded_image.getImageIcon());
	}

	private void generateGUIContent() {
		JSplitPane root_content_pane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, this.generateOptionPanel(),
				this.generatePreviewPanel());
		this.add(root_content_pane);
	}

	private JPanel generateOptionPanel() {
		// Create main panel.
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setMinimumSize(new Dimension(200, 480));

		// Create ImageLoadingPanel.
		this.image_loader = new ImageLoadingPanel();
		panel.add(this.image_loader);

		JButton beadingbutton = new JButton("Generate Bead Pattern");
		beadingbutton.setAlignmentX(CENTER_ALIGNMENT);
		beadingbutton.setActionCommand("BeadImage");
		beadingbutton.addActionListener(this);

		panel.add(beadingbutton);
		return (panel);
	}

	private JTabbedPane generatePreviewPanel() {
		this.image_tab_holder = new JTabbedPane(JTabbedPane.TOP);

		this.unbeaded_image = new UnbeadedImage();
		this.unbeaded_image_holder = new JLabel();
		this.unbeaded_image_holder.setMinimumSize(new Dimension(440, 480));
		this.unbeaded_image_holder
				.setHorizontalAlignment(SwingConstants.CENTER);
		this.unbeaded_image_holder.setVerticalAlignment(SwingConstants.CENTER);

		JScrollPane scrollpane = new JScrollPane(this.unbeaded_image_holder);

		this.image_tab_holder.addTab("Source Image", scrollpane);
		return (this.image_tab_holder);

	}

	private void generateBeadedImageTab() {
		this.beaded_image = new BeadedImage();
		this.beaded_image_holder = new JLabel();
		this.beaded_image_holder.setMinimumSize(new Dimension(440, 480));
		this.beaded_image_holder.setHorizontalAlignment(SwingConstants.CENTER);
		this.beaded_image_holder.setVerticalAlignment(SwingConstants.CENTER);

		JScrollPane scrollpane = new JScrollPane(this.beaded_image_holder);

		this.image_tab_holder.addTab("Bead Pattern", scrollpane);
	}
}
