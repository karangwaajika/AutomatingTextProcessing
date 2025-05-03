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
            throw new WomanExistException("Woman ID provided exists already: "
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
        if (!(woman.name.matches("[a-zA-Z ]"))) {
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
                .filter(w -> w.getMaritalStatus().equals(field)).toList();
        if (womenList.isEmpty()) {
            throw new InvalidMaritalStatusException("No Marital status with the name '"
                    + field + "'");
        }

        return womenList;
    }

}
