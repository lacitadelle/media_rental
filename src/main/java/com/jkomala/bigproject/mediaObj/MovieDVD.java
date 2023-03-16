package com.jkomala.bigproject.mediaObj;

public class MovieDVD extends Media {
    double mediaDataSize; // in megabytes

    public MovieDVD(int id, int yearPublished, String title, double mediaDataSize) {
        super(id, yearPublished, title);
        this.mediaDataSize = mediaDataSize;
    }

    // getter and setter for local variables
    public double getMediaDataSize() {
        return mediaDataSize;
    }

    public void setMediaDataSize(double mediaDataSize) {
        this.mediaDataSize = mediaDataSize;
    }

    // instance methods
    @Override
    public double calculateRentalFee() {
        return super.calculateRentalFee();
    }

    @Override
    public String toString() {
        return String.format("<MovieDVD>\n" +
                "\t<id>%s</id>\n" +
                "\t<title>%s</title>\n" +
                "\t<yearPublished>%s</yearPublished>\n" +
                "\t<mediaDataSize>%s</mediaDataSize>\n" +
                "</MovieDVD>", this.getId(), this.getTitle(), this.getYearPublished(), this.getMediaDataSize());
    }

}