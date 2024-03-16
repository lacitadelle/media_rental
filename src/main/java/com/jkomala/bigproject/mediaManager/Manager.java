package com.jkomala.bigproject.mediaManager;

import com.jkomala.bigproject.mediaObj.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;

public class Manager {
    private ArrayList<Media> mediaLibrary = new ArrayList<>();
    private File directoryPath;

    public Manager(String directory) throws IOException, ParserConfigurationException, SAXException {
        // handle file I/O: load all files in directory into fileList[].
        directoryPath = new File(directory);
        File[] fileList = directoryPath.listFiles();

        if (fileList == null) {
            throw new FileNotFoundException("Could not load directory"); // throw an error if unable to find directory.
        }

        // prepare XML parser.
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        for (File mediaFile: fileList) { // iterate over each File in fileList
            Document parsedMediaObject = null;
            try {
                parsedMediaObject = documentBuilder.parse(mediaFile);
            } catch (SAXException e) {
                System.out.println("Possible corrupted XML file.");
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
        }
        // sort mediaLibrary by id
        mediaLibrary.sort((o1, o2) -> o1.getId() - o2.getId());
        System.out.println("Successfully loaded " + mediaLibrary.size() + " media objects.");

    }

    /**
     * Use this method while manually adding a media object to ensure a unique id.
     * @return a new unique id.
     */
    public int getNewId() {
        return mediaLibrary.get(mediaLibrary.size() - 1).getId() + 1;
    }

    /**
     * Add new media object to mediaLibrary, id must be unique.
     * @param media
     * @throws IllegalArgumentException
     */
    public void addMedia(Media media) throws IllegalArgumentException, IOException {
        // if the id of the media object is already in use, throw an error.
        for (Media mediaObject: mediaLibrary) {
            if (mediaObject.getId() == media.getId()) {
                throw new IllegalArgumentException(String.format("Id: %s already in use. Use Manager.getNewId() to get a unique sequential ID.", media.getId()));
            }
        }
        mediaLibrary.add(media);

        // write string with media.toXMLString() to a file named "Media" + media.getId() + ".xml"
        String xmlContent = media.toXMLString();
        // create a new file
        File newMediaFile = new File(directoryPath + "/Media" + media.getId() + ".xml");
        // write xmlContent to newMediaFile
        FileWriter writer = new FileWriter(newMediaFile);
        writer.write(xmlContent);
        writer.close();
    }

    /**
     * Search media library by title.
     * @param title
     * @return an ArrayList of all media objects with matching title. An empty ArrayList if there is no match.
     */
    public ArrayList<Media> findMediaByTitle(String title) {
        title = title.toLowerCase(); // make title lowercase to make search more forgiving
        ArrayList<Media> matched = new ArrayList<>();
        for (Media mediaItem : mediaLibrary) {
            String mediaItemTitle = mediaItem.getTitle().toLowerCase();
            if (mediaItemTitle.contains(title)) {
                matched.add(mediaItem);
            }
        }
        return matched;
    }

    /**
     * Rent media object with specified id.
     * @param id
     * @return
     * @throws IOException
     */
    public int rentMedia(int id) throws IOException {
        // search mediaLibrary for media object with id
        for (Media mediaItem : mediaLibrary) {
            if (mediaItem.getId() == id) {
                // if media object is available, rent it and print the rental fee
                if (mediaItem.getAvailability()) {
                    mediaItem.setAvailability(false);
                    // update media file
                    File mediaFile = new File(directoryPath + "/Media" + mediaItem.getId() + ".xml");
                    FileWriter writer = new FileWriter(mediaFile);
                    writer.write(mediaItem.toXMLString());
                    writer.close();
                    // print rental fee
                    System.out.println("Media successfully rented: Rental fee $" + mediaItem.calculateRentalFee());
                    //return a positive number to indicate success
                    return 1;
                }
                // if media object is not available, return -1
                else {
                    return -1;
                }
            }
        }

        return 0; // return 0 if object with specified id is not found.
    }
    
}
