package com.mrpnut08.imagebeader.xml;

import java.io.File;

import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler {

	public XMLHandler(String filename) throws Exception {
		XMLReader.parseFile(filename, this);
	}
	
	public XMLHandler(File file) throws Exception {
		XMLReader.parseFile(file, this);
	}
}
