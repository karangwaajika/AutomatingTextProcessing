package org.example.automatictextprocessing;

import org.apache.logging.log4j.Level;
import org.example.automatictextprocessing.exceptions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        Database db = new Database();

        Woman w1 = new Single(Woman.womanNbr, "Jessica", 32, "Single",
                true, false);
        Woman w2 = new Single(Woman.womanNbr, "Jossee", 27, "Single",
                true, true);
        Woman w3 = new Married(Woman.womanNbr, "Solange", 12, "Married",
                false, "2018-04-12");
        Woman w4 = new Widowed(Woman.womanNbr, "Deborah", 65, "Widowed",
                false, "2024-07-12");
        Woman w5 = new Divorced(Woman.womanNbr, "Sandra", 38, "Divorced",
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

        // ################### View Women ##########################
        System.out.println("###### Women list ########");
        db.getAllWomen().forEach((woman) -> {
            System.out.printf("%d| name:%s, age: %d, marital_status: %s, Employed: %b\n",
                    woman.getWomanId(), woman.getName(), woman.getAge(), woman.getMaritalStatus(),
                    woman.isEmployed());
        });
    }
}
