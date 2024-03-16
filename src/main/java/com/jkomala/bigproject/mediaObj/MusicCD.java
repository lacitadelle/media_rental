package com.jkomala.bigproject.mediaObj;

import org.w3c.dom.Document;

import java.time.LocalDate;

public class MusicCD extends Media {
    int playtime; // in minutes

    public MusicCD(int id, int yearPublished, String title, int playtime, boolean availability) {
        super(id, yearPublished, title, availability);
        this.playtime = playtime;
    }

    public MusicCD(Document musicCdData) {
        super(musicCdData);
        playtime = Integer.parseInt(musicCdData.getElementsByTagName("playtime").item(0).getTextContent());
    }

    // getter and setter for local variable
    public int getPlaytime() {
        return playtime;
    }

    public void setPlaytime(int playtime) {
        this.playtime = playtime;
    }

    @Override
    public double calculateRentalFee() {
        int currentYear = LocalDate.now().getYear();
        double rentalFee = playtime * 0.02;

        if (this.getYearPublished() == currentYear) {
            rentalFee += 1.0;
        }
        return rentalFee;
    }

    @Override
    public String toXMLString() {
        return String.format(
                "<?xml version=\"1.0\" ?>\n" +
                "<MusicCD>\n" +
                super.toXMLString() +
                "\t<playtime>%s</playtime>\n" +
                "</MusicCD>", playtime);
    }

    @Override
    public String toString() {
        return ("Type: Music CD, " +
                super.toString() +
                "Playtime: " + playtime + " minutes");
    }
}
