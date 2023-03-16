package com.jkomala.bigproject.mediaManager;

import com.jkomala.bigproject.mediaObj.EBook;
import com.jkomala.bigproject.mediaObj.Media;
import com.jkomala.bigproject.mediaObj.MovieDVD;
import com.jkomala.bigproject.mediaObj.MusicCD;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
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
        File directoryPath = new File(directory);

        File fileList[] = directoryPath.listFiles(); // return null specified directory doesn't exist.
        if (fileList == null) {
            throw new FileNotFoundException("Could not load directory"); // throw an error if unable to find directory.
        }

        Media media;
        String line;
        Scanner scanner;

        // prepare XML parser.
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        for (File mediaFile: fileList) { // iterate over each File in fileList
            String fileName = mediaFile.getName();

            if (fileName.contains("EBook")) {
                Document ebookData = documentBuilder.parse(mediaFile); // throw SAXException
                mediaLibrary.add(new EBook(ebookData));
            } else if (fileName.contains("MovieDVD")) {
                Document dvdData = documentBuilder.parse(mediaFile); // throw SAXException
                mediaLibrary.add(new MovieDVD(dvdData));
            } else if (fileName.contains("Music")) {
                Document musicCdData = documentBuilder.parse(mediaFile);
                mediaLibrary.add(new MusicCD(musicCdData));
            }
            else {
                System.out.printf("%s is unrecognized by this program.\n", fileName);
            }

        }
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        Manager manager = new Manager("C:/media_objects");
        System.out.println(manager.mediaLibrary.get(0));
        System.out.println(manager.mediaLibrary.get(11));
        System.out.println(manager.mediaLibrary.get(22));
    }


}
