package com.jkomala.bigproject.mediaObj;

import org.w3c.dom.Document;

abstract public class Media {
    private int id;
    private int yearPublished;
    private String title;

    // constructor
    public Media(int id, int yearPublished, String title) {
        this.id = id;
        this.yearPublished = yearPublished;
        this.title = title;
    }

    // constructor for reading from files
    public Media(Document mediaFile) {
        id = Integer.parseInt(mediaFile.getElementsByTagName("id").item(0).getTextContent());
        yearPublished = Integer.parseInt(mediaFile.getElementsByTagName("yearPublished").item(0).getTextContent());
        title = mediaFile.getElementsByTagName("title").item(0).getTextContent();
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

    // instance methods
    public double calculateRentalFee() {
        return 3.5;
    }

}
