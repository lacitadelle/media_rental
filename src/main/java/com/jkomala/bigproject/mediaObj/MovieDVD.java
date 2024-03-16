package com.jkomala.bigproject.mediaObj;

import org.w3c.dom.Document;

public class MovieDVD extends Media {
    private double mediaDataSize; // in megabytes

    public MovieDVD(int id, int yearPublished, String title, double mediaDataSize, boolean availability) {
        super(id, yearPublished, title, availability);
        this.mediaDataSize = mediaDataSize;
    }

    public MovieDVD(Document dvdData) {
        super(dvdData);
        mediaDataSize = Double.parseDouble(dvdData.getElementsByTagName("mediaDataSize").item(0).getTextContent());
    }

    // getter and setter for local variables
    public double getMediaDataSize() {
        return mediaDataSize;
    }

    public void setMediaDataSize(double mediaDataSize) {
        this.mediaDataSize = mediaDataSize;
    }


    @Override
    public String toXMLString() {
        return String.format(
                "<?xml version=\"1.0\" ?>\n" +
                "<MovieDVD>\n" +
                super.toXMLString() +
                "\t<mediaDataSize>%s</mediaDataSize>\n" +
                "</MovieDVD>", mediaDataSize);
    }

    @Override
    public String toString() {
        return  (
                "Type: Movie DVD, " +
                super.toString() +
                "Media Size: " + mediaDataSize + "MB"
        );
    }
}
