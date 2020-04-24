package com.example.crimereporter.ui.view;

public class ReportView {
    private String id,address, city, pincode, subject, details, status;

    public ReportView() {
    }

    public ReportView(String id, String address, String city, String pincode, String subject, String details, String status) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.pincode = pincode;
        this.subject = subject;
        this.details = details;
        this.status = status;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
