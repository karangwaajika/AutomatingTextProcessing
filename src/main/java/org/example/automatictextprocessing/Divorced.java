package org.example.automatictextprocessing;

public class Divorced extends Woman {
    private String dateDivorced;

    public Divorced(String nationalId,int womanId, String name, int age, String maritalStatus,
                    boolean isEmployed, String dateDivorced) {
        super(nationalId, womanId, name, age, maritalStatus, isEmployed);
        this.dateDivorced = dateDivorced;
    }

    public void setDateDivorced(String dateDivorced) {
        this.dateDivorced = dateDivorced;
    }

    public String getDateDivorced() {
        return dateDivorced;
    }
}
