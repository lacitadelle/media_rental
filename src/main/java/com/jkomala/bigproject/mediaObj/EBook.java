package com.jkomala.bigproject.mediaObj;

import org.w3c.dom.Document;

import java.time.LocalDate;

public class EBook extends Media {
    private int chapters;

    // constructor
    public EBook(int id, int yearPublished, String title, int chapters) {
        super(id, yearPublished, title);
        this.chapters = chapters;
    }

    // constructor for reading from the file
    public EBook(Document ebookData) {
        super(ebookData);
        // retrieve the number of chapters
        chapters = Integer.parseInt(ebookData.getElementsByTagName("chapters").item(0).getTextContent());
    }

    // getter and setter for local variables
    public int getChapters() {
        return chapters;
    }

    public void setChapters(int chapters) {
        this.chapters = chapters;
    }

    @Override
    public double calculateRentalFee() {
        int currentYear = LocalDate.now().getYear();
        double rentalFee = super.calculateRentalFee() + (chapters * 0.10);
        if (this.getYearPublished() == currentYear) {
            rentalFee += 1.0; // add $1 for EBook title released in the current year
        }
        return rentalFee;
    }

    @Override
    public String toString() {
        return String.format("<EBook>\n" +
                "\t<id>%s</id>\n" +
                "\t<title>%s</title>\n" +
                "\t<yearPublished>%s</yearPublished>\n" +
                "\t<chapters>%s</chapters>\n" +
                "</EBook>", this.getId(), this.getTitle(), this.getYearPublished(), this.getChapters());
    }

}
