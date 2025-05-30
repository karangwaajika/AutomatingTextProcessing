package org.example.automatictextprocessing;

import org.apache.logging.log4j.Level;
import org.example.automatictextprocessing.exceptions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        Database db = new Database();

        Woman w1 = new Single("1299783054905076", Woman.womanNbr, "Jessica", 32, "Single",
                true, false);
        Woman w2 = new Single("1399783054905076", Woman.womanNbr, "Jossee", 27, "Single",
                true, true);
        Woman w3 = new Married("1499783054905076", Woman.womanNbr, "Solange", 42, "Married",
                false, "2018-04-12");
        Woman w4 = new Widowed("1599783054905076", Woman.womanNbr, "Deborah", 65, "Widowed",
                false, "2024-07-12");
        Woman w5 = new Divorced("1699783054905076", Woman.womanNbr, "Sandra", 38, "Divorced",
                false, "2025-01-02");

        try {
            db.addWoman(w1.getWomanId(), w1);
            db.addWoman(w2.getWomanId(), w2);
            db.addWoman(w3.getWomanId(), w3);
            db.addWoman(w4.getWomanId(), w4);
            db.addWoman(w5.getWomanId(), w5);
        } catch (NotEmptyDateDivorcedException | NotEmptyDateMarriedException |
                 NotEmptyMaritalStatusException | NotEmptyNameException |
                 NotEmptySpouseDeathDateException | UnderAgeException e) {

            logger.log(Level.ERROR, e.getMessage());
        }


        // Delete woman
        try {
            db.removeWoman(6);
        } catch (WomanNotFoundException e) {
            logger.log(Level.ERROR, e.getMessage());
        }

        // Filter by Marital status
        try {
            List<Woman> womenList = db.filterByName("So");
            System.out.println("##### Filtered ######");
            womenList.forEach((woman) -> {
                System.out.printf("%d| name:%s, age: %d, marital_status: %s, Employed: %b\n",
                        woman.getWomanId(), woman.getName(), woman.getAge(), woman.getMaritalStatus(),
                        woman.getIsEmployed());
            });

        } catch (InvalidMaritalStatusException e) {
            logger.log(Level.ERROR, e.getMessage());
        }

        // ################### View Women ##########################
        System.out.println("###### Women list ########");
        db.getAllWomen().forEach((woman) -> {
            System.out.printf("%d| name:%s, age: %d, marital_status: %s, Employed: %b\n",
                    woman.getWomanId(), woman.getName(), woman.getAge(), woman.getMaritalStatus(),
                    woman.getIsEmployed());
        });

        // ################### View Statistics ##########################
        System.out.println("###### Women Statistics ########");
        db.makeStatisticReport().forEach((woman) -> {
            System.out.printf(" Marital status:%s, Number: %d, Employed: %d, UnEmployed: %d, " +
                    "Under_30: %d, Above_30: %d\n",woman.getMaritalStatus(), woman.getNumber(),
                    woman.getEmployed(), woman.getUnEmployed(), woman.getAbove_30(), woman.getAbove_30());
        });
    }
}
