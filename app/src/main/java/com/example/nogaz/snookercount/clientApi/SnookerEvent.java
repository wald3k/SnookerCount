package com.example.nogaz.snookercount.clientApi;

/**
 * Created by Nogaz on 15.02.2017.
 */

public class SnookerEvent {
    private String eventStartDate;
    private String eventName;
    private String eventPlaceCountry;
    private String eventPlaceCity;

    private String eventStartDateDay;
    private String eventStartDateMonth;
    private String eventStartDateYear;


    public SnookerEvent(){

    }

    public String getEventPlace() {
        return eventPlaceCity + ", " + eventPlaceCountry;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
        String[] date= eventStartDate.split("-");
        eventStartDateYear = date[0];
        eventStartDateMonth = date[1];
        eventStartDateDay = date[2];
    }
    public String getMonth(){
        return this.eventStartDateMonth;
    }
    public String getYear(){
        return this.eventStartDateYear;
    }
    public String getDay(){
        return this.eventStartDateDay;
    }
    public String getMonth(String month){
        switch (month){
            case "01":
                return "JAN";
            case "02":
                return "FEB";
            case "03":
                return "MAR";
            case "04":
                return "APR";
            case "05":
                return "MAY";
            case "06":
                return "JUN";
            case "07":
                return "JUL";
            case "08":
                return "AUG";
            case "09":
                return  "SEP";
            case "10":
                return "OCT";
            case "11":
                return "NOV";
            case "12":
                return "DEC";
            default:
                return "?";
        }
    }

    public String getEventPlaceCountry() {
        return eventPlaceCountry;
    }

    public void setEventPlaceCountry(String eventPlaceCountry) {
        this.eventPlaceCountry = eventPlaceCountry;
    }

    public String getEventPlaceCity() {
        return eventPlaceCity;
    }

    public void setEventPlaceCity(String eventPlaceCity) {
        this.eventPlaceCity = eventPlaceCity;
    }

    @Override
    public String toString() {
        String string = eventName + " : " + eventStartDate + " : " + getEventPlace() + "\n";
        return string;
    }

}
