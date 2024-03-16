package com.jkomala.bigproject.cmdInterface;

import com.jkomala.bigproject.mediaManager.Manager;
import com.jkomala.bigproject.mediaObj.Media;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MediaRentalSystem {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        Manager manager = null; // import media files from this directory
        Scanner scanner = new Scanner(System.in);
        // greeting, but only once
        System.out.println("Welcome to Media Rental System");
        while (true) {
            // print menu
            System.out.println("---------------------");
            System.out.println("1: Load media objects...");
            System.out.println("2: Search media object...");
            System.out.println("3: Rent media object...");
            System.out.println("9: Quit");
            System.out.print("Enter your selection: ");

            int selection = -1;

            // validate user selection
            while (selection < 0) {
                try {
                    selection = scanner.nextInt(); // read user input
                    if (selection < 0) {
                        System.out.println("Please choose a non-negative integer.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please choose 1, 2, 3 or 9.");
                    scanner.next();
                }
            }

            switch (selection) {
                case 1:
                    manager = loadMediaObject();
                    break;
                case 2:
                    handleMediaSearch(manager);
                    break;
                case 3:
                    handleMediaRental(manager);
                    break;
                case 9:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");

            }

        }


    }

    private static Manager loadMediaObject() throws IOException, ParserConfigurationException, SAXException {
        Scanner scanner = new Scanner(System.in);
        // prompt
        System.out.print("Enter directory path: ");
        String directoryPath = scanner.next();

        // attempt to load the files
        try {
            return new Manager(directoryPath);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage()); // can't find directory
        } catch (FileSystemException ex) {
            System.out.println(ex.getMessage());
            System.exit(1); // possible corruption in library object(s), can't proceed.
        }

        return null;
    }

    private static void handleMediaSearch(Manager manager) {
        // check if a Manager was successfully instantiated.
        if (manager == null) {
            System.out.println("Media object not loaded.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        // prompt
        System.out.print("Enter media title: ");
        String mediaTitle = scanner.nextLine();

        // perform search
        ArrayList<Media> searchHits = manager.findMediaByTitle(mediaTitle);
        String resultStatus = searchHits.isEmpty() ?
                "No media with such title" : ("Found " + searchHits.size() + " object(s)"); // indicate result

        // print result(s)
        System.out.println(resultStatus);
        int count = 0;
        for (Media media : searchHits) {
            System.out.printf("(%d) ", ++count);
            System.out.println(media);
        }

    }

    private static void handleMediaRental(Manager manager) throws IOException {
        // check if a Manager was successfully instantiated.
        if (manager == null) {
            System.out.println("Media object not loaded.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int id = 0;
        boolean validIdEntered = false;

        while (!validIdEntered) {
            System.out.print("Enter media id: ");
            try {
                id = scanner.nextInt();
                validIdEntered = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid media id.");
                scanner.next(); // clear the invalid input from the scanner buffer
            }
        }

        // attempt to rent -- rentMedia(id) returns 1 if successful. The fee is already printed by the Manager.
        switch (manager.rentMedia(id)) {
            case -1 -> System.out.println("The media you requested is currently not available.");
            case 0 -> System.out.println("Unable to find media with id " + id + ".");
        }


    }
}
