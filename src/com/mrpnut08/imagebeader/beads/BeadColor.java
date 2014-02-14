package com.mrpnut08.imagebeader.beads;

import java.awt.Color;

public class BeadColor {

	public static final BeadColor BLANK = new BeadColor("", "Blank", 0, 0, 0, 0);

	private String name, id;
	private Color color;

	public BeadColor(String id, String name, int red, int green, int blue,
			int alpha) {
		this.id = id;
		this.name = name;

		this.color = new Color(red, green, blue, alpha);
	}

	public Color getColor() {
		return this.color;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Color getContrastingColor() {
			float average = this.color.getRed() + this.getColor().getGreen()
					+ this.color.getBlue();
			average /= 3;
			return (average < 128) ? Color.WHITE : Color.BLACK;
	}
}
