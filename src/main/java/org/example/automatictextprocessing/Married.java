package org.example.automatictextprocessing;

public class Married extends Woman {
    private String dateMarried;

    public Married(int womanId, String name, int age, String maritalStatus,
                   boolean isEmployed, String dateMarried) {
        super(womanId, name, age, maritalStatus, isEmployed);
        this.dateMarried = dateMarried;
    }

    public void setDateMarried(String dateMarried) {
        this.dateMarried = dateMarried;
    }

    public String getDateMarried() {
        return this.dateMarried;
    }
}
