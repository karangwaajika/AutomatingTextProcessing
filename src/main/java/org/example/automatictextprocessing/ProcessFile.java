package org.example.automatictextprocessing;

import org.example.automatictextprocessing.exceptions.*;

import java.io.*;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ProcessFile {
    public String readFromAFile(String path) throws IOException {

        FileReader fr = null;
        fr = new FileReader(path);
        BufferedReader reader = new BufferedReader(fr);
        String line;
        String result = "";

        while ((line = reader.readLine()) != null) {
            result += line + "\n";
        }
        fr.close();
        reader.close();
        return result;

    }

    public void writeToFile(String path, String text) throws IOException {

        FileWriter fw = null;
        fw = new FileWriter(path, true);

        BufferedWriter writer = new BufferedWriter(fw);
        writer.write(text);
        writer.newLine();

        writer.close();
        fw.close();

    }

    public String cleanData(String data, String separator, String typeOfData) throws IOException {
        BufferedReader reader = null;
        if(typeOfData.equals("text")){
            reader = new BufferedReader(new StringReader(data));
        }else{
            reader = new BufferedReader(new FileReader(data));
        }

        String cleanedData = clean(reader, separator);
        reader.close();

        return cleanedData;

    }

    private String clean(BufferedReader reader, String separator) throws IOException {
        String line;
        String formatedData = "";

        int countLine = 0;
        while ((line = reader.readLine()) != null) {
            countLine++;
            String[] womanData = new String[6];
            womanData = line.split(separator);
            if (line.split(separator).length != 6) {
                throw new InvalidWomanDataException("Woman information is missing some fields at line " + countLine);
            }

            String nationalId = womanData[0].trim();
            if (nationalId.length() != 16 || !(Pattern.matches("\\d+", nationalId))) {
                throw new InvalidNationaldException("Invalid national ID at line " + countLine);
            }

            String name = womanData[1].trim();
            if (!(Pattern.matches("^[a-zA-Z\\s]+$", name))) {
                throw new InvalidNameException("Invalid name at line " + countLine);
            }

            String age = womanData[2].trim();
            if (!(Pattern.matches("\\d+", age))) {
                throw new InvalidAgeException("Invalid age at line " + countLine);
            }

            String maritalStatus = womanData[3].trim();
            if (!(maritalStatus.matches("^[a-zA-Z\\s]+$"))) {
                throw new InvalidMaritalStatusException("Invalid marital status at line " + countLine);
            }

            String isEmployed = womanData[4].trim().toLowerCase();
            if (!(isEmployed.matches("false|true"))) {
                throw new InvalidBooleanException("Please provide a boolean string at line " + countLine);
            }

            String lastField = womanData[5].trim();
            if (maritalStatus.equalsIgnoreCase("Single")) {
                if (!(lastField.matches("false|true"))) {
                    throw new InvalidBooleanException("Please provide a boolean string at line " + countLine);
                }
            } else {

                long countDatePart = Arrays.stream(lastField.split("-"))
                        .filter(n -> Pattern.matches("\\d+", n))
                        .count();
                if (countDatePart != 3) {
                    throw new InvalidDateException("Please follow 2022-12-20 format, date is invalid at "
                            + countLine);
                }

            }
            // display in a readable format
            String lastFieldName;
            if(maritalStatus.equalsIgnoreCase("Single")){
                lastFieldName = "InRelationship";
            } else if (maritalStatus.equalsIgnoreCase("Married")) {
                lastFieldName = "Married date";
            }else if (maritalStatus.equalsIgnoreCase("Divorced")) {
                lastFieldName = "Divorced date";
            }else{
                lastFieldName = "Spouse Death date";
            }
            formatedData += String.format("%d. ID: %s, Name: %s, Age: %s, Marital status: %s, " +
                    "Employed: %s, %s: %s\n", countLine ,nationalId, name, age, maritalStatus, isEmployed,
                    lastFieldName, lastField);

        }
        return formatedData;
    }


}
