package org.example.automatictextprocessing;

public class Single extends Woman {
    private boolean isInRelationship;

    public Single(String nationalId, int womanId, String name, int age, String maritalStatus,
                  boolean isEmployed, boolean isInRelationship) {
        super(nationalId, womanId, name, age, maritalStatus, isEmployed);
        this.isInRelationship = isInRelationship;
    }

    public void setRelationship(boolean inRelationship) {
        isInRelationship = inRelationship;
    }

    public boolean getRelationship() {
        return this.isInRelationship;
    }
}
