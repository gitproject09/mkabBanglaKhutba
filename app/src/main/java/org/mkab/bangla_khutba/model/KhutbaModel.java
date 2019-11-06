package org.mkab.bangla_khutba.model;

public class KhutbaModel {

    private String year = "year";
    private String month = "month";
    private String date= "date";
    private String title = "title";
    private String khutba_details = "khutba_details";

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKhutba_details() {
        return khutba_details;
    }

    public void setKhutba_details(String khutba_details) {
        this.khutba_details = khutba_details;
    }
}