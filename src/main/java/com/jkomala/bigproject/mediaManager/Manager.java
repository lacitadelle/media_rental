package com.jkomala.bigproject.mediaManager;

import com.jkomala.bigproject.mediaObj.EBook;
import com.jkomala.bigproject.mediaObj.Media;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * stores a list of Media objects
 *  has functionality to load Media objects from files
 *  creates/updates Media files
 *  has functionality to add new Media object to its Media list
 *  has functionality to find all media objects for a specific title and returns that list
 *  has functionality to rent Media based on id (updates rental status on media, updates
 * file, returns rental fee)
 */
public class Manager {
    ArrayList<Media> mediaLibrary = new ArrayList<>();

    public Manager(String directory) throws IOException, ParserConfigurationException, SAXException {
        File directoryPath = new File(directory);

        File fileList[] = directoryPath.listFiles(); // returns null specified directory doesn't exist.
        if (fileList == null) {
            throw new FileNotFoundException("Could not load directory"); // throw an error if unable to find directory.
        }

        Media media;
        String line;
        Scanner scanner;

        // prepares XML parser.
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        for (File mediaFile: fileList) {
            String fileName = mediaFile.getName();

            if (fileName.contains("EBook")) {
                Document ebookData = documentBuilder.parse(mediaFile); // throws SAXException
                mediaLibrary.add(new EBook(ebookData));
            }

        }


    }


}
