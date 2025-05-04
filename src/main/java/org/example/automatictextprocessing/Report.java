package org.example.automatictextprocessing;

public class Report {
    protected String maritalStatus;
    protected long number;
    protected long employed;
    protected long unEmployed;
    protected long under_30;
    protected long above_30;

    public Report(String maritalStatus, long number, long employed, long unEmployed,
                  long under_30, long above_30){
        this.maritalStatus = maritalStatus;
        this.number = number;
        this.employed = employed;
        this.unEmployed = unEmployed;
        this.under_30 = under_30;
        this.above_30 = above_30;
    }

    public long getEmployed() {
        return employed;
    }

    public void setUnEmployed(long unEmployed) {
        this.unEmployed = unEmployed;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public long getNumber() {
        return number;
    }

    public long getAbove_30() {
        return above_30;
    }

    public long getUnder_30() {
        return under_30;
    }

    public long getUnEmployed() {
        return unEmployed;
    }

    public void setAbove_30(long above_30) {
        this.above_30 = above_30;
    }

    public void setEmployed(long employed) {
        this.employed = employed;
    }

    public void setUnder_30(long under_30) {
        this.under_30 = under_30;
    }

    public void setNumber(long number) {
        this.number = number;
    }

}
