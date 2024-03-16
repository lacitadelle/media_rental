package com.jkomala.bigproject.mediaObj;

import com.jkomala.bigproject.mediaManager.Manager;
import org.w3c.dom.Document;

abstract public class Media {
    private int id;
    private int yearPublished;
    private String title;
    private boolean availability;

    // constructor
    public Media(int id, int yearPublished, String title, boolean availability) {
        this.id = id;
        this.yearPublished = yearPublished;
        this.title = title;
        this.availability = availability;
    }

    // constructor for reading from files
    public Media(Document mediaFile) {
        id = Integer.parseInt(mediaFile.getElementsByTagName("id").item(0).getTextContent());
        yearPublished = Integer.parseInt(mediaFile.getElementsByTagName("yearPublished").item(0).getTextContent());
        title = mediaFile.getElementsByTagName("title").item(0).getTextContent();
        availability = Boolean.parseBoolean(mediaFile.getElementsByTagName("availability").item(0).getTextContent());
    }


    //getters and setters
    public int getId() {
        return id;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    // instance methods
    public double calculateRentalFee() {
        return 3.5;
    }

    public String toXMLString() {
        return String.format(
                ("\t<id>%s</id>\n" +
                "\t<title>%s</title>\n" +
                "\t<availability>%s</availability>\n" +
                "\t<yearPublished>%s</yearPublished>\n"),
                id, title, availability, yearPublished
        );
    }

    @Override
    public String toString() {
        return String.format(
                ("id: %d, " +
                "Title: %s, " +
                "Year Published: %d, " +
                "Availability: %s, "), id, title, yearPublished, availability
        );
    }
}
