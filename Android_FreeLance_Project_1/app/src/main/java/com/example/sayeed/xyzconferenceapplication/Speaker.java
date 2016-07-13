package com.example.sayeed.xyzconferenceapplication;

public class Speaker {

    private String first, last, affiliation, email, bio;
    private boolean verbose;

    public Speaker (String first, String last, String affiliation, String email, String bio) {
        this.first = first;
        this.last = last;
        this.affiliation = affiliation;
        this.email = email;
        this.bio = bio;
    }

    @Override
    public String toString () {
        return verbose ?
                (first + " " + last + " from " + affiliation + ". Email: " + email + ". Bio: " + bio)
                :
                (first + " " + last);
    }

    public void setVerbose (boolean verbose) {
        this.verbose = verbose;
    }

    public boolean verbose () {
        return verbose;
    }
}