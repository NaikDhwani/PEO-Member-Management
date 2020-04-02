package com.cosc592.peomembers;

public class CommitteeManagement {
    private String title, description, date, name;
    private int institute_id, committee_id;

    public CommitteeManagement(int institute_id, String title, String description, String date){
        this.institute_id = institute_id;
        this.title = String.valueOf(title.charAt(0)).toUpperCase() + title.substring(1);
        this.description = description;
        this.date = date;
    }

    public CommitteeManagement(int committee_id, String name, String title){
        this.committee_id = committee_id;
        this.name = name;
        this.title = String.valueOf(title.charAt(0)).toUpperCase() + title.substring(1);
    }

    public CommitteeManagement(int committee_id, String description){
        this.committee_id = committee_id;
        this.description = description;
    }

    public int getCommittee_id() {
        return committee_id;
    }

    public String getInstitute_name() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getInstitute_id() {
        return institute_id;
    }
}
