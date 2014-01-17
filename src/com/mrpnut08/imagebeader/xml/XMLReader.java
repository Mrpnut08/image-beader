package com.mrpnut08.imagebeader.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

public class XMLReader {
	
	public static void parseFile(String filename, DefaultHandler handle) throws Exception {
		XMLReader.parseFile(new File(filename), handle);
	}
	
	public static void parseFile(File xml_file, DefaultHandler handle) throws Exception {
		
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			
			InputStream inputStream= new FileInputStream(xml_file);
  	      	Reader reader = new InputStreamReader(inputStream,"UTF-8");

  	      	InputSource source = new InputSource(reader);
  	      	source.setEncoding("UTF-8");
  	      	
  	      	parser.parse(source, handle);
	}
}
