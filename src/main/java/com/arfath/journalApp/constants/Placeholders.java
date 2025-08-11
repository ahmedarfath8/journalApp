package com.arfath.journalApp.constants;

public interface Placeholders {
    //this is best practice to store and re-use constants (instead of hardcoded placeholders like this "<city>","<apiKey>")

    String API_KEY = "<apiKey>";  // so whenever we replace Placeholder.API_KEY its actually replacing "<apiKey>"
    String CITY = "<city>"; //same thing as above
}
