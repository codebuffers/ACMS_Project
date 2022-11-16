package com.example.acms;


//this is the MAIN Model class which is used by all the adaters to retrieve user(visitor) data
public class Model {

    private String visitorimage;
    private String visitorname;
    private String visitoric;
    private String visitorphone;
    private String visitdate;
    private String visittime;
    private String visitexittime;
    private String visitorstatus;
    private String visitorticket;
    private String visitreason;
    private String statusreason;

    public Model() {

    }

    //constructor below
    public Model(String visitorimage, String visitorname,
                 String visitoric, String visitorphone,
                 String visitdate, String visittime, String visitexittime,
                 String visitorstatus, String visitorticket, String visitreason,
                 String statusreason) {
        this.visitorimage = visitorimage;
        this.visitorname = visitorname;
        this.visitoric = visitoric;
        this.visitorphone = visitorphone;
        this.visitdate = visitdate;
        this.visittime = visittime;
        this.visitexittime = visitexittime;
        this.visitorstatus = visitorstatus;
        this.visitorticket = visitorticket;
        this.visitreason = visitreason;
        this.statusreason = statusreason;
    }


    //getters & setters below
    public String getVisitorimage() {
        return visitorimage;
    }

    public void setVisitorimage(String visitorimage) {
        this.visitorimage = visitorimage;
    }

    public String getVisitorname() {
        return visitorname;
    }

    public void setVisitorname(String visitorname) {
        this.visitorname = visitorname;
    }

    public String getVisitoric() {
        return visitoric;
    }

    public void setVisitoric(String visitoric) {
        this.visitoric = visitoric;
    }

    public String getVisitorphone() {
        return visitorphone;
    }

    public void setVisitorphone(String visitorphone) {
        this.visitorphone = visitorphone;
    }

    public String getVisitdate() {
        return visitdate;
    }

    public void setVisitdate(String visitdate) {
        this.visitdate = visitdate;
    }

    public String getVisittime() {
        return visittime;
    }

    public void setVisittime(String visittime) {
        this.visittime = visittime;
    }

    public String getVisitexittime() {
        return visitexittime;
    }

    public void setVisitexittime(String visitexittime) {
        this.visitexittime = visitexittime;
    }

    public String getVisitorstatus() {
        return visitorstatus;
    }

    public void setVisitorstatus(String visitorstatus) {
        this.visitorstatus = visitorstatus;
    }

    public String getVisitorticket() {
        return visitorticket;
    }

    public void setVisitorticket(String visitorticket) {
        this.visitorticket = visitorticket;
    }

    public String getVisitreason() {
        return visitreason;
    }

    public void setVisitreason(String visitreason) {
        this.visitreason = visitreason;
    }

    public String getStatusreason() {
        return statusreason;
    }

    public void setStatusreason(String statusreason) {
        this.statusreason = statusreason;
    }
}
