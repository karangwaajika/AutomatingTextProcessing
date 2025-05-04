package org.example.automatictextprocessing;

import java.util.Comparator;

public class SortAgeAscending implements Comparator<Woman> {
    @Override
    public int compare(Woman o1, Woman o2) {
        if (o1.getAge() > o2.getAge()) {
            return 1;
        } else {
            return -1;
        }
    }
}