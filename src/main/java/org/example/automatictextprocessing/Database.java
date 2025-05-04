package org.example.automatictextprocessing;

import org.example.automatictextprocessing.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {
    static final HashMap<Integer, Woman> women = new HashMap<>();

    public String addWoman(Integer womanId, Woman woman) {
        if (woman.maritalStatus.isEmpty()) {
            throw new NotEmptyMaritalStatusException("Marital status cannot be empty!!!");
        }
        long countWomen = women.values().stream()
                .filter(w -> w.getNationalId() == woman.getNationalId())
                .count();
        if (countWomen > 0) {
            throw new WomanExistException("Woman ID provided of " + woman.name + " exists already: "
                    + woman.getNationalId());
        }

        switch (woman.maritalStatus) {
            case "Married":
                Married m = (Married) woman;
                if (m.getDateMarried().isEmpty()) {
                    throw new NotEmptyDateMarriedException("Please provide date of marriage");
                }
                break;
            case "Widowed":
                Widowed w = (Widowed) woman;
                if (w.getSpouseDeathDate().isEmpty()) {
                    throw new NotEmptyMaritalStatusException("Please provide date of spouse death");
                }
                break;
            case "Divorced":
                Divorced d = (Divorced) woman;
                if (d.getDateDivorced().isEmpty()) {
                    throw new NotEmptyDateDivorcedException("Please provide date of divorced");
                }
                break;
            default:
                break;

        }
        if (woman.age <= 17) {
            throw new UnderAgeException("You must be above 17 !!!");
        }
        if (woman.name.isEmpty()) {
            throw new NotEmptyNameException("Name cannot be empty !!!");
        }
        if (!(woman.name.matches("^[a-zA-Z\\s]+$"))) {
            throw new InvalidNameException("Name is invalid !!!");
        }


        women.put(womanId, woman); // insert employee
        return "Woman's added successfully !!";
    }

    public ArrayList<Woman> getAllWomen() {
        return new ArrayList<>(women.values());
    }

    public int removeWoman(Integer womanId) {
        if (!(women.containsKey(womanId))) {
            throw new WomanNotFoundException("Woman ID provided doesn't exist: "
                    + womanId);
        }
        women.remove(womanId);
        return womanId;

    }

    public List<Woman> filterByMaritalStatus(String field) {
        List<Woman> womenList = women.values().stream()
                .filter(w -> w.getMaritalStatus().equalsIgnoreCase(field)).toList();
        if (womenList.isEmpty()) {
            throw new InvalidMaritalStatusException("No Marital status with the name '"
                    + field + "'");
        }

        return womenList;
    }


    public List<Woman> filterByName(String field) {
        List<Woman> womenList = women.values().stream()
                .filter(w -> w.getName().contains(field)).toList();
        if (womenList.isEmpty()) {
            throw new InvalidNameException("No woman with the name '"
                    + field + "'");
        }
        return womenList;
    }

    public ArrayList<Report> makeStatisticReport(){

        // general statistics
        int totalWomen = women.size();
        int totalWomenAge = women.values().stream()
                .map(n->n.getAge())
                .reduce(0, (age1, age2)-> age1 + age2);
        long unEmployed = women.values().stream()
                .filter(w-> !w.getIsEmployed())
                .count();
        long employed = women.values().stream()
                .filter(Woman::getIsEmployed)
                .count();

        long singleWomen = women.values().stream()
                .filter(w->w.getMaritalStatus().equalsIgnoreCase("Single"))
                .count();
        long marriedWomen = women.values().stream()
                .filter(w->w.getMaritalStatus().equalsIgnoreCase("Married"))
                .count();
        long widowedWomen = women.values().stream()
                .filter(w->w.getMaritalStatus().equalsIgnoreCase("Widowed"))
                .count();
        long divorcedWomen = women.values().stream()
                .filter(w->w.getMaritalStatus().equalsIgnoreCase("Divorced"))
                .count();


        //specific report
        ArrayList<Report> list = new ArrayList<>();
        Report single = specifiReport(singleWomen, "Single");
        Report married = specifiReport(marriedWomen, "Married");
        Report divorced = specifiReport(divorcedWomen, "Divorced");
        Report widowed = specifiReport(widowedWomen, "Widowed");

        list.add(single);
        list.add(married);
        list.add(divorced);
        list.add(widowed);
        
        return list;

    }

    public Report specifiReport(long number, String maritalStatus){
        // single statistics
        long employed = women.values().stream()
                .filter(w->
                        w.getMaritalStatus().equalsIgnoreCase(maritalStatus)
                                && w.getIsEmployed())
                .count();
        long unEmployed = women.values().stream()
                .filter(w->
                        w.getMaritalStatus().equalsIgnoreCase(maritalStatus)
                                && !w.getIsEmployed())
                .count();
        long under_30 = women.values().stream()
                .filter(w->
                        w.getMaritalStatus().equalsIgnoreCase(maritalStatus)
                                && w.getAge() < 30)
                .count();
        long above_30 = women.values().stream()
                .filter(w->
                        w.getMaritalStatus().equalsIgnoreCase(maritalStatus)
                                && w.getAge() >= 30)
                .count();
        return new Report(maritalStatus, number, employed, unEmployed, under_30, above_30);
    }

}
