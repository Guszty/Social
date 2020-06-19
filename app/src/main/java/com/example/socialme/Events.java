package com.example.socialme;

public class Events {
    String title;
    String location;
    String description;
    String date;

    public Events(){}

    /*public Events(String _title, String _date, String _description, String _location)
    {
        title = _title;
        date = _date;
        description = _description;
        location = _location;
    }*/
    public String getTitle(){return title;}
    public void setTitle(String _title){ title = _title;}
    public String getDate(){return date;}
    public void setDate(String _date){ date = _date;}
    public String getDescription(){return description;}
    public void setDescription(String _description){ description = _description;}
    public String getLocation(){return location;}
    public void setLocation(String _location){ location = _location;}
    /*public String toString(){return title + " " + date + " " + description + " " + location;}*/
}
