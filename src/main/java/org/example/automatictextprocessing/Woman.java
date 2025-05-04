package org.example.automatictextprocessing;

public class Woman {
    private final int womanId;
    protected String nationalId;
    protected String name;
    protected int age;
    protected String maritalStatus;
    protected boolean isEmployed;
    static int womanNbr = 1;

    public Woman(String nationalId, int womanId, String name, int age,
                 String maritalStatus, boolean isEmployed) {
        this.nationalId = nationalId;
        this.womanId = womanId;
        this.age = age;
        this.name = name;
        this.maritalStatus = maritalStatus;
        this.isEmployed = isEmployed;
        womanNbr++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWomanId() {
        return womanId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getNationalId() {
        return this.nationalId;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public boolean getIsEmployed() {
        return isEmployed;
    }

    public void setIsEmployed(boolean isEmployed) {
        this.isEmployed = isEmployed;
    }
}
