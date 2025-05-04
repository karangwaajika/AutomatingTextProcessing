package org.example.automatictextprocessing;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.automatictextprocessing.exceptions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProcessData {
    private static final Logger logger = LogManager.getLogger(ProcessData.class);

    public static void main(String[] args) throws IOException {
        /*
         * DEALING WITH PROCESSING DATA FROM A TEXT INPUT
         */

        // ##### Search a string from a text ######
        System.out.println("##### Search a string from a text ######");
        ProcessText textProcessor = new ProcessText();
        String text = "sat on the cthedral and caught a cterpillar\nI have many things to do with my cat";
        String result = textProcessor.searchText(text, "\\bcat\\b", "text");
        System.out.println(result);

        // ##### Replace a string ######
        System.out.println("##### Replace a string ######");
        String text1 = "078 298 3266\n ajika Paul";
        System.out.println(textProcessor.replaceText(text, "\\bcat\\b", "dog", "text"));
        System.out.println(textProcessor.replaceText(text1, "\\s", "-", "text"));

        /*
         * DEALING WITH PROCESSING DATA FROM A FILE
         */
        // ##### Read text from a file ######
        System.out.println("##### Read text from a file ######");
        ProcessFile fileProcessor = new ProcessFile();

        try {
            String readText = fileProcessor
                    .readFromAFile("src/main/java/org/example/automatictextprocessing/file2.txt");
            System.out.println(readText);
        } catch (IOException | FileEmptyException e) {
            if (e instanceof FileNotFoundException) {
                logger.log(Level.ERROR, "File selected not found !!");
            } else {
                logger.log(Level.ERROR, e.getMessage());
            }
        }

        // ##### Write to a file ######
        System.out.println("##### Write to a file ######");

        try {
            String text3 = "This is what I always wanted!!";
            fileProcessor
                    .writeToFile("src/main/java/org/example/automatictextprocessing/file3.txt", text3);
            System.out.println("File has been written to successfully !!!");
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                logger.log(Level.ERROR, "File selected not found !!");
            } else {
                logger.log(Level.ERROR, e.getMessage());
            }
        }

        // ##### clean women data from a list #########
        try {
            String m = "1199786054927076 | Alliah Gorgeous | 26 | Single | false | false";
            String result1 = fileProcessor
                    .cleanData(m, "|", "text");
            System.out.println("Result: " + result1);
        } catch (NotEmptyDateDivorcedException | NotEmptyDateMarriedException | NotEmptyMaritalStatusException |
                 NotEmptyNameException | NotEmptySpouseDeathDateException | UnderAgeException | IOException |
                 InvalidMaritalStatusException | InvalidAgeException | InvalidBooleanException |
                 InvalidNameException | InvalidDateException | InvalidWomanDataException |
                 InvalidNationaldException e) {

            if (e instanceof FileNotFoundException) {
                logger.log(Level.ERROR, "File selected not found !!");
            } else {
                logger.log(Level.ERROR, e.getMessage());
            }
        }

    }
}
