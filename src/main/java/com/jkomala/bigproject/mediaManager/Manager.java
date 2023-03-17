package com.jkomala.bigproject.mediaManager;

import com.jkomala.bigproject.mediaObj.EBook;
import com.jkomala.bigproject.mediaObj.Media;
import com.jkomala.bigproject.mediaObj.MovieDVD;
import com.jkomala.bigproject.mediaObj.MusicCD;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * stores a list of Media objects
 *  has functionality to load Media objects from files -- done
 *  creates/updates Media files -- WIP
 *  has functionality to add new Media object to its Media list -- done
 *  has functionality to find all media objects for a specific title and returns that list -- WIP
 *  has functionality to rent Media based on id (updates rental status on media, updates -- WIP
 * file, returns rental fee)
 */
public class Manager {
    ArrayList<Media> mediaLibrary = new ArrayList<>();

    public Manager(String directory) throws IOException, ParserConfigurationException, SAXException {
        // handle file I/O: load all files in directory into fileList[].
        File directoryPath = new File(directory);
        File[] fileList = directoryPath.listFiles(); // return null specified directory doesn't exist.

        if (fileList == null) {
            throw new FileNotFoundException("Could not load directory"); // throw an error if unable to find directory.
        }

        // prepare XML parser.
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        for (File mediaFile: fileList) { // iterate over each File in fileList
            Document parsedMediaObject = documentBuilder.parse(mediaFile);
            // rootElement specifies the type of our media object.
            Element rootElement = parsedMediaObject.getDocumentElement();
            String mediaObjectType = rootElement.getTagName();

            // create an instance of appropriate media type
            switch (mediaObjectType) {
                case "EBook" -> mediaLibrary.add(new EBook(parsedMediaObject));
                case "MovieDVD" -> mediaLibrary.add(new MovieDVD(parsedMediaObject));
                case "MusicCD" -> mediaLibrary.add(new MusicCD(parsedMediaObject));
                default -> throw new FileSystemException("Directory contains unrecognized object or invalid XML file.");
            }
            // sort mediaLibrary by id
            mediaLibrary.sort((o1, o2) -> o1.getId() - o2.getId());
        }



    }

    /**
     * Use this method while manually adding media objects to mediaLibrary to ensure a unique id.
     * @return the id of the last media object in mediaLibrary
     */
    public int getLastId() {
        return mediaLibrary.get(mediaLibrary.size() - 1).getId();
    }

    // a void method that adds a new media object to the mediaLibrary
    public void addMedia(Media media) {
        // if the id of the media object is already in use, throw an error.
        for (Media mediaObject: mediaLibrary) {
            if (mediaObject.getId() == media.getId()) {
                throw new IllegalArgumentException("Id already in use. Use Manager.");
            }
        }
        mediaLibrary.add(media);
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        Manager manager = new Manager("C:/media_objects");
        // print every media object in mediaLibrary
        for (Media media: manager.mediaLibrary) {
            System.out.println(media);
        }
    }


}
