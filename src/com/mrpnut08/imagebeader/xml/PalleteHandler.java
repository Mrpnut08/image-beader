package com.mrpnut08.imagebeader.xml;

import java.io.File;
import java.util.HashSet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.mrpnut08.imagebeader.beads.BeadColor;

public class PalleteHandler extends XMLHandler{
	
	private int red,
				green,
				blue,
				alpha;
	private String pallete_name,
				   name,
				   code,
				   open_tag;
	
	private HashSet<BeadColor> pallete;
	private BeadColor color;
	
	public PalleteHandler(String filename) throws Exception {
		super(filename);
	}
	
	public PalleteHandler(File file) throws Exception {
		super(file);
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		switch (qName){
			case "pallete":
				this.pallete_name = attributes.getValue("type");
				this.pallete = new HashSet<BeadColor>();
			break;
			
			case "color":
				red = 0;
				green = 0;
				blue = 0;
				alpha = 0;
			break;
		}
		open_tag= qName;
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		String value = new String(ch, start, length);
		value = value.trim();
		
		switch (this.open_tag) {
		
			case "name":
				this.name = value;
			break;
			
			case "code":
				this.code = value;
			break;
			
			case "red":
				this.red = Integer.parseInt(value);
			break;
			
			case "green":
				this.green = Integer.parseInt(value);
			break;
			
			case "blue":
				this.blue = Integer.parseInt(value);
			break;
			
			case "alpha":
				this.alpha = Integer.parseInt(value);
			break;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		if(!open_tag.equals(qName)){
			throw new SAXParseException("XML malformed, missmatched '"+ open_tag +"' tag in XML file." , null);
		}
		
		switch (qName) {
			case "color":
				this.color = new BeadColor(this.code, 
											this.name, 
											this.red, 
											this.green, 
											this.blue, 
											this.alpha);
				this.pallete.add(this.color);
				open_tag="pallete";
			break;
			
			case "pallete":
				open_tag = null;
			break;
			
			case "red":
			case "green":
			case "blue":
			case "alpha":
				open_tag = "values";
			break;
			
			default:
				open_tag="color";
		}
	}
	
	public String getPalleteName() {
		return this.pallete_name;
	}

	public HashSet<BeadColor> getBeadColorSet() {
		return this.pallete;
	}
}
