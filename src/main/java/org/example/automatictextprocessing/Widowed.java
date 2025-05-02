package org.example.automatictextprocessing;

public class Widowed extends Woman {
    private String spouseDeathDate;

    public Widowed(int womanId, String name, int age, String maritalStatus,
                   boolean isEmployed, String spouseDeathDate) {
        super(womanId, name, age, maritalStatus, isEmployed);
        this.spouseDeathDate = spouseDeathDate;
    }

    public String getSpouseDeathDate() {
        return spouseDeathDate;
    }

    public void setSpouseDeathDate(String spouseDeathDate) {
        this.spouseDeathDate = spouseDeathDate;
    }
}
