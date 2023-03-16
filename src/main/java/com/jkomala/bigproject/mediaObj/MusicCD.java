package com.jkomala.bigproject.mediaObj;

import org.w3c.dom.Document;

import java.time.LocalDate;

public class MusicCD extends Media {
    int playtime; // in minutes

    public MusicCD(int id, int yearPublished, String title, int playtime) {
        super(id, yearPublished, title);
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
    public String toString() {
        return String.format("<MusicCD>\n" +
                "\t<id>%s</id>\n" +
                "\t<title>%s</title>\n" +
                "\t<yearPublished>%s</yearPublished>\n" +
                "\t<playtime>%s</playtime>\n" +
                "</MusicCD>", this.getId(), this.getTitle(), this.getYearPublished(), this.getPlaytime());
    }

}
