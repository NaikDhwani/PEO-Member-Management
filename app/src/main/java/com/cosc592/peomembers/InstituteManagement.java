package com.cosc592.peomembers;

public class InstituteManagement {

    private String name, address, email, contact_number;
    private int institute_id;

    public InstituteManagement(int institute_id, String name, String address, String contact_number, String email){
        this.institute_id = institute_id;
        this.name = String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1);
        this.address = address;
        this.contact_number = contact_number;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public int getInstitute_id() {
        return institute_id;
    }

    public String getContact_number() {
        return contact_number;
    }
}
